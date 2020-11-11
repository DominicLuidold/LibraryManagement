package at.fhv.teamg.librarymanagement.server.persistance.dao;

import at.fhv.teamg.librarymanagement.server.persistance.BaseDao;
import at.fhv.teamg.librarymanagement.server.persistance.entity.User;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class UserDao extends BaseDao<User> {
    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<User> find(UUID uuid) {
        return this.find(User.class, uuid);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<User> persist(User elem) {
        return this.persist(User.class, elem);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<User> update(User elem) {
        return this.update(User.class, elem);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean remove(User elem) {
        return this.remove(User.class, elem);
    }

    public List<User> getAll() {
        return super.getAll(User.class);
    }
}
