package at.fhv.teamg.librarymanagement.client.rmi;

import at.fhv.teamg.librarymanagement.shared.dto.Message;
import at.fhv.teamg.librarymanagement.shared.ifaces.IMessageClient;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class MessageClient extends UnicastRemoteObject implements IMessageClient {
    List<Message> messages;
    List<IMessageSubscriber> subscribers = new LinkedList<>();

    public MessageClient() throws RemoteException {
        super();
        messages = RmiClient.getInstance().getAllMessages();
    }

    public void subscribe(IMessageSubscriber sub) {
        subscribers.add(sub);
        sub.update(messages);
    }

    @Override
    public void update(Message message) throws RemoteException {
        Optional<Message> messageOptional = messages.stream()
            .filter(m -> m.id.equals(message.id))
            .findFirst();

        if (messageOptional.isPresent()) {
            System.out.println("got message update");
            Message m = messageOptional.get();
            m.status = message.status;
            m.message = message.message;
            m.dateTime = message.dateTime;
        } else {
            System.out.println("got new Message");
            messages.add(message);
        }

        subscribers.forEach(sub -> sub.update(messages));
    }
}
