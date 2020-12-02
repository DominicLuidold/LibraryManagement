package at.fhv.teamg.librarymanagement.server.persistance.dao;

import at.fhv.teamg.librarymanagement.server.persistance.BaseDao;
import at.fhv.teamg.librarymanagement.server.persistance.entity.Medium;
import at.fhv.teamg.librarymanagement.server.persistance.entity.Medium;
import java.util.Optional;
import java.util.UUID;

public class MediumDao extends BaseDao<Medium> {
    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Medium> find(UUID uuid) {
        return this.find(Medium.class, uuid);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Medium> persist(Medium elem) {
        return this.persist(Medium.class, elem);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Medium> update(Medium elem) {
        return this.update(Medium.class, elem);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean remove(Medium elem) {
        return this.remove(Medium.class, elem);
    }
}
