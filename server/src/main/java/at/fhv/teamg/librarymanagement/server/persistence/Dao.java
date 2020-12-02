package at.fhv.teamg.librarymanagement.server.persistence;

import java.util.Optional;
import java.util.UUID;

public interface Dao<T> {
    /**
     * Finds the object based on the provided {@link UUID}.
     *
     * @param uuid The UUID to use
     * @return The object
     */
    Optional<T> find(UUID uuid);

    /**
     * Persists an object.
     *
     * @param elem The object to persist
     * @return {@link Optional#empty()} if persisting not possible
     */
    Optional<T> persist(T elem);

    /**
     * Updates an existing object.
     *
     * @param elem The object to update
     * @return {@link Optional#empty()} if updating not possible
     */
    Optional<T> update(T elem);

    /**
     * Removes an existing object.
     *
     * @param elem The object to remove
     * @return true if removing was successful, false otherwise
     */
    boolean remove(T elem);
}
