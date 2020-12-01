package at.fhv.teamg.librarymanagement.client.ejb;

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

    private EjbLibraryRemote ejbLibrary;

    public EjbClient() {
        Properties properties = new Properties();
        properties.put(
            Context.INITIAL_CONTEXT_FACTORY,
            "org.wildfly.naming.client.WildFlyInitialContextFactory"
        );
        properties.put(Context.PROVIDER_URL, "http-remoting://" + SERVER_ADDRESS + ":8080");

        try {
            Context context = new InitialContext(properties);
            ejbLibrary = (EjbLibraryRemote) context.lookup(
                "ejb:/LibraryServer/EjbTest!at.fhv.teamg.librarymanagement.shared.ifaces.ejb"
                    + ".EjbLibrary"
            );
        } catch (NamingException e) {
            LOG.error(e);
        }
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
        return null;
    }

    @Override
    public DvdDto getDvdDetail(DvdDto dvdDto) {
        return null;
    }

    @Override
    public GameDto getGameDetail(GameDto gameDto) {
        return null;
    }

    /* #### GET ALL #### */

    @Override
    public List<MediumCopyDto> getAllBookCopies(BookDto bookDto) {
        return null;
    }

    @Override
    public List<MediumCopyDto> getAllDvdCopies(DvdDto dvdDto) {
        return null;
    }

    @Override
    public List<MediumCopyDto> getAllGameCopies(GameDto gameDto) {
        return null;
    }

    @Override
    public List<TopicDto> getAllTopics() {
        return null;
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
}
