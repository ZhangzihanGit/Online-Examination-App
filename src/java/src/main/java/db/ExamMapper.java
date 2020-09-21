package db;

import domain.Exam;
import domain.Question;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ExamMapper {
    private static final Logger logger = LogManager.getLogger(ExamMapper.class);
    public static Exam loadWithId(Integer id) {
        String sql = "SELECT * FROM exam.exam WHERE id = ?";
        Exam exam = new Exam();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = DBConnection.prepare(sql);
            preparedStatement.setInt(1,id);
            IdentityMap<Exam> map = IdentityMap.getInstance(exam);
            exam = map.get(id);
            if (exam == null) {
                exam = ExamMapper.load(preparedStatement.executeQuery());
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return exam;
    }

    private static Exam load(ResultSet resultSet) {
        Exam exam = null;
        try {
            String showName = resultSet.getString("show_name");
            String description = resultSet.getString("description");
//            int subjectId = resultSet.getInt("subjectId");
            int id = resultSet.getInt("id");
            boolean isPublished = resultSet.getBoolean("isPublished");
            List<Question> questions = new ArrayList<>();
            questions = QuestionMapper.loadQuestionsFromExamId(id);
            exam = new Exam(id,showName,description,questions,isPublished);
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return exam;
    }
}
