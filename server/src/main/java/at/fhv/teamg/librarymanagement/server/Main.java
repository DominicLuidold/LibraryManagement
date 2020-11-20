package at.fhv.teamg.librarymanagement.server;

import at.fhv.teamg.librarymanagement.server.jms.JmsConsumer;
import at.fhv.teamg.librarymanagement.server.jms.JmsProducer;
import at.fhv.teamg.librarymanagement.server.rmi.Cache;
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
        LOG.info("Project initialized successfully");

        final JmsProducer sender;
        try {
            sender = JmsProducer.getInstance();
            final JmsConsumer receiver = JmsConsumer.getInstance();
            receiver.startListener();

            for (int i = 1; i <= 5; i++) {
                String m = "Hello world! " + i;
                sender.sendMessage(m);
                Thread.sleep(300);
            }

            sender.destroy();
            receiver.destroy();

        } catch (JMSException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}