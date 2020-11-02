package at.fhv.teamg.librarymanagement.server.persistance.dao;

import at.fhv.teamg.librarymanagement.server.persistance.BaseDao;
import at.fhv.teamg.librarymanagement.server.persistance.entity.Book;
import java.util.Optional;
import java.util.UUID;

public class BookDao extends BaseDao<Book> {
    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Book> find(UUID uuid) {
        return this.find(Book.class, uuid);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Book> persist(Book elem) {
        return this.persist(Book.class, elem);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Book> update(Book elem) {
        return this.update(Book.class, elem);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean remove(Book elem) {
        return this.remove(Book.class, elem);
    }
}
