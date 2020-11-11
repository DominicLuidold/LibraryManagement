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
        LOG.debug("Start up RMI Server");
        try {
            LocateRegistry.createRegistry(9988);
            LibraryFactoryInterface remote = new LibraryFactory();
            Naming.rebind(
                "rmi://vsts-team007.westeurope.cloudapp.azure.com/libraryfactory",
                remote
            );
            System.out.println("Server is ready");
            LOG.debug("RMI Server is ready.");

        } catch (Exception e) {
            LOG.error("RMI Server failed: " + e);
        }
    }
}