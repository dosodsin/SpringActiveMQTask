import Jms.EmbeddedBroker;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        context.start();
        //EmbeddedBroker broker=context.getBean("createEmbeddedBroker",EmbeddedBroker.class);
        //broker.runBroker();
    }

}

