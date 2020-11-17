package at.fhv.teamg.librarymanagement.server.rmi;

import at.fhv.teamg.librarymanagement.server.domain.LendingService;
import at.fhv.teamg.librarymanagement.server.domain.MediumCopyService;
import at.fhv.teamg.librarymanagement.server.domain.ReservationService;
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
    private final LendingService lendingService = new LendingService();
    private final MediumCopyService mediumCopyService = new MediumCopyService();
    private final ReservationService reservationService = new ReservationService();
    private final Cache cache = Cache.getInstance();
    private LoginDto loggedInUser;

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

    @Override
    public List<TopicDto> getAllTopics() throws RemoteException {
        return cache.getAllTopics();
    }

    @Override
    public List<UserDto> getAllUsers() throws RemoteException {
        return cache.getAllUsers();
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

    @Override
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