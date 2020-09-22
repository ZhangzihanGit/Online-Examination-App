package db;

import domain.*;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper {
    private static final Logger logger = LogManager.getLogger(UserMapper.class);
    public static User loadWithId(Integer id) {
        String sql = "SELECT * FROM exam.user WHERE id = ?";
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
//        User user = new User();



        return null;
    }

    private static User load(ResultSet resultSet) {
        User user = null;
        try {
            Integer id = resultSet.getInt("id");
            String usertype = resultSet.getString("usertype");
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
                user.setName(username);
                user.setUserType(UserType.valueOf(usertype));

                // Load subjects

            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return user;
    }

}
