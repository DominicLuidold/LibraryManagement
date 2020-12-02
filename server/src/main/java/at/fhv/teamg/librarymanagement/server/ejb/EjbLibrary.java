package at.fhv.teamg.librarymanagement.server.ejb;

import at.fhv.teamg.librarymanagement.server.common.BaseLibrary;
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
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

@Remote(EjbLibraryRemote.class)
@Stateless
public class EjbLibrary implements EjbLibraryRemote {
    private static final Logger LOG = LogManager.getLogger(EjbLibrary.class);
    private static final long serialVersionUID = -5750370668080459015L;

    private final BaseLibrary baseLibrary = BaseLibrary.getInstance();

    /* #### SEARCH #### */

    @Override
    public List<BookDto> searchBook(BookDto bookDto) {
        return baseLibrary.searchBook(bookDto);
    }

    @Override
    public List<DvdDto> searchDvd(DvdDto dvdDto) {
        return baseLibrary.searchDvd(dvdDto);
    }

    @Override
    public List<GameDto> searchGame(GameDto gameDto) {
        return baseLibrary.searchGame(gameDto);
    }

    /* #### DETAILS #### */

    @Override
    public BookDto getBookDetail(BookDto bookDto) {
        return baseLibrary.getBookDetail(bookDto);
    }

    @Override
    public DvdDto getDvdDetail(DvdDto dvdDto) {
        return baseLibrary.getDvdDetail(dvdDto);
    }

    @Override
    public GameDto getGameDetail(GameDto gameDto) {
        return baseLibrary.getGameDetail(gameDto);
    }

    /* #### GET ALL #### */

    @Override
    public List<MediumCopyDto> getAllBookCopies(BookDto bookDto) {
        return baseLibrary.getAllBookCopies(bookDto);
    }

    @Override
    public List<MediumCopyDto> getAllDvdCopies(DvdDto dvdDto) {
        return baseLibrary.getAllDvdCopies(dvdDto);
    }

    @Override
    public List<MediumCopyDto> getAllGameCopies(GameDto gameDto) {
        return baseLibrary.getAllGameCopies(gameDto);
    }

    @Override
    public List<TopicDto> getAllTopics() {
        return baseLibrary.getAllTopics();
    }

    @Override
    public List<UserDto> getAllCustomers() {
        return baseLibrary.getAllCustomers();
    }

    /* #### RESERVATION #### */

    @Override
    public MessageDto<ReservationDto> reserveBook(ReservationDto reservationDto) {
        return baseLibrary.reserveBook(reservationDto);
    }

    @Override
    public MessageDto<ReservationDto> reserveDvd(ReservationDto reservationDto) {
        return baseLibrary.reserveDvd(reservationDto);
    }

    @Override
    public MessageDto<ReservationDto> reserveGame(ReservationDto reservationDto) {
        return baseLibrary.reserveGame(reservationDto);
    }

    @Override
    public MessageDto<EmptyDto> removeReservation(ReservationDto reservationDto) {
        return baseLibrary.removeReservation(reservationDto);
    }

    @Override
    public List<ReservationDto> getAllBookReservations(BookDto bookDto) {
        return baseLibrary.getAllBookReservations(bookDto);
    }

    @Override
    public List<ReservationDto> getAllDvdReservations(DvdDto dvdDto) {
        return baseLibrary.getAllDvdReservations(dvdDto);
    }

    @Override
    public List<ReservationDto> getAllGameReservations(GameDto gameDto) {
        return baseLibrary.getAllGameReservations(gameDto);
    }

    /* ##### LENDING ##### */

    @Override
    public MessageDto<LendingDto> lendBook(LendingDto lendingDto) {
        return baseLibrary.lendBook(lendingDto);
    }

    @Override
    public MessageDto<LendingDto> lendDvd(LendingDto lendingDto) {
        return baseLibrary.lendDvd(lendingDto);
    }

    @Override
    public MessageDto<LendingDto> lendGame(LendingDto lendingDto) {
        return baseLibrary.lendGame(lendingDto);
    }

    @Override
    public MessageDto<EmptyDto> extendBook(MediumCopyDto mediumCopyDto) {
        return baseLibrary.extendBook(mediumCopyDto);
    }

    @Override
    public MessageDto<EmptyDto> extendDvd(MediumCopyDto mediumCopyDto) {
        return baseLibrary.extendDvd(mediumCopyDto);
    }

    @Override
    public MessageDto<EmptyDto> extendGame(MediumCopyDto mediumCopyDto) {
        return baseLibrary.extendGame(mediumCopyDto);
    }

    @Override
    public MessageDto<EmptyDto> returnBook(MediumCopyDto mediumCopyDto) {
        return baseLibrary.returnBook(mediumCopyDto);
    }

    @Override
    public MessageDto<EmptyDto> returnDvd(MediumCopyDto mediumCopyDto) {
        return baseLibrary.returnDvd(mediumCopyDto);
    }

    @Override
    public MessageDto<EmptyDto> returnGame(MediumCopyDto mediumCopyDto) {
        return baseLibrary.returnGame(mediumCopyDto);
    }

    /* #### LOGIN #### */

    @Override
    public MessageDto<LoginDto> loginUser(LoginDto loginDto) {
        return baseLibrary.loginUser(loginDto);
    }
}
