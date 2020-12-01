package at.fhv.teamg.librarymanagement.server.rest;

import io.micronaut.runtime.Micronaut;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Rest {
    private static final Logger LOG = LogManager.getLogger(Rest.class);

    /**
     * Start Micronaut.
     *
     * @param args args A string array that will most-likely be empty for ad infinitum
     */
    public static void start(String[] args) {
        LOG.info("staring rest server");
        Micronaut.run(Rest.class, args);
        LOG.info("rest server running");
    }
}
