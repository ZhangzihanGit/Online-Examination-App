package db;

import domain.Subject;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SubjectMapper {
    private static final Logger logger = LogManager.getLogger(SubjectMapper.class);
    public static Subject loadWithId(String id) {
        return null;
    }


    public static void addSubject(int subjectId) {

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
//            IdentityMap<Subject> map = IdentityMap.getInstance(subject.getClass());
            Integer id = resultSet.getInt("id");
            System.out.println(id);
            // If not previously loaded, load from DB.
//            if (map.get(id) == null) {
                String showName = resultSet.getString("show_name");
                String description = resultSet.getString("description");
                Integer instructorId = resultSet.getInt("instructorId");

//            TODO: Load UserMapper and ExamMapper here// 或者是直接UserMapper，具体实现再商量,
//             to construct Subject Object.  => See Subject Constructor.
//            Instructor instructor = InstructorMapper.loadWithId(instructorId);
                subject.setId(id);
//                subject.setExams(ExamMapper.loadWithId());
                subject.setSubjectCode(showName);
                subject.setDescription(description);

//                map.put()
//            }
        }catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return subject;
    }
}
