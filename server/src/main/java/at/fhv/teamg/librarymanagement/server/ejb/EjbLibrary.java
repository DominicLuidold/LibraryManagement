package at.fhv.teamg.librarymanagement.server.ejb;

import at.fhv.teamg.librarymanagement.server.domain.MediumCopyService;
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
import at.fhv.teamg.librarymanagement.shared.ifaces.ejb.EjbLibraryRemote;
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

    private final Cache cache = Cache.getInstance();
    private final MediumCopyService mediumCopyService = new MediumCopyService();

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
        return null;
    }

    /* #### RESERVATION #### */

    @Override
    public MessageDto<ReservationDto> reserveBook(ReservationDto reservationDto) {
        return null;
    }

    @Override
    public MessageDto<ReservationDto> reserveDvd(ReservationDto reservationDto) {
        return null;
    }

    @Override
    public MessageDto<ReservationDto> reserveGame(ReservationDto reservationDto) {
        return null;
    }

    @Override
    public MessageDto<EmptyDto> removeReservation(ReservationDto reservationDto) {
        return null;
    }

    @Override
    public List<ReservationDto> getAllBookReservations(BookDto bookDto) {
        return null;
    }

    @Override
    public List<ReservationDto> getAllDvdReservations(DvdDto dvdDto) {
        return null;
    }

    @Override
    public List<ReservationDto> getAllGameReservations(GameDto gameDto) {
        return null;
    }

    /* ##### LENDING ##### */

    @Override
    public MessageDto<LendingDto> lendBook(LendingDto lendingDto) {
        return null;
    }

    @Override
    public MessageDto<LendingDto> lendGame(LendingDto lendingDto) {
        return null;
    }

    @Override
    public MessageDto<LendingDto> lendDvd(LendingDto lendingDto) {
        return null;
    }

    @Override
    public MessageDto<EmptyDto> extendBook(MediumCopyDto mediumCopyDto) {
        return null;
    }

    @Override
    public MessageDto<EmptyDto> extendDvd(MediumCopyDto mediumCopyDto) {
        return null;
    }

    @Override
    public MessageDto<EmptyDto> extendGame(MediumCopyDto mediumCopyDto) {
        return null;
    }

    /* #### LOGIN #### */

    @Override
    public MessageDto<LoginDto> loginUser(LoginDto loginDto) {
        return null;
    }
}
