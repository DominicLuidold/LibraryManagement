package at.fhv.teamg.librarymanagement.server.domain;

import at.fhv.teamg.librarymanagement.server.domain.common.Utils;
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
import at.fhv.teamg.librarymanagement.shared.dto.EmptyDto;
import at.fhv.teamg.librarymanagement.shared.dto.GameDto;
import at.fhv.teamg.librarymanagement.shared.dto.MessageDto;
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
     * Creates a new reservation.
     *
     * @param reservationDto Dto without id
     * @return Newly created Reservation
     */
    public MessageDto<ReservationDto> createReservation(ReservationDto reservationDto) {
        Optional<Medium> mediumOptional = findMediumById(reservationDto.getMediumId());
        if (mediumOptional.isEmpty()) {
            return Utils.createMessageResponse(
                "Could not find specified medium",
                MessageDto.MessageType.FAILURE
            );
        }

        Medium medium = mediumOptional.get();
        for (MediumCopy mediumCopy : medium.getCopies()) {
            if (mediumCopy.isAvailable()) {
                return Utils.createMessageResponse(
                    "Selected medium has available copies",
                    MessageDto.MessageType.FAILURE
                );
            }
        }

        Optional<User> userOptional = findUserById(reservationDto.getUserId());
        if (userOptional.isEmpty()) {
            return Utils.createMessageResponse(
                "Could not process provided user information",
                MessageDto.MessageType.ERROR
            );
        }

        User user = userOptional.get();
        if (!user.getRole().getName().equals(UserRoleName.Customer)) {
            return Utils.createMessageResponse(
                "User is not allowed to reserve a medium",
                MessageDto.MessageType.FAILURE
            );
        }

        for (Reservation reservation : user.getReservations()) {
            if (medium.getId().equals(reservation.getMedium().getId())) {
                return Utils.createMessageResponse(
                    "Only one reservation per user is allowed",
                    MessageDto.MessageType.FAILURE
                );
            }
        }

        Reservation reservation = new Reservation();
        reservation.setId(UUID.randomUUID());
        reservation.setEndDate(reservationDto.getEndDate());
        reservation.setStartDate(reservationDto.getStartDate());
        reservation.setMedium(medium);
        reservation.setUser(user);

        if (updateReservation(reservation).isEmpty()) {
            return Utils.createMessageResponse(
                "Updating reservation information has failed",
                MessageDto.MessageType.ERROR
            );
        }

        ReservationDto.ReservationDtoBuilder builder =
            new ReservationDto.ReservationDtoBuilder(reservation.getId());

        builder.startDate(reservation.getStartDate())
            .endDate(reservation.getEndDate())
            .mediumId(reservation.getMedium().getId())
            .userId(reservation.getUser().getId());

        return Utils.createMessageResponse(
            "Reservation created successfully",
            MessageDto.MessageType.SUCCESS,
            builder.build()
        );
    }

    /**
     * Deletes a reservation.
     *
     * @param reservationDto The reservation to delete
     * @return A MessageDto
     */
    public MessageDto<EmptyDto> deleteReservation(ReservationDto reservationDto) {
        Optional<Reservation> reservationOptional = findReservationById(reservationDto.getId());
        if (reservationOptional.isEmpty()) {
            return Utils.createMessageResponse(
                "Could not find specified reservation",
                MessageDto.MessageType.FAILURE
            );
        }

        Optional<Medium> mediumOptional = findMediumById(reservationDto.getMediumId());
        if (mediumOptional.isEmpty()) {
            return Utils.createMessageResponse(
                "Could not find specified medium",
                MessageDto.MessageType.FAILURE
            );
        }

        Reservation reservation = reservationOptional.get();
        if (!removeReservation(reservation)) {
            return Utils.createMessageResponse(
                "Could not remove reservation",
                MessageDto.MessageType.ERROR
            );
        }

        return Utils.createMessageResponse(
            "Reservation removed successfully",
            MessageDto.MessageType.SUCCESS
        );
    }

    protected Optional<Medium> findMediumById(UUID id) {
        MediumDao dao = new MediumDao();
        return dao.find(id);
    }

    protected Optional<Reservation> findReservationById(UUID id) {
        return new ReservationDao().find(id);
    }

    protected Optional<Reservation> updateReservation(Reservation reservation) {
        return new ReservationDao().update(reservation);
    }

    protected boolean removeReservation(Reservation reservation) {
        return new ReservationDao().remove(reservation);
    }
}
