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

    /**
     * Generate the query from input sql.
     * @param string SQL query in string
     * @return prepare statement.
     */
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

    /**
     * Return a DB connection
     * @return
     */
    public static Connection getDBConnection() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Class not found " + e);
        }
        if (dbConnection == null) {
            try {
                // URI used in production environment
                URI dbURI = new URI(System.getenv("DATABASE_URL"));
                // URI used in local development environment
//                URI dbURI = new URI(Objects.requireNonNull(SecretLoader.getDBUri()));
//
                String username = dbURI.getUserInfo().split(":")[0];
                String password = dbURI.getUserInfo().split(":")[1];
                String dbURL = "jdbc:postgresql://" + dbURI.getHost() + ':' + dbURI.getPort() + dbURI.getPath() +
                        "?sslmode=require";

                // Used in local database
//                String dbURL = "jdbc:postgresql://localhost:5432/myDB";
//                String username = "postgres";
//                String password = "123";

                logger.info(dbURL);
                dbConnection = DriverManager.getConnection(dbURL,username,password);
            } catch (SQLException | URISyntaxException e) {
                e.printStackTrace();
            }
        }
        return dbConnection;
    }
}