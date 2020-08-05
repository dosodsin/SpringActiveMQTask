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

    private String dbUrl;
    private String login;
    private String password;

    public String getDbUrl() {
        return dbUrl;
    }

    public void setDbUrl(String dbUrl) {
        this.dbUrl = dbUrl;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private static final Logger logger = LogManager.getLogger();

    public Connection getConnection(String dbUrl,String login, String password) {

        try {
            return DriverManager.getConnection(dbUrl, login, password);
        } catch (SQLException ex) {
            logger.error("driver didn't find", ex.getMessage());
        }
        return null;
    }
}
