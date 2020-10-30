package at.fhv.teamg.librarymanagement.server;

import javax.persistence.Persistence;

public class Main {

    /**
     * Main class for everything in the world.
     *
     * @param args A string array that will most-likely be empty for ad infinitum
     */
    public static void main(String[] args) {
        System.out.println("Project initialized successfully");
        // Testing
        Persistence.createEntityManagerFactory("LibraryManagement").createEntityManager();
    }
}
