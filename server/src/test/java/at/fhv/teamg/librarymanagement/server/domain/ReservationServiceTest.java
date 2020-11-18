package at.fhv.teamg.librarymanagement.server.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
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
import at.fhv.teamg.librarymanagement.shared.dto.EmptyDto;
import at.fhv.teamg.librarymanagement.shared.dto.GameDto;
import at.fhv.teamg.librarymanagement.shared.dto.MessageDto;
import at.fhv.teamg.librarymanagement.shared.dto.ReservationDto;
import at.fhv.teamg.librarymanagement.shared.enums.UserRoleName;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import org.junit.jupiter.api.Test;

public class ReservationServiceTest {
    private static final UUID validReservationId = UUID.randomUUID();
    private static final UUID notValidReservationId = UUID.randomUUID();
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
    void createReservation_shouldReturnSuccess() {
        MediumCopy mediumCopyMock = mock(MediumCopy.class);
        when(mediumCopyMock.isAvailable()).thenReturn(false);
        Set<MediumCopy> mediumCopies = new HashSet<>();
        mediumCopies.add(mediumCopyMock);

        Medium mediumMock = mock(Medium.class);
        when(mediumMock.getId()).thenReturn(validMediumId);
        when(mediumMock.getCopies()).thenReturn(mediumCopies);

        Medium otherMediumMock = mock(Medium.class);
        when(otherMediumMock.getId()).thenReturn(notValidMediumId);

        Reservation reservationMock = mock(Reservation.class);
        when(reservationMock.getMedium()).thenReturn(otherMediumMock);
        Set<Reservation> mockedReservations = new HashSet<>();
        mockedReservations.add(reservationMock);

        User userMock = mock(User.class);
        UserRole userRoleMock = mock(UserRole.class);

        when(userMock.getId()).thenReturn(validUserId);
        when(userMock.getRole()).thenReturn(userRoleMock);
        when(userMock.getReservations()).thenReturn(mockedReservations);
        when(userRoleMock.getName()).thenReturn(UserRoleName.Customer);

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

        // Assertions
        MessageDto<ReservationDto> messageDto = reservationService.createReservation(
            builder.build()
        );

        assertEquals(MessageDto.MessageType.SUCCESS, messageDto.getType());
        assertNotNull(messageDto.getResult());
    }

    @Test
    void createReservation_shouldNotReturnSuccess() {
        Medium mediumMock = mock(Medium.class);
        User userMock = mock(User.class);
        UserRole userRoleMock = mock(UserRole.class);

        when(mediumMock.getId()).thenReturn(validMediumId);
        when(userMock.getId()).thenReturn(validUserId);
        when(userMock.getRole()).thenReturn(userRoleMock);
        when(userMock.getReservations()).thenReturn(new HashSet<>());
        when(userRoleMock.getName()).thenReturn(UserRoleName.Admin);

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

        // Medium not found
        MessageDto<ReservationDto> messageDto = reservationService.createReservation(
            builder.build()
        );
        assertEquals(MessageDto.MessageType.FAILURE, messageDto.getType());
        assertNull(messageDto.getResult());

        builder = new ReservationDto.ReservationDtoBuilder();
        builder
            .endDate(LocalDate.now())
            .startDate(LocalDate.now())
            .mediumId(validMediumId)
            .userId(notValidUserId);

        // User not found
        messageDto = reservationService.createReservation(builder.build());
        assertEquals(MessageDto.MessageType.ERROR, messageDto.getType());
        assertNull(messageDto.getResult());

        builder = new ReservationDto.ReservationDtoBuilder();
        builder
            .endDate(LocalDate.now())
            .startDate(LocalDate.now())
            .mediumId(validMediumId)
            .userId(validUserId);
        ReservationDto validReservation = builder.build();

        // User's role doesn't match CUSTOMER
        messageDto = reservationService.createReservation(validReservation);
        assertEquals(MessageDto.MessageType.FAILURE, messageDto.getType());
        assertNull(messageDto.getResult());

        when(userRoleMock.getName()).thenReturn(UserRoleName.Customer);
        doReturn(Optional.empty()).when(reservationService)
            .updateReservation(any(Reservation.class));

        // Updating reservation failed
        messageDto = reservationService.createReservation(validReservation);
        assertEquals(MessageDto.MessageType.ERROR, messageDto.getType());
        assertNull(messageDto.getResult());
    }

    @Test
    void createReservation_shouldReturnFailure_whenCopiesAvailable() {
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
        MessageDto<ReservationDto> messageDto = reservationService.createReservation(
            builder.build()
        );

        assertEquals(MessageDto.MessageType.FAILURE, messageDto.getType());
        assertNull(messageDto.getResult());
    }

