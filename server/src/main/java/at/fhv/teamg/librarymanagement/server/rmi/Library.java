package at.fhv.teamg.librarymanagement.server.rmi;

import at.fhv.teamg.librarymanagement.server.domain.BookService;
import at.fhv.teamg.librarymanagement.server.domain.DetailService;
import at.fhv.teamg.librarymanagement.server.domain.DvdService;
import at.fhv.teamg.librarymanagement.server.domain.GameService;
import at.fhv.teamg.librarymanagement.server.domain.LendingService;
import at.fhv.teamg.librarymanagement.server.domain.MediumCopyService;
import at.fhv.teamg.librarymanagement.server.domain.ReservationService;
import at.fhv.teamg.librarymanagement.server.domain.TopicService;
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
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Library extends UnicastRemoteObject implements LibraryInterface {
    private static final Logger LOG = LogManager.getLogger(Library.class);
    private static final long serialVersionUID = -443483629739057113L;
    private final GameService gameService = new GameService();
    private final BookService bookService = new BookService();
    private final DvdService dvdService = new DvdService();
    private final DetailService detailService = new DetailService();
    private final MediumCopyService mediumCopyService = new MediumCopyService();
    private final ReservationService reservationService = new ReservationService();
    private final LendingService lendingService = new LendingService();
    private final TopicService topicService = new TopicService();

    public Library() throws RemoteException {
        super();
    }

    @Override
    public List<GameDto> searchGame(GameDto gameDto) throws RemoteException {
        return gameService.search(gameDto);
    }

    @Override
    public List<BookDto> searchBook(BookDto bookDto) throws RemoteException {
        return bookService.search(bookDto);
    }

    @Override
    public List<DvdDto> searchDvd(DvdDto dvdDto) throws RemoteException {
        return dvdService.search(dvdDto);
    }

    @Override
    public Optional<GameDto> getGameDetail(GameDto gameDto) throws RemoteException {
        return detailService.getGameDetail(gameDto);
    }

    @Override
    public Optional<BookDto> getBookDetail(BookDto bookDto) throws RemoteException {
        return detailService.getBookDetail(bookDto);
    }

    @Override
    public Optional<DvdDto> getDvdDetail(DvdDto dvdDto) throws RemoteException {
        return detailService.getDvdDetail(dvdDto);
    }

    @Override
    public List<MediumCopyDto> getAllGameCopies(GameDto gameDto) throws RemoteException {
        return mediumCopyService.getCopies(gameDto);
    }

    @Override
    public List<MediumCopyDto> getAllBookCopies(BookDto bookDto) throws RemoteException {
        return mediumCopyService.getCopies(bookDto);
    }

    @Override
    public List<MediumCopyDto> getAllDvdCopies(DvdDto dvdDto) throws RemoteException {
        return mediumCopyService.getCopies(dvdDto);
    }

    @Override
    public List<ReservationDto> getAllGameReservations(GameDto gameDto) throws RemoteException {
        return reservationService.getReservations(gameDto);
    }

    @Override
    public List<ReservationDto> getAllBookReservations(BookDto bookDto) throws RemoteException {
        return reservationService.getReservations(bookDto);
    }

    @Override
    public List<ReservationDto> getAllDvdReservations(DvdDto dvdDto) throws RemoteException {
        return reservationService.getReservations(dvdDto);
    }

    @Override
    public Optional<ReservationDto> reserveGame(ReservationDto reservationDto)
        throws RemoteException {
        return reservationService.createReservation(reservationDto);
    }

    @Override
    public Optional<ReservationDto> reserveBook(ReservationDto reservationDto)
        throws RemoteException {
        return reservationService.createReservation(reservationDto);
    }

    @Override
    public Optional<ReservationDto> reserveDvd(ReservationDto reservationDto)
        throws RemoteException {
        return reservationService.createReservation(reservationDto);
    }

    @Override
    public Optional<LendingDto> lendGame(LendingDto lendingDto) throws RemoteException {
        return lendingService.createLending(lendingDto);
    }

    @Override
    public Optional<LendingDto> lendBook(LendingDto lendingDto) throws RemoteException {
        return lendingService.createLending(lendingDto);
    }

    @Override
    public Optional<LendingDto> lendDvd(LendingDto lendingDto) throws RemoteException {
        return lendingService.createLending(lendingDto);
    }

    @Override
    public Boolean returnGame(MediumCopyDto copyDto) throws RemoteException {
        return lendingService.returnLending(copyDto);
    }

    @Override
    public Boolean returnBook(MediumCopyDto copyDto) throws RemoteException {
        return lendingService.returnLending(copyDto);
    }

    @Override
    public Boolean returnDvd(MediumCopyDto copyDto) throws RemoteException {
        return lendingService.returnLending(copyDto);
    }

    @Override
    public List<TopicDto> getAllTopics() throws RemoteException {
        return topicService.getAllTopics();
    }
}
