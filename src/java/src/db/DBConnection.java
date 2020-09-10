package db;

import org.postgresql.Driver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DBConnection {
    private static Connection dbConnection;
    private static final String URL = null;
    private static final String USERNAME =   null;
    private static final String PASSWORD = null;

    public static PreparedStatement prepare(String string) {
        PreparedStatement statement = null;
        try {
            Connection dbConnection = getDBConnection();
            statement = dbConnection.prepareStatement(string);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return statement;
    }

    public static Connection getDBConnection() {
        if (dbConnection == null) {
            try {
                dbConnection = DriverManager.getConnection(URL,USERNAME,PASSWORD);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return dbConnection;
    }
}
