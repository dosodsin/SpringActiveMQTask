package Jms;

import org.apache.activemq.broker.BrokerService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EmbeddedBroker {
    private static final Logger logger = LogManager.getLogger();

    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public EmbeddedBroker(BrokerService brokerService, String url) {

        try {

            brokerService.addConnector(url);
            brokerService.setPersistent(false);
            brokerService.setUseJmx(false);
            brokerService.start();

        } catch (Exception ex) {
            logger.error("port already occupied", ex.getMessage());
        }
    }
}
