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
