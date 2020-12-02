package at.fhv.teamg.librarymanagement.server.ejb;

import at.fhv.teamg.librarymanagement.server.domain.LendingService;
import at.fhv.teamg.librarymanagement.server.domain.MediumCopyService;
import at.fhv.teamg.librarymanagement.server.domain.ReservationService;
import at.fhv.teamg.librarymanagement.server.domain.UserService;
import at.fhv.teamg.librarymanagement.server.rmi.Cache;
import at.fhv.teamg.librarymanagement.shared.dto.BookDto;
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
import at.fhv.teamg.librarymanagement.shared.ifaces.ejb.EjbLibraryRemote;
import java.util.LinkedList;
import java.util.List;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Remote(EjbLibraryRemote.class)
@Stateless
public class EjbLibrary implements EjbLibraryRemote {
    private static final Logger LOG = LogManager.getLogger(EjbLibrary.class);
    private static final long serialVersionUID = -7519307780728640540L;

    private static final String UNAUTHORIZED_MESSAGE = "User not authorized to perform this action";

    private final Cache cache = Cache.getInstance();
    private final LendingService lendingService = new LendingService();
    private final MediumCopyService mediumCopyService = new MediumCopyService();
    private final ReservationService reservationService = new ReservationService();
    private final UserService userService = new UserService();

    private LoginDto loggedInUser;

    /* #### SEARCH #### */

    @Override
    public List<BookDto> searchBook(BookDto bookDto) {
        return cache.searchBook(bookDto);
    }

    @Override
    public List<DvdDto> searchDvd(DvdDto dvdDto) {
        return cache.searchDvd(dvdDto);
    }

    @Override
    public List<GameDto> searchGame(GameDto gameDto) {
        return cache.searchGame(gameDto);
    }

    /* #### DETAILS #### */

    @Override
    public BookDto getBookDetail(BookDto bookDto) {
        return cache.getBookDetail(bookDto.getId());
    }

    @Override
    public DvdDto getDvdDetail(DvdDto dvdDto) {
        return cache.getDvdDetail(dvdDto.getId());
    }

    @Override
    public GameDto getGameDetail(GameDto gameDto) {
        return cache.getGameDetail(gameDto.getId());
    }

    /* #### GET ALL #### */

    @Override
    public List<MediumCopyDto> getAllBookCopies(BookDto bookDto) {
        return mediumCopyService.getCopies(bookDto);
    }

    @Override
    public List<MediumCopyDto> getAllDvdCopies(DvdDto dvdDto) {
        return mediumCopyService.getCopies(dvdDto);
    }

    @Override
    public List<MediumCopyDto> getAllGameCopies(GameDto gameDto) {
        return mediumCopyService.getCopies(gameDto);
    }

    @Override
    public List<TopicDto> getAllTopics() {
        return cache.getAllTopics();
    }

    @Override
    public List<UserDto> getAllCustomers() {
        if (UserService.isUserRoleSufficient(UserRoleName.Librarian, loggedInUser)) {
            return cache.getAllCustomers();
        }
        return new LinkedList<>();
    }

    /* #### RESERVATION #### */

    @Override
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

    @Override
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

    @Override
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

    @Override
    public MessageDto<EmptyDto> removeReservation(ReservationDto reservationDto) {
        if (!UserService.isUserRoleSufficient(UserRoleName.Librarian, loggedInUser)) {
            return new MessageDto.MessageDtoBuilder<EmptyDto>()
                .withType(MessageDto.MessageType.FAILURE)
                .withMessage(UNAUTHORIZED_MESSAGE)
                .build();
        }
        return reservationService.deleteReservation(reservationDto);
    }

    @Override
    public List<ReservationDto> getAllBookReservations(BookDto bookDto) {
        if (!UserService.isUserRoleSufficient(UserRoleName.Librarian, loggedInUser)) {
            return new LinkedList<>();
        }
        return reservationService.getReservations(bookDto);
    }

    @Override
    public List<ReservationDto> getAllDvdReservations(DvdDto dvdDto) {
        if (!UserService.isUserRoleSufficient(UserRoleName.Librarian, loggedInUser)) {
            return new LinkedList<>();
        }
        return reservationService.getReservations(dvdDto);
    }

    @Override
    public List<ReservationDto> getAllGameReservations(GameDto gameDto) {
        if (!UserService.isUserRoleSufficient(UserRoleName.Librarian, loggedInUser)) {
            return new LinkedList<>();
        }
        return reservationService.getReservations(gameDto);
    }

    /* ##### LENDING ##### */

    @Override
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

    @Override
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

    @Override
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

    @Override
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

    @Override
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

    @Override
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

    /* #### LOGIN #### */

    @Override
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
}
