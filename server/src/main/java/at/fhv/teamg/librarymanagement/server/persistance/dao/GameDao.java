package at.fhv.teamg.librarymanagement.server.persistance.dao;

import at.fhv.teamg.librarymanagement.server.persistance.BaseDao;
import at.fhv.teamg.librarymanagement.server.persistance.entity.Game;
import java.util.Optional;
import java.util.UUID;

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
}
