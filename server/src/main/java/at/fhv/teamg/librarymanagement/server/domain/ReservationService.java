package at.fhv.teamg.librarymanagement.server.domain;

import at.fhv.teamg.librarymanagement.server.persistance.entity.Book;
import at.fhv.teamg.librarymanagement.server.persistance.entity.Dvd;
import at.fhv.teamg.librarymanagement.server.persistance.entity.Game;
import at.fhv.teamg.librarymanagement.server.persistance.entity.Medium;
import at.fhv.teamg.librarymanagement.shared.dto.BookDto;
import at.fhv.teamg.librarymanagement.shared.dto.DvdDto;
import at.fhv.teamg.librarymanagement.shared.dto.GameDto;
import at.fhv.teamg.librarymanagement.shared.dto.ReservationDto;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

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
                .startDate(reservation.getStartDate())
                .userId(reservation.getUser().getId());

            reservations.add(builder.build());
        });

        return reservations;
    }
}
