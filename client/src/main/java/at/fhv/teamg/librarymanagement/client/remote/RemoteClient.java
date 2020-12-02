package at.fhv.teamg.librarymanagement.client.remote;

import at.fhv.teamg.librarymanagement.client.controller.internal.ConnectionType;
import at.fhv.teamg.librarymanagement.shared.dto.BookDto;
import at.fhv.teamg.librarymanagement.shared.dto.CustomMessage;
import at.fhv.teamg.librarymanagement.shared.dto.DvdDto;
import at.fhv.teamg.librarymanagement.shared.dto.EmptyDto;
import at.fhv.teamg.librarymanagement.shared.dto.GameDto;
import at.fhv.teamg.librarymanagement.shared.dto.LendingDto;
import at.fhv.teamg.librarymanagement.shared.dto.LoginDto;
import at.fhv.teamg.librarymanagement.shared.dto.MediumCopyDto;
import at.fhv.teamg.librarymanagement.shared.dto.MessageDto;
import at.fhv.teamg.librarymanagement.shared.dto.ReservationDto;
import at.fhv.teamg.librarymanagement.shared.dto.TopicDto;
import at.fhv.teamg.librarymanagement.shared.dto.UserDto;
import at.fhv.teamg.librarymanagement.shared.ifaces.LibraryInterface;
import at.fhv.teamg.librarymanagement.shared.ifaces.MessageClientInterface;
import java.rmi.RemoteException;
import java.util.List;

public class RemoteClient implements LibraryInterface {
    private static final RmiClient RMI_CLIENT = RmiClient.getInstance();
    private static final EjbClient EJB_CLIENT = EjbClient.getInstance();

    private static RemoteClient CLIENT_INSTANCE;
    private ConnectionType connectionType;

    /**
     * Singleton Client
     */
    private RemoteClient() {
        // Intentionally empty
    }

    /**
     * Returns a singleton instance.
     *
     * @return RemoteClient singleton instance
     */
    public static RemoteClient getInstance() {
        if (CLIENT_INSTANCE == null) {
            CLIENT_INSTANCE = new RemoteClient();
        }
        return CLIENT_INSTANCE;
    }

    public void setConnectionType(ConnectionType connectionType) {
        this.connectionType = connectionType;
    }

    /* #### SEARCH #### */

    @Override
    public List<BookDto> searchBook(BookDto bookDto) throws RemoteException {
        if (connectionType.equals(ConnectionType.EJB)) {
            return EJB_CLIENT.searchBook(bookDto);
        } else {
            return RMI_CLIENT.searchBook(bookDto);
        }
    }

    @Override
    public List<DvdDto> searchDvd(DvdDto dvdDto) throws RemoteException {
        if (connectionType.equals(ConnectionType.EJB)) {
            return EJB_CLIENT.searchDvd(dvdDto);
        } else {
            return RMI_CLIENT.searchDvd(dvdDto);
        }
    }

    @Override
    public List<GameDto> searchGame(GameDto gameDto) throws RemoteException {
        if (connectionType.equals(ConnectionType.EJB)) {
            return EJB_CLIENT.searchGame(gameDto);
        } else {
            return RMI_CLIENT.searchGame(gameDto);
        }
    }

    /* #### DETAILS #### */

    @Override
    public BookDto getBookDetail(BookDto bookDto) throws RemoteException {
        if (connectionType.equals(ConnectionType.EJB)) {
            return EJB_CLIENT.getBookDetail(bookDto);
        } else {
            return RMI_CLIENT.getBookDetail(bookDto);
        }
    }

    @Override
    public DvdDto getDvdDetail(DvdDto dvdDto) throws RemoteException {
        if (connectionType.equals(ConnectionType.EJB)) {
            return EJB_CLIENT.getDvdDetail(dvdDto);
        } else {
            return RMI_CLIENT.getDvdDetail(dvdDto);
        }
    }

    @Override
    public GameDto getGameDetail(GameDto gameDto) throws RemoteException {
        if (connectionType.equals(ConnectionType.EJB)) {
            return EJB_CLIENT.getGameDetail(gameDto);
        } else {
            return RMI_CLIENT.getGameDetail(gameDto);
        }
    }

    /* #### GET ALL #### */

