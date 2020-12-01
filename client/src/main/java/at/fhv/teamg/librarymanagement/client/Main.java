package at.fhv.teamg.librarymanagement.client;

import at.fhv.teamg.librarymanagement.shared.ifaces.ejb.EjbTestRemote;
import java.util.Properties;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class Main {
    private static final Logger LOG = LogManager.getLogger(Main.class);

    /**
     * Main class for everything.
     *
     * @param args A string array that will most-likely be empty for ad infinitum
     */
    public static void main(String[] args) {
        // TODO: Remove this, EJB demo
        try {
            Properties props = new Properties();

            props.put(
                Context.INITIAL_CONTEXT_FACTORY,
                "org.wildfly.naming.client.WildFlyInitialContextFactory"
            );
            props.put(Context.PROVIDER_URL, "http-remoting://localhost:8080");

            Context context = new InitialContext(props);

            EjbTestRemote etr = (EjbTestRemote) context.lookup(
                "ejb:/LibraryServer/EjbTest!at.fhv.teamg.librarymanagement.shared.ifaces.ejb"
                    + ".EjbTestRemote");
            System.out.println(etr.getName("Team G"));

        } catch (NamingException ex) {
            ex.printStackTrace();
        }
        MainGui.main();
    }
}