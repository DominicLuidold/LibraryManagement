package at.fhv.teamg.librarymanagement.server.domain;

import at.fhv.teamg.librarymanagement.server.persistence.entity.Book;
import at.fhv.teamg.librarymanagement.server.persistence.entity.Dvd;
import at.fhv.teamg.librarymanagement.server.persistence.entity.Game;
import at.fhv.teamg.librarymanagement.shared.dto.BookDto;
import at.fhv.teamg.librarymanagement.shared.dto.DvdDto;
import at.fhv.teamg.librarymanagement.shared.dto.GameDto;
import java.util.Optional;
import java.util.UUID;

public class DetailService extends BaseMediaService {
    /**
     * Get Book detail by id.
     *
     * @param bookDto BookDto with id
     * @return BookDto
     */
    public Optional<BookDto> getBookDetail(BookDto bookDto) {
        UUID id = bookDto.getId();
        Optional<Book> bookOptional = findBookById(id);
        BookDto.BookDtoBuilder builder = new BookDto.BookDtoBuilder(id);

        if (bookOptional.isPresent()) {
            Book book = bookOptional.get();
            builder.author(book.getAuthor())
                .availability(Utils.getAvailability(book.getMedium()))
                .isbn10(book.getIsbn10())
                .isbn13(book.getIsbn13())
                .languageKey(book.getLanguageKey())
                .publisher(book.getPublisher())
                .releaseDate(book.getMedium().getReleaseDate())
                .storageLocation(book.getMedium().getStorageLocation())
                .title(book.getMedium().getTitle())
                .topic(book.getMedium().getTopic().getId())
                .mediumId(book.getMedium().getId());

            return Optional.of(builder.build());
        }

        return Optional.empty();
    }

    /**
     * Get Dvd Detail by Id.
     *
     * @param dvdDto DvdDto with id
     * @return DvdDto
     */
    public Optional<DvdDto> getDvdDetail(DvdDto dvdDto) {
        UUID id = dvdDto.getId();
        Optional<Dvd> dvdOptional = findDvdById(id);
        DvdDto.DvdDtoBuilder builder = new DvdDto.DvdDtoBuilder(id);

        if (dvdOptional.isPresent()) {
            Dvd dvd = dvdOptional.get();
            builder.actors(dvd.getActors())
                .availability(Utils.getAvailability(dvd.getMedium()))
                .ageRestriction(dvd.getAgeRestriction())
                .durationMinutes(String.valueOf(dvd.getDurationMinutes()))
                .releaseDate(dvd.getMedium().getReleaseDate())
                .storageLocation(dvd.getMedium().getStorageLocation())
                .studio(dvd.getStudio())
                .director(dvd.getDirector())
                .title(dvd.getMedium().getTitle())
                .topic(dvd.getMedium().getTopic().getId())
                .mediumId(dvd.getMedium().getId());

            return Optional.of(builder.build());
        }

        return Optional.empty();
    }

    /**
     * Get Game Detail by Id.
     *
     * @param gameDto GameDto with id
     * @return GameDto
     */
    public Optional<GameDto> getGameDetail(GameDto gameDto) {
        UUID id = gameDto.getId();
        Optional<Game> gameOptional = findGameById(id);
        GameDto.GameDtoBuilder builder = new GameDto.GameDtoBuilder(id);

        if (gameOptional.isPresent()) {
            Game game = gameOptional.get();
            builder.ageRestriction(game.getAgeRestriction())
                .availability(Utils.getAvailability(game.getMedium()))
                .developer(game.getDeveloper())
                .platforms(game.getPlatforms())
                .publisher(game.getPublisher())
                .releaseDate(game.getMedium().getReleaseDate())
                .storageLocation(game.getMedium().getStorageLocation())
                .title(game.getMedium().getTitle())
                .topic(game.getMedium().getTopic().getId())
                .mediumId(game.getMedium().getId());

            return Optional.of(builder.build());
        }

        return Optional.empty();
    }
}
