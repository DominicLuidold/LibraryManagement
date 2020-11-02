package at.fhv.teamg.librarymanagement.server.persistance.dao;

import at.fhv.teamg.librarymanagement.server.persistance.BaseDao;
import at.fhv.teamg.librarymanagement.server.persistance.entity.Dvd;
import java.util.Optional;
import java.util.UUID;

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
}
