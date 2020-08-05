package Jms;

import javax.jms.Connection;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Random;

public class MessageSender {

    private static final Logger logger = LogManager.getLogger();

    private Connection connection;
    private Session session;
    private MessageProducer messageProducer;

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public MessageProducer getMessageProducer() {
        return messageProducer;
    }

    public void setMessageProducer(MessageProducer messageProducer) {
        this.messageProducer = messageProducer;
    }

    public void sendMessage() {

        try {

            TextMessage message = session.createTextMessage(randomString());
            messageProducer.send(message);

        } catch (Exception ex) {
            logger.error("Message didnt't send", ex.getMessage());
        }
    }

    public static String randomString() {
        String charactetrs = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String randomString = "";
        int length = 7;

        Random random = new Random();

        char[] text = new char[length];

        for (int i = 0; i < length; i++) {
            text[i] = charactetrs.charAt(random.nextInt(charactetrs.length()));
        }

        for (int i = 0; i < text.length; i++) {
            randomString += text[i];
        }
        return randomString;
    }
}
