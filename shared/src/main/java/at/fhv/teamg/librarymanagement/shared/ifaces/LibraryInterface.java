package at.fhv.teamg.librarymanagement.shared.ifaces;

import at.fhv.teamg.librarymanagement.shared.dto.BookDto;
import at.fhv.teamg.librarymanagement.shared.dto.DvdDto;
import at.fhv.teamg.librarymanagement.shared.dto.GameDto;
import at.fhv.teamg.librarymanagement.shared.dto.LendingDto;
import at.fhv.teamg.librarymanagement.shared.dto.LoginDto;
import at.fhv.teamg.librarymanagement.shared.dto.MediumCopyDto;
import at.fhv.teamg.librarymanagement.shared.dto.MessageDto;
import at.fhv.teamg.librarymanagement.shared.dto.ReservationDto;
import at.fhv.teamg.librarymanagement.shared.dto.TopicDto;
import at.fhv.teamg.librarymanagement.shared.dto.UserDto;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface LibraryInterface extends Remote {
    List<GameDto> searchGame(GameDto gameDto) throws RemoteException;

    List<BookDto> searchBook(BookDto bookDto) throws RemoteException;

    List<DvdDto> searchDvd(DvdDto dvdDto) throws RemoteException;

    GameDto getGameDetail(GameDto gameDto) throws RemoteException;

    BookDto getBookDetail(BookDto bookDto) throws RemoteException;

    DvdDto getDvdDetail(DvdDto dvdDto) throws RemoteException;

    List<MediumCopyDto> getAllGameCopies(GameDto gameDto) throws RemoteException;

    List<MediumCopyDto> getAllBookCopies(BookDto bookDto) throws RemoteException;

    List<MediumCopyDto> getAllDvdCopies(DvdDto dvdDto) throws RemoteException;

    List<ReservationDto> getAllGameReservations(GameDto gameDto) throws RemoteException;

    List<ReservationDto> getAllBookReservations(BookDto bookDto) throws RemoteException;

    List<ReservationDto> getAllDvdReservations(DvdDto dvdDto) throws RemoteException;

    ReservationDto reserveGame(ReservationDto reservationDto) throws RemoteException;

    ReservationDto reserveBook(ReservationDto reservationDto) throws RemoteException;

    ReservationDto reserveDvd(ReservationDto reservationDto) throws RemoteException;

    LendingDto lendGame(LendingDto lendingDto) throws RemoteException;

    LendingDto lendBook(LendingDto lendingDto) throws RemoteException;

    LendingDto lendDvd(LendingDto lendingDto) throws RemoteException;

    Boolean returnGame(MediumCopyDto mediumCopyDto) throws RemoteException;

    Boolean returnBook(MediumCopyDto mediumCopyDto) throws RemoteException;

    Boolean returnDvd(MediumCopyDto mediumCopyDto) throws RemoteException;

    List<TopicDto> getAllTopics() throws RemoteException;

    List<UserDto> getAllUsers() throws RemoteException;

    MessageDto extendBook(MediumCopyDto mediumCopyDto) throws RemoteException;

    MessageDto extendDvd(MediumCopyDto mediumCopyDto) throws RemoteException;

    MessageDto extendGame(MediumCopyDto mediumCopyDto) throws RemoteException;

    LoginDto loginUser(LoginDto loginDto) throws RemoteException;

}