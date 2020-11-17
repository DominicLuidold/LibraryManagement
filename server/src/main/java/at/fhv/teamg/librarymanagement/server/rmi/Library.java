package at.fhv.teamg.librarymanagement.server.rmi;

import at.fhv.teamg.librarymanagement.server.domain.BookService;
import at.fhv.teamg.librarymanagement.server.domain.DetailService;
import at.fhv.teamg.librarymanagement.server.domain.DvdService;
import at.fhv.teamg.librarymanagement.server.domain.GameService;
import at.fhv.teamg.librarymanagement.server.domain.LendingService;
import at.fhv.teamg.librarymanagement.server.domain.MediumCopyService;
import at.fhv.teamg.librarymanagement.server.domain.ReservationService;
import at.fhv.teamg.librarymanagement.server.domain.TopicService;
import at.fhv.teamg.librarymanagement.server.domain.UserService;
import at.fhv.teamg.librarymanagement.shared.dto.BookDto;
import at.fhv.teamg.librarymanagement.shared.dto.DvdDto;
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
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
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
    private final UserService userService = new UserService();
    private final Cache cache = Cache.getInstance();
    private LoginDto loggedInUser;

    private static final List<MessageClientInterface> clients = new LinkedList<>();
    private static final List<Message> messages = new LinkedList<>();

    public Library() throws RemoteException {
        super();
    }

    @Override
    public List<GameDto> searchGame(GameDto gameDto) throws RemoteException {
        return cache.searchGame(gameDto);
    }

    @Override
    public List<BookDto> searchBook(BookDto bookDto) throws RemoteException {
        return cache.searchBook(bookDto);
    }

    @Override
    public List<DvdDto> searchDvd(DvdDto dvdDto) throws RemoteException {
        return cache.searchDvd(dvdDto);
    }

    @Override
    public GameDto getGameDetail(GameDto gameDto) throws RemoteException {
        return cache.getGameDetail(gameDto.getId());
    }

    @Override
    public BookDto getBookDetail(BookDto bookDto) throws RemoteException {
        return cache.getBookDetail(bookDto.getId());
    }

    @Override
    public DvdDto getDvdDetail(DvdDto dvdDto) throws RemoteException {
        return cache.getDvdDetail(dvdDto.getId());
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

    private ReservationDto reserve(ReservationDto reservationDto) {
        Optional<ReservationDto> result = reservationService.createReservation(reservationDto);
        if (result.isPresent()) {
            return result.get();
        }
        LOG.error("Cannot create reservation");
        return null;
    }

    @Override
    public ReservationDto reserveGame(ReservationDto reservationDto)
        throws RemoteException {
        ReservationDto result = this.reserve(reservationDto);
        cache.invalidateGameCacheMedium(reservationDto.getMediumId());
        return result;
    }

    @Override
    public ReservationDto reserveBook(ReservationDto reservationDto)
        throws RemoteException {
        // same as above
        ReservationDto result = this.reserve(reservationDto);
        cache.invalidateBookCacheMedium(reservationDto.getMediumId());
        return result;
    }

    @Override
    public ReservationDto reserveDvd(ReservationDto reservationDto)
        throws RemoteException {
        // same as above
        ReservationDto result = this.reserve(reservationDto);
        cache.invalidateDvdCacheMedium(reservationDto.getMediumId());
        return result;
    }

    private LendingDto lend(LendingDto lendingDto) {
        // we're using Java8, so no .orElseThrow(), AND optional is not serializable, yay...
        Optional<LendingDto> result = lendingService.createLending(lendingDto);
        if (result.isPresent()) {
            return result.get();
        }
        LOG.error("Cannot create lending");
        return null;
    }

    @Override
    public LendingDto lendGame(LendingDto lendingDto) throws RemoteException {
        LendingDto result = this.lend(lendingDto);
        cache.invalidateGameCacheMediumCopy(lendingDto.getMediumCopyId());
        return result;
    }

    @Override
    public LendingDto lendBook(LendingDto lendingDto) throws RemoteException {
        LendingDto result = this.lend(lendingDto);
        cache.invalidateBookCacheMediumCopy(lendingDto.getMediumCopyId());
        return result;
    }

    @Override
    public LendingDto lendDvd(LendingDto lendingDto) throws RemoteException {
        LendingDto result = this.lend(lendingDto);
        cache.invalidateDvdCacheMediumCopy(lendingDto.getMediumCopyId());
        addMessage(new Message(
            UUID.randomUUID(),
            "Test Message lend dvd",
            Message.Status.Open,
            LocalDateTime.now()
        ));
        return result;
    }

    @Override
    public Boolean returnGame(MediumCopyDto copyDto) throws RemoteException {
        Boolean result = lendingService.returnLending(copyDto);
        cache.invalidateGameCacheMediumCopy(copyDto.getId());
        return result;
    }

    @Override
    public Boolean returnBook(MediumCopyDto copyDto) throws RemoteException {
        Boolean result = lendingService.returnLending(copyDto);
        cache.invalidateBookCacheMediumCopy(copyDto.getId());
        return result;
    }

    @Override
    public Boolean returnDvd(MediumCopyDto copyDto) throws RemoteException {
        Boolean result = lendingService.returnLending(copyDto);
        cache.invalidateDvdCacheMediumCopy(copyDto.getId());
        addMessage(new Message(
            UUID.randomUUID(),
            "Test Message return dvd",
            Message.Status.Open,
            LocalDateTime.now()
        ));
        return result;
    }

    @Override
    public List<TopicDto> getAllTopics() throws RemoteException {
        return cache.getAllTopics();
    }

    @Override
    public List<UserDto> getAllUsers() throws RemoteException {
        return cache.getAllUsers();
    }

    @Override
    public MessageDto extendBook(MediumCopyDto mediumCopyDto) throws RemoteException {
        MessageDto result = lendingService.extendLending(mediumCopyDto);
        cache.invalidateBookCacheMediumCopy(mediumCopyDto.getId());
        return result;
    }

    @Override
    public MessageDto extendDvd(MediumCopyDto mediumCopyDto) throws RemoteException {
        MessageDto result = lendingService.extendLending(mediumCopyDto);
        cache.invalidateDvdCacheMediumCopy(mediumCopyDto.getId());
        return result;
    }

    @Override
    public MessageDto extendGame(MediumCopyDto mediumCopyDto) throws RemoteException {
        MessageDto result = lendingService.extendLending(mediumCopyDto);
        cache.invalidateGameCacheMediumCopy(mediumCopyDto.getId());
        return result;
    }

    @Override
    public void registerForMessages(MessageClientInterface client) throws RemoteException {
        LOG.info("new message subscriber");
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
                LOG.info("Client can not be messaged -> remove from list");
                clients.remove(client);
            }
        }).start();
    }

    public static void addMessage(Message message) {
        messages.add(message);
        clients.forEach(client -> updateClient(client, message));
    }

    private static void updateMessage(Message message) {
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

    public LoginDto loginUser(LoginDto loginDto) throws RemoteException {
        loggedInUser = userService.authenticateUser(loginDto);
        return loggedInUser;
    }

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
