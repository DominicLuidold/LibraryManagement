package at.fhv.teamg.librarymanagement.client.rmi;

import at.fhv.teamg.librarymanagement.shared.dto.CustomMessage;
import at.fhv.teamg.librarymanagement.shared.ifaces.MessageClientInterface;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MessageClient extends UnicastRemoteObject implements MessageClientInterface {
    private static final Logger LOG = LogManager.getLogger(MessageClientInterface.class);
    private final boolean polling;
    List<CustomMessage> messages = new LinkedList<>();
    Consumer<List<CustomMessage>> onUpdate;

    /**
     * Instantiates a new MessageClient.
     *
     * @param doPolling should the client poll messages from the server
     * @throws RemoteException because rmi
     */
    public MessageClient(boolean doPolling) throws RemoteException {
        super();
        polling = doPolling;
        if (doPolling) {
            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    try {
                        messages = RmiClient.getInstance().getAllMessages();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    onUpdate.accept(messages);
                }
            };
            new Timer().scheduleAtFixedRate(task, 0, 20000);
        } else {
            messages = RmiClient.getInstance().getAllMessages();
        }
    }

    /**
     * Poll messages from Server.
     */
    public void poll() {
        if (polling) {
            new Thread(() -> {
                try {
                    Thread.sleep(200);
                    messages = RmiClient.getInstance().getAllMessages();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                onUpdate.accept(messages);
            }).start();
        }
    }


    public void onUpdate(Consumer<List<CustomMessage>> consumer) {
        onUpdate = consumer;
        onUpdate.accept(messages);
    }

    @Override
    public void update(CustomMessage message) throws RemoteException {
        Optional<CustomMessage> messageOptional = messages.stream()
            .filter(m -> m.id.equals(message.id))
            .findFirst();

        if (messageOptional.isPresent()) {
            CustomMessage m = messageOptional.get();
            if (message.status.equals(CustomMessage.Status.Archived)) {
                LOG.info("Removing archived message");
                messages.remove(m);
            } else {
                m.status = message.status;
                m.message = message.message;
                m.dateTime = message.dateTime;
                m.userId = message.userId;
            }
        } else {
            LOG.info("got new Message");
            messages.add(message);
        }

        onUpdate.accept(messages);
    }
}
