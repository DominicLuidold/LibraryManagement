package at.fhv.teamg.librarymanagement.server.persistance.dao;

import at.fhv.teamg.librarymanagement.server.persistance.BaseDao;
import at.fhv.teamg.librarymanagement.server.persistance.entity.User;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UserDao extends BaseDao<User> {
    private static final Logger LOG = LogManager.getLogger(UserDao.class);

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

    /**
     * Find User by its Username.
     *
     * @param username to search for.
     * @return a Optional of the User.
     */
    public Optional<User> findByName(String username) {
        TypedQuery<User> query = entityManager.createQuery(
            "SELECT u FROM User u "
                + "JOIN UserRole ur ON ur = u.role "
                + "WHERE u.username = :username ",
            User.class
        );
        query.setParameter("username", "%" + username + "%");

        Optional<User> user = Optional.empty();

        try {
            user = Optional.of(query.getSingleResult());
        } catch (NoResultException e) {
            LOG.error("Finding user by username failed", e);
        }
        return user;
    }
}
