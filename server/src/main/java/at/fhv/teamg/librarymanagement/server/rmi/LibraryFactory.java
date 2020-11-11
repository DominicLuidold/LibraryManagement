package at.fhv.teamg.librarymanagement.server.rmi;

import at.fhv.teamg.librarymanagement.shared.ifaces.LibraryFactoryInterface;
import at.fhv.teamg.librarymanagement.shared.ifaces.LibraryInterface;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class LibraryFactory extends UnicastRemoteObject implements LibraryFactoryInterface {
    private static final Logger LOG = LogManager.getLogger(LibraryFactory.class);

    // Add constructor to match signature of super.
    public LibraryFactory() throws RemoteException {}

    @Override
    public LibraryInterface getLibrary() throws RemoteException {
        LOG.debug("Deliver instance of a Library.");
        return new Library();
    }
}