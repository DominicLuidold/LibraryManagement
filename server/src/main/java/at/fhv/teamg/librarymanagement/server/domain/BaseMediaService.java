package at.fhv.teamg.librarymanagement.server.domain;

import at.fhv.teamg.librarymanagement.server.persistence.dao.BookDao;
import at.fhv.teamg.librarymanagement.server.persistence.dao.DvdDao;
import at.fhv.teamg.librarymanagement.server.persistence.dao.GameDao;
import at.fhv.teamg.librarymanagement.server.persistence.dao.MediumCopyDao;
import at.fhv.teamg.librarymanagement.server.persistence.dao.MediumDao;
import at.fhv.teamg.librarymanagement.server.persistence.dao.TopicDao;
import at.fhv.teamg.librarymanagement.server.persistence.dao.UserDao;
import at.fhv.teamg.librarymanagement.server.persistence.entity.Book;
import at.fhv.teamg.librarymanagement.server.persistence.entity.Dvd;
import at.fhv.teamg.librarymanagement.server.persistence.entity.Game;
import at.fhv.teamg.librarymanagement.server.persistence.entity.Medium;
import at.fhv.teamg.librarymanagement.server.persistence.entity.MediumCopy;
import at.fhv.teamg.librarymanagement.server.persistence.entity.Topic;
import at.fhv.teamg.librarymanagement.server.persistence.entity.User;
import java.util.Optional;
import java.util.UUID;

/**
 * The {@link BaseMediaService} class is used for common database actions. These actions
 * need to be able to get mocked for unit testing.
 */
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

    protected Optional<Medium> findMediumById(UUID uuid) {
        return new MediumDao().find(uuid);
    }

    protected Optional<MediumCopy> findMediumCopyById(UUID id) {
        return new MediumCopyDao().find(id);
    }

    protected Optional<User> findUserById(UUID id) {
        return new UserDao().find(id);
    }

    protected Optional<Topic> findTopicById(UUID id) {
        return new TopicDao().find(id);
    }
}
