package at.fhv.teamg.librarymanagement.client.rmi;

import at.fhv.teamg.librarymanagement.shared.dto.BookDto;
import at.fhv.teamg.librarymanagement.shared.dto.DvdDto;
import at.fhv.teamg.librarymanagement.shared.dto.GameDto;
import at.fhv.teamg.librarymanagement.shared.dto.LendingDto;
import at.fhv.teamg.librarymanagement.shared.dto.MediumCopyDto;
import at.fhv.teamg.librarymanagement.shared.dto.ReservationDto;
import at.fhv.teamg.librarymanagement.shared.dto.TopicDto;
import at.fhv.teamg.librarymanagement.shared.ifaces.LibraryFactoryInterface;
import at.fhv.teamg.librarymanagement.shared.ifaces.LibraryInterface;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RmiClient implements LibraryInterface {

    private static final Logger LOG = LogManager.getLogger(RmiClient.class);
    private static RmiClient instance;
    private LibraryInterface library;

    /**
     * Singleton Client for RMI.
     */
    private RmiClient() {

        try {
            Registry registry = LocateRegistry.getRegistry("127.0.0.1");
            LibraryFactoryInterface libraryFactory =
                    (LibraryFactoryInterface) Naming.lookup("rmi://localhost:9988/libraryfactory");
            library = libraryFactory.getLibrary();
        } catch (Exception e) {
            LOG.error(e);
        }
    }

    /**
     * Returns a singleton instance.
     *
     * @return RmiClient Singleton Instance
     */
    public static RmiClient getInstance() {
        if (instance == null) {
            instance = new RmiClient();
        }
        return instance;
    }

    @Override
    public List<GameDto> searchGame(GameDto gameDto) throws RemoteException {
        return library.searchGame(gameDto);
    }

    @Override
    public List<BookDto> searchBook(BookDto bookDto) throws RemoteException {
        System.out.println("searchBook");
        return library.searchBook(bookDto);
    }

    @Override
    public List<DvdDto> searchDvd(DvdDto dvdDto) throws RemoteException {
        return library.searchDvd(dvdDto);
    }

    @Override
    public Optional<GameDto> getGameDetail(GameDto gameDto) throws RemoteException {
        return library.getGameDetail(gameDto);
    }

    @Override
    public Optional<BookDto> getBookDetail(BookDto bookDto) throws RemoteException {
        return library.getBookDetail(bookDto);
    }

    @Override
    public Optional<DvdDto> getDvdDetail(DvdDto dvdDto) throws RemoteException {
        return library.getDvdDetail(dvdDto);
    }

    @Override
    public List<MediumCopyDto> getAllGameCopies(GameDto gameDto) throws RemoteException {
        return library.getAllGameCopies(gameDto);
    }

    @Override
    public List<MediumCopyDto> getAllBookCopies(BookDto bookDto) throws RemoteException {
        return library.getAllBookCopies(bookDto);
    }

    @Override
    public List<MediumCopyDto> getAllDvdCopies(DvdDto dvdDto) throws RemoteException {
        return library.getAllDvdCopies(dvdDto);
    }

    @Override
    public List<ReservationDto> getAllGameReservations(GameDto gameDto) throws RemoteException {
        return library.getAllGameReservations(gameDto);
    }

    @Override
    public List<ReservationDto> getAllBookReservations(BookDto bookDto) throws RemoteException {
        return library.getAllBookReservations(bookDto);
    }

    @Override
    public List<ReservationDto> getAllDvdReservations(DvdDto dvdDto) throws RemoteException {
        return library.getAllDvdReservations(dvdDto);
    }

    @Override
    public Optional<ReservationDto> reserveGame(ReservationDto reservationDto)
            throws RemoteException {
        return library.reserveGame(reservationDto);
    }

    @Override
    public Optional<ReservationDto> reserveBook(ReservationDto reservationDto)
            throws RemoteException {
        return library.reserveBook(reservationDto);
    }

    @Override
    public Optional<ReservationDto> reserveDvd(ReservationDto reservationDto)
            throws RemoteException {
        return library.reserveDvd(reservationDto);
    }

    @Override
    public Optional<LendingDto> lendGame(LendingDto lendingDto) throws RemoteException {
        return library.lendGame(lendingDto);
    }

    @Override
    public Optional<LendingDto> lendBook(LendingDto lendingDto) throws RemoteException {
        return library.lendBook(lendingDto);
    }

    @Override
    public Optional<LendingDto> lendDvd(LendingDto lendingDto) throws RemoteException {
        return library.lendDvd(lendingDto);
    }

    @Override
    public Boolean returnGame(MediumCopyDto mediumCopyDto) throws RemoteException {
        return library.returnGame(mediumCopyDto);
    }

    @Override
    public Boolean returnBook(MediumCopyDto mediumCopyDto) throws RemoteException {
        return library.returnBook(mediumCopyDto);
    }

    @Override
    public Boolean returnDvd(MediumCopyDto mediumCopyDto) throws RemoteException {
        return library.returnDvd(mediumCopyDto);
    }

    @Override
    public List<TopicDto> getAllTopics() throws RemoteException {
        return library.getAllTopics();
    }
}