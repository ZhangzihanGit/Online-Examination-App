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

    /**
     * Add a question to DB.
     * @param question Question domain object
     * @return id of the created object
     */
    public static void  addQuestion(Question question) {
        String sql = "INSERT INTO exam.question (examid, question_type, " +
                "description,mark, options) RETURNING id";
        int questionId = 0;
        int examId =question.getExamId();
        String description = question.getDescription();
        int mark = question.getMark();
        String options = question.getOptions();
        QuestionType type = question.getQuestionType();

        try {
            PreparedStatement statement = DBConnection.prepare(sql);
            statement.setInt(1,examId);
            statement.setString(2,type.toString().toLowerCase());
            statement.setString(3,description);
            statement.setInt(4,mark);
            statement.setString(5,options);

            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()) {
                questionId = resultSet.getInt("id");
                if(questionId!=0) {
                    logger.info("questions id is: " + questionId);
                    question.setQuestionID(questionId);
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
            QuestionType questionType = QuestionType.valueOf(resultSet.
                    getString("question_type"));
            // TODO: comma separated string. Current DB schema needs a fix on Options.
            String options = resultSet.getString("options");
            IdentityMap<Question> map = IdentityMap.getInstance(Question.class);

            question = map.get(id);
            if (question == null) {
                question = new Question(id,description,options,questionType);
            }

        }catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return question;
    }

    public static Question loadWithId(Integer id) {
        // need a non-null assertion here.
        return null;
    }
}
