package at.fhv.teamg.librarymanagement.server.rmi;

import at.fhv.teamg.librarymanagement.server.domain.LendingService;
import at.fhv.teamg.librarymanagement.server.domain.MediumCopyService;
import at.fhv.teamg.librarymanagement.server.domain.ReservationService;
import at.fhv.teamg.librarymanagement.server.domain.UserService;
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
import at.fhv.teamg.librarymanagement.shared.enums.UserRoleName;
import at.fhv.teamg.librarymanagement.shared.ifaces.LibraryInterface;
import at.fhv.teamg.librarymanagement.shared.ifaces.MessageClientInterface;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.LinkedList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Library extends UnicastRemoteObject implements LibraryInterface {
    private static final Logger LOG = LogManager.getLogger(Library.class);
    private static final long serialVersionUID = -443483629739057113L;

    private static final List<MessageClientInterface> clients = new LinkedList<>();
    private static final List<Message> messages = new LinkedList<>();

    private final LendingService lendingService = new LendingService();
    private final MediumCopyService mediumCopyService = new MediumCopyService();
    private final ReservationService reservationService = new ReservationService();
    private final UserService userService = new UserService();
    private final Cache cache = Cache.getInstance();

    private LoginDto loggedInUser;

    public Library() throws RemoteException {
        super();
    }

    /* #### SEARCH #### */

    @Override
    public List<BookDto> searchBook(BookDto bookDto) throws RemoteException {
        return cache.searchBook(bookDto);
    }

    @Override
    public List<DvdDto> searchDvd(DvdDto dvdDto) throws RemoteException {
        return cache.searchDvd(dvdDto);
    }

    @Override
    public List<GameDto> searchGame(GameDto gameDto) throws RemoteException {
        return cache.searchGame(gameDto);
    }

    /* #### DETAILS #### */

    @Override
    public BookDto getBookDetail(BookDto bookDto) throws RemoteException {
        return cache.getBookDetail(bookDto.getId());
    }

    @Override
    public DvdDto getDvdDetail(DvdDto dvdDto) throws RemoteException {
        return cache.getDvdDetail(dvdDto.getId());
    }

    @Override
    public GameDto getGameDetail(GameDto gameDto) throws RemoteException {
        return cache.getGameDetail(gameDto.getId());
    }

    /* #### GET ALL #### */

    @Override
    public List<MediumCopyDto> getAllBookCopies(BookDto bookDto) throws RemoteException {
        return mediumCopyService.getCopies(bookDto);
    }

    @Override
    public List<MediumCopyDto> getAllDvdCopies(DvdDto dvdDto) throws RemoteException {
        return mediumCopyService.getCopies(dvdDto);
    }

    @Override
    public List<MediumCopyDto> getAllGameCopies(GameDto gameDto) throws RemoteException {
        return mediumCopyService.getCopies(gameDto);
    }

    @Override
    public List<TopicDto> getAllTopics() throws RemoteException {
        return cache.getAllTopics();
    }

    @Override
    public List<UserDto> getAllUsers() throws RemoteException {
        return cache.getAllUsers();
    }

    /* #### RESERVATION #### */

    @Override
    public MessageDto<ReservationDto> reserveBook(ReservationDto reservationDto)
        throws RemoteException {
        MessageDto<ReservationDto> result = reservationService.createReservation(reservationDto);
        cache.invalidateBookCacheMedium(reservationDto.getMediumId());
        return result;
    }

    @Override
    public MessageDto<ReservationDto> reserveDvd(ReservationDto reservationDto)
        throws RemoteException {
        MessageDto<ReservationDto> result = reservationService.createReservation(reservationDto);
        cache.invalidateDvdCacheMedium(reservationDto.getMediumId());
        return result;
    }

    @Override
    public MessageDto<ReservationDto> reserveGame(ReservationDto reservationDto)
        throws RemoteException {
        MessageDto<ReservationDto> result = reservationService.createReservation(reservationDto);
        cache.invalidateGameCacheMedium(reservationDto.getMediumId());
        return result;
    }

    @Override
    public MessageDto<EmptyDto> removeReservation(ReservationDto reservationDto)
        throws RemoteException {
        return reservationService.deleteReservation(reservationDto);
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
    public List<ReservationDto> getAllGameReservations(GameDto gameDto) throws RemoteException {
        return reservationService.getReservations(gameDto);
    }

    /* ##### LENDING ##### */

    @Override
    public MessageDto<LendingDto> lendBook(LendingDto lendingDto) throws RemoteException {
        MessageDto<LendingDto> result = lendingService.createLending(lendingDto);
        cache.invalidateBookCacheMediumCopy(lendingDto.getMediumCopyId());
        return result;
    }

    @Override
    public MessageDto<LendingDto> lendGame(LendingDto lendingDto) throws RemoteException {
        MessageDto<LendingDto> result = lendingService.createLending(lendingDto);
        cache.invalidateGameCacheMediumCopy(lendingDto.getMediumCopyId());
        return result;
    }

    @Override
    public MessageDto<LendingDto> lendDvd(LendingDto lendingDto) throws RemoteException {
        MessageDto<LendingDto> result = lendingService.createLending(lendingDto);
        cache.invalidateDvdCacheMediumCopy(lendingDto.getMediumCopyId());
        return result;
    }

    @Override
    public MessageDto<EmptyDto> extendBook(MediumCopyDto mediumCopyDto) throws RemoteException {
        MessageDto<EmptyDto> result = lendingService.extendLending(mediumCopyDto);
        cache.invalidateBookCacheMediumCopy(mediumCopyDto.getId());
        return result;
    }

    @Override
    public MessageDto<EmptyDto> extendDvd(MediumCopyDto mediumCopyDto) throws RemoteException {
        MessageDto<EmptyDto> result = lendingService.extendLending(mediumCopyDto);
        cache.invalidateDvdCacheMediumCopy(mediumCopyDto.getId());
        return result;
    }

    @Override
    public MessageDto<EmptyDto> extendGame(MediumCopyDto mediumCopyDto) throws RemoteException {
        MessageDto<EmptyDto> result = lendingService.extendLending(mediumCopyDto);
        cache.invalidateGameCacheMediumCopy(mediumCopyDto.getId());
        return result;
    }

    @Override
    public MessageDto<EmptyDto> returnBook(MediumCopyDto copyDto) throws RemoteException {
        MessageDto<EmptyDto> result = lendingService.returnLending(copyDto);
        cache.invalidateBookCacheMediumCopy(copyDto.getId());
        return result;
    }

    @Override
    public MessageDto<EmptyDto> returnDvd(MediumCopyDto copyDto) throws RemoteException {
        MessageDto<EmptyDto> result = lendingService.returnLending(copyDto);
        cache.invalidateDvdCacheMediumCopy(copyDto.getId());
        return result;
    }

    @Override
    public MessageDto<EmptyDto> returnGame(MediumCopyDto copyDto) throws RemoteException {
        MessageDto<EmptyDto> result = lendingService.returnLending(copyDto);
        cache.invalidateGameCacheMediumCopy(copyDto.getId());
        return result;
    }

    /* #### LOGIN #### */

    public MessageDto<LoginDto> loginUser(LoginDto loginDto) throws RemoteException {
        return userService.authenticateUser(loginDto);
    }

    /* #### MESSAGING #### */

    @Override
    public void registerForMessages(MessageClientInterface client) throws RemoteException {
        LOG.debug("Registering new message subscriber [{}]", client);
        clients.add(client);
    }

    @Override
    public List<Message> getAllMessages() throws RemoteException {
        return messages;
    }

    private static void updateClient(MessageClientInterface client, Message message) {
        new Thread(() -> {
            try {
                client.update(message);
            } catch (RemoteException e) {
                LOG.error("Client cannot be messaged", e);
                LOG.debug("Removing client [{}] from list", client);
                clients.remove(client);
            }
        }).start();
    }

    /**
     * Add a new message.
     *
     * @param message the new message
     */
    public static void addMessage(Message message) {
        messages.add(message);
        clients.forEach(client -> updateClient(client, message));
    }

    /**
     * Update an existing message.
     *
     * @param message message with the same id of an already existing message
     */
    public static void updateMessage(Message message) {
        messages.stream()
            .filter(m -> m.id.equals(message.id))
            .findFirst()
            .ifPresent(m -> {
                m.dateTime = message.dateTime;
                m.message = message.message;
                m.status = message.status;
                clients.forEach(client -> updateClient(client, m));
            });
    }

    /* #### AUTHORIZATION #### */

    private boolean isValid(UserRoleName userRoleNeeded) {
        /*
         * Admin can perform all Actions.
         */
        if (userRoleNeeded.equals(UserRoleName.Admin)) {
            if (loggedInUser.getUserRoleName().equals(UserRoleName.Admin)) {
                return true;
            }
        }

        /*
         * Libararian can perfrom Lending and Reservations.
         */
        if (userRoleNeeded.equals(UserRoleName.Librarian)) {
            if (loggedInUser.getUserRoleName().equals(UserRoleName.Librarian)
                || loggedInUser.getUserRoleName().equals(UserRoleName.Admin)
            ) {
                return true;
            }
        }

        /*
         * Customer are "Guest User" can only search.
         */
        if (userRoleNeeded.equals(UserRoleName.Customer)) {
            if (loggedInUser.getUserRoleName().equals(UserRoleName.Customer)
                || loggedInUser.getUserRoleName().equals(UserRoleName.Librarian)
                || loggedInUser.getUserRoleName().equals(UserRoleName.Admin)
            ) {
                return true;
            }
        }
        return false;
    }
}
