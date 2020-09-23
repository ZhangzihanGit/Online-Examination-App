package db;

import domain.*;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UserMapper {
    private static final Logger logger = LogManager.getLogger(UserMapper.class);
    public static User loadWithId(Integer id) {
        String sql = "SELECT * FROM exam.users WHERE id = ?";
        User user = null;
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = DBConnection.prepare(sql);
            preparedStatement.setInt(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            user = UserMapper.load(resultSet);
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return user;
    }

    public static User loadWithUsername(String username) {
        String sql = "SELECT * FROM exam.users WHERE username = ?";
        User user = null;
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = DBConnection.prepare(sql);
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                user = UserMapper.load(resultSet);
                logger.info(user.getName() + user.getUserId() + user.getUserType());
            } else {
                // TODO: if username not found?
                logger.error("User not found");
            }

        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return user;
    }

    private static User load(ResultSet resultSet) {
        User user = null;
        try {
            Integer id = resultSet.getInt("id");
            String usertype = resultSet.getString("users_type");
            List<Subject> subjects = null;

            if (usertype.equalsIgnoreCase(UserType.ADMIN.toString())) {
                user = new Admin();
                subjects = SubjectMapper.loadAllSubjects();
            }
            else if (usertype.equalsIgnoreCase(UserType.STUDENT.toString())) {
                user = new Student();
                subjects = SubjectMapper.loadStudentSubjects(id);
            }
            else{
                user = new Instructor();
                subjects = SubjectMapper.loadInstructorSubjects(id);
            }

            String username = resultSet.getString("username");
            String showName = resultSet.getString("show_name");
            user.setUserId(id);
            user.setName(username);
            user.setShowName(showName);
            user.setUserType(UserType.valueOf(usertype.toUpperCase()));
            user.setSubjects(subjects);

        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return user;
    }

}
