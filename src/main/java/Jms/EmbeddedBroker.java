package Jms;

import org.apache.activemq.broker.BrokerService;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EmbeddedBroker {

    BrokerService brokerService;

    public BrokerService getBrokerService() {
        return brokerService;
    }

    public void setBrokerService(BrokerService brokerService) {
        this.brokerService = brokerService;
    }

    private static final Logger logger = LogManager.getLogger();

    public static String readUrl() {
        Properties properties = new Properties();
        String url = "";
        try (InputStream inputStream = new FileInputStream("src/main/resources/application.properties")) {
            properties.load(inputStream);
            url = properties.getProperty("embeddedBroker.url");
        } catch (IOException ex) {
            logger.error("file with properties not found", ex.getMessage());
        }
        return url;
    }

    public void runBroker() {

        try {

            brokerService.addConnector(readUrl());
            brokerService.setPersistent(false);
            brokerService.setUseJmx(false);
            brokerService.start();

        } catch (Exception ex) {
            logger.error("port already occupied", ex.getMessage());
        }
    }
}
