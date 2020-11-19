package at.fhv.teamg.librarymanagement.server.rmi;

import at.fhv.teamg.librarymanagement.shared.ifaces.LibraryFactoryInterface;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RmiServer {
    private static final Logger LOG = LogManager.getLogger(RmiServer.class);

    /**
     * Start the RMI Server for serving the Java Client a Library Service.
     */
    public RmiServer() {
        LOG.debug("Starting RMI server");
        try {
            LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
            LibraryFactoryInterface remote = new LibraryFactory();
            Naming.rebind("rmi://localhost/libraryfactory", remote);
            LOG.debug("RMI server started successfully");
        } catch (Exception e) {
            LOG.error("Starting RMI server failed", e);
        }
    }
}