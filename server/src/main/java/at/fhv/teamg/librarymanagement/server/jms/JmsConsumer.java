package at.fhv.teamg.librarymanagement.server.jms;

import at.fhv.teamg.librarymanagement.server.rmi.Library;
import at.fhv.teamg.librarymanagement.shared.dto.CustomMessage;
import at.fhv.teamg.librarymanagement.shared.ifaces.MessageClientInterface;
import java.rmi.RemoteException;
import java.util.List;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Session;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class JmsConsumer implements MessageListener {

    private static final Logger LOG = LogManager.getLogger(JmsConsumer.class);
    private Library library;
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

    /**
     * Starts the JMS listener.
     * @throws JMSException When unable to start session
     */
    public void startListener() throws JMSException {
        ConnectionFactory factory = JmsProvider.getConnectionFactory();
        this.con = factory.createConnection();
        con.start();

        Session session = con.createSession(false, Session.CLIENT_ACKNOWLEDGE);

        Queue queue = session.createQueue("library.queue");
        MessageConsumer consumer = session.createConsumer(queue);
        consumer.setMessageListener(this);
        LOG.debug("Starting JMS listener");
    }

    @Override
    public void onMessage(Message message) {
        LOG.debug("Received a JMS message");
        if (message instanceof ObjectMessage) {
            ObjectMessage m =
                (ObjectMessage) message;
            try {
                CustomMessage customMessage = (CustomMessage) m.getObject();
                LOG.debug(
                    "Message received: {}, Thread: {}",
                    customMessage.getMessage(),
                    Thread.currentThread().getName()
                );

                List<MessageClientInterface> clients = Library.getClients();
                clients.forEach(client -> updateClient(client, customMessage));
            } catch (JMSException e) {
                LOG.error("Cannot read received message",e);
            }
        }
    }

    private static void updateClient(
        MessageClientInterface client,
        CustomMessage customMessage
    ) {
        new Thread(() -> {
            try {
                client.update(customMessage);
            } catch (RemoteException e) {
                LOG.error("Client cannot be messaged", e);
                LOG.debug("Removing client [{}] from subscriber list", client.hashCode());
                Library.getClients().remove(client);
            }
        }).start();
    }

    public void destroy() throws JMSException {
        con.close();
    }
}