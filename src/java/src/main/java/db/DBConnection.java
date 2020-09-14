package db;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.postgresql.Driver;
import util.SecretLoader;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.*;
import java.util.Objects;

public class DBConnection {
    private static final Logger logger = LogManager.getLogger(DBConnection.class);
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

    public static String mockDataTest() {
        String sql = "SELECT * FROM userlists WHERE userid = 1234";
        try{
            PreparedStatement ps = prepare(sql);
            ResultSet rs = ps.executeQuery();
            int id=0;
            String username = null;
            while (rs.next()) {
                id = rs.getInt(1);
                username = rs.getString("username");
            }
            return username;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return "error";
    }

    public static Connection getDBConnection() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Class not found " + e);
        }
        if (dbConnection == null) {
            try {
                // URI used in production environment
//                URI dbURI = new URI(System.getenv("DATABASE_URL"));
                // URI used in local development environment
                URI dbURI = new URI(Objects.requireNonNull(SecretLoader.getDBUri()));

                String username = dbURI.getUserInfo().split(":")[0];
                String password = dbURI.getUserInfo().split(":")[1];
                String dbURL = "jdbc:postgresql://" + dbURI.getHost() + ':' + dbURI.getPort() + dbURI.getPath() +
                        "?sslmode=require";
                logger.info(dbURL);
                dbConnection = DriverManager.getConnection(dbURL,username,password);
            } catch (SQLException | URISyntaxException e) {
                e.printStackTrace();
            }
        }
        return dbConnection;
    }
}