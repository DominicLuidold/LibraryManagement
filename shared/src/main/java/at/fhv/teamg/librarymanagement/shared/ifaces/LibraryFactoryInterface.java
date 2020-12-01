package at.fhv.teamg.librarymanagement.shared.ifaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface LibraryFactoryInterface extends Remote {
    LibraryInterface getLibrary() throws RemoteException;
}
