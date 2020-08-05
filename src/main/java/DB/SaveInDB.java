package DB;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.jms.TextMessage;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Properties;

public class SaveInDB {

    private static final Logger logger = LogManager.getLogger();
    private Connection connection;

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public void saveInDB(TextMessage textMessage) throws ClassNotFoundException {
        Class.forName(databaseDriver());
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("insert into message_body(body) values(?)");
            preparedStatement.setString(1, textMessage.getText());
            preparedStatement.executeUpdate();

            String sql = "insert into message_header(deliveryMode,destination,expiration,messageID,priority,redelivered) values(?,?,?,?,?,?)";
            PreparedStatement preparedStatement1 = connection.prepareStatement(sql);
            preparedStatement1.setInt(1, textMessage.getJMSDeliveryMode());
            preparedStatement1.setString(2, String.valueOf(textMessage.getJMSDestination()));
            preparedStatement1.setLong(3, textMessage.getJMSExpiration());
            preparedStatement1.setString(4, textMessage.getJMSMessageID());
            preparedStatement1.setInt(5, textMessage.getJMSPriority());
            preparedStatement1.setBoolean(6, textMessage.getJMSRedelivered());
            preparedStatement1.executeUpdate();

        } catch (Exception ex) {
            logger.error("message didnt't save", ex.getMessage());
        }
    }

    public static String databaseDriver(){
        Properties properties = new Properties();
        String driver = "";
        try (InputStream inputStream = new FileInputStream("src/main/resources/application.properties")) {
            properties.load(inputStream);
            driver = properties.getProperty("db.driver");
        } catch (IOException ex) {
            logger.error("file with properties not found", ex.getMessage());
        }
        return driver;
    }

}
