package at.fhv.teamg.librarymanagement.server.persistence.dao;

import at.fhv.teamg.librarymanagement.server.persistence.BaseDao;
import at.fhv.teamg.librarymanagement.server.persistence.entity.UserRole;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UserRoleDao extends BaseDao<UserRole> {
    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<UserRole> find(UUID uuid) {
        return this.find(UserRole.class, uuid);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<UserRole> persist(UserRole elem) {
        return this.persist(UserRole.class, elem);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<UserRole> update(UserRole elem) {
        return this.update(UserRole.class, elem);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean remove(UserRole elem) {
        return this.remove(UserRole.class, elem);
    }

    public List<UserRole> getAll() {
        return super.getAll(UserRole.class);
    }
}
