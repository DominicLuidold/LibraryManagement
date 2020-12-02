package at.fhv.teamg.librarymanagement.client.ejb;

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
import java.util.Properties;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EjbClient implements EjbLibraryRemote {
    private static final Logger LOG = LogManager.getLogger(EjbClient.class);
    private static final String SERVER_ADDRESS = "vsts-team007.westeurope.cloudapp.azure.com";

    private static EjbClient EJB_INSTANCE;
    private EjbLibraryRemote ejbLibrary;

    private EjbClient() {
        Properties properties = new Properties();
        properties.put(
            Context.INITIAL_CONTEXT_FACTORY,
            "org.wildfly.naming.client.WildFlyInitialContextFactory"
        );
        properties.put(Context.PROVIDER_URL, "http-remoting://" + SERVER_ADDRESS + ":8080");

        try {
            Context context = new InitialContext(properties);
            ejbLibrary = (EjbLibraryRemote) context.lookup(
                "ejb:/LibraryServer/EjbLibrary!at.fhv.teamg.librarymanagement.shared.ifaces.ejb"
                    + ".EjbLibraryRemote"
            );
        } catch (NamingException e) {
            LOG.error(e);
        }
    }

    /**
     * Returns a singleton instance.
     *
     * @return EjbClient singleton instance
     */
    public static EjbClient getInstance() {
        if (null == EJB_INSTANCE) {
            EJB_INSTANCE = new EjbClient();
        }

        return EJB_INSTANCE;
    }

    /* #### SEARCH #### */

    @Override
    public List<BookDto> searchBook(BookDto bookDto) {
        return ejbLibrary.searchBook(bookDto);
    }

    @Override
    public List<DvdDto> searchDvd(DvdDto dvdDto) {
        return ejbLibrary.searchDvd(dvdDto);
    }

    @Override
    public List<GameDto> searchGame(GameDto gameDto) {
        return ejbLibrary.searchGame(gameDto);
    }

    /* #### DETAILS #### */

    @Override
    public BookDto getBookDetail(BookDto bookDto) {
        return ejbLibrary.getBookDetail(bookDto);
    }

    @Override
    public DvdDto getDvdDetail(DvdDto dvdDto) {
        return ejbLibrary.getDvdDetail(dvdDto);
    }

    @Override
    public GameDto getGameDetail(GameDto gameDto) {
        return ejbLibrary.getGameDetail(gameDto);
    }

    /* #### GET ALL #### */

    @Override
    public List<MediumCopyDto> getAllBookCopies(BookDto bookDto) {
        return ejbLibrary.getAllBookCopies(bookDto);
    }

    @Override
    public List<MediumCopyDto> getAllDvdCopies(DvdDto dvdDto) {
        return ejbLibrary.getAllDvdCopies(dvdDto);
    }

    @Override
    public List<MediumCopyDto> getAllGameCopies(GameDto gameDto) {
        return ejbLibrary.getAllGameCopies(gameDto);
    }

    @Override
    public List<TopicDto> getAllTopics() {
        return ejbLibrary.getAllTopics();
    }

    @Override
    public List<UserDto> getAllCustomers() {
        return ejbLibrary.getAllCustomers();
    }

    /* #### RESERVATION #### */

    @Override
    public MessageDto<ReservationDto> reserveBook(ReservationDto reservationDto) {
        return ejbLibrary.reserveBook(reservationDto);
    }

    @Override
    public MessageDto<ReservationDto> reserveDvd(ReservationDto reservationDto) {
        return ejbLibrary.reserveDvd(reservationDto);
    }

    @Override
    public MessageDto<ReservationDto> reserveGame(ReservationDto reservationDto) {
        return ejbLibrary.reserveGame(reservationDto);
    }

    @Override
    public MessageDto<EmptyDto> removeReservation(ReservationDto reservationDto) {
        return ejbLibrary.removeReservation(reservationDto);
    }

    @Override
    public List<ReservationDto> getAllBookReservations(BookDto bookDto) {
        return ejbLibrary.getAllBookReservations(bookDto);
    }

    @Override
    public List<ReservationDto> getAllDvdReservations(DvdDto dvdDto) {
        return ejbLibrary.getAllDvdReservations(dvdDto);
    }

    @Override
    public List<ReservationDto> getAllGameReservations(GameDto gameDto) {
        return ejbLibrary.getAllGameReservations(gameDto);
    }

    /* ##### LENDING ##### */

    @Override
    public MessageDto<LendingDto> lendBook(LendingDto lendingDto) {
        return ejbLibrary.lendBook(lendingDto);
    }

    @Override
    public MessageDto<LendingDto> lendGame(LendingDto lendingDto) {
        return ejbLibrary.lendGame(lendingDto);
    }

    @Override
    public MessageDto<LendingDto> lendDvd(LendingDto lendingDto) {
        return ejbLibrary.lendDvd(lendingDto);
    }

    @Override
    public MessageDto<EmptyDto> extendBook(MediumCopyDto mediumCopyDto) {
        return ejbLibrary.extendBook(mediumCopyDto);
    }

    @Override
    public MessageDto<EmptyDto> extendDvd(MediumCopyDto mediumCopyDto) {
        return ejbLibrary.extendDvd(mediumCopyDto);
    }

    @Override
    public MessageDto<EmptyDto> extendGame(MediumCopyDto mediumCopyDto) {
        return ejbLibrary.extendGame(mediumCopyDto);
    }

    /* #### LOGIN #### */

    @Override
    public MessageDto<LoginDto> loginUser(LoginDto loginDto) {
        return ejbLibrary.loginUser(loginDto);
    }
}
