package at.fhv.teamg.librarymanagement.server.rmi;

import at.fhv.teamg.librarymanagement.server.BaseLibrary;
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
import at.fhv.teamg.librarymanagement.shared.ifaces.LibraryInterface;
import at.fhv.teamg.librarymanagement.shared.ifaces.MessageClientInterface;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import javax.jms.Message;

public class Library extends UnicastRemoteObject implements LibraryInterface {
    private static final long serialVersionUID = 4717665108551967512L;

    private final BaseLibrary baseLibrary = BaseLibrary.getInstance();

    public Library() throws RemoteException {
        super();
    }

    /* #### SEARCH #### */

    @Override
    public List<BookDto> searchBook(BookDto bookDto) throws RemoteException {
        return baseLibrary.searchBook(bookDto);
    }

    @Override
    public List<DvdDto> searchDvd(DvdDto dvdDto) throws RemoteException {
        return baseLibrary.searchDvd(dvdDto);
    }

    @Override
    public List<GameDto> searchGame(GameDto gameDto) throws RemoteException {
        return baseLibrary.searchGame(gameDto);
    }

    /* #### DETAILS #### */

    @Override
    public BookDto getBookDetail(BookDto bookDto) throws RemoteException {
        return baseLibrary.getBookDetail(bookDto);
    }

    @Override
    public DvdDto getDvdDetail(DvdDto dvdDto) throws RemoteException {
        return baseLibrary.getDvdDetail(dvdDto);
    }

    @Override
    public GameDto getGameDetail(GameDto gameDto) throws RemoteException {
        return baseLibrary.getGameDetail(gameDto);
    }

    /* #### GET ALL #### */

    @Override
    public List<MediumCopyDto> getAllBookCopies(BookDto bookDto) throws RemoteException {
        return baseLibrary.getAllBookCopies(bookDto);
    }

    @Override
    public List<MediumCopyDto> getAllDvdCopies(DvdDto dvdDto) throws RemoteException {
        return baseLibrary.getAllDvdCopies(dvdDto);
    }

    @Override
    public List<MediumCopyDto> getAllGameCopies(GameDto gameDto) throws RemoteException {
        return baseLibrary.getAllGameCopies(gameDto);
    }

    @Override
    public List<TopicDto> getAllTopics() throws RemoteException {
        return baseLibrary.getAllTopics();
    }

    @Override
    public List<UserDto> getAllUsers() throws RemoteException {
        return baseLibrary.getAllUsers();
    }

    @Override
    public List<UserDto> getAllCustomers() throws RemoteException {
        return baseLibrary.getAllCustomers();
    }

    /* #### RESERVATION #### */

    @Override
    public MessageDto<ReservationDto> reserveBook(ReservationDto reservationDto)
        throws RemoteException {
        return baseLibrary.reserveBook(reservationDto);
    }

    @Override
    public MessageDto<ReservationDto> reserveDvd(ReservationDto reservationDto)
        throws RemoteException {
        return baseLibrary.reserveDvd(reservationDto);
    }

    @Override
    public MessageDto<ReservationDto> reserveGame(ReservationDto reservationDto)
        throws RemoteException {
        return baseLibrary.reserveGame(reservationDto);
    }

    @Override
    public MessageDto<EmptyDto> removeReservation(ReservationDto reservationDto)
        throws RemoteException {
        return baseLibrary.removeReservation(reservationDto);
    }

    @Override
    public List<ReservationDto> getAllBookReservations(BookDto bookDto) throws RemoteException {
        return baseLibrary.getAllBookReservations(bookDto);
    }

    @Override
    public List<ReservationDto> getAllDvdReservations(DvdDto dvdDto) throws RemoteException {
        return baseLibrary.getAllDvdReservations(dvdDto);
    }

    @Override
    public List<ReservationDto> getAllGameReservations(GameDto gameDto) throws RemoteException {
        return baseLibrary.getAllGameReservations(gameDto);
    }

    /* ##### LENDING ##### */

    @Override
    public MessageDto<LendingDto> lendBook(LendingDto lendingDto) throws RemoteException {
        return baseLibrary.lendBook(lendingDto);
    }

    @Override
    public MessageDto<LendingDto> lendDvd(LendingDto lendingDto) throws RemoteException {
        return baseLibrary.lendDvd(lendingDto);
    }

    @Override
    public MessageDto<LendingDto> lendGame(LendingDto lendingDto) throws RemoteException {
        return baseLibrary.lendGame(lendingDto);
    }

    @Override
    public MessageDto<EmptyDto> extendBook(MediumCopyDto mediumCopyDto) throws RemoteException {
        return baseLibrary.extendBook(mediumCopyDto);
    }

    @Override
    public MessageDto<EmptyDto> extendDvd(MediumCopyDto mediumCopyDto) throws RemoteException {
        return baseLibrary.extendDvd(mediumCopyDto);
    }

    @Override
    public MessageDto<EmptyDto> extendGame(MediumCopyDto mediumCopyDto) throws RemoteException {
        return baseLibrary.extendGame(mediumCopyDto);
    }

    @Override
    public MessageDto<EmptyDto> returnBook(MediumCopyDto mediumCopyDto) throws RemoteException {
        return baseLibrary.returnBook(mediumCopyDto);
    }

    @Override
    public MessageDto<EmptyDto> returnDvd(MediumCopyDto mediumCopyDto) throws RemoteException {
        return baseLibrary.returnDvd(mediumCopyDto);
    }

    @Override
    public MessageDto<EmptyDto> returnGame(MediumCopyDto mediumCopyDto) throws RemoteException {
        return baseLibrary.returnGame(mediumCopyDto);
    }

    /* #### LOGIN #### */

    @Override
    public MessageDto<LoginDto> loginUser(LoginDto loginDto) throws RemoteException {
        return baseLibrary.loginUser(loginDto);
    }

    /* #### MESSAGING #### */

    @Override
    public void registerForMessages(MessageClientInterface client) throws RemoteException {
        baseLibrary.registerForMessages(client);
    }

    @Override
    public List<CustomMessage> getAllMessages() throws RemoteException {
        return baseLibrary.getAllMessages();
    }

    @Override
    public void addMessage(CustomMessage message) throws RemoteException {
        baseLibrary.addMessage(message);
    }

    @Override
    public void updateMessageStatus(CustomMessage customMessage) throws RemoteException {
        baseLibrary.updateMessageStatus(customMessage);
    }

    public static void addAndSendMessage(CustomMessage customMessage) {
        BaseLibrary.addAndSendMessage(customMessage);
    }

    public static void addMessageWithoutSending(CustomMessage customMessage, Message m) {
        BaseLibrary.addMessageWithoutSending(customMessage, m);
    }

    public static boolean containsMessage(CustomMessage customMessage) {
        return BaseLibrary.containsMessage(customMessage);
    }

    public static void updateMessage(CustomMessage customMessage) {
        BaseLibrary.updateMessage(customMessage);
    }

    public static List<MessageClientInterface> getClients() {
        return BaseLibrary.getClients();
    }
}
