package at.fhv.teamg.librarymanagement.server.persistence.dao;

import at.fhv.teamg.librarymanagement.server.persistence.BaseDao;
import at.fhv.teamg.librarymanagement.server.persistence.entity.MediumCopy;
import java.util.Optional;
import java.util.UUID;

public class MediumCopyDao extends BaseDao<MediumCopy> {
    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<MediumCopy> find(UUID uuid) {
        return find(MediumCopy.class, uuid);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<MediumCopy> persist(MediumCopy elem) {
        return persist(MediumCopy.class, elem);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<MediumCopy> update(MediumCopy elem) {
        return update(MediumCopy.class, elem);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean remove(MediumCopy elem) {
        return remove(MediumCopy.class, elem);
    }
}