    @Override
    public List<MediumCopyDto> getAllBookCopies(BookDto bookDto) throws RemoteException {
        if (connectionType.equals(ConnectionType.EJB)) {
            return EJB_CLIENT.getAllBookCopies(bookDto);
        } else {
            return RMI_CLIENT.getAllBookCopies(bookDto);
        }
    }

    @Override
    public List<MediumCopyDto> getAllDvdCopies(DvdDto dvdDto) throws RemoteException {
        if (connectionType.equals(ConnectionType.EJB)) {
            return EJB_CLIENT.getAllDvdCopies(dvdDto);
        } else {
            return RMI_CLIENT.getAllDvdCopies(dvdDto);
        }
    }

    @Override
    public List<MediumCopyDto> getAllGameCopies(GameDto gameDto) throws RemoteException {
        if (connectionType.equals(ConnectionType.EJB)) {
            return EJB_CLIENT.getAllGameCopies(gameDto);
        } else {
            return RMI_CLIENT.getAllGameCopies(gameDto);
        }
    }

    @Override
    public List<TopicDto> getAllTopics() throws RemoteException {
        if (connectionType.equals(ConnectionType.EJB)) {
            return EJB_CLIENT.getAllTopics();
        } else {
            return RMI_CLIENT.getAllTopics();
        }
    }

    @Override
    public List<UserDto> getAllUsers() throws RemoteException {
        return RMI_CLIENT.getAllUsers();
    }

    @Override
    public List<UserDto> getAllCustomers() throws RemoteException {
        if (connectionType.equals(ConnectionType.EJB)) {
            return EJB_CLIENT.getAllCustomers();
        } else {
            return RMI_CLIENT.getAllCustomers();
        }
    }

    /* #### RESERVATION #### */

    @Override
    public MessageDto<ReservationDto> reserveBook(ReservationDto reservationDto)
        throws RemoteException {
        if (connectionType.equals(ConnectionType.EJB)) {
            return EJB_CLIENT.reserveBook(reservationDto);
        } else {
            return RMI_CLIENT.reserveBook(reservationDto);
        }
    }

    @Override
    public MessageDto<ReservationDto> reserveDvd(ReservationDto reservationDto)
        throws RemoteException {
        if (connectionType.equals(ConnectionType.EJB)) {
            return EJB_CLIENT.reserveDvd(reservationDto);
        } else {
            return RMI_CLIENT.reserveDvd(reservationDto);
        }
    }

    @Override
    public MessageDto<ReservationDto> reserveGame(ReservationDto reservationDto)
        throws RemoteException {
        if (connectionType.equals(ConnectionType.EJB)) {
            return EJB_CLIENT.reserveGame(reservationDto);
        } else {
            return RMI_CLIENT.reserveGame(reservationDto);
        }
    }

    @Override
    public MessageDto<EmptyDto> removeReservation(ReservationDto reservationDto)
        throws RemoteException {
        if (connectionType.equals(ConnectionType.EJB)) {
            return EJB_CLIENT.removeReservation(reservationDto);
        } else {
            return RMI_CLIENT.removeReservation(reservationDto);
        }
    }

    @Override
    public List<ReservationDto> getAllBookReservations(BookDto bookDto) throws RemoteException {
        if (connectionType.equals(ConnectionType.EJB)) {
            return EJB_CLIENT.getAllBookReservations(bookDto);
        } else {
            return RMI_CLIENT.getAllBookReservations(bookDto);
        }
    }

    @Override
    public List<ReservationDto> getAllDvdReservations(DvdDto dvdDto) throws RemoteException {
        if (connectionType.equals(ConnectionType.EJB)) {
            return EJB_CLIENT.getAllDvdReservations(dvdDto);
        } else {
            return RMI_CLIENT.getAllDvdReservations(dvdDto);
        }
    }

    @Override
    public List<ReservationDto> getAllGameReservations(GameDto gameDto) throws RemoteException {
        if (connectionType.equals(ConnectionType.EJB)) {
            return EJB_CLIENT.getAllGameReservations(gameDto);
        } else {
            return RMI_CLIENT.getAllGameReservations(gameDto);
        }
    }

    /* ##### LENDING ##### */

