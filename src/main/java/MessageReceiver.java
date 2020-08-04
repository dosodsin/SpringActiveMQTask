import javax.jms.Connection;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.TextMessage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MessageReceiver {

    private static final Logger logger = LogManager.getLogger();
    SaveInDB saveInDB;
    private Connection connection;

    public SaveInDB getSaveInDB() {
        return saveInDB;
    }

    public void setSaveInDB(SaveInDB saveInDB) {
        this.saveInDB = saveInDB;
    }

    private MessageConsumer messageConsumer;

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public MessageConsumer getMessageConsumer() {
        return messageConsumer;
    }

    public void setMessageConsumer(MessageConsumer messageConsumer) {
        this.messageConsumer = messageConsumer;
    }

    public void receiveMessage() {

        try {

            connection.start();
            Message message = messageConsumer.receive();
            if (message instanceof TextMessage) {
                TextMessage textMessage = (TextMessage) message;
                saveInDB.saveInDB(textMessage);
            }
            connection.close();
        } catch (Exception e) {
            logger.error("problem with connection", e.getMessage());
        }
    }
}
