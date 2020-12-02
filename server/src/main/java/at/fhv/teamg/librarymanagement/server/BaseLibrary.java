package at.fhv.teamg.librarymanagement.server;

import at.fhv.teamg.librarymanagement.server.domain.LendingService;
import at.fhv.teamg.librarymanagement.server.domain.MediumCopyService;
import at.fhv.teamg.librarymanagement.server.domain.MessageService;
import at.fhv.teamg.librarymanagement.server.domain.ReservationService;
import at.fhv.teamg.librarymanagement.server.domain.UserService;
import at.fhv.teamg.librarymanagement.server.jms.JmsProducer;
import at.fhv.teamg.librarymanagement.server.rmi.Cache;
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
import at.fhv.teamg.librarymanagement.shared.enums.UserRoleName;
import at.fhv.teamg.librarymanagement.shared.ifaces.MessageClientInterface;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.jms.JMSException;
import javax.jms.Message;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BaseLibrary {
    private static final Logger LOG = LogManager.getLogger(BaseLibrary.class);
    private static final List<MessageClientInterface> CLIENTS = new LinkedList<>();
    private static final Map<CustomMessage, Message> CUSTOM_MESSAGES = new HashMap<>();
    private static final String UNAUTHORIZED_MESSAGE = "User not authorized to perform this action";

    private static BaseLibrary INSTANCE;

    private final Cache cache = Cache.getInstance();
    private final LendingService lendingService = new LendingService();
    private final MediumCopyService mediumCopyService = new MediumCopyService();
    private final ReservationService reservationService = new ReservationService();
    private final UserService userService = new UserService();

    private LoginDto loggedInUser;

    private BaseLibrary() {
        // Intentionally empty
    }

    /**
     * Returns an instance of the BaseLibrary.
     *
     * @return Singleton instance of {@link BaseLibrary}
     */
    public static BaseLibrary getInstance() {
        if (null == INSTANCE) {
            INSTANCE = new BaseLibrary();
        }
        return INSTANCE;
    }

    /* #### SEARCH #### */

    public List<BookDto> searchBook(BookDto bookDto) {
        return cache.searchBook(bookDto);
    }

    public List<DvdDto> searchDvd(DvdDto dvdDto) {
        return cache.searchDvd(dvdDto);
    }

    public List<GameDto> searchGame(GameDto gameDto) {
        return cache.searchGame(gameDto);
    }

    /* #### DETAILS #### */

    public BookDto getBookDetail(BookDto bookDto) {
        return cache.getBookDetail(bookDto.getId());
    }

    public DvdDto getDvdDetail(DvdDto dvdDto) {
        return cache.getDvdDetail(dvdDto.getId());
    }

    public GameDto getGameDetail(GameDto gameDto) {
        return cache.getGameDetail(gameDto.getId());
    }

    /* #### GET ALL #### */

    public List<MediumCopyDto> getAllBookCopies(BookDto bookDto) {
        return mediumCopyService.getCopies(bookDto);
    }

    public List<MediumCopyDto> getAllDvdCopies(DvdDto dvdDto) {
        return mediumCopyService.getCopies(dvdDto);
    }

    public List<MediumCopyDto> getAllGameCopies(GameDto gameDto) {
        return mediumCopyService.getCopies(gameDto);
    }

    public List<TopicDto> getAllTopics() {
        return cache.getAllTopics();
    }

    /**
     * Returns all users.
     *
     * @return List of users
     */
    public List<UserDto> getAllUsers() {
        if (UserService.isUserRoleSufficient(UserRoleName.Librarian, loggedInUser)) {
            return cache.getAllUsers();
        }
        return new LinkedList<>();
    }

    /**
     * Returns all customers.
     *
     * @return List of customers
     */
    public List<UserDto> getAllCustomers() {
        if (UserService.isUserRoleSufficient(UserRoleName.Librarian, loggedInUser)) {
            return cache.getAllCustomers();
        }
        return new LinkedList<>();
    }

    /* #### RESERVATION #### */

    /**
     * Reserves a book.
     *
     * @param reservationDto Dto containing book information
     * @return MessageDto
     */
    public MessageDto<ReservationDto> reserveBook(ReservationDto reservationDto) {
        if (!UserService.isUserRoleSufficient(UserRoleName.Librarian, loggedInUser)) {
            return new MessageDto.MessageDtoBuilder<ReservationDto>()
                .withType(MessageDto.MessageType.FAILURE)
                .withMessage(UNAUTHORIZED_MESSAGE)
                .build();
        }

        MessageDto<ReservationDto> result = reservationService.createReservation(reservationDto);
        cache.invalidateBookCacheMedium(reservationDto.getMediumId());
        return result;
    }

    /**
     * Reserves a dvd.
     *
     * @param reservationDto Dto containing dvd information
     * @return MessageDto
     */
    public MessageDto<ReservationDto> reserveDvd(ReservationDto reservationDto) {
        if (!UserService.isUserRoleSufficient(UserRoleName.Librarian, loggedInUser)) {
            return new MessageDto.MessageDtoBuilder<ReservationDto>()
                .withType(MessageDto.MessageType.FAILURE)
                .withMessage(UNAUTHORIZED_MESSAGE)
                .build();
        }
        MessageDto<ReservationDto> result = reservationService.createReservation(reservationDto);
        cache.invalidateDvdCacheMedium(reservationDto.getMediumId());
        return result;
    }

    /**
     * Reserves a game.
     *
     * @param reservationDto Dto containing game information
     * @return MessageDto
     */
    public MessageDto<ReservationDto> reserveGame(ReservationDto reservationDto) {
        if (!UserService.isUserRoleSufficient(UserRoleName.Librarian, loggedInUser)) {
            return new MessageDto.MessageDtoBuilder<ReservationDto>()
                .withType(MessageDto.MessageType.FAILURE)
                .withMessage(UNAUTHORIZED_MESSAGE)
                .build();
        }
        MessageDto<ReservationDto> result = reservationService.createReservation(reservationDto);
        cache.invalidateGameCacheMedium(reservationDto.getMediumId());
        return result;
    }

    /**
     * Removes a reservation.
     *
     * @param reservationDto Dto containing information
     * @return MessageDto
     */
    public MessageDto<EmptyDto> removeReservation(ReservationDto reservationDto) {
        if (!UserService.isUserRoleSufficient(UserRoleName.Librarian, loggedInUser)) {
            return new MessageDto.MessageDtoBuilder<EmptyDto>()
                .withType(MessageDto.MessageType.FAILURE)
                .withMessage(UNAUTHORIZED_MESSAGE)
                .build();
        }
        return reservationService.deleteReservation(reservationDto);
    }

    /**
     * Returns all reservations for a book.
     *
     * @param bookDto BookDto
     * @return List of reservations
     */
    public List<ReservationDto> getAllBookReservations(BookDto bookDto) {
        if (!UserService.isUserRoleSufficient(UserRoleName.Librarian, loggedInUser)) {
            return new LinkedList<>();
        }
        return reservationService.getReservations(bookDto);
    }

    /**
     * Returns all reservations for a dvd.
     *
     * @param dvdDto BookDto
     * @return List of reservations
     */
    public List<ReservationDto> getAllDvdReservations(DvdDto dvdDto) {
        if (!UserService.isUserRoleSufficient(UserRoleName.Librarian, loggedInUser)) {
            return new LinkedList<>();
        }
        return reservationService.getReservations(dvdDto);
    }

    /**
     * Returns all reservations for a game.
     *
     * @param gameDto BookDto
     * @return List of reservations
     */
    public List<ReservationDto> getAllGameReservations(GameDto gameDto) {
        if (!UserService.isUserRoleSufficient(UserRoleName.Librarian, loggedInUser)) {
            return new LinkedList<>();
        }
        return reservationService.getReservations(gameDto);
    }

    /* ##### LENDING ##### */

    /**
     * Lends a book.
     *
     * @param lendingDto Dto containing information
     * @return MessageDto
     */
    public MessageDto<LendingDto> lendBook(LendingDto lendingDto) {
        if (!UserService.isUserRoleSufficient(UserRoleName.Librarian, loggedInUser)) {
            return new MessageDto.MessageDtoBuilder<LendingDto>()
                .withType(MessageDto.MessageType.FAILURE)
                .withMessage(UNAUTHORIZED_MESSAGE)
                .build();
        }
        MessageDto<LendingDto> result = lendingService.createLending(lendingDto);
        cache.invalidateBookCacheMediumCopy(lendingDto.getMediumCopyId());
        return result;
    }

    /**
     * Lends a dvd.
     *
     * @param lendingDto Dto containing information
     * @return MessageDto
     */
    public MessageDto<LendingDto> lendDvd(LendingDto lendingDto) {
        if (!UserService.isUserRoleSufficient(UserRoleName.Librarian, loggedInUser)) {
            return new MessageDto.MessageDtoBuilder<LendingDto>()
                .withType(MessageDto.MessageType.FAILURE)
                .withMessage(UNAUTHORIZED_MESSAGE)
                .build();
        }
        MessageDto<LendingDto> result = lendingService.createLending(lendingDto);
        cache.invalidateDvdCacheMediumCopy(lendingDto.getMediumCopyId());
        return result;
    }

    /**
     * Lends a game.
     *
     * @param lendingDto Dto containing information
     * @return MessageDto
     */
    public MessageDto<LendingDto> lendGame(LendingDto lendingDto) {
        if (!UserService.isUserRoleSufficient(UserRoleName.Librarian, loggedInUser)) {
            return new MessageDto.MessageDtoBuilder<LendingDto>()
                .withType(MessageDto.MessageType.FAILURE)
                .withMessage(UNAUTHORIZED_MESSAGE)
                .build();
        }
        MessageDto<LendingDto> result = lendingService.createLending(lendingDto);
        cache.invalidateGameCacheMediumCopy(lendingDto.getMediumCopyId());
        return result;
    }

    /**
     * Extends a book.
     *
     * @param mediumCopyDto Dto containing information.
     * @return MessageDto
     */
    public MessageDto<EmptyDto> extendBook(MediumCopyDto mediumCopyDto) {
        if (!UserService.isUserRoleSufficient(UserRoleName.Librarian, loggedInUser)) {
            return new MessageDto.MessageDtoBuilder<EmptyDto>()
                .withType(MessageDto.MessageType.FAILURE)
                .withMessage(UNAUTHORIZED_MESSAGE)
                .build();
        }
        MessageDto<EmptyDto> result = lendingService.extendLending(mediumCopyDto);
        cache.invalidateBookCacheMediumCopy(mediumCopyDto.getId());
        return result;
    }

    /**
     * Extends a dvd.
     *
     * @param mediumCopyDto Dto containing information.
     * @return MessageDto
     */
    public MessageDto<EmptyDto> extendDvd(MediumCopyDto mediumCopyDto) {
        if (!UserService.isUserRoleSufficient(UserRoleName.Librarian, loggedInUser)) {
            return new MessageDto.MessageDtoBuilder<EmptyDto>()
                .withType(MessageDto.MessageType.FAILURE)
                .withMessage(UNAUTHORIZED_MESSAGE)
                .build();
        }
        MessageDto<EmptyDto> result = lendingService.extendLending(mediumCopyDto);
        cache.invalidateDvdCacheMediumCopy(mediumCopyDto.getId());
        return result;
    }

    /**
     * Extends a game.
     *
     * @param mediumCopyDto Dto containing information.
     * @return MessageDto
     */
    public MessageDto<EmptyDto> extendGame(MediumCopyDto mediumCopyDto) {
        if (!UserService.isUserRoleSufficient(UserRoleName.Librarian, loggedInUser)) {
            return new MessageDto.MessageDtoBuilder<EmptyDto>()
                .withType(MessageDto.MessageType.FAILURE)
                .withMessage(UNAUTHORIZED_MESSAGE)
                .build();
        }
        MessageDto<EmptyDto> result = lendingService.extendLending(mediumCopyDto);
        cache.invalidateGameCacheMediumCopy(mediumCopyDto.getId());
        return result;
    }

    /**
     * Returns a book.
     *
     * @param copyDto Dto containing information
     * @return MessageDto
     */
    public MessageDto<EmptyDto> returnBook(MediumCopyDto copyDto) {
        if (!UserService.isUserRoleSufficient(UserRoleName.Librarian, loggedInUser)) {
            return new MessageDto.MessageDtoBuilder<EmptyDto>()
                .withType(MessageDto.MessageType.FAILURE)
                .withMessage(UNAUTHORIZED_MESSAGE)
                .build();
        }
        MessageDto<EmptyDto> result = lendingService.returnLending(copyDto);
        cache.invalidateBookCacheMediumCopy(copyDto.getId());
        return result;
    }

    /**
     * Returns a dvd.
     *
     * @param copyDto Dto containing information
     * @return MessageDto
     */
    public MessageDto<EmptyDto> returnDvd(MediumCopyDto copyDto) {
        if (!UserService.isUserRoleSufficient(UserRoleName.Librarian, loggedInUser)) {
            return new MessageDto.MessageDtoBuilder<EmptyDto>()
                .withType(MessageDto.MessageType.FAILURE)
                .withMessage(UNAUTHORIZED_MESSAGE)
                .build();
        }
        MessageDto<EmptyDto> result = lendingService.returnLending(copyDto);
        cache.invalidateDvdCacheMediumCopy(copyDto.getId());
        return result;
    }

    /**
     * Returns a game.
     *
     * @param copyDto Dto containing information
     * @return MessageDto
     */
    public MessageDto<EmptyDto> returnGame(MediumCopyDto copyDto) {
        if (!UserService.isUserRoleSufficient(UserRoleName.Librarian, loggedInUser)) {
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
    public MessageDto<LoginDto> loginUser(LoginDto loginDto) {
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
            ).build();
    }

    /* #### MESSAGING #### */

    public void registerForMessages(MessageClientInterface client) {
        LOG.debug("Registering new message subscriber [{}]", client.hashCode());
        CLIENTS.add(client);
    }

    public List<CustomMessage> getAllMessages() {
        LOG.debug("Returning all Messages to client");
        return new LinkedList<>(CUSTOM_MESSAGES.keySet());
    }

    public void addMessage(CustomMessage message) {
        addAndSendMessage(message);
    }

    /**
     * Update message Status.
     *
     * @param customMessage message with the same id of an already existing message
     */
    public void updateMessageStatus(CustomMessage customMessage) {
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
        } else if (loggedInUser.getUserRoleName().equals(UserRoleName.Admin)) {
            LOG.info("message admin override");
        } else if (!messageToUpdate.userId.equals(loggedInUser.getId())) {
            LOG.error("Logged-in user is not allowed to update this message");
            return;
        }

        messageToUpdate.status = customMessage.status;

        updateMessage(messageToUpdate);

        if (messageToUpdate.status.equals(CustomMessage.Status.Archived)) {
            if (new MessageService().archiveMessage(messageToUpdate).getType().equals(
                MessageDto.MessageType.ERROR)
            ) {
                LOG.error("Unable to archive message to DB");
            }

            Message jmsMessage = CUSTOM_MESSAGES.get(messageToUpdate);
            try {
                jmsMessage.acknowledge();
            } catch (JMSException e) {
                LOG.error("Unable to archive (acknowledge) message", e);
            }

            CUSTOM_MESSAGES.remove(messageToUpdate);
        }
    }

    /**
     * Add a new message.
     *
     * @param customMessage the new message
     */
    public static void addAndSendMessage(CustomMessage customMessage) {
        try {
            Message m = JmsProducer.getInstance().sendMessage(customMessage);
            CUSTOM_MESSAGES.put(customMessage, m);
        } catch (JMSException e) {
            LOG.error("Cannot send message to queue", e);
        }
    }

    public static void addMessageWithoutSending(CustomMessage customMessage, Message m) {
        CUSTOM_MESSAGES.put(customMessage, m);
    }

    /**
     * Checks if library contains a message.
     *
     * @param customMessage Message to check
     */
    public static boolean containsMessage(CustomMessage customMessage) {
        return CUSTOM_MESSAGES.containsKey(customMessage);
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
                    LOG.error("Cannot send message over JMS: {}", customMessage);
                }
            });
    }

    public static List<MessageClientInterface> getClients() {
        return CLIENTS;
    }
}
