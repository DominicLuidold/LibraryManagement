package at.fhv.teamg.librarymanagement.server.persistance.dao;

import at.fhv.teamg.librarymanagement.server.persistance.BaseDao;
import at.fhv.teamg.librarymanagement.server.persistance.entity.Lending;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class LendingDao extends BaseDao<Lending> {
    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Lending> find(UUID uuid) {
        return this.find(Lending.class, uuid);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Lending> persist(Lending elem) {
        return this.persist(Lending.class, elem);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Lending> update(Lending elem) {
        return this.update(Lending.class, elem);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean remove(Lending elem) {
        return this.remove(Lending.class, elem);
    }

    public List<Lending> getAll() {
        return super.getAll(Lending.class);
    }
}
