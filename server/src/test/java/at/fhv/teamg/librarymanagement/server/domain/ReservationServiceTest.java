package at.fhv.teamg.librarymanagement.server.domain;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import at.fhv.teamg.librarymanagement.server.persistance.entity.Book;
import at.fhv.teamg.librarymanagement.server.persistance.entity.Dvd;
import at.fhv.teamg.librarymanagement.server.persistance.entity.Game;
import at.fhv.teamg.librarymanagement.server.persistance.entity.Medium;
import at.fhv.teamg.librarymanagement.server.persistance.entity.Reservation;
import at.fhv.teamg.librarymanagement.server.persistance.entity.User;
import at.fhv.teamg.librarymanagement.shared.dto.BookDto;
import at.fhv.teamg.librarymanagement.shared.dto.DvdDto;
import at.fhv.teamg.librarymanagement.shared.dto.GameDto;
import java.util.HashSet;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;

public class ReservationServiceTest {
    private static final UUID validId = UUID.fromString("16748d88-517f-4684-bb39-ef2fa1168d74");
    private static final UUID notValidId = UUID.fromString("B16B00B5-4711-1337-6969-BADBADBADBAD");

    @Test
    void getReservationsBook_shouldReturnFilledList() {
        Book bookMock = mock(Book.class);
        Medium mediumMock = mock(Medium.class);
        Reservation reservationMock = mock(Reservation.class);
        HashSet<Reservation> reservations = new HashSet<>();
        reservations.add(reservationMock);
        User userMock = mock(User.class);

        when(bookMock.getMedium()).thenReturn(mediumMock);
        when(mediumMock.getReservations()).thenReturn(reservations);
        when(reservationMock.getUser()).thenReturn(userMock);

        ReservationService reservationService = spy(ReservationService.class);
        doReturn(Optional.of(bookMock)).when(reservationService).findBookById(validId);

        BookDto bookDtoMock = mock(BookDto.class);
        when(bookDtoMock.getId()).thenReturn(validId);

        assert (!reservationService.getReservations(bookDtoMock).isEmpty());
    }

    @Test
    void getReservationsBook_shouldReturnEmptyList() {
        ReservationService reservationService = spy(ReservationService.class);
        doReturn(Optional.empty()).when(reservationService).findBookById(notValidId);

        BookDto bookDtoMock = mock(BookDto.class);
        when(bookDtoMock.getId()).thenReturn(notValidId);

        assert (reservationService.getReservations(bookDtoMock).isEmpty());
    }

    @Test
    void getReservationsDvd_shouldReturnFilledList() {
        Dvd dvdMock = mock(Dvd.class);
        Medium mediumMock = mock(Medium.class);
        Reservation reservationMock = mock(Reservation.class);
        HashSet<Reservation> reservations = new HashSet<>();
        reservations.add(reservationMock);
        User userMock = mock(User.class);

        when(dvdMock.getMedium()).thenReturn(mediumMock);
        when(mediumMock.getReservations()).thenReturn(reservations);
        when(reservationMock.getUser()).thenReturn(userMock);

        ReservationService reservationService = spy(ReservationService.class);
        doReturn(Optional.of(dvdMock)).when(reservationService).findDvdById(validId);

        DvdDto dvdDtoMock = mock(DvdDto.class);
        when(dvdDtoMock.getId()).thenReturn(validId);

        assert (!reservationService.getReservations(dvdDtoMock).isEmpty());
    }

    @Test
    void getReservationsDvd_shouldReturnEmptyList() {
        ReservationService reservationService = spy(ReservationService.class);
        doReturn(Optional.empty()).when(reservationService).findDvdById(notValidId);

        DvdDto dvdDtoMock = mock(DvdDto.class);
        when(dvdDtoMock.getId()).thenReturn(notValidId);

        assert (reservationService.getReservations(dvdDtoMock).isEmpty());
    }

    @Test
    void getReservationsGame_shouldReturnFilledList() {
        Game gameMock = mock(Game.class);
        Medium mediumMock = mock(Medium.class);
        Reservation reservationMock = mock(Reservation.class);
        HashSet<Reservation> reservations = new HashSet<>();
        reservations.add(reservationMock);
        User userMock = mock(User.class);

        when(gameMock.getMedium()).thenReturn(mediumMock);
        when(mediumMock.getReservations()).thenReturn(reservations);
        when(reservationMock.getUser()).thenReturn(userMock);

        ReservationService reservationService = spy(ReservationService.class);
        doReturn(Optional.of(gameMock)).when(reservationService).findGameById(validId);

        GameDto gameDtoMock = mock(GameDto.class);
        when(gameDtoMock.getId()).thenReturn(validId);

        assert (!reservationService.getReservations(gameDtoMock).isEmpty());
    }

    @Test
    void getReservationsGame_shouldReturnEmptyList() {
        ReservationService reservationService = spy(ReservationService.class);
        doReturn(Optional.empty()).when(reservationService).findGameById(notValidId);

        GameDto gameDtoMock = mock(GameDto.class);
        when(gameDtoMock.getId()).thenReturn(notValidId);

        assert (reservationService.getReservations(gameDtoMock).isEmpty());
    }
}
