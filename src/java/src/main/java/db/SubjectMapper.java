package db;

import domain.Exam;
import domain.Instructor;
import domain.Student;
import domain.Subject;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SubjectMapper {
    private static final Logger logger = LogManager.getLogger(SubjectMapper.class);
    public static Subject loadWithId(int id) {
        String sql = "SELECT * FROM exam.subject WHERE id = ?";
        PreparedStatement statement = null;
        Subject subject = null;
        try {
            statement = DBConnection.prepare(sql);
            statement.setInt(1,id);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                subject = SubjectMapper.load(resultSet);
            }
        }catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return subject;
    }

    public static void updateStudentSubject(Subject subject, Student student) {

    }


    public static void addSubject(Subject subject) {
        // TODO: also need to update student_subject_relation table
        String sql = "INSERT INTO exam.subject (show_name, description, instructorid) " +
                " VALUES (?,?,?) RETURNING id";

        String studentSql = "INSERT INTO exam.student_subject_relation (studentid, subjectid) " +
                "VALUES (?,?)";
        String showName = subject.getSubjectCode();
        String description = subject.getDescription();

        List<Instructor> instructors = subject.getInstructors();
        List<Integer> instructorIds = new ArrayList<>();
        List<Student> students = subject.getStudents();
        List<Integer> studentIds = new ArrayList<>();

        for (Instructor i : instructors) {
            instructorIds.add(i.getUserId());
        }
        for (Student s : students) {
            studentIds.add(s.getUserId());
        }
        // TODO: In Part3, PUT [11, 12, 13] as string into subject table
        // insert a new subject in Subject table
        PreparedStatement statement = DBConnection.prepare(sql);
        int subjectId = 0;
        try {
            statement.setString(1,showName);
            statement.setString(2,description);
            statement.setInt(3, instructorIds.get(0));
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                subjectId = resultSet.getInt("id");
            }
            if (subjectId!=0) {
                subject.setId(subjectId);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }

        // insert student-subject mappings in Student_Subject_Relation table
        statement = DBConnection.prepare(studentSql);
        for (int i = 0; i < studentIds.size(); i++) {
            try {
                statement.setInt(1, studentIds.get(i));
                statement.setInt(2, subjectId);
                statement.executeQuery();
            } catch (SQLException e) {
                logger.error(e.getMessage());
            }
        }
    }
    /**
     * Student loads subjects by id
     * @param userid
     * @return
     */
    public static List<Subject> loadStudentSubjects(int userid) {
        String sql = "SELECT subjectid, studentid FROM exam.student_subject_relation WHERE studentid = ?";
        List<Subject> subjects = new ArrayList<>();
        try {
            PreparedStatement statement = DBConnection.prepare(sql);
            statement.setInt(1,userid);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()) {
                int subjectId = resultSet.getInt("subjectid");
                subjects.add(SubjectMapper.loadSubject(subjectId));
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return subjects;
    }

    /**
     * Instructor loads subjects by id
     * @param userId
     * @return
     */
    public static List<Subject> loadInstructorSubjects(int userId) {
        String sql = "SELECT * FROM exam.subject where instructorid=?";
        List<Subject> subjects = new ArrayList<>();
        try {
            PreparedStatement statement = DBConnection.prepare(sql);
            statement.setInt(1,userId);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()) {
                subjects.add(SubjectMapper.load(resultSet));
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return subjects;
    }

    /**
     * Admin loads all subjects
     * @return
     */
    public static List<Subject> loadAllSubjects() {
        String sql = "SELECT * FROM exam.subject";
        List<Subject> subjects = new ArrayList<>();
        try {
            PreparedStatement statement = DBConnection.prepare(sql);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()) {
                subjects.add(SubjectMapper.load(resultSet));
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return subjects;
    }

    public static Subject loadSubject (int subjectid) {
        String sql = "SELECT * FROM exam.subject WHERE id = ?";
        Subject subject = null;
        try {
            PreparedStatement statement = DBConnection.prepare(sql);
            statement.setInt(1,subjectid);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                subject = SubjectMapper.load(resultSet);
            }
        } catch (SQLException e) {
            logger.error(e.toString());
        }
        return subject;
    }

    private static Subject load(ResultSet resultSet) {
        Subject subject = new Subject();
        try {
            Integer id = resultSet.getInt("id");
            logger.info("Load subject with id: " + id);

            String showName = resultSet.getString("show_name");
            String description = resultSet.getString("description");
            Integer instructorId = resultSet.getInt("instructorId");

            // TODO: student and subject holds each other's reference, can lead infinite loop
//            Instructor instructor = InstructorMapper.loadWithId(instructorId);
            subject.setId(id);
            subject.setExams(ExamMapper.loadExamListWithSubject(id));
            subject.setSubjectCode(showName);
            subject.setDescription(description);

//            List<Student> students = StudentMapper.loadStudentsBySubject(id);
        }catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return subject;
    }
}
