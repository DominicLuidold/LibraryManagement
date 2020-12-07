package at.fhv.teamg.librarymanagement.server.domain;

import at.fhv.teamg.librarymanagement.server.persistence.entity.Book;
import at.fhv.teamg.librarymanagement.server.persistence.entity.Dvd;
import at.fhv.teamg.librarymanagement.server.persistence.entity.Game;
import at.fhv.teamg.librarymanagement.server.persistence.entity.Medium;
import at.fhv.teamg.librarymanagement.server.persistence.entity.MediumCopy;
import at.fhv.teamg.librarymanagement.server.persistence.entity.Reservation;
import at.fhv.teamg.librarymanagement.shared.dto.BookDto;
import at.fhv.teamg.librarymanagement.shared.dto.DvdDto;
import at.fhv.teamg.librarymanagement.shared.dto.GameDto;
import at.fhv.teamg.librarymanagement.shared.dto.MessageDto;
import at.fhv.teamg.librarymanagement.shared.ifaces.Dto;

/**
 * A class containing common functionalities that can be used without any specific context.
 */
public final class Utils {

    /**
     * Determines the availability string (e.g. {@code 3/5}) for a specific {@link Medium}.
     *
     * @param medium The medium to use
     * @return A string containing the availability
     */
    public static String getAvailability(Medium medium) {
        int copies = medium.getCopies().size();

        int availableCopies = 0;
        for (MediumCopy copy : medium.getCopies()) {
            if (copy.isAvailable()) {
                availableCopies++;
            }
        }

        return availableCopies + "/" + copies;
    }

    /**
     * Creates a new {@link MessageDto} with given input.
     *
     * @param message The message
     * @param type    The type of response
     * @return A MessageDto
     */
    public static <T extends Dto> MessageDto<T> createMessageResponse(
        String message,
        MessageDto.MessageType type
    ) {
        return new MessageDto.MessageDtoBuilder<T>()
            .withMessage(message)
            .withType(type)
            .build();
    }

    /**
     * Creates a new {@link MessageDto} with given input and a result ({@link Dto}).
     *
     * @param message The message
     * @param type    The type of response
     * @param result  The result of the performed action
     * @return A MessageDto
     */
    public static <T extends Dto> MessageDto<T> createMessageResponse(
        String message,
        MessageDto.MessageType type,
        T result
    ) {
        return new MessageDto.MessageDtoBuilder<T>()
            .withMessage(message)
            .withType(type)
            .withResult(result)
            .build();
    }

    /**
     * Creates a new {@link BookDto} with given input.
     *
     * @param book         book Entity
     * @param availability String
     * @return BookDto
     */
    public static BookDto createBookDto(Book book, String availability) {
        return new BookDto.BookDtoBuilder(book.getId())
            .author(book.getAuthor())
            .availability(availability)
            .isbn10(book.getIsbn10())
            .isbn13(book.getIsbn13())
            .languageKey(book.getLanguageKey())
            .publisher(book.getPublisher())
            .releaseDate(book.getMedium().getReleaseDate())
            .storageLocation(book.getMedium().getStorageLocation())
            .title(book.getMedium().getTitle())
            .topic(book.getMedium().getTopic().getId())
            .mediumId(book.getMedium().getId())
            .build();
    }

    /**
     * Creates a new {@link DvdDto} with given input.
     *
     * @param dvd          Dvd Entity
     * @param availability String
     * @return DvdDto
     */
    public static DvdDto createDvdDto(Dvd dvd, String availability) {
        return new DvdDto.DvdDtoBuilder(dvd.getId())
            .availability(availability)
            .actors(dvd.getActors())
            .ageRestriction(dvd.getAgeRestriction())
            .durationMinutes(String.valueOf(dvd.getDurationMinutes()))
            .releaseDate(dvd.getMedium().getReleaseDate())
            .storageLocation(dvd.getMedium().getStorageLocation())
            .studio(dvd.getStudio())
            .director(dvd.getDirector())
            .title(dvd.getMedium().getTitle())
            .topic(dvd.getMedium().getTopic().getId())
            .mediumId(dvd.getMedium().getId())
            .build();
    }

    /**
     * Creates a new {@link GameDto} with given input.
     *
     * @param game         game Entity
     * @param availability String
     * @return GameDto
     */
    public static GameDto createGameDto(Game game, String availability) {
        return new GameDto.GameDtoBuilder(game.getId())
            .availability(availability)
            .ageRestriction(game.getAgeRestriction())
            .developer(game.getDeveloper())
            .platforms(game.getPlatforms())
            .publisher(game.getPublisher())
            .releaseDate(game.getMedium().getReleaseDate())
            .storageLocation(game.getMedium().getStorageLocation())
            .title(game.getMedium().getTitle())
            .topic(game.getMedium().getTopic().getId())
            .mediumId(game.getMedium().getId())
            .build();
    }

    /**
     * Creates an returned reserved medium message.
     *
     * @param medium medium entity used to create the message
     * @return message
     */
    public static String createReturnReservationMessage(Medium medium) {
        Reservation reservation = medium.getReservations().stream().findFirst().get();
        int reservationCount = medium.getReservations().size();

        StringBuilder builder = new StringBuilder()
            .append("Returned ")
            .append(medium.getType().getName())
            .append(" ")
            .append(medium.getTitle())
            .append(" is reserved by Customer ")
            .append(reservation.getUser().getName())
            .append(" (")
            .append(reservation.getUser().getUsername())
            .append(")");

        if (reservationCount > 1) {
            builder.append(" and ")
                .append(reservationCount - 1)
                .append(" more");
        }

        return builder.toString();
    }
}
