package at.fhv.teamg.librarymanagement.server.domain;

import at.fhv.teamg.librarymanagement.server.persistance.dao.BookDao;
import at.fhv.teamg.librarymanagement.server.persistance.dao.DvdDao;
import at.fhv.teamg.librarymanagement.server.persistance.dao.GameDao;
import at.fhv.teamg.librarymanagement.server.persistance.entity.Book;
import at.fhv.teamg.librarymanagement.server.persistance.entity.Dvd;
import at.fhv.teamg.librarymanagement.server.persistance.entity.Game;
import at.fhv.teamg.librarymanagement.server.persistance.entity.Medium;
import at.fhv.teamg.librarymanagement.server.persistance.entity.MediumCopy;
import java.util.Optional;
import java.util.UUID;

public abstract class BaseMediaService {
    protected Optional<Book> findBookById(UUID id) {
        BookDao dao = new BookDao();
        return dao.find(id);
    }

    protected Optional<Game> findGameById(UUID id) {
        GameDao dao = new GameDao();
        return dao.find(id);
    }

    protected Optional<Dvd> findDvdById(UUID id) {
        DvdDao dao = new DvdDao();
        return dao.find(id);
    }

    /**
     * Determines the availability string (e.g. {@code 3/5}) for a specific {@link Medium}.
     *
     * @param medium The medium to use
     * @return A string containing the availability
     */
    protected String getAvailability(Medium medium) {
        int copies = medium.getCopies().size();

        int availableCopies = 0;
        for (MediumCopy copy : medium.getCopies()) {
            if (copy.isAvailable()) {
                availableCopies++;
            }
        }

        return availableCopies + "/" + copies;
    }
}
