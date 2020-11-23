package at.fhv.teamg.librarymanagement.server.rmi;

import at.fhv.teamg.librarymanagement.server.domain.LendingService;
import at.fhv.teamg.librarymanagement.server.domain.MediumCopyService;
import at.fhv.teamg.librarymanagement.server.domain.ReservationService;
import at.fhv.teamg.librarymanagement.server.domain.UserService;
import at.fhv.teamg.librarymanagement.server.jms.JmsConsumer;
import at.fhv.teamg.librarymanagement.server.jms.JmsProducer;
import at.fhv.teamg.librarymanagement.shared.dto.BookDto;
import at.fhv.teamg.librarymanagement.shared.dto.DvdDto;
import at.fhv.teamg.librarymanagement.shared.dto.EmptyDto;
import at.fhv.teamg.librarymanagement.shared.dto.GameDto;
import at.fhv.teamg.librarymanagement.shared.dto.LendingDto;
import at.fhv.teamg.librarymanagement.shared.dto.LoginDto;
import at.fhv.teamg.librarymanagement.shared.dto.MediumCopyDto;
import at.fhv.teamg.librarymanagement.shared.dto.CustomMessage;
import at.fhv.teamg.librarymanagement.shared.dto.MessageDto;
import at.fhv.teamg.librarymanagement.shared.dto.ReservationDto;
import at.fhv.teamg.librarymanagement.shared.dto.TopicDto;
import at.fhv.teamg.librarymanagement.shared.dto.UserDto;
import at.fhv.teamg.librarymanagement.shared.enums.UserRoleName;
import at.fhv.teamg.librarymanagement.shared.ifaces.LibraryInterface;
import at.fhv.teamg.librarymanagement.shared.ifaces.MessageClientInterface;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.jms.JMSException;
import javax.jms.Message;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Library extends UnicastRemoteObject implements LibraryInterface {
    private static final Logger LOG = LogManager.getLogger(Library.class);
    private static final long serialVersionUID = -443483629739057113L;

    private static final List<MessageClientInterface> clients = new LinkedList<>();
    private static final Map<CustomMessage, Message> CUSTOM_MESSAGES = new HashMap<>();

    private final LendingService lendingService = new LendingService();
    private final MediumCopyService mediumCopyService = new MediumCopyService();
    private final ReservationService reservationService = new ReservationService();
    private final UserService userService = new UserService();
    private final Cache cache = Cache.getInstance();
    private final JmsConsumer consumer = JmsConsumer.getInstance(this);

    private LoginDto loggedInUser;
    private static final String UNAUTHORIZED_MESSAGE = "User not authorized to perform this action";

    public Library() throws RemoteException {
        super();
        try {
            consumer.startListener();
        } catch (JMSException e) {
            LOG.error("Cannot start message JMS listener", e);
        }
    }

    public static List<MessageClientInterface> getClients() {
        return clients;
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
        if (isValid(UserRoleName.Librarian)) {
            return cache.getAllUsers();
        }
        return new LinkedList<UserDto>();
    }

    @Override
    public List<UserDto> getAllCustomers() throws RemoteException {
        if (isValid(UserRoleName.Librarian)) {
            return cache.getAllCustomers();
        }
        return new LinkedList<UserDto>();
    }

    /* #### RESERVATION #### */

    @Override
    public MessageDto<ReservationDto> reserveBook(ReservationDto reservationDto)
        throws RemoteException {
        if (!isValid(UserRoleName.Librarian)) {
            return new MessageDto.MessageDtoBuilder<ReservationDto>()
                .withType(MessageDto.MessageType.FAILURE)
                .withMessage(UNAUTHORIZED_MESSAGE)
                .build();
        }

        MessageDto<ReservationDto> result = reservationService.createReservation(reservationDto);
        cache.invalidateBookCacheMedium(reservationDto.getMediumId());
        return result;
    }

    @Override
    public MessageDto<ReservationDto> reserveDvd(ReservationDto reservationDto)
        throws RemoteException {
        if (!isValid(UserRoleName.Librarian)) {
            return new MessageDto.MessageDtoBuilder<ReservationDto>()
                .withType(MessageDto.MessageType.FAILURE)
                .withMessage(UNAUTHORIZED_MESSAGE)
                .build();
        }
        MessageDto<ReservationDto> result = reservationService.createReservation(reservationDto);
        cache.invalidateDvdCacheMedium(reservationDto.getMediumId());
        return result;
    }

    @Override
    public MessageDto<ReservationDto> reserveGame(ReservationDto reservationDto)
        throws RemoteException {
        if (!isValid(UserRoleName.Librarian)) {
            return new MessageDto.MessageDtoBuilder<ReservationDto>()
                .withType(MessageDto.MessageType.FAILURE)
                .withMessage(UNAUTHORIZED_MESSAGE)
                .build();
        }
        MessageDto<ReservationDto> result = reservationService.createReservation(reservationDto);
        cache.invalidateGameCacheMedium(reservationDto.getMediumId());
        return result;
    }

    @Override
    public MessageDto<EmptyDto> removeReservation(ReservationDto reservationDto)
        throws RemoteException {
        if (!isValid(UserRoleName.Librarian)) {
            return new MessageDto.MessageDtoBuilder<EmptyDto>()
                .withType(MessageDto.MessageType.FAILURE)
                .withMessage(UNAUTHORIZED_MESSAGE)
                .build();
        }
        return reservationService.deleteReservation(reservationDto);
    }

    @Override
    public List<ReservationDto> getAllBookReservations(BookDto bookDto) throws RemoteException {
        if (!isValid(UserRoleName.Librarian)) {
            return new LinkedList<ReservationDto>();
        }
        return reservationService.getReservations(bookDto);
    }

    @Override
    public List<ReservationDto> getAllDvdReservations(DvdDto dvdDto) throws RemoteException {
        if (!isValid(UserRoleName.Librarian)) {
            return new LinkedList<ReservationDto>();
        }
        return reservationService.getReservations(dvdDto);
    }

    @Override
    public List<ReservationDto> getAllGameReservations(GameDto gameDto) throws RemoteException {
        if (!isValid(UserRoleName.Librarian)) {
            return new LinkedList<ReservationDto>();
        }
        return reservationService.getReservations(gameDto);
    }

    /* ##### LENDING ##### */

    @Override
    public MessageDto<LendingDto> lendBook(LendingDto lendingDto) throws RemoteException {
        if (!isValid(UserRoleName.Librarian)) {
            return new MessageDto.MessageDtoBuilder<LendingDto>()
                .withType(MessageDto.MessageType.FAILURE)
                .withMessage(UNAUTHORIZED_MESSAGE)
                .build();
        }
        MessageDto<LendingDto> result = lendingService.createLending(lendingDto);
        cache.invalidateBookCacheMediumCopy(lendingDto.getMediumCopyId());
        return result;
    }

    @Override
    public MessageDto<LendingDto> lendGame(LendingDto lendingDto) throws RemoteException {
        if (!isValid(UserRoleName.Librarian)) {
            return new MessageDto.MessageDtoBuilder<LendingDto>()
                .withType(MessageDto.MessageType.FAILURE)
                .withMessage(UNAUTHORIZED_MESSAGE)
                .build();
        }
        MessageDto<LendingDto> result = lendingService.createLending(lendingDto);
        cache.invalidateGameCacheMediumCopy(lendingDto.getMediumCopyId());
        return result;
    }

    @Override
    public MessageDto<LendingDto> lendDvd(LendingDto lendingDto) throws RemoteException {
        if (!isValid(UserRoleName.Librarian)) {
            return new MessageDto.MessageDtoBuilder<LendingDto>()
                .withType(MessageDto.MessageType.FAILURE)
                .withMessage(UNAUTHORIZED_MESSAGE)
                .build();
        }
        MessageDto<LendingDto> result = lendingService.createLending(lendingDto);
        cache.invalidateDvdCacheMediumCopy(lendingDto.getMediumCopyId());
        return result;
    }

    @Override
    public MessageDto<EmptyDto> extendBook(MediumCopyDto mediumCopyDto) throws RemoteException {
        if (!isValid(UserRoleName.Librarian)) {
            return new MessageDto.MessageDtoBuilder<EmptyDto>()
                .withType(MessageDto.MessageType.FAILURE)
                .withMessage(UNAUTHORIZED_MESSAGE)
                .build();
        }
        MessageDto<EmptyDto> result = lendingService.extendLending(mediumCopyDto);
        cache.invalidateBookCacheMediumCopy(mediumCopyDto.getId());
        return result;
    }

    @Override
    public MessageDto<EmptyDto> extendDvd(MediumCopyDto mediumCopyDto) throws RemoteException {
        if (!isValid(UserRoleName.Librarian)) {
            return new MessageDto.MessageDtoBuilder<EmptyDto>()
                .withType(MessageDto.MessageType.FAILURE)
                .withMessage(UNAUTHORIZED_MESSAGE)
                .build();
        }
        MessageDto<EmptyDto> result = lendingService.extendLending(mediumCopyDto);
        cache.invalidateDvdCacheMediumCopy(mediumCopyDto.getId());
        return result;
    }

    @Override
    public MessageDto<EmptyDto> extendGame(MediumCopyDto mediumCopyDto) throws RemoteException {
        if (!isValid(UserRoleName.Librarian)) {
            return new MessageDto.MessageDtoBuilder<EmptyDto>()
                .withType(MessageDto.MessageType.FAILURE)
                .withMessage(UNAUTHORIZED_MESSAGE)
                .build();
        }
        MessageDto<EmptyDto> result = lendingService.extendLending(mediumCopyDto);
        cache.invalidateGameCacheMediumCopy(mediumCopyDto.getId());
        return result;
    }

    @Override
    public MessageDto<EmptyDto> returnBook(MediumCopyDto copyDto) throws RemoteException {
        if (!isValid(UserRoleName.Librarian)) {
            return new MessageDto.MessageDtoBuilder<EmptyDto>()
                .withType(MessageDto.MessageType.FAILURE)
                .withMessage(UNAUTHORIZED_MESSAGE)
                .build();
        }
        MessageDto<EmptyDto> result = lendingService.returnLending(copyDto);
        cache.invalidateBookCacheMediumCopy(copyDto.getId());
        return result;
    }

    @Override
    public MessageDto<EmptyDto> returnDvd(MediumCopyDto copyDto) throws RemoteException {
        if (!isValid(UserRoleName.Librarian)) {
            return new MessageDto.MessageDtoBuilder<EmptyDto>()
                .withType(MessageDto.MessageType.FAILURE)
                .withMessage(UNAUTHORIZED_MESSAGE)
                .build();
        }
        MessageDto<EmptyDto> result = lendingService.returnLending(copyDto);
        cache.invalidateDvdCacheMediumCopy(copyDto.getId());
        return result;
    }

    @Override
    public MessageDto<EmptyDto> returnGame(MediumCopyDto copyDto) throws RemoteException {
        if (!isValid(UserRoleName.Librarian)) {
            return new MessageDto.MessageDtoBuilder<EmptyDto>()
                .withType(MessageDto.MessageType.FAILURE)
                .withMessage(UNAUTHORIZED_MESSAGE)
                .build();
        }
        MessageDto<EmptyDto> result = lendingService.returnLending(copyDto);
        cache.invalidateGameCacheMediumCopy(copyDto.getId());
        return result;
    }

    /* #### LOGIN #### */

    /**
     * Try to login User specified in loginDto.
     *
     * @param loginDto User to login
     * @return dto with UserRoleName is the so the Gui know what View to load
     */
    public MessageDto<LoginDto> loginUser(LoginDto loginDto) throws RemoteException {
        if (!loginDto.getUsername().equals("guest")) {
            MessageDto<LoginDto> loggedInUserMessage = userService.authenticateUser(loginDto);
            loggedInUser = loggedInUserMessage.getResult();
            return loggedInUserMessage;
        }
        return new MessageDto.MessageDtoBuilder<LoginDto>()
            .withType(MessageDto.MessageType.SUCCESS)
            .withResult(
                new LoginDto.LoginDtoBuilder()
                    .withIsValid(true)
                    .withUsername("guest")
                    .withUserRoleName(UserRoleName.Customer)
                    .build()
            )
            .build();
    }

    /* #### MESSAGING #### */

    @Override
    public void registerForMessages(MessageClientInterface client) throws RemoteException {
        LOG.debug("Registering new message subscriber [{}]", client.hashCode());
        clients.add(client);
    }

    @Override
    public List<CustomMessage> getAllMessages() throws RemoteException {
        return new LinkedList<>(CUSTOM_MESSAGES.keySet());
    }


    /**
     * Add a new message.
     *
     * @param customMessage the new message
     */
    public static void addMessage(CustomMessage customMessage) {
        try {
            Message m = JmsProducer.getInstance().sendMessage(customMessage);
            CUSTOM_MESSAGES.put(customMessage, m);
        } catch (JMSException e) {
            LOG.error("Cannot send message to queue", e);
        }
    }

    /**
     * Update an existing message.
     *
     * @param customMessage message with the same id of an already existing message
     */
    public static void updateMessage(CustomMessage customMessage) {
        CUSTOM_MESSAGES.keySet().stream()
            .filter(m -> m.id.equals(customMessage.id))
            .findFirst()
            .ifPresent(m -> {
                m.dateTime = customMessage.dateTime;
                m.message = customMessage.message;
                m.status = customMessage.status;
                try {
                    Message m2 = JmsProducer.getInstance().sendMessage(customMessage);
                    CUSTOM_MESSAGES.put(m, m2);
                } catch (JMSException e) {
                    LOG.error("Cannot send message over JMS", customMessage);
                }
            });
    }

    /**
     * Update message Status.
     *
     * @param customMessage message with the same id of an already existing message
     */
    public void updateMessageStatus(CustomMessage customMessage) throws RemoteException {
        var messageOptional = CUSTOM_MESSAGES.keySet().stream()
            .filter(m -> m.id.equals(customMessage.id))
            .findFirst();

        if (messageOptional.isEmpty()) {
            LOG.error("Got message with invalid Id");
            return;
        }

        var messageToUpdate = messageOptional.get();

        if (messageToUpdate.userId == null) {
            messageToUpdate.userId = loggedInUser.getId();
        } else if (!messageToUpdate.userId.equals(loggedInUser.getId())) {
            LOG.error("Logged-in user is not allowed to update this message");
            return;
        }

        messageToUpdate.status = customMessage.status;

        updateMessage(messageToUpdate);

        if (messageToUpdate.status.equals(CustomMessage.Status.Archived)) {
            //TODO Remove message from JMS Queue and persist in DB
            Message jmsMessage = CUSTOM_MESSAGES.get(messageToUpdate);
            try {
                jmsMessage.acknowledge();
            } catch (JMSException e) {
                LOG.error("Unable to archive (acknowledge) message", e);
            }

            CUSTOM_MESSAGES.remove(messageToUpdate);
        }
    }

    /* #### AUTHORIZATION #### */

    private boolean isValid(UserRoleName userRoleNeeded) {
        /*
         * Armin can perform all Actions.
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

    /* Try to make no Code duplicating (But Failed :D )
    public static MessageDto<Dto> getFailureMessage(String message, Class clazz) {
        MessageDto<clazz> result = new MessageDto.MessageDtoBuilder<>()
            .withType(MessageDto.MessageType.FAILURE)
            .withMessage(message)
            .build();
        return result;
    }
    */
}