    @Test
    void createReservation_shouldReturnFailure_whenUserAlreadyHasReservation() {
        // Mocks
        Medium mediumMock = mock(Medium.class);
        Reservation reservationMock = mock(Reservation.class);

        Set<Reservation> mockedReservations = new HashSet<>();
        mockedReservations.add(reservationMock);

        User userMock = mock(User.class);
        UserRole userRoleMock = mock(UserRole.class);

        when(mediumMock.getId()).thenReturn(validMediumId);
        when(reservationMock.getMedium()).thenReturn(mediumMock);
        when(userMock.getId()).thenReturn(validUserId);
        when(userMock.getRole()).thenReturn(userRoleMock);
        when(userMock.getReservations()).thenReturn(mockedReservations);
        when(userRoleMock.getName()).thenReturn(UserRoleName.Customer);

        // Service
        ReservationService reservationService = spy(ReservationService.class);
        doReturn(Optional.of(mediumMock)).when(reservationService).findMediumById(validMediumId);
        doReturn(Optional.of(userMock)).when(reservationService).findUserById(validUserId);

        ReservationDto.ReservationDtoBuilder builder = new ReservationDto.ReservationDtoBuilder();
        builder.endDate(LocalDate.now())
            .startDate(LocalDate.now())
            .mediumId(validMediumId)
            .userId(validUserId);

        // Assertions
        MessageDto<ReservationDto> messageDto = reservationService.createReservation(
            builder.build()
        );

        assertEquals(MessageDto.MessageType.FAILURE, messageDto.getType());
        assertNull(messageDto.getResult());
    }

    @Test
    void deleteReservation_shouldReturnSuccess() {
        // Mocks
        Medium mediumMock = mock(Medium.class);
        when(mediumMock.getId()).thenReturn(validMediumId);

        Reservation reservationMock = mock(Reservation.class);
        when(reservationMock.getId()).thenReturn(validReservationId);
        when(reservationMock.getMedium()).thenReturn(mediumMock);

        // Service
        ReservationService reservationService = spy(ReservationService.class);
        doReturn(Optional.of(reservationMock)).when(reservationService)
            .findReservationById(validReservationId);
        doReturn(Optional.of(mediumMock)).when(reservationService).findMediumById(validMediumId);
        doReturn(true).when(reservationService).removeReservation(reservationMock);

        ReservationDto.ReservationDtoBuilder builder = new ReservationDto.ReservationDtoBuilder(
            validReservationId
        ).mediumId(validMediumId);

        // Assertions
        MessageDto<EmptyDto> messageDto = reservationService.deleteReservation(builder.build());

        assertEquals(MessageDto.MessageType.SUCCESS, messageDto.getType());
        // No result given due to EmptyDto
        assertNull(messageDto.getResult());
    }

    @Test
    void deleteReservation_shouldReturnFailure_whenReservationNotFound() {
        // Mocks
        Reservation reservationMock = mock(Reservation.class);
        when(reservationMock.getId()).thenReturn(validReservationId);

        // Service
        ReservationService reservationService = spy(ReservationService.class);
        doReturn(Optional.empty()).when(reservationService)
            .findReservationById(notValidReservationId);

        ReservationDto.ReservationDtoBuilder builder = new ReservationDto.ReservationDtoBuilder(
            notValidReservationId
        );

        // Assertions
        MessageDto<EmptyDto> messageDto = reservationService.deleteReservation(builder.build());

        assertEquals(MessageDto.MessageType.FAILURE, messageDto.getType());
        assertNull(messageDto.getResult());
    }

    @Test
    void deleteReservation_shouldReturnFailure_whenMediumNotFound() {
        // Mocks
        Medium mediumMock = mock(Medium.class);
        when(mediumMock.getId()).thenReturn(notValidMediumId);

        Reservation reservationMock = mock(Reservation.class);
        when(reservationMock.getId()).thenReturn(validReservationId);
        when(reservationMock.getMedium()).thenReturn(mediumMock);

        // Service
        ReservationService reservationService = spy(ReservationService.class);
        doReturn(Optional.of(reservationMock)).when(reservationService)
            .findReservationById(validReservationId);
        doReturn(Optional.empty()).when(reservationService).findMediumById(notValidMediumId);

        ReservationDto.ReservationDtoBuilder builder = new ReservationDto.ReservationDtoBuilder(
            validReservationId
        ).mediumId(notValidMediumId);

        // Assertions
        MessageDto<EmptyDto> messageDto = reservationService.deleteReservation(builder.build());

        assertEquals(MessageDto.MessageType.FAILURE, messageDto.getType());
        assertNull(messageDto.getResult());
    }

    @Test
    void deleteReservation_shouldReturnFailure_whenRemovingFails() {
        // Mocks
        Medium mediumMock = mock(Medium.class);
        when(mediumMock.getId()).thenReturn(validMediumId);

        Reservation reservationMock = mock(Reservation.class);
        when(reservationMock.getId()).thenReturn(validReservationId);
        when(reservationMock.getMedium()).thenReturn(mediumMock);

        // Service
        ReservationService reservationService = spy(ReservationService.class);
        doReturn(Optional.of(reservationMock)).when(reservationService)
            .findReservationById(validReservationId);
        doReturn(Optional.of(mediumMock)).when(reservationService).findMediumById(validMediumId);
        doReturn(false).when(reservationService).removeReservation(reservationMock);

        ReservationDto.ReservationDtoBuilder builder = new ReservationDto.ReservationDtoBuilder(
            validReservationId
        ).mediumId(validMediumId);

        // Assertions
        MessageDto<EmptyDto> messageDto = reservationService.deleteReservation(builder.build());

        assertEquals(MessageDto.MessageType.ERROR, messageDto.getType());
        assertNull(messageDto.getResult());
    }
}
