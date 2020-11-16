package at.fhv.teamg.librarymanagement.server.domain;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import at.fhv.teamg.librarymanagement.server.persistance.entity.Book;
import at.fhv.teamg.librarymanagement.server.persistance.entity.Dvd;
import at.fhv.teamg.librarymanagement.server.persistance.entity.Game;
import at.fhv.teamg.librarymanagement.server.persistance.entity.Medium;
import at.fhv.teamg.librarymanagement.server.persistance.entity.MediumCopy;
import at.fhv.teamg.librarymanagement.server.persistance.entity.Reservation;
import at.fhv.teamg.librarymanagement.server.persistance.entity.User;
import at.fhv.teamg.librarymanagement.server.persistance.entity.UserRole;
import at.fhv.teamg.librarymanagement.shared.dto.BookDto;
import at.fhv.teamg.librarymanagement.shared.dto.DvdDto;
import at.fhv.teamg.librarymanagement.shared.dto.GameDto;
import at.fhv.teamg.librarymanagement.shared.dto.ReservationDto;
import at.fhv.teamg.librarymanagement.shared.enums.UserRoleName;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import org.junit.jupiter.api.Test;

public class ReservationServiceTest {
    private static final UUID validReservationId =
        UUID.fromString("16748d88-517f-4684-bb39-ef2fa1168d74");
    private static final UUID notValidReservationId =
        UUID.fromString("B16B00B5-4711-1337-6969-BADBADBADBAD");
    private static final UUID validUserId = UUID.randomUUID();
    private static final UUID notValidUserId = UUID.randomUUID();
    private static final UUID validMediumId = UUID.randomUUID();
    private static final UUID notValidMediumId = UUID.randomUUID();

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
        doReturn(Optional.of(bookMock)).when(reservationService).findBookById(validReservationId);

        BookDto bookDtoMock = mock(BookDto.class);
        when(bookDtoMock.getId()).thenReturn(validReservationId);

        assertFalse(reservationService.getReservations(bookDtoMock).isEmpty());
    }

    @Test
    void getReservationsBook_shouldReturnEmptyList() {
        ReservationService reservationService = spy(ReservationService.class);
        doReturn(Optional.empty()).when(reservationService).findBookById(notValidReservationId);

        BookDto bookDtoMock = mock(BookDto.class);
        when(bookDtoMock.getId()).thenReturn(notValidReservationId);

        assertTrue(reservationService.getReservations(bookDtoMock).isEmpty());
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
        doReturn(Optional.of(dvdMock)).when(reservationService).findDvdById(validReservationId);

        DvdDto dvdDtoMock = mock(DvdDto.class);
        when(dvdDtoMock.getId()).thenReturn(validReservationId);

        assertFalse(reservationService.getReservations(dvdDtoMock).isEmpty());
    }

    @Test
    void getReservationsDvd_shouldReturnEmptyList() {
        ReservationService reservationService = spy(ReservationService.class);
        doReturn(Optional.empty()).when(reservationService).findDvdById(notValidReservationId);

        DvdDto dvdDtoMock = mock(DvdDto.class);
        when(dvdDtoMock.getId()).thenReturn(notValidReservationId);

        assertTrue(reservationService.getReservations(dvdDtoMock).isEmpty());
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
        doReturn(Optional.of(gameMock)).when(reservationService).findGameById(validReservationId);

        GameDto gameDtoMock = mock(GameDto.class);
        when(gameDtoMock.getId()).thenReturn(validReservationId);

        assertFalse(reservationService.getReservations(gameDtoMock).isEmpty());
    }

    @Test
    void getReservationsGame_shouldReturnEmptyList() {
        ReservationService reservationService = spy(ReservationService.class);
        doReturn(Optional.empty()).when(reservationService).findGameById(notValidReservationId);

        GameDto gameDtoMock = mock(GameDto.class);
        when(gameDtoMock.getId()).thenReturn(notValidReservationId);

        assertTrue(reservationService.getReservations(gameDtoMock).isEmpty());
    }

    @Test
    void createReservation_shouldReturnDto() {
        MediumCopy mediumCopyMock = mock(MediumCopy.class);
        Set<MediumCopy> mediumCopies = new HashSet<>();
        mediumCopies.add(mediumCopyMock);

        Medium mediumMock = mock(Medium.class);
        User userMock = mock(User.class);
        UserRole userRoleMock = mock(UserRole.class);
        Reservation reservationMock = mock(Reservation.class);

        when(mediumCopyMock.isAvailable()).thenReturn(false);
        when(mediumMock.getId()).thenReturn(validMediumId);
        when(mediumMock.getCopies()).thenReturn(mediumCopies);
        when(userMock.getId()).thenReturn(validUserId);
        when(userMock.getRole()).thenReturn(userRoleMock);
        when(userRoleMock.getName()).thenReturn(UserRoleName.CUSTOMER);

        ReservationService reservationService = spy(ReservationService.class);
        doReturn(Optional.of(mediumMock)).when(reservationService).findMediumById(validMediumId);
        doReturn(Optional.of(userMock)).when(reservationService).findUserById(validUserId);
        doReturn(Optional.of(reservationMock)).when(reservationService)
            .updateReservation(any(Reservation.class));

        ReservationDto.ReservationDtoBuilder builder = new ReservationDto.ReservationDtoBuilder();
        builder.endDate(LocalDate.now())
            .startDate(LocalDate.now())
            .mediumId(validMediumId)
            .userId(validUserId);

        assertTrue(reservationService.createReservation(builder.build()).isPresent());
    }

    @Test
    void createReservation_shouldReturnEmpty() {
        Medium mediumMock = mock(Medium.class);
        User userMock = mock(User.class);
        UserRole userRoleMock = mock(UserRole.class);

        when(mediumMock.getId()).thenReturn(validMediumId);
        when(userMock.getId()).thenReturn(validUserId);
        when(userMock.getRole()).thenReturn(userRoleMock);
        when(userRoleMock.getName()).thenReturn(UserRoleName.ADMIN);

        ReservationService reservationService = spy(ReservationService.class);
        doReturn(Optional.empty()).when(reservationService).findMediumById(notValidMediumId);
        doReturn(Optional.empty()).when(reservationService).findUserById(notValidUserId);
        doReturn(Optional.of(mediumMock)).when(reservationService).findMediumById(validMediumId);
        doReturn(Optional.of(userMock)).when(reservationService).findUserById(validUserId);

        ReservationDto.ReservationDtoBuilder builder = new ReservationDto.ReservationDtoBuilder();
        builder
            .endDate(LocalDate.now())
            .startDate(LocalDate.now())
            .mediumId(notValidMediumId)
            .userId(notValidUserId);
        assertFalse(reservationService.createReservation(builder.build()).isPresent());

        builder = new ReservationDto.ReservationDtoBuilder();
        builder
            .endDate(LocalDate.now())
            .startDate(LocalDate.now())
            .mediumId(validMediumId)
            .userId(notValidUserId);
        assertFalse(reservationService.createReservation(builder.build()).isPresent());

        builder = new ReservationDto.ReservationDtoBuilder();
        builder
            .endDate(LocalDate.now())
            .startDate(LocalDate.now())
            .mediumId(validMediumId)
            .userId(validUserId);
        ReservationDto validReservation = builder.build();
        assertFalse(reservationService.createReservation(validReservation).isPresent());

        when(userRoleMock.getName()).thenReturn(UserRoleName.CUSTOMER);
        doReturn(Optional.empty()).when(reservationService)
            .updateReservation(any(Reservation.class));

        assertFalse(reservationService.createReservation(validReservation).isPresent());
    }

    @Test
    void createReservation_shouldReturnEmpty_whenCopiesAvailable() {
        // Mocks
        MediumCopy mediumCopyMock = mock(MediumCopy.class);
        Set<MediumCopy> mediumCopies = new HashSet<>();
        mediumCopies.add(mediumCopyMock);
        Medium mediumMock = mock(Medium.class);

        when(mediumCopyMock.isAvailable()).thenReturn(true);
        when(mediumMock.getId()).thenReturn(validMediumId);
        when(mediumMock.getCopies()).thenReturn(mediumCopies);

        // Service
        ReservationService reservationService = spy(ReservationService.class);
        doReturn(Optional.of(mediumMock)).when(reservationService).findMediumById(validMediumId);

        ReservationDto.ReservationDtoBuilder builder = new ReservationDto.ReservationDtoBuilder();
        builder.endDate(LocalDate.now())
            .startDate(LocalDate.now())
            .mediumId(validMediumId)
            .userId(validUserId);

        // Assertions
        assertFalse(reservationService.createReservation(builder.build()).isPresent());
    }
}
