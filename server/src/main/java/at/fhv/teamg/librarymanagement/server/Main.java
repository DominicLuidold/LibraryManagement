package at.fhv.teamg.librarymanagement.server;

import at.fhv.teamg.librarymanagement.server.common.Cache;
import at.fhv.teamg.librarymanagement.server.jms.JmsConsumer;
import at.fhv.teamg.librarymanagement.server.rmi.RmiServer;
import at.fhv.teamg.librarymanagement.server.tasks.TaskRunner;
import javax.jms.JMSException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {
    private static final Logger LOG = LogManager.getLogger(Main.class);

    /**
     * Main class for everything in the world.
     *
     * @param args A string array that will most-likely be empty for ad infinitum
     */
    public static void main(String[] args) {
        Cache.getInstance();
        TaskRunner.run();
        new RmiServer();
        try {
            JmsConsumer.getInstance().startListener();
        } catch (JMSException e) {
            LOG.error("Cannot start message JMS listener", e);
        }
        LOG.info("Project initialized successfully");
    }
}