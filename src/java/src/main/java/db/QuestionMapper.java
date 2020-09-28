package db;

import domain.Question;
import domain.QuestionType;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class QuestionMapper {
    private static final Logger logger = LogManager.getLogger(QuestionMapper.class);

    public static void deleteQuestion(Question question) {
        String sql = "DELETE FROM exam.question " +
                " WHERE id=?";
        int id = question.getQuestionId();
        PreparedStatement statement = null;
        try {
            statement = DBConnection.prepare(sql);
            statement.setInt(1,id);
            ResultSet resultSet = statement.executeQuery();
            // TODO: future error handling may need?
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
    }

    public static void updateQuestion(Question question) {
        String sql = "UPDATE exam.question SET " +
                "examId=?, question_type=?::questiontype, description=?, mark=?, options=? WHERE id = ?";
        PreparedStatement preparedStatement = DBConnection.prepare(sql);
        try {
            preparedStatement.setInt(1,question.getExamId());
            preparedStatement.setString(2,question.getQuestionType().toString().toLowerCase());
            preparedStatement.setString(3,question.getDescription());
            preparedStatement.setInt(4,question.getMark());
            preparedStatement.setInt(6,question.getQuestionId());
            if (question.getOptions() !=null) {
                preparedStatement.setString(5,question.getOptions());
            }
            else {
                preparedStatement.setString(5,null);
            }
            ResultSet resultSet = preparedStatement.executeQuery();
            logger.info("the question is updated, id is " + question.getQuestionId());
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
    }
    /**
     * Add a question to DB.
     * @param question Question domain object
     * @return id of the created object
     */
    public static void  addQuestion(Question question) {
        String sql = "INSERT INTO exam.question (examid, question_type, " +
                "description,mark, options) VALUES(?,?::questiontype,?,?,?) RETURNING id";
        int questionId = 0;
        int examId =question.getExamId();
        String description = question.getDescription();
        int mark = question.getMark();
        String options = question.getOptions();
        QuestionType type = question.getQuestionType();

        try {
            logger.info("Hello");
            PreparedStatement statement = DBConnection.prepare(sql);
            statement.setInt(1,examId);
//            statement.set
            statement.setString(2,type.toString().toLowerCase());
            statement.setString(3,description);
            statement.setInt(4,mark);
            statement.setString(5,options);

            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()) {
                questionId = resultSet.getInt("id");
                if(questionId!=0) {
                    logger.info("questions id is: " + questionId);
                    question.setQuestionId(questionId);
                }
            }
        }catch (SQLException e) {
            logger.error(e.toString());
        }
    }

    public static List<Question> loadQuestionsFromExamId(int examid) {
        String sql = "SELECT * FROM exam.question WHERE examId = ?";
        PreparedStatement preparedStatement = null;
        List<Question> questions = new ArrayList<Question>();
        try {
            Question question = new Question();
            preparedStatement = DBConnection.prepare(sql);
            preparedStatement.setInt(1,examid);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                questions.add(QuestionMapper.load(resultSet));
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return questions;
    }

    private static Question load(ResultSet resultSet) {
        Question question = new Question();
        try {
            int id = resultSet.getInt("id");
            String description = resultSet.getString("description");
            int examId = resultSet.getInt("examid");
            int mark = resultSet.getInt("mark");
            QuestionType questionType = QuestionType.valueOf(resultSet.
                    getString("question_type").toUpperCase());
            // TODO: comma separated string. Current DB schema needs a fix on Options.
            String options = resultSet.getString("options");
                logger.info("create new question object here ");
                question = new Question(id,description,options,questionType,examId,mark);

        }catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return question;
    }

    public static Question loadWithId(Integer id) {
        Question question = new Question();
        String sql = "SELECT * FROM exam.question WHERE id = ?";
        PreparedStatement preparedStatement = DBConnection.prepare(sql);
        try {
            preparedStatement.setInt(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                question = QuestionMapper.load(resultSet);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return question;
    }
}
