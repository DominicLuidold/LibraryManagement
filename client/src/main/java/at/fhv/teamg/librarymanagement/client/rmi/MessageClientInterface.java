package at.fhv.teamg.librarymanagement.client.rmi;

import at.fhv.teamg.librarymanagement.shared.dto.Message;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MessageClientInterface extends UnicastRemoteObject implements
    at.fhv.teamg.librarymanagement.shared.ifaces.MessageClientInterface {
    private static final Logger LOG = LogManager.getLogger(MessageClientInterface.class);
    List<Message> messages;
    Consumer<List<Message>> onUpdate;

    public MessageClientInterface() throws RemoteException {
        super();
        messages = RmiClient.getInstance().getAllMessages();
    }

    public void onUpdate(Consumer<List<Message>> consumer) {
        onUpdate = consumer;
        onUpdate.accept(messages);
    }

    @Override
    public void update(Message message) throws RemoteException {
        Optional<Message> messageOptional = messages.stream()
            .filter(m -> m.id.equals(message.id))
            .findFirst();

        if (messageOptional.isPresent()) {
            LOG.info("got message update");
            Message m = messageOptional.get();
            m.status = message.status;
            m.message = message.message;
            m.dateTime = message.dateTime;
        } else {
            LOG.info("got new Message");
            messages.add(message);
        }

        onUpdate.accept(messages);
    }
}
