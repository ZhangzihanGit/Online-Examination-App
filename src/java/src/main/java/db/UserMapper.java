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
                logger.info(user.getName() + user.getId() + user.getUserType());
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

            if (usertype.equalsIgnoreCase(UserType.ADMIN.toString())) {
                user = new Admin();
            }
            else if (usertype.equalsIgnoreCase(UserType.STUDENT.toString())) {
                user = new Student();
            }
            else user = new Instructor();
            IdentityMap<User> map = IdentityMap.getInstance(User.class);

            // If the object is not loaded, load from rs.
            if (map.get(id)==null ){
                String username = resultSet.getString("username");
                String show_name = resultSet.getString("show_name");
                user.setId(id);
                user.setName(username);
                user.setUserType(UserType.valueOf(usertype.toUpperCase()));
                // Load subjects
                List<Subject> subjects = SubjectMapper.loadAllSubjects(id);
                user.setSubjects(subjects);
            } else {
                user = map.get(id);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return user;
    }

}
