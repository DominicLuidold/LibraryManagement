package at.fhv.teamg.librarymanagement.server.jms;

import at.fhv.teamg.librarymanagement.server.rmi.Library;
import at.fhv.teamg.librarymanagement.shared.dto.CustomMessage;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Session;

public class JmsProducer {

    private static JmsProducer INSTANCE;
    private final MessageProducer producer;
    private final Session session;
    private final Connection con;

    /**
     * Returns an instance.
     *
     * @return Instance
     */
    public static JmsProducer getInstance() throws JMSException {
        if (INSTANCE == null) {
            INSTANCE = new JmsProducer();
        }
        return INSTANCE;
    }

    private JmsProducer() throws JMSException {
        ConnectionFactory factory = JmsProvider.getConnectionFactory();
        this.con = factory.createConnection();
        con.start();

        this.session = con.createSession(false, Session.CLIENT_ACKNOWLEDGE);
        Queue queue = session.createQueue("library.queue");
        this.producer = session.createProducer(queue);
    }

    /**
     * Sends a message to the JMS provider (queue).
     *
     * @param customMessage The message to be sent
     * @return The JMS message (to acknowledge later on)
     * @throws JMSException If unable to send
     */
    public ObjectMessage sendMessage(CustomMessage customMessage) throws JMSException {
        System.out.printf("Sending message: %s, Thread:%s%n",
            customMessage,
            Thread.currentThread().getName());
        ObjectMessage m = session.createObjectMessage(customMessage);
        producer.send(m);

        return m;
    }

    public void destroy() throws JMSException {
        con.close();
    }
}
