package db;

import domain.Instructor;
import domain.Subject;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SubjectMapper {
    private static Logger logger = LogManager.getLogger(SubjectMapper.class);
    public static Subject loadWithId(String id) {
        return null;
    }
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

    private static Subject load(ResultSet resultSet) {
        Subject subject = new Subject();
        try {
            IdentityMap<Subject> map = IdentityMap.getInstance(subject);

            Integer id = resultSet.getInt("id");

            subject = map.get(id);
            // If not previously loaded, load from DB.
            if (subject == null) {
                subject = new Subject();
                String showName = resultSet.getString("show_name");
                String description = resultSet.getString("description");

                Integer instructorId = resultSet.getInt("instructorId");

//            TODO: Load UserMapper and ExamMapper here// 或者是直接UserMapper，具体实现再商量,
//             to construct Subject Object.  => See Subject Constructor.
//            Instructor instructor = InstructorMapper.loadWithId(instructorId);
                subject.setSubjectCode(showName);
                subject.setDescription(description);
            }
        }catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return subject;
    }
}
