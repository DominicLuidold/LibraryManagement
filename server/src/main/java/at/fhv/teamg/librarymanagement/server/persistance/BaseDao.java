package at.fhv.teamg.librarymanagement.server.persistance;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * This class acts as a base for all DAOs by handling the {@link EntityManagerFactory} and
 * {@link EntityManager} and therefore managing the database connections.
 *
 * @param <T> The type of DAO, based on JPA entities
 */
public abstract class BaseDao<T> implements Dao<T> {
    private static final Logger LOG = LogManager.getLogger(BaseDao.class);
    private static final EntityManagerFactory entityManagerFactory =
        Persistence.createEntityManagerFactory(
            "LibraryManagement"
        );
    protected static EntityManager entityManager = entityManagerFactory.createEntityManager();

    /**
     * Finds the object based on the provided {@link UUID}.
     *
     * @param clazz Class of the object
     * @param uuid  The UUID to use
     * @return {@link Optional#ofNullable(T)} containing the entity
     */
    protected Optional<T> find(Class<T> clazz, UUID uuid) {
        return Optional.ofNullable(entityManager.find(clazz, uuid));
    }

    /**
     * Persists an object.
     *
     * @param clazz Class of the object
     * @param elem  The object to persist
     * @return {@link Optional#empty()} if persisting not possible
     */
    protected Optional<T> persist(Class<T> clazz, T elem) {
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.persist(elem);
            transaction.commit();
            return Optional.of(elem);
        } catch (Exception e) {
            if (null != transaction && transaction.isActive()) {
                transaction.rollback();
            }
            LOG.error("Could not persist element", e);
        } finally {
            entityManager.clear();
        }
        return Optional.empty();
    }

    /**
     * Updates an existing object.
     *
     * @param clazz Class of the object
     * @param elem  The object to update
     * @return {@link Optional#empty()} if updating not possible
     */
    protected Optional<T> update(Class<T> clazz, T elem) {
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.merge(elem);
            transaction.commit();
            return Optional.of(elem);
        } catch (Exception e) {
            if (null != transaction && transaction.isActive()) {
                transaction.rollback();
            }
            LOG.error("Could not update element", e);
            return Optional.empty();
        } finally {
            entityManager.clear();
        }
    }

    /**
     * Removes an existing object.
     *
     * @param clazz Class of the object
     * @param elem  The object to remove
     * @return true if removing was successful, false otherwise
     */
    protected boolean remove(Class<T> clazz, T elem) {
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.remove(elem);
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (null != transaction && transaction.isActive()) {
                transaction.rollback();
            }
            LOG.error("Could not remove element", e);
            return false;
        } finally {
            entityManager.clear();
        }
    }

    /**
     * Returns all objects from a class.
     *
     * @param clazz Class of the object
     * @return A List of objects
     */
    protected List<T> getAll(Class<T> clazz) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(clazz);
        Root<T> rootEntry = criteriaQuery.from(clazz);
        CriteriaQuery<T> all = criteriaQuery.select(rootEntry);

        TypedQuery<T> allQuery = entityManager.createQuery(all);
        entityManager.clear();
        return allQuery.getResultList();
    }
}
