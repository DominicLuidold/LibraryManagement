package at.fhv.teamg.librarymanagement.client.rmi;

import at.fhv.teamg.librarymanagement.shared.dto.BookDto;
import at.fhv.teamg.librarymanagement.shared.dto.DvdDto;
import at.fhv.teamg.librarymanagement.shared.dto.EmptyDto;
import at.fhv.teamg.librarymanagement.shared.dto.GameDto;
import at.fhv.teamg.librarymanagement.shared.dto.LendingDto;
import at.fhv.teamg.librarymanagement.shared.dto.LoginDto;
import at.fhv.teamg.librarymanagement.shared.dto.MediumCopyDto;
import at.fhv.teamg.librarymanagement.shared.dto.Message;
import at.fhv.teamg.librarymanagement.shared.dto.MessageDto;
import at.fhv.teamg.librarymanagement.shared.dto.ReservationDto;
import at.fhv.teamg.librarymanagement.shared.dto.TopicDto;
import at.fhv.teamg.librarymanagement.shared.dto.UserDto;
import at.fhv.teamg.librarymanagement.shared.ifaces.LibraryFactoryInterface;
import at.fhv.teamg.librarymanagement.shared.ifaces.LibraryInterface;
import at.fhv.teamg.librarymanagement.shared.ifaces.MessageClientInterface;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RmiClient implements LibraryInterface {
    private static final Logger LOG = LogManager.getLogger(RmiClient.class);
    private static String SERVER_ADDRESS = "vsts-team007.westeurope.cloudapp.azure.com";
    private static RmiClient RMI_INSTANCE;
    private LibraryInterface library;

    /**
     * Singleton Client for RMI.
     */
    private RmiClient() {
        // Fix RMI hostname to prevent RMI issues
        System.setProperty(
            "java.rmi.server.hostname",
            SERVER_ADDRESS
        );

        try {
            LibraryFactoryInterface libraryFactory = (LibraryFactoryInterface) Naming.lookup(
                "rmi://" + SERVER_ADDRESS + "/libraryfactory"
            );
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
        if (RMI_INSTANCE == null) {
            RMI_INSTANCE = new RmiClient();
        }
        return RMI_INSTANCE;
    }

    public static void setServerAddress(String address) {
        RMI_INSTANCE = null;
        SERVER_ADDRESS = address;
    }

    /* #### SEARCH #### */

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
    public List<GameDto> searchGame(GameDto gameDto) throws RemoteException {
        return library.searchGame(gameDto);
    }

    /* #### DETAILS #### */

    @Override
    public BookDto getBookDetail(BookDto bookDto) throws RemoteException {
        return library.getBookDetail(bookDto);
    }

    @Override
    public DvdDto getDvdDetail(DvdDto dvdDto) throws RemoteException {
        return library.getDvdDetail(dvdDto);
    }

    @Override
    public GameDto getGameDetail(GameDto gameDto) throws RemoteException {
        return library.getGameDetail(gameDto);
    }

    /* #### GET ALL #### */

    @Override
    public List<MediumCopyDto> getAllBookCopies(BookDto bookDto) throws RemoteException {
        return library.getAllBookCopies(bookDto);
    }

    @Override
    public List<MediumCopyDto> getAllDvdCopies(DvdDto dvdDto) throws RemoteException {
        return library.getAllDvdCopies(dvdDto);
    }

    @Override
    public List<MediumCopyDto> getAllGameCopies(GameDto gameDto) throws RemoteException {
        return library.getAllGameCopies(gameDto);
    }

    @Override
    public List<TopicDto> getAllTopics() throws RemoteException {
        return library.getAllTopics();
    }

    @Override
    public List<UserDto> getAllUsers() throws RemoteException {
        return library.getAllUsers();
    }

    @Override
    public List<UserDto> getAllCustomers() throws RemoteException {
        return library.getAllCustomers();
    }

    /* #### RESERVATION #### */

    @Override
    public MessageDto<ReservationDto> reserveBook(ReservationDto reservationDto)
        throws RemoteException {
        return library.reserveBook(reservationDto);
    }

    @Override
    public MessageDto<ReservationDto> reserveDvd(ReservationDto reservationDto)
        throws RemoteException {
        return library.reserveDvd(reservationDto);
    }

    @Override
    public MessageDto<ReservationDto> reserveGame(ReservationDto reservationDto)
        throws RemoteException {
        return library.reserveGame(reservationDto);
    }

    @Override
    public MessageDto<EmptyDto> removeReservation(ReservationDto reservationDto)
        throws RemoteException {
        return library.removeReservation(reservationDto);
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
    public List<ReservationDto> getAllGameReservations(GameDto gameDto) throws RemoteException {
        return library.getAllGameReservations(gameDto);
    }

    /* ##### LENDING ##### */

    @Override
    public MessageDto<LendingDto> lendBook(LendingDto lendingDto) throws RemoteException {
        return library.lendBook(lendingDto);
    }

    @Override
    public MessageDto<LendingDto> lendDvd(LendingDto lendingDto) throws RemoteException {
        return library.lendDvd(lendingDto);
    }

    @Override
    public MessageDto<LendingDto> lendGame(LendingDto lendingDto) throws RemoteException {
        return library.lendGame(lendingDto);
    }

    @Override
    public MessageDto<EmptyDto> extendBook(MediumCopyDto mediumCopyDto) throws RemoteException {
        return library.extendBook(mediumCopyDto);
    }

    @Override
    public MessageDto<EmptyDto> extendDvd(MediumCopyDto mediumCopyDto) throws RemoteException {
        return library.extendDvd(mediumCopyDto);
    }

    @Override
    public MessageDto<EmptyDto> extendGame(MediumCopyDto mediumCopyDto) throws RemoteException {
        return library.extendGame(mediumCopyDto);
    }

    @Override
    public MessageDto<EmptyDto> returnBook(MediumCopyDto mediumCopyDto) throws RemoteException {
        return library.returnBook(mediumCopyDto);
    }

    @Override
    public MessageDto<EmptyDto> returnDvd(MediumCopyDto mediumCopyDto) throws RemoteException {
        return library.returnDvd(mediumCopyDto);
    }

    @Override
    public MessageDto<EmptyDto> returnGame(MediumCopyDto mediumCopyDto) throws RemoteException {
        return library.returnGame(mediumCopyDto);
    }

    /* #### LOGIN #### */

    @Override
    public MessageDto<LoginDto> loginUser(LoginDto loginDto) throws RemoteException {
        return library.loginUser(loginDto);
    }

    /* #### MESSAGING #### */

    @Override
    public void registerForMessages(MessageClientInterface client) throws RemoteException {
        library.registerForMessages(client);
    }

    @Override
    public List<Message> getAllMessages() throws RemoteException {
        return library.getAllMessages();
    }
}
