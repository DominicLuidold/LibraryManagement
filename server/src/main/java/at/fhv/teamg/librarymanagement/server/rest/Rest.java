package at.fhv.teamg.librarymanagement.server.rest;

import io.micronaut.runtime.Micronaut;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@OpenAPIDefinition(
    info = @Info(
        title = "LibraryManagement",
        version = "1.0",
        description = "REST API for Library Management",
        contact = @Contact(url = "https://fhv.at", name = "FHV Team G")
    )
)
public class Rest {
    private static final Logger LOG = LogManager.getLogger(Rest.class);
    public static final String ADMIN = "Admin";
    public static final String LIBRARIAN = "Librarian";

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
