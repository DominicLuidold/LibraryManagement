package at.fhv.teamg.librarymanagement.server.rmi;

import at.fhv.teamg.librarymanagement.server.domain.BookService;
import at.fhv.teamg.librarymanagement.server.domain.DvdService;
import at.fhv.teamg.librarymanagement.server.domain.GameService;
import at.fhv.teamg.librarymanagement.shared.dto.BookDto;
import at.fhv.teamg.librarymanagement.shared.dto.DvdDto;
import at.fhv.teamg.librarymanagement.shared.dto.GameDto;
import at.fhv.teamg.librarymanagement.shared.dto.LendingDto;
import at.fhv.teamg.librarymanagement.shared.dto.MediumCopyDto;
import at.fhv.teamg.librarymanagement.shared.dto.ReservationDto;
import at.fhv.teamg.librarymanagement.shared.dto.TopicDto;
import at.fhv.teamg.librarymanagement.shared.ifaces.LibraryInterface;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Library extends UnicastRemoteObject implements LibraryInterface {
    private static final Logger LOG = LogManager.getLogger(Library.class);
    private static final long serialVersionUID = -443483629739057113L;

    public Library() throws RemoteException {
        super();
    }

    @Override
    public List<GameDto> searchGame(GameDto gameDto) throws RemoteException {
        return new GameService().search(gameDto);
    }

    @Override
    public List<BookDto> searchBook(BookDto bookDto) throws RemoteException {
        return new BookService().search(bookDto);
    }

    @Override
    public List<DvdDto> searchDvd(DvdDto dvdDto) throws RemoteException {
        return new DvdService().search(dvdDto);
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

    @Override
    public List<TopicDto> getAllTopics() throws RemoteException {
        return null;
    }
}
