package at.fhv.teamg.librarymanagement.server.jms;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

/**
 * @author Valentin Goronjic
 */
public class JmsConsumer implements MessageListener {

    private static JmsConsumer INSTANCE;
    private Connection con;

    private JmsConsumer() { }

    /**
     * Returns an instance.
     *
     * @return Instance
     */
    public static JmsConsumer getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new JmsConsumer();
        }
        return INSTANCE;
    }

    public void startListener() throws JMSException {
        ConnectionFactory factory = JmsProvider.getConnectionFactory();
        this.con = factory.createConnection();
        con.start();

        Session session = con.createSession(false, Session.AUTO_ACKNOWLEDGE);

        Queue queue = session.createQueue("library.queue");
        MessageConsumer consumer = session.createConsumer(queue);
        consumer.setMessageListener(this);
    }

    @Override
    public void onMessage(Message message) {
        if (message instanceof TextMessage) {
            TextMessage tm = (TextMessage) message;
            try {
                System.out.printf("Message received: %s, Thread: %s%n",
                    tm.getText(),
                    Thread.currentThread().getName());
            } catch (JMSException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void destroy() throws JMSException {
        con.close();
    }
}