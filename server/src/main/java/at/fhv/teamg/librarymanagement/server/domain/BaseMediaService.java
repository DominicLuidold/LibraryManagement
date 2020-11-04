package at.fhv.teamg.librarymanagement.server.domain;

import at.fhv.teamg.librarymanagement.server.persistance.dao.BookDao;
import at.fhv.teamg.librarymanagement.server.persistance.dao.DvdDao;
import at.fhv.teamg.librarymanagement.server.persistance.dao.GameDao;
import at.fhv.teamg.librarymanagement.server.persistance.entity.Book;
import at.fhv.teamg.librarymanagement.server.persistance.entity.Dvd;
import at.fhv.teamg.librarymanagement.server.persistance.entity.Game;
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
}
