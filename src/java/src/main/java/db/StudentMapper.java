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
import java.util.*;

public class StudentMapper {
    private static final Logger logger = LogManager.getLogger(StudentMapper.class);


    public static Map<Integer, List<Integer>> loadTakingExams() {
        Map<Integer, List<Integer>> studentExamMap = new HashMap<>();
        String sql = "SELECT id,taking_exams FROM exam.users WHERE users_type='student'";
        PreparedStatement statement = null;
        List<Integer> takingExamList = new ArrayList<>();
        try {
            statement = DBConnection.prepare(sql);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String examList = resultSet.getString("taking_exams");
                if (examList!=null && !examList.isEmpty()) {
                    logger.info("EXAMLISST : " +examList);
                    takingExamList = convertFromStringToInt(examList);
                }
                int id = resultSet.getInt("id");
                studentExamMap.put(id, takingExamList);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return studentExamMap;
    }

    private static List<Integer> convertFromStringToInt(String examList) {
        String replace = examList.replace("[","");
        String replace1 = replace.replace("]","");
        List<String> arrayList = new ArrayList<String>
                (Arrays.asList(replace1.split(",")));
        List<Integer> takingExamList = new ArrayList<Integer>();
        for(String fav:arrayList){
            takingExamList.add(Integer.parseInt(fav.trim()));
            logger.info(Integer.parseInt(fav.trim()));
        }
        return takingExamList;
    }

    public static void updateTakingExam(Integer id, List<Integer> takingExamList) {
        String sql = "UPDATE exam.users SET taking_exams =?  WHERE id = ?";
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = DBConnection.prepare(sql);
            logger.info("New taking examlist added : " +takingExamList.toString());
            preparedStatement.setString(1,takingExamList.toString());
            preparedStatement.setInt(2,id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
    }

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

                Student student = StudentMapper.load(resultSet);
                students.add(student);
                logger.info("here: " + userName + " " + userId);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return students;
    }
}