    @Override
    public MessageDto<LendingDto> lendBook(LendingDto lendingDto) throws RemoteException {
        if (connectionType.equals(ConnectionType.EJB)) {
            return EJB_CLIENT.lendBook(lendingDto);
        } else {
            return RMI_CLIENT.lendBook(lendingDto);
        }
    }

    @Override
    public MessageDto<LendingDto> lendDvd(LendingDto lendingDto) throws RemoteException {
        if (connectionType.equals(ConnectionType.EJB)) {
            return EJB_CLIENT.lendDvd(lendingDto);
        } else {
            return RMI_CLIENT.lendDvd(lendingDto);
        }
    }

    @Override
    public MessageDto<LendingDto> lendGame(LendingDto lendingDto) throws RemoteException {
        if (connectionType.equals(ConnectionType.EJB)) {
            return EJB_CLIENT.lendGame(lendingDto);
        } else {
            return RMI_CLIENT.lendGame(lendingDto);
        }
    }

    @Override
    public MessageDto<EmptyDto> extendBook(MediumCopyDto mediumCopyDto) throws RemoteException {
        if (connectionType.equals(ConnectionType.EJB)) {
            return EJB_CLIENT.extendBook(mediumCopyDto);
        } else {
            return RMI_CLIENT.extendBook(mediumCopyDto);
        }
    }

    @Override
    public MessageDto<EmptyDto> extendDvd(MediumCopyDto mediumCopyDto) throws RemoteException {
        if (connectionType.equals(ConnectionType.EJB)) {
            return EJB_CLIENT.extendDvd(mediumCopyDto);
        } else {
            return RMI_CLIENT.extendDvd(mediumCopyDto);
        }
    }

    @Override
    public MessageDto<EmptyDto> extendGame(MediumCopyDto mediumCopyDto) throws RemoteException {
        if (connectionType.equals(ConnectionType.EJB)) {
            return EJB_CLIENT.extendGame(mediumCopyDto);
        } else {
            return RMI_CLIENT.extendGame(mediumCopyDto);
        }
    }

    @Override
    public MessageDto<EmptyDto> returnBook(MediumCopyDto mediumCopyDto) throws RemoteException {
        if (connectionType.equals(ConnectionType.EJB)) {
            return EJB_CLIENT.returnBook(mediumCopyDto);
        } else {
            return RMI_CLIENT.returnBook(mediumCopyDto);
        }
    }

    @Override
    public MessageDto<EmptyDto> returnDvd(MediumCopyDto mediumCopyDto) throws RemoteException {
        if (connectionType.equals(ConnectionType.EJB)) {
            return EJB_CLIENT.returnDvd(mediumCopyDto);
        } else {
            return RMI_CLIENT.returnDvd(mediumCopyDto);
        }
    }

    @Override
    public MessageDto<EmptyDto> returnGame(MediumCopyDto mediumCopyDto) throws RemoteException {
        if (connectionType.equals(ConnectionType.EJB)) {
            return EJB_CLIENT.returnGame(mediumCopyDto);
        } else {
            return RMI_CLIENT.returnGame(mediumCopyDto);
        }
    }

    /* #### LOGIN #### */

    @Override
    public MessageDto<LoginDto> loginUser(LoginDto loginDto) throws RemoteException {
        if (connectionType.equals(ConnectionType.EJB)) {
            return EJB_CLIENT.loginUser(loginDto);
        } else {
            return RMI_CLIENT.loginUser(loginDto);
        }
    }

    /* #### MESSAGING #### */

    @Override
    public void registerForMessages(MessageClientInterface client) throws RemoteException {
        // Messaging only works for RMI
        RMI_CLIENT.registerForMessages(client);
    }

    @Override
    public List<CustomMessage> getAllMessages() throws RemoteException {
        // Messaging only works for RMI
        return RMI_CLIENT.getAllMessages();
    }


    @Override
    public void addMessage(CustomMessage message) throws RemoteException {
        // Messaging only works for RMI
        RMI_CLIENT.addMessage(message);
    }

    @Override
    public void updateMessageStatus(CustomMessage customMessage) throws RemoteException {
        // Messaging only works for RMI
        RMI_CLIENT.updateMessageStatus(customMessage);
    }
}
