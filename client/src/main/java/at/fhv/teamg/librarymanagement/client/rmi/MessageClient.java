package at.fhv.teamg.librarymanagement.client.rmi;

import at.fhv.teamg.librarymanagement.shared.dto.Message;
import at.fhv.teamg.librarymanagement.shared.ifaces.IMessageClient;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class MessageClient extends UnicastRemoteObject implements IMessageClient {
    List<Message> messages = new LinkedList<>();

    protected MessageClient(List<Message> messages) throws RemoteException {
        super();
        this.messages = messages;
        System.out.println("initial messages");
        this.messages.forEach(message ->
            System.out.println(message.dateTime + "\t" + message.message + "\t" + message.status)
        );
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

        System.out.println(message.dateTime + "\t" + message.message + "\t" + message.status);
    }
}
