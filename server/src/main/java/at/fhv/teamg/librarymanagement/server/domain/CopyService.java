package at.fhv.teamg.librarymanagement.server.domain;

import at.fhv.teamg.librarymanagement.server.persistance.entity.Book;
import at.fhv.teamg.librarymanagement.server.persistance.entity.Dvd;
import at.fhv.teamg.librarymanagement.server.persistance.entity.Game;
import at.fhv.teamg.librarymanagement.server.persistance.entity.Medium;
import at.fhv.teamg.librarymanagement.shared.dto.BookDto;
import at.fhv.teamg.librarymanagement.shared.dto.DvdDto;
import at.fhv.teamg.librarymanagement.shared.dto.GameDto;
import at.fhv.teamg.librarymanagement.shared.dto.MediumCopyDto;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class CopyService extends BaseMediaService {

    /**
     * Get List of copies.
     *
     * @param bookDto bookDto with id
     * @return List of copies
     */
    public List<MediumCopyDto> getCopies(BookDto bookDto) {
        Optional<Book> bookOptional = findBookById(bookDto.getId());

        Medium medium;
        if (bookOptional.isPresent()) {
            medium = bookOptional.get().getMedium();
        } else {
            return new LinkedList<>();
        }

        return getCopies(medium);
    }

    /**
     * Get List of copies.
     *
     * @param gameDto gameDto with id
     * @return List of copies
     */
    public List<MediumCopyDto> getCopies(GameDto gameDto) {
        Optional<Game> gameOptional = findGameById(gameDto.getId());

        Medium medium;
        if (gameOptional.isPresent()) {
            medium = gameOptional.get().getMedium();
        } else {
            return new LinkedList<>();
        }

        return getCopies(medium);
    }

    /**
     * Get List of copies.
     *
     * @param dvdDto dvdDto with id
     * @return List of copies
     */
    public List<MediumCopyDto> getCopies(DvdDto dvdDto) {
        Optional<Dvd> dvdOptional = findDvdById(dvdDto.getId());

        Medium medium;
        if (dvdOptional.isPresent()) {
            medium = dvdOptional.get().getMedium();
        } else {
            return new LinkedList<>();
        }

        return getCopies(medium);
    }

    private List<MediumCopyDto> getCopies(Medium medium) {
        List<MediumCopyDto> copies = new LinkedList<>();

        medium.getCopies().forEach(copy -> {
            MediumCopyDto.CopyDtoBuilder builder = new MediumCopyDto.CopyDtoBuilder(copy.getId());

            builder.isAvailable(copy.isAvailable())
                .mediumID(medium.getId());

            copies.add(builder.build());
        });

        return copies;
    }
}
