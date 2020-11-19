package at.fhv.teamg.librarymanagement.server.domain;

import at.fhv.teamg.librarymanagement.server.persistance.entity.Book;
import at.fhv.teamg.librarymanagement.server.persistance.entity.Dvd;
import at.fhv.teamg.librarymanagement.server.persistance.entity.Game;
import at.fhv.teamg.librarymanagement.server.persistance.entity.Lending;
import at.fhv.teamg.librarymanagement.server.persistance.entity.Medium;
import at.fhv.teamg.librarymanagement.shared.dto.BookDto;
import at.fhv.teamg.librarymanagement.shared.dto.DvdDto;
import at.fhv.teamg.librarymanagement.shared.dto.GameDto;
import at.fhv.teamg.librarymanagement.shared.dto.MediumCopyDto;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MediumCopyService extends BaseMediaService {
    private static final Logger LOG = LogManager.getLogger(BaseMediaService.class);
    private final LendingService lendingService;

    public MediumCopyService() {
        lendingService = new LendingService();
    }

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
            MediumCopyDto.MediumCopyDtoBuilder builder =
                new MediumCopyDto.MediumCopyDtoBuilder(copy.getId());

            LocalDate lendTill = null;
            UUID lendingUser = null;
            Optional<Lending> possibleLending = lendingService.getCurrentLending(copy.getLending());
            if (possibleLending.isPresent()) {
                lendTill = possibleLending.get().getEndDate();
                lendingUser = possibleLending.get().getUser().getId();
            }

            if (possibleLending.isEmpty() && !copy.isAvailable()) {
                LOG.error("Copy [{}] is unavailable but no lending found", copy.getId());
            }

            builder.isAvailable(copy.isAvailable())
                .mediumID(medium.getId())
                .lendTill(lendTill)
                .currentLendingUser(lendingUser);

            copies.add(builder.build());
        });

        return copies;
    }
}
