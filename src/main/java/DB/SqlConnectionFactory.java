package DB;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SqlConnectionFactory {

    private static final Logger logger = LogManager.getLogger();

    public Connection getConnection() {

        Properties properties = new Properties();
        String dbUrl = "";
        String login = "";
        String password = "";
        try (InputStream inputStream = new FileInputStream("src/main/resources/application.properties")) {

            properties.load(inputStream);
            dbUrl = properties.getProperty("db.url");
            login = properties.getProperty("db.login");
            password = properties.getProperty("db.password");

        } catch (IOException ex) {
            logger.error("file with properties not found", ex.getMessage());
        }

        try {
            return DriverManager.getConnection(dbUrl, login, password);
        } catch (SQLException ex) {
            logger.error("driver didn't find", ex.getMessage());
        }
        return null;
    }
}
