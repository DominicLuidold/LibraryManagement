package at.fhv.teamg.librarymanagement.shared.ifaces;

import at.fhv.teamg.librarymanagement.shared.dto.CustomMessage;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface MessageClientInterface extends Remote {
    void update(CustomMessage customMessage) throws RemoteException;
}
