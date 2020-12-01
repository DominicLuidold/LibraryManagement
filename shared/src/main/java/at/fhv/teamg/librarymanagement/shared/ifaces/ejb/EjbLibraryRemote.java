package at.fhv.teamg.librarymanagement.shared.ifaces.ejb;

import at.fhv.teamg.librarymanagement.shared.dto.BookDto;
import at.fhv.teamg.librarymanagement.shared.dto.DvdDto;
import at.fhv.teamg.librarymanagement.shared.dto.EmptyDto;
import at.fhv.teamg.librarymanagement.shared.dto.GameDto;
import at.fhv.teamg.librarymanagement.shared.dto.LendingDto;
import at.fhv.teamg.librarymanagement.shared.dto.MediumCopyDto;
import at.fhv.teamg.librarymanagement.shared.dto.MessageDto;
import at.fhv.teamg.librarymanagement.shared.dto.ReservationDto;
import at.fhv.teamg.librarymanagement.shared.dto.TopicDto;
import at.fhv.teamg.librarymanagement.shared.dto.UserDto;
import at.fhv.teamg.librarymanagement.shared.ifaces.LibraryInterface;
import java.io.Serializable;
import java.util.List;
import javax.ejb.Remote;

/**
 * This interface contains some of the methods defined in {@link LibraryInterface}.
 * The EJB implementation does not aim to provide full functionality, compared to RMI.
 */
@Remote
public interface EjbLibraryRemote extends Serializable {
    /* #### SEARCH #### */

    List<BookDto> searchBook(BookDto bookDto);

    List<DvdDto> searchDvd(DvdDto dvdDto);

    List<GameDto> searchGame(GameDto gameDto);

    /* #### DETAILS #### */

    BookDto getBookDetail(BookDto bookDto);

    DvdDto getDvdDetail(DvdDto dvdDto);

    GameDto getGameDetail(GameDto gameDto);

    /* #### GET ALL #### */

    List<MediumCopyDto> getAllBookCopies(BookDto bookDto);

    List<MediumCopyDto> getAllDvdCopies(DvdDto dvdDto);

    List<MediumCopyDto> getAllGameCopies(GameDto gameDto);

    List<TopicDto> getAllTopics();

    List<UserDto> getAllCustomers();

    /* #### RESERVATION #### */

    MessageDto<ReservationDto> reserveBook(ReservationDto reservationDto);

    MessageDto<ReservationDto> reserveDvd(ReservationDto reservationDto);

    MessageDto<ReservationDto> reserveGame(ReservationDto reservationDto);

    MessageDto<EmptyDto> removeReservation(ReservationDto reservationDto);

    List<ReservationDto> getAllBookReservations(BookDto bookDto);

    List<ReservationDto> getAllDvdReservations(DvdDto dvdDto);

    List<ReservationDto> getAllGameReservations(GameDto gameDto);

    /* ##### LENDING ##### */

    MessageDto<LendingDto> lendBook(LendingDto lendingDto);

    MessageDto<LendingDto> lendGame(LendingDto lendingDto);

    MessageDto<LendingDto> lendDvd(LendingDto lendingDto);

    MessageDto<EmptyDto> extendBook(MediumCopyDto mediumCopyDto);

    MessageDto<EmptyDto> extendDvd(MediumCopyDto mediumCopyDto);

    MessageDto<EmptyDto> extendGame(MediumCopyDto mediumCopyDto);
}
