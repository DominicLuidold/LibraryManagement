package at.fhv.teamg.librarymanagement.server.jms;

import javax.jms.ConnectionFactory;
import org.apache.activemq.ActiveMQConnectionFactory;

public class JmsProvider {
    // https://www.logicbig.com/tutorials/java-ee-tutorial/jms/jms-helloworld.html

    /**
     * Returns the connection factory to reach the queue.
     *
     * @return ConnectionFactory
     */
    public static ConnectionFactory getConnectionFactory() {
        /*
        The VM transport allows clients to connect to each other inside
        the VM without the overhead of the network communication.
        */
        ActiveMQConnectionFactory connectionFactory =
            new ActiveMQConnectionFactory("vm://localhost");
        connectionFactory.setTrustAllPackages(true);
        return connectionFactory;
    }
}