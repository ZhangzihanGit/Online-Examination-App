package db;

import domain.Instructor;
import domain.Student;
import domain.Subject;
import domain.UserType;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentMapper {
    private static final Logger logger = LogManager.getLogger(StudentMapper.class);

    public static Student loadWithId(Integer id) {
        // need to non-null check 
        return null;
    }

    public static List<Student> loadStudentsBySubject(int subjectId) {
        String sql = "SELECT * FROM exam.users as u INNER JOIN exam.student_subject_relation as s ON" +
                " u.id = s.studentid WHERE s.subjectid = ?";
        List<Student> students = new ArrayList<>();
        try {
            PreparedStatement statement = DBConnection.prepare(sql);
            statement.setInt(1, subjectId);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()) {
                int userId = resultSet.getInt("id");
                String userName = resultSet.getString("username");
                String showName = resultSet.getString("show_name");
                String userType = resultSet.getString("users_type");
                List<Subject> subjects =   SubjectMapper.loadStudentSubjects(userId);

                Student student = new Student(userId, subjects, false, userName,
                        UserType.valueOf(userType.toUpperCase()), showName);
                students.add(student);
                logger.info(userName + " " + userId);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }

        return students;
    }
}
