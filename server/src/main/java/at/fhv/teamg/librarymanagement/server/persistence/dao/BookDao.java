package at.fhv.teamg.librarymanagement.server.persistance.dao;

import at.fhv.teamg.librarymanagement.server.persistance.BaseDao;
import at.fhv.teamg.librarymanagement.server.persistance.entity.Book;
import at.fhv.teamg.librarymanagement.server.persistance.entity.Topic;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import javax.persistence.TypedQuery;

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

    /**
     * Finds a {@link Book} entity by supplied fields.
     *
     * @param title  Title to look for
     * @param author Author to look for
     * @param isbn13 ISBN-13 to look for
     * @param topic  Topic name to look for
     * @return List of Book entities
     */
    public List<Book> findBy(String title, String author, String isbn13, String topic) {
        TypedQuery<Book> query = entityManager.createQuery(
            "SELECT b FROM Book b "
                + "JOIN Medium m ON b = m.book "
                + "LEFT JOIN Topic t ON m.topic = t "
                + "WHERE m.title LIKE :title "
                + "AND b.author LIKE :author "
                + "AND b.isbn13 LIKE :isbn13 "
                + "AND t.name LIKE :topic",
            Book.class
        );

        query.setMaxResults(300);
        query.setParameter("title", "%" + title + "%");
        query.setParameter("author", "%" + author + "%");
        query.setParameter("isbn13", "%" + isbn13 + "%");
        query.setParameter("topic", "%" + topic + "%");

        return new LinkedList<>(query.getResultList());
    }

    public List<Book> getAll() {
        return super.getAll(Book.class);
    }
}
