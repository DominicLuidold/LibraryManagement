package at.fhv.teamg.librarymanagement.shared.ifaces;

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
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface LibraryInterface extends Remote {
    /* #### SEARCH #### */

    List<BookDto> searchBook(BookDto bookDto) throws RemoteException;

    List<DvdDto> searchDvd(DvdDto dvdDto) throws RemoteException;

    List<GameDto> searchGame(GameDto gameDto) throws RemoteException;

    /* #### DETAILS #### */

    BookDto getBookDetail(BookDto bookDto) throws RemoteException;

    DvdDto getDvdDetail(DvdDto dvdDto) throws RemoteException;

    GameDto getGameDetail(GameDto gameDto) throws RemoteException;

    /* #### GET ALL #### */

    List<MediumCopyDto> getAllBookCopies(BookDto bookDto) throws RemoteException;

    List<MediumCopyDto> getAllDvdCopies(DvdDto dvdDto) throws RemoteException;

    List<MediumCopyDto> getAllGameCopies(GameDto gameDto) throws RemoteException;

    List<TopicDto> getAllTopics() throws RemoteException;

    List<UserDto> getAllUsers() throws RemoteException;

    List<UserDto> getAllCustomers() throws RemoteException;

    /* #### RESERVATION #### */

    MessageDto<ReservationDto> reserveBook(ReservationDto reservationDto) throws RemoteException;

    MessageDto<ReservationDto> reserveDvd(ReservationDto reservationDto) throws RemoteException;

    MessageDto<ReservationDto> reserveGame(ReservationDto reservationDto) throws RemoteException;

    MessageDto<EmptyDto> removeReservation(ReservationDto reservationDto) throws RemoteException;

    List<ReservationDto> getAllBookReservations(BookDto bookDto) throws RemoteException;

    List<ReservationDto> getAllDvdReservations(DvdDto dvdDto) throws RemoteException;

    List<ReservationDto> getAllGameReservations(GameDto gameDto) throws RemoteException;

    /* ##### LENDING ##### */

    MessageDto<LendingDto> lendBook(LendingDto lendingDto) throws RemoteException;

    MessageDto<LendingDto> lendGame(LendingDto lendingDto) throws RemoteException;

    MessageDto<LendingDto> lendDvd(LendingDto lendingDto) throws RemoteException;

    MessageDto<EmptyDto> extendBook(MediumCopyDto mediumCopyDto) throws RemoteException;

    MessageDto<EmptyDto> extendDvd(MediumCopyDto mediumCopyDto) throws RemoteException;

    MessageDto<EmptyDto> extendGame(MediumCopyDto mediumCopyDto) throws RemoteException;

    MessageDto<EmptyDto> returnBook(MediumCopyDto mediumCopyDto) throws RemoteException;

    MessageDto<EmptyDto> returnDvd(MediumCopyDto mediumCopyDto) throws RemoteException;

    MessageDto<EmptyDto> returnGame(MediumCopyDto mediumCopyDto) throws RemoteException;

    /* #### LOGIN #### */

    MessageDto<LoginDto> loginUser(LoginDto loginDto) throws RemoteException;

    /* #### MESSAGING #### */

    void registerForMessages(MessageClientInterface client) throws RemoteException;

    List<CustomMessage> getAllMessages() throws RemoteException;

    void updateMessageStatus(CustomMessage customMessage) throws RemoteException;
}