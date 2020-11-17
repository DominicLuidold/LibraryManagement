package at.fhv.teamg.librarymanagement.server.domain;

import at.fhv.teamg.librarymanagement.server.persistance.dao.MediumDao;
import at.fhv.teamg.librarymanagement.server.persistance.dao.ReservationDao;
import at.fhv.teamg.librarymanagement.server.persistance.entity.Book;
import at.fhv.teamg.librarymanagement.server.persistance.entity.Dvd;
import at.fhv.teamg.librarymanagement.server.persistance.entity.Game;
import at.fhv.teamg.librarymanagement.server.persistance.entity.Medium;
import at.fhv.teamg.librarymanagement.server.persistance.entity.MediumCopy;
import at.fhv.teamg.librarymanagement.server.persistance.entity.Reservation;
import at.fhv.teamg.librarymanagement.server.persistance.entity.User;
import at.fhv.teamg.librarymanagement.shared.dto.BookDto;
import at.fhv.teamg.librarymanagement.shared.dto.DvdDto;
import at.fhv.teamg.librarymanagement.shared.dto.GameDto;
import at.fhv.teamg.librarymanagement.shared.dto.ReservationDto;
import at.fhv.teamg.librarymanagement.shared.enums.UserRoleName;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ReservationService extends BaseMediaService {
    /**
     * Get List of reservations.
     *
     * @param bookDto BookDto with id
     * @return List of reservations
     */
    public List<ReservationDto> getReservations(BookDto bookDto) {
        Optional<Book> bookOptional = findBookById(bookDto.getId());

        Medium medium;
        if (bookOptional.isPresent()) {
            medium = bookOptional.get().getMedium();
        } else {
            return new LinkedList<>();
        }

        return getReservations(medium);
    }

    /**
     * Get List of reservations.
     *
     * @param dvdDto DvdDto with id
     * @return List of reservations
     */
    public List<ReservationDto> getReservations(DvdDto dvdDto) {
        Optional<Dvd> dvdOptional = findDvdById(dvdDto.getId());

        Medium medium;
        if (dvdOptional.isPresent()) {
            medium = dvdOptional.get().getMedium();
        } else {
            return new LinkedList<>();
        }

        return getReservations(medium);
    }

    /**
     * Get List of reservations.
     *
     * @param gameDto GameDto with id
     * @return List of reservations
     */
    public List<ReservationDto> getReservations(GameDto gameDto) {
        Optional<Game> gameOptional = findGameById(gameDto.getId());

        Medium medium;
        if (gameOptional.isPresent()) {
            medium = gameOptional.get().getMedium();
        } else {
            return new LinkedList<>();
        }

        return getReservations(medium);
    }

    private List<ReservationDto> getReservations(Medium medium) {
        List<ReservationDto> reservations = new LinkedList<>();

        medium.getReservations().forEach(reservation -> {
            ReservationDto.ReservationDtoBuilder builder =
                new ReservationDto.ReservationDtoBuilder(reservation.getId());

            builder.endDate(reservation.getEndDate())
                .mediumId(medium.getId())
                .mediumName(medium.getTitle())
                .startDate(reservation.getStartDate())
                .userId(reservation.getUser().getId())
                .userName(reservation.getUser().getName());

            reservations.add(builder.build());
        });

        return reservations;
    }

    /**
     * Create a new Reservation.
     *
     * @param reservationDto Dto without id
     * @return Newly created Reservation
     */
    public Optional<ReservationDto> createReservation(ReservationDto reservationDto) {
        Optional<Medium> medium = findMediumById(reservationDto.getMediumId());
        if (!medium.isPresent()) {
            return Optional.empty();
        }

        for (MediumCopy mediumCopy : medium.get().getCopies()) {
            if (mediumCopy.isAvailable()) {
                return Optional.empty();
            }
        }

        Optional<User> user = findUserById(reservationDto.getUserId());
        if (!user.isPresent()) {
            return Optional.empty();
        }

        if (!user.get().getRole().getName().equals(UserRoleName.Customer)) {
            return Optional.empty();
        }

        Reservation reservation = new Reservation();
        reservation.setId(UUID.randomUUID());
        reservation.setEndDate(reservationDto.getEndDate());
        reservation.setStartDate(reservationDto.getStartDate());
        reservation.setMedium(medium.get());
        reservation.setUser(user.get());

        if (!updateReservation(reservation).isPresent()) {
            return Optional.empty();
        }

        ReservationDto.ReservationDtoBuilder builder =
            new ReservationDto.ReservationDtoBuilder(reservation.getId());

        builder.startDate(reservation.getStartDate())
            .endDate(reservation.getEndDate())
            .mediumId(reservation.getMedium().getId())
            .userId(reservation.getUser().getId());

        return Optional.of(builder.build());
    }

    protected Optional<Medium> findMediumById(UUID id) {
        MediumDao dao = new MediumDao();
        return dao.find(id);
    }

    protected Optional<Reservation> updateReservation(Reservation reservation) {
        ReservationDao dao = new ReservationDao();
        return dao.update(reservation);
    }
}
