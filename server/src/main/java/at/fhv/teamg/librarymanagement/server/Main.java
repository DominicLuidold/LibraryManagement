package at.fhv.teamg.librarymanagement.server;

import at.fhv.teamg.librarymanagement.server.common.Cache;
import at.fhv.teamg.librarymanagement.server.jms.JmsConsumer;
import at.fhv.teamg.librarymanagement.server.rest.Rest;
import at.fhv.teamg.librarymanagement.server.rmi.RmiServer;
import at.fhv.teamg.librarymanagement.server.tasks.TaskRunner;
import java.util.concurrent.Callable;
import javax.jms.JMSException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import picocli.CommandLine;

@CommandLine.Command(name = "LibraryServer.jar", mixinStandardHelpOptions = true,
    version = "LibraryServer 1.0", description = "Starts the LibraryManagement Server Components")
public class Main implements Callable<Integer> {
    private static final Logger LOG = LogManager.getLogger(Main.class);

    @CommandLine.Option(names = {"-r", "--rmi"}, description = "Start RMI server + Cache")
    private boolean rmi;

    @CommandLine.Option(names = {"-a", "--api"}, description = "Start REST API + Cache")
    private boolean api;

    @CommandLine.Option(names = {"-t", "--tasks"}, description = "Start Tasks")
    private boolean tasks;

    // not needed now but could be important in the future
    @CommandLine.Option(names = {"-c", "--cache"}, description = "Start Cache", required = false)
    private boolean cache;


    private String[] args;

    public void setArgs(String[] args) {
        this.args = args;
    }

    @Override
    public Integer call() throws Exception {
        LOG.info("Starting cache due to launch argument");
        Cache.getInstance();
        if (tasks == true) {
            LOG.info("Starting task runner due to launch argument");
            TaskRunner.run();
        }
        if (api == true) {
            LOG.info("Starting REST API due to launch argument");
            Rest.start(args);
        }
        if (rmi == true) {
            LOG.info("Starting RMI due to launch argument");
            new RmiServer();
            try {
                JmsConsumer.getInstance().startListener();
            } catch (JMSException e) {
                LOG.error("Cannot start message JMS listener", e);
            }
        }

        LOG.info("Project initialized successfully");
        return 0;
    }

    /**
     * Main class for everything in the world.
     *
     * @param args A string array that will most-likely be empty for ad infinitum
     */
    public static void main(String[] args) {
        Main m = new Main();
        m.setArgs(args);
        new CommandLine(m).execute(args);
    }
}