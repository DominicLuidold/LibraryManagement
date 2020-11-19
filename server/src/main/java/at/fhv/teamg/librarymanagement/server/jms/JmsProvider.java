package at.fhv.teamg.librarymanagement.server.jms;

import javax.jms.ConnectionFactory;
import org.apache.activemq.ActiveMQConnectionFactory;

/**
 * @author Valentin Goronjic
 */
public class JmsProvider {
    // https://www.logicbig.com/tutorials/java-ee-tutorial/jms/jms-helloworld.html
    public static ConnectionFactory getConnectionFactory() {
         /*The VM transport allows clients to connect to each other inside
                 the VM without the overhead of the network communication. */
        ConnectionFactory connectionFactory =
            new ActiveMQConnectionFactory("vm://localhost");

        return connectionFactory;
    }
}