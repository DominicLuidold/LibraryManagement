package at.fhv.teamg.librarymanagement.client.rmi;

import at.fhv.teamg.librarymanagement.shared.dto.BookDto;
import at.fhv.teamg.librarymanagement.shared.dto.DvdDto;
import at.fhv.teamg.librarymanagement.shared.dto.GameDto;
import at.fhv.teamg.librarymanagement.shared.ifaces.LibraryFactoryInterface;
import at.fhv.teamg.librarymanagement.shared.ifaces.LibraryInterface;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.time.LocalDate;
import java.util.UUID;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RmiClient {
    private static final Logger LOG = LogManager.getLogger(RmiClient.class);
    private LibraryInterface library;

    /**
     * Client for RMI.
     */
    public RmiClient() {
        try {
            Registry registry = LocateRegistry.getRegistry("127.0.0.1");
            LibraryFactoryInterface libraryFactory =
                    (LibraryFactoryInterface) Naming.lookup("libraryfactory");
            library = libraryFactory.getLibrary();

            GameDto gameDto = new GameDto.GameDtoBuilder(UUID.randomUUID())
                    .ageRestriction("FSK18")
                    .developer("Axl Rose")
                    .platforms("ALL")
                    .publisher("The Game Company")
                    .releaseDate(LocalDate.MIN)
                    .storageLocation("Not available")
                    .title("Game of Life")
                    .build();

            DvdDto dvdDto = new DvdDto.DvdDtoBuilder(UUID.randomUUID())
                    .title("Not a good Film")
                    .director("Brazzers")
                    .storageLocation("In4ss 69")
                    .releaseDate(LocalDate.now())
                    .durationMinutes("69")
                    .actors("Big Rick wiht big Dick, Man with horsecock, hairstyle doesn't matter")
                    .ageRestriction("FSK35")
                    .build();

            BookDto bookDto = new BookDto.BookDtoBuilder(UUID.randomUUID())
                    .title("no")
                    .author("Siri")
                    .build();

            /*
            BookDto returnbook = library.searchBook(bookDto);
            System.out.println(returnbook.toString());

            GameDto returngame = library.searchGame(gameDto);
            System.out.println(returngame.toString());

            DvdDto returndvd = library.searchDvd(dvdDto);
            System.out.println(returndvd.toString());
*/
        } catch (Exception e) {
            LOG.error(e);
        }
    }
}
