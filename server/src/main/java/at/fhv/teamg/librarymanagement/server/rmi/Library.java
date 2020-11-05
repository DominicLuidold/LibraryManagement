package at.fhv.teamg.librarymanagement.server.rmi;

import at.fhv.teamg.librarymanagement.server.persistance.entity.Dvd;
import at.fhv.teamg.librarymanagement.shared.dto.BookDto;
import at.fhv.teamg.librarymanagement.shared.dto.DvdDto;
import at.fhv.teamg.librarymanagement.shared.dto.GameDto;
import at.fhv.teamg.librarymanagement.shared.dto.LendingDto;
import at.fhv.teamg.librarymanagement.shared.dto.MediumCopyDto;
import at.fhv.teamg.librarymanagement.shared.dto.ReservationDto;
import at.fhv.teamg.librarymanagement.shared.ifaces.LibraryInterface;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Library extends UnicastRemoteObject implements LibraryInterface {
    private static final Logger LOG = LogManager.getLogger(Library.class);

    //Fix exception to match super constructor
    public Library() throws RemoteException {}

    @Override
    public List<GameDto> searchGame(GameDto gameDto) throws RemoteException {
        List<GameDto> mockResult = new ArrayList<>();
        mockResult.add(new GameDto.GameDtoBuilder(UUID.randomUUID())
            .title("Lord of the Rings - The Fellowship of the Ring")
            .platforms("PC")
            .developer("Dev1")
            .releaseDate(LocalDate.now())
            .build());
        mockResult.add(new GameDto.GameDtoBuilder(UUID.randomUUID())
            .title("The Lord of the Rings - The Two Towers")
            .platforms("XBOX One")
            .developer("DICE")
            .releaseDate(LocalDate.now())
            .build());
        mockResult.add(new GameDto.GameDtoBuilder(UUID.randomUUID())
            .title("Harry Potter and the Philosopher`s Stone")
            .platforms("PlayStation 5")
            .developer("Dev2")
            .releaseDate(LocalDate.now())
            .build());
        return mockResult;
    }

    @Override
    public List<BookDto> searchBook(BookDto bookDto) throws RemoteException {
        List<BookDto> mockResult = new ArrayList<>();
        mockResult.add(new BookDto.BookDtoBuilder(UUID.randomUUID())
            .title("Lord of the Rings - The Fellowship of the Ring")
            .author("J.R.R. Tolkien")
            .storageLocation("B-01")
            .build());
        mockResult.add(new BookDto.BookDtoBuilder(UUID.randomUUID())
            .title("The Lord of the Rings - The Two Towers")
            .author("J.R.R. Tolkien")
            .storageLocation("B-02")
            .build());
        mockResult.add(new BookDto.BookDtoBuilder(UUID.randomUUID())
            .title("Harry Potter and the Philosopher`s Stone")
            .author("J.K. Rowling")
            .storageLocation("B-03")
            .build());
        return mockResult;
    }

    @Override
    public List<DvdDto> searchDvd(DvdDto dvdDto) throws RemoteException {
        List<DvdDto> mockResult = new ArrayList<>();
        mockResult.add(new DvdDto.DvdDtoBuilder(UUID.randomUUID())
            .title("Lord of the Rings - The Fellowship of the Ring")
            .director("J.R.R. Tolkien")
            .storageLocation("M-1")
            .releaseDate(LocalDate.now())
            .build());
        mockResult.add(new DvdDto.DvdDtoBuilder(UUID.randomUUID())
            .title("The Lord of the Rings - The Two Towers")
            .director("J.R.R. Tolkien")
            .storageLocation("M-2")
            .releaseDate(LocalDate.now())
            .build());
        mockResult.add(new DvdDto.DvdDtoBuilder(UUID.randomUUID())
            .title("Harry Potter and the Philosopher`s Stone")
            .director("J.R.R. Tolkien")
            .storageLocation("M-3")
            .releaseDate(LocalDate.now())
            .build());
        return mockResult;
    }

    @Override
    public GameDto getGameDetail(GameDto gameDto) throws RemoteException {
        return null;
    }

    @Override
    public BookDto getBookDetail(BookDto bookDto) throws RemoteException {
        return null;
    }

    @Override
    public DvdDto getDvdDetail(DvdDto dvdDto) throws RemoteException {
        return null;
    }

    @Override
    public List<MediumCopyDto> getAllGameCopies(GameDto gameDto) throws RemoteException {
        return null;
    }

    @Override
    public List<MediumCopyDto> getAllBookCopies(BookDto bookDto) throws RemoteException {
        return null;
    }

    @Override
    public List<MediumCopyDto> getAllDvdCopies(DvdDto dvdDto) throws RemoteException {
        return null;
    }

    @Override
    public List<ReservationDto> getAllGameReservations(GameDto gameDto) throws RemoteException {
        return null;
    }

    @Override
    public List<ReservationDto> getAllBookReservations(BookDto bookDto) throws RemoteException {
        return null;
    }

    @Override
    public List<ReservationDto> getAllDvdReservations(DvdDto dvdDto) throws RemoteException {
        return null;
    }

    @Override
    public ReservationDto reserveGame(GameDto gameDto) throws RemoteException {
        return null;
    }

    @Override
    public ReservationDto reserveBook(BookDto bookDto) throws RemoteException {
        return null;
    }

    @Override
    public ReservationDto reserveDvd(DvdDto dvdDto) throws RemoteException {
        return null;
    }

    @Override
    public LendingDto lendGame(LendingDto lendingDto) throws RemoteException {
        return null;
    }

    @Override
    public LendingDto lendBook(LendingDto lendingDto) throws RemoteException {
        return null;
    }

    @Override
    public LendingDto lendDvd(LendingDto lendingDto) throws RemoteException {
        return null;
    }

    @Override
    public Boolean returnGame(MediumCopyDto copyDto) throws RemoteException {
        return null;
    }

    @Override
    public Boolean returnBook(MediumCopyDto copyDto) throws RemoteException {
        return null;
    }

    @Override
    public Boolean returnDvd(MediumCopyDto copyDto) throws RemoteException {
        return null;
    }
}
