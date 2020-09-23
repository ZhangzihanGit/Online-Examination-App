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


    public static Exam getExam(int userId, int subjectId) {
        String sql = "SELECT e.description, e.subjectId FROM exam.exam as e INNER JOIN exam.subject AS s ON e.subjectId " +
                "= s.id INNER JOIN exam.student_subject_relation AS n ON s.id = n.subjectId" +
                " INNER JOIN exam.users as u ON u.id = n.studentId WHERE u.id=? and " +
                "s.id = ?";
        try {
            PreparedStatement preparedStatement = DBConnection.prepare(sql);
            preparedStatement.setInt(1,userId);
            preparedStatement.setInt(2,subjectId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                logger.info("Hello " + resultSet.getString("description") + resultSet.getString("subjectId"));
            }
        } catch (SQLException e) {
            logger.error(e.toString());
        }

        return null;
    }
    public static void addExam(Exam exam) {
        String sql = "INSERT INTO exam.exam (show_name, subjectId, description,isPublished)" +
                "VALUES (?,?,?,?) RETURNING id";
        PreparedStatement statement = null;
        int examId=0;
        try {
            statement = DBConnection.prepare(sql);
            // TODO： 这个字段是否需要不确定，现在写死了是因为db schema要求该字段not null
            statement.setString(1, "exam");
            statement.setInt(2,exam.getSubjectId());
            statement.setString(3,exam.getDescription());
            statement.setBoolean(4,false);

            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()) {
                examId = resultSet.getInt("id");
            }
            // Check if adding successfully
            if (examId !=0 ) {
                exam.setId(examId);
            }
            logger.info("the serial id is: " + examId);
        } catch (SQLException e) {
            logger.error(e.toString());
        }
    }

    public static Exam loadWithId(Integer id) {
        String sql = "SELECT * FROM exam.exam WHERE id = ?";
        Exam exam = new Exam();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = DBConnection.prepare(sql);
            preparedStatement.setInt(1,id);
            IdentityMap<Exam> map = IdentityMap.getInstance(Exam.class);
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
            int subjectId = resultSet.getInt("subjectId");
            int id = resultSet.getInt("id");
            boolean isPublished = resultSet.getBoolean("isPublished");
            List<Question> questions = new ArrayList<>();
            questions = QuestionMapper.loadQuestionsFromExamId(id);
            exam = new Exam(id,subjectId,description,questions,isPublished);
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return exam;
    }
}
