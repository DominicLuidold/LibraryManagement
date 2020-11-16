package at.fhv.teamg.librarymanagement.server.domain;

import at.fhv.teamg.librarymanagement.server.persistance.dao.BookDao;
import at.fhv.teamg.librarymanagement.server.persistance.dao.DvdDao;
import at.fhv.teamg.librarymanagement.server.persistance.dao.GameDao;
import at.fhv.teamg.librarymanagement.server.persistance.dao.MediumCopyDao;
import at.fhv.teamg.librarymanagement.server.persistance.dao.MediumDao;
import at.fhv.teamg.librarymanagement.server.persistance.dao.TopicDao;
import at.fhv.teamg.librarymanagement.server.persistance.dao.UserDao;
import at.fhv.teamg.librarymanagement.server.persistance.entity.Book;
import at.fhv.teamg.librarymanagement.server.persistance.entity.Dvd;
import at.fhv.teamg.librarymanagement.server.persistance.entity.Game;
import at.fhv.teamg.librarymanagement.server.persistance.entity.Medium;
import at.fhv.teamg.librarymanagement.server.persistance.entity.MediumCopy;
import at.fhv.teamg.librarymanagement.server.persistance.entity.Topic;
import at.fhv.teamg.librarymanagement.server.persistance.entity.User;
import java.util.Optional;
import java.util.UUID;

public abstract class BaseMediaService {
    protected Optional<Book> findBookById(UUID id) {
        return new BookDao().find(id);
    }

    protected Optional<Game> findGameById(UUID id) {
        return new GameDao().find(id);
    }

    protected Optional<Dvd> findDvdById(UUID id) {
        return new DvdDao().find(id);
    }

    protected Optional<MediumCopy> findMediumCopyById(UUID id) {
        return new MediumCopyDao().find(id);
    }

    protected Optional<Medium> findMediumById(UUID uuid) {
        return new MediumDao().find(uuid);
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

    protected Optional<User> findUserById(UUID id) {
        UserDao dao = new UserDao();
        return dao.find(id);
    }

    protected Optional<Topic> findTopicById(UUID id) {
        return new TopicDao().find(id);
    }
}
