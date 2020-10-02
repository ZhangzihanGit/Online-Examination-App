package db;

import domain.Answer;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AnswerMapper {
    private final static Logger logger = LogManager.getLogger(AnswerMapper.class);

    public static void updateAnswer(Answer answer) {
        String sql = "UPDATE exam.answer SET " +
                " mark =? WHERE id = ?";
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = DBConnection.prepare(sql);
            preparedStatement.setInt(1,answer.getMark());
            preparedStatement.setInt(2,answer.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            logger.info("the answer is updated with mark: " + answer.getMark());
        } catch (SQLException e){
            logger.error(e.getMessage());
        }
    }

    public static List<Answer> loadAnswers(int submissionId) {
        String sql = "SELECT * FROM exam.answer WHERE submissionid = ?";
        PreparedStatement statement = null;
        List<Answer> answers = new ArrayList<>();

        try {
            statement =DBConnection.prepare(sql);
            statement.setInt(1,submissionId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                answers.add(AnswerMapper.load(resultSet));
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return answers;
    }

    public static Answer loadWithId(Integer id) {
        String sql = "SELECT * FROM exam.answer WHERE id = ?";
        PreparedStatement statement = null;
        Answer answer = null;
        try {
            statement = DBConnection.prepare(sql);
            statement.setInt(1,id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                answer = AnswerMapper.load(resultSet);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return answer;
    }

    public static void addAnswer(Answer answer) {
        String sql = "INSERT INTO exam.answer (submissionid, " +
                "questionid, content) VALUES (?,?,?) RETURNING id";
        int submissionId = answer.getSubmissionId();
        int questionId = answer.getQuestionId();
        String content = answer.getContent();
        int answerId = 0;

        PreparedStatement statement = DBConnection.prepare(sql);
        try {
            statement.setInt(1,submissionId);
            statement.setInt(2,questionId);
            statement.setString(3,content);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                answerId = resultSet.getInt("id");
                if (answerId !=0) {
                    logger.info("new answer is added. id is " + answerId);
                    answer.setId(answerId);
                }
            }
        } catch (SQLException e) {
            logger.info(e.getMessage());
        }
    }

    private static Answer load(ResultSet resultSet) {
        Answer answer = null;
        try {
            int questionId = resultSet.getInt("questionid");
            int submissionId = resultSet.getInt("submissionid");
            String content = resultSet.getString("content");
            int id = resultSet.getInt("id");
            int mark = resultSet.getInt("mark");

            answer = new Answer(id,questionId,content,submissionId,mark);

        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return  answer;
    }
}
