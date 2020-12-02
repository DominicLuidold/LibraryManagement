package at.fhv.teamg.librarymanagement.server.persistence.dao;

import at.fhv.teamg.librarymanagement.server.persistence.BaseDao;
import at.fhv.teamg.librarymanagement.server.persistence.entity.Reservation;
import java.util.Optional;
import java.util.UUID;

public class ReservationDao extends BaseDao<Reservation> {
    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Reservation> find(UUID uuid) {
        return this.find(Reservation.class, uuid);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Reservation> persist(Reservation elem) {
        return this.persist(Reservation.class, elem);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Reservation> update(Reservation elem) {
        return this.update(Reservation.class, elem);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean remove(Reservation elem) {
        return this.remove(Reservation.class, elem);
    }
}
