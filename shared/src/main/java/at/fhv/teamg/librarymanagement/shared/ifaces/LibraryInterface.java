package at.fhv.teamg.librarymanagement.shared.ifaces;

import at.fhv.teamg.librarymanagement.shared.dto.BookDto;
import at.fhv.teamg.librarymanagement.shared.dto.DvdDto;
import at.fhv.teamg.librarymanagement.shared.dto.GameDto;
import at.fhv.teamg.librarymanagement.shared.dto.LendingDto;
import at.fhv.teamg.librarymanagement.shared.dto.MediumCopyDto;
import at.fhv.teamg.librarymanagement.shared.dto.ReservationDto;
import at.fhv.teamg.librarymanagement.shared.dto.TopicDto;
import at.fhv.teamg.librarymanagement.shared.dto.UserDto;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Optional;

public interface LibraryInterface extends Remote {
    //Search for all Medium Types
    List<GameDto> searchGame(GameDto gameDto) throws RemoteException;

    List<BookDto> searchBook(BookDto bookDto) throws RemoteException;

    List<DvdDto> searchDvd(DvdDto dvdDto) throws RemoteException;

    //Get Detail for all Medium Types
    GameDto getGameDetail(GameDto gameDto) throws RemoteException;

    BookDto getBookDetail(BookDto bookDto) throws RemoteException;

    DvdDto getDvdDetail(DvdDto dvdDto) throws RemoteException;

    //Get all Copies of all Medium Types
    List<MediumCopyDto> getAllGameCopies(GameDto gameDto) throws RemoteException;

    List<MediumCopyDto> getAllBookCopies(BookDto bookDto) throws RemoteException;

    List<MediumCopyDto> getAllDvdCopies(DvdDto dvdDto) throws RemoteException;

    //Get all Reservations of all Medium Types
    List<ReservationDto> getAllGameReservations(GameDto gameDto) throws RemoteException;

    List<ReservationDto> getAllBookReservations(BookDto bookDto) throws RemoteException;

    List<ReservationDto> getAllDvdReservations(DvdDto dvdDto) throws RemoteException;

    //Reserve for all Medium Types
    ReservationDto  reserveGame(ReservationDto reservationDto) throws RemoteException;

    ReservationDto  reserveBook(ReservationDto reservationDto) throws RemoteException;

    ReservationDto  reserveDvd(ReservationDto reservationDto) throws RemoteException;

    //Lend for all Medium Types
    LendingDto lendGame(LendingDto lendingDto) throws RemoteException;

    LendingDto lendBook(LendingDto lendingDto) throws RemoteException;

    LendingDto lendDvd(LendingDto lendingDto) throws RemoteException;

    //Return all Medium Types
    Boolean returnGame(MediumCopyDto mediumCopyDto) throws RemoteException;

    Boolean returnBook(MediumCopyDto mediumCopyDto) throws RemoteException;

    Boolean returnDvd(MediumCopyDto mediumCopyDto) throws RemoteException;

    //Get all available Topics
    List<TopicDto> getAllTopics() throws RemoteException;

    //Get all Users
    List<UserDto> getAllUsers() throws RemoteException;

}