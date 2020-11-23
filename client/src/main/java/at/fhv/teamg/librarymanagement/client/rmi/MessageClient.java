package at.fhv.teamg.librarymanagement.client.rmi;

import at.fhv.teamg.librarymanagement.shared.dto.CustomMessage;
import at.fhv.teamg.librarymanagement.shared.ifaces.MessageClientInterface;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MessageClient extends UnicastRemoteObject implements MessageClientInterface {
    private static final Logger LOG = LogManager.getLogger(MessageClientInterface.class);
    List<CustomMessage> messages;
    Consumer<List<CustomMessage>> onUpdate;

    public MessageClient() throws RemoteException {
        super();
        messages = RmiClient.getInstance().getAllMessages();
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
            LOG.info("got message update");
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
