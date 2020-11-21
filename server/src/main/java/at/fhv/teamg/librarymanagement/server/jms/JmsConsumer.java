package at.fhv.teamg.librarymanagement.server.jms;

import at.fhv.teamg.librarymanagement.server.rmi.Library;
import at.fhv.teamg.librarymanagement.shared.ifaces.MessageClientInterface;
import java.rmi.RemoteException;
import java.util.List;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author Valentin Goronjic
 */
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
    public static JmsConsumer getInstance(Library l) {
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
        if (message instanceof at.fhv.teamg.librarymanagement.shared.dto.Message) {
            at.fhv.teamg.librarymanagement.shared.dto.Message m =
                (at.fhv.teamg.librarymanagement.shared.dto.Message) message;
            System.out.printf("Message received: %s, Thread: %s%n",
                m.getMessage(),
                Thread.currentThread().getName());
            List<MessageClientInterface> clients = Library.getClients();

            clients.forEach(client -> updateClient(client, m));
        }
    }

    private static void updateClient(
        MessageClientInterface client,
        at.fhv.teamg.librarymanagement.shared.dto.Message message
    ) {
        new Thread(() -> {
            try {
                client.update(message);
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