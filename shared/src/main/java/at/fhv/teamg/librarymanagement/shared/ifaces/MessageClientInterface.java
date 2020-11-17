package at.fhv.teamg.librarymanagement.shared.ifaces;

import at.fhv.teamg.librarymanagement.shared.dto.Message;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface MessageClientInterface extends Remote {
    void update(Message message) throws RemoteException;
}
