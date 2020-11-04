package at.fhv.teamg.librarymanagement.client;

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
        // args not needed
        try {
            MainGui.main();
        } catch (Exception e) {
            LOG.error(e);
        }
    }
}