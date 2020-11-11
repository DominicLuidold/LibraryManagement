package at.fhv.teamg.librarymanagement.server.persistance.dao;

import at.fhv.teamg.librarymanagement.server.persistance.BaseDao;
import at.fhv.teamg.librarymanagement.server.persistance.entity.Dvd;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import javax.persistence.TypedQuery;

public class DvdDao extends BaseDao<Dvd> {
    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Dvd> find(UUID uuid) {
        return this.find(Dvd.class, uuid);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Dvd> persist(Dvd elem) {
        return this.persist(Dvd.class, elem);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Dvd> update(Dvd elem) {
        return this.update(Dvd.class, elem);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean remove(Dvd elem) {
        return this.remove(Dvd.class, elem);
    }

    /**
     * Finds a {@link Dvd} entity by supplied fields.
     *
     * @param title       Title to look for
     * @param director    Director to look for
     * @param releaseDate Year of publication to look for
     * @param topic       Topic name to look for
     * @return List of Dvd entities
     */
    public List<Dvd> findBy(String title, String director, LocalDate releaseDate, String topic) {
        TypedQuery<Dvd> query = entityManager.createQuery(
            "SELECT d FROM Dvd d "
                + "JOIN Medium m ON d = m.dvd "
                + "LEFT JOIN Topic t ON m.topic = t "
                + "WHERE m.title LIKE :title "
                + "AND d.director LIKE :director "
                + "AND m.releaseDate >= :releaseDate "
                + "AND t.name LIKE :topic",
            Dvd.class
        );

        query.setMaxResults(300);
        query.setParameter("title", "%" + title + "%");
        query.setParameter("director", "%" + director + "%");
        query.setParameter("releaseDate", releaseDate);
        query.setParameter("topic", "%" + topic + "%");

        return new LinkedList<>(query.getResultList());
    }
}
