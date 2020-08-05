package DB;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.jms.TextMessage;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class SaveInDB {

    private static final Logger logger = LogManager.getLogger();
    private Connection connection;
    private String driver;

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public void saveInDB(TextMessage textMessage) throws ClassNotFoundException {
        Class.forName(driver);
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


}
