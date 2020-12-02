package at.fhv.teamg.librarymanagement.server.persistance.dao;

import at.fhv.teamg.librarymanagement.server.persistance.BaseDao;
import at.fhv.teamg.librarymanagement.server.persistance.entity.Book;
import at.fhv.teamg.librarymanagement.server.persistance.entity.Game;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import javax.persistence.TypedQuery;

public class GameDao extends BaseDao<Game> {
    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Game> find(UUID uuid) {
        return this.find(Game.class, uuid);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Game> persist(Game elem) {
        return this.persist(Game.class, elem);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Game> update(Game elem) {
        return this.update(Game.class, elem);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean remove(Game elem) {
        return this.remove(Game.class, elem);
    }

    /**
     * Finds a {@link Game} entity by supplied fields.
     *
     * @param title     Title to look for
     * @param developer Developer to look for
     * @param platform  Platform(s) to look for
     * @param topic     Topic name to look for
     * @return List of Game entities
     */
    public List<Game> findBy(String title, String developer, String platform, String topic) {
        TypedQuery<Game> query = entityManager.createQuery(
            "SELECT g FROM Game g "
                + "JOIN Medium m ON g = m.game "
                + "LEFT JOIN Topic t ON m.topic = t "
                + "WHERE m.title LIKE :title "
                + "AND g.developer LIKE :developer "
                + "AND g.platforms LIKE :platform "
                + "AND t.name LIKE :topic",
            Game.class
        );

        query.setMaxResults(300);
        query.setParameter("title", "%" + title + "%");
        query.setParameter("developer", "%" + developer + "%");
        query.setParameter("platform", "%" + platform + "%");
        query.setParameter("topic", "%" + topic + "%");

        return new LinkedList<>(query.getResultList());
    }

    public List<Game> getAll() {
        return super.getAll(Game.class);
    }
}
