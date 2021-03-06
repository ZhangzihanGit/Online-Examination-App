package db;

import domain.Instructor;
import domain.Subject;
import domain.UserType;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InstructorMapper {
    private static final Logger logger = LogManager.getLogger(InstructorMapper.class);
    public static Instructor loadWithId(Integer id) {
        // need a non-null assertion here.
        return null;
    }

    public static List<Instructor> loadAllInstructors() {

        String sql = "SELECT * FROM exam.users WHERE users_type = 'instructor'";
        List<Instructor> instructors = new ArrayList<>();
        try {
            PreparedStatement statement = DBConnection.prepare(sql);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()) {
                int userId = resultSet.getInt("id");
                String userName = resultSet.getString("username");
                String showName = resultSet.getString("show_name");
                String userType = resultSet.getString("users_type");
                List<Subject> subjects =   SubjectMapper.loadInstructorSubjects(userId);

                Instructor instructor = new Instructor(userId, userName, subjects,
                        UserType.valueOf(userType.toUpperCase()), showName);
                instructors.add(instructor);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }

        return instructors;
    }

    public static List<Instructor> loadInstructorsBySubject(int subjectId) {

        String sql = "SELECT * FROM exam.users as u INNER JOIN exam.user_subject_relation as s ON" +
                " u.id = s.userid AND u.users_type = 'instructor' WHERE s.subjectid = ?";
        List<Instructor> instructors = new ArrayList<>();
        try {
            PreparedStatement statement = DBConnection.prepare(sql);
            statement.setInt(1, subjectId);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()) {
                int userId = resultSet.getInt("id");
                String userName = resultSet.getString("username");
                String showName = resultSet.getString("show_name");
                String userType = resultSet.getString("users_type");
                List<Subject> subjects =   SubjectMapper.loadInstructorSubjects(userId);

                Instructor instructor = new Instructor(userId, userName, subjects,
                        UserType.valueOf(userType.toUpperCase()), showName);
                instructors.add(instructor);
                logger.info(userName + " " + userId);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }

        return instructors;
    }
}
