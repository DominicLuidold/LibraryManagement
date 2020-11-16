package at.fhv.teamg.librarymanagement.client.rmi;

import at.fhv.teamg.librarymanagement.shared.dto.Message;
import java.util.List;

public interface IMessageSubscriber {
    void update(List<Message> messages);
}
