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
        String sql = "SELECT * FROM exam.users WHERE id = ?";
        logger.info("id: " + id);
        Student student = null;
        try {
            PreparedStatement statement = DBConnection.prepare(sql);
            statement.setInt(1,id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                student = StudentMapper.load(resultSet);
            }
            return student;
        }catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return null;
    }

    private static Student load(ResultSet resultSet) {
        Student student = null;
        try {
            int userId = resultSet.getInt("id");
            String userName = resultSet.getString("username");
            String showName = resultSet.getString("show_name");
            String userType = resultSet.getString("users_type");
            boolean isInExam = resultSet.getBoolean("is_in_exam");
            List<Subject> subjects =  SubjectMapper.loadStudentSubjects(userId);

            student = new Student(userId, subjects, isInExam, userName,
                    UserType.valueOf(userType.toUpperCase()), showName);
            logger.info("======== " + userName + " " + userId);
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return student;
    }

    public static boolean updateStatus(Student student) {
        String sql = "UPDATE exam.users SET" +
                " is_in_exam = ? WHERE id = ?";
        int id = student.getUserId();
        boolean isInExam = student.getInExam();
        logger.info("Student is in exam? " + isInExam);

        PreparedStatement statement = null;
        try {
            statement = DBConnection.prepare(sql);
            statement.setBoolean(1, isInExam);
            statement.setInt(2,id);
            // update SQL should use executeUpdate instead of executeQuery
            statement.executeUpdate();

            return true;
        } catch (SQLException e) {
            logger.error(e.getMessage());
            return false;
        }
    }

    public static List<Student> loadAllStudents() {
        String sql = "SELECT * FROM exam.users WHERE users_type = 'student'";
        List<Student> students = new ArrayList<>();
        try {
            PreparedStatement statement = DBConnection.prepare(sql);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()) {
                int userId = resultSet.getInt("id");
                String userName = resultSet.getString("username");

                Student student = StudentMapper.load(resultSet);
                students.add(student);
                logger.info("======== " + userName + " " + userId);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }

        return students;
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

//                Student student = new Student(userId, subjects, false, userName,
//                        UserType.valueOf(userType.toUpperCase()), showName);
                Student student = StudentMapper.load(resultSet);
                students.add(student);
                logger.info(userName + " " + userId);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return students;
    }
}
