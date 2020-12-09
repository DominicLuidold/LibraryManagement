package at.fhv.teamg.librarymanagement.server.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import at.fhv.teamg.librarymanagement.server.persistence.entity.Lending;
import at.fhv.teamg.librarymanagement.server.persistence.entity.Medium;
import at.fhv.teamg.librarymanagement.server.persistence.entity.MediumCopy;
import at.fhv.teamg.librarymanagement.server.persistence.entity.MediumType;
import at.fhv.teamg.librarymanagement.server.persistence.entity.Reservation;
import at.fhv.teamg.librarymanagement.server.persistence.entity.User;
import at.fhv.teamg.librarymanagement.server.persistence.entity.UserRole;
import at.fhv.teamg.librarymanagement.shared.dto.EmptyDto;
import at.fhv.teamg.librarymanagement.shared.dto.LendingDto;
import at.fhv.teamg.librarymanagement.shared.dto.MediumCopyDto;
import at.fhv.teamg.librarymanagement.shared.dto.MessageDto;
import at.fhv.teamg.librarymanagement.shared.enums.UserRoleName;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import org.junit.jupiter.api.Test;

public class LendingServiceTest {
    private static final UUID validCopyID = UUID.randomUUID();
    private static final UUID notValidCopyID = UUID.randomUUID();
    private static final UUID validUserID = UUID.randomUUID();
    private static final UUID notValidUserID = UUID.randomUUID();

    @Test
    void createLending_shouldReturnSuccess() {
        MediumCopy mediumCopyMock = mock(MediumCopy.class);
        User userMock = mock(User.class);
        Lending lendingMock = mock(Lending.class);
        Medium mediumMock = mock(Medium.class);
        MediumType mediumTypeMock = mock(MediumType.class);
        UserRole role = mock(UserRole.class);

        when(mediumCopyMock.isAvailable()).thenReturn(true);
        when(mediumCopyMock.getMedium()).thenReturn(mediumMock);
        when(mediumMock.getType()).thenReturn(mediumTypeMock);

        LendingService lendingService = spy(LendingService.class);
        doReturn(Optional.of(mediumCopyMock)).when(lendingService).findMediumCopyById(validCopyID);
        doReturn(Optional.of(userMock)).when(lendingService).findUserById(validUserID);
        doReturn(Optional.of(lendingMock)).when(lendingService).updateLending(any(Lending.class));
        doReturn(Optional.of(mediumCopyMock)).when(lendingService)
            .updateMediumCopy(any(MediumCopy.class));
        doReturn(UserRoleName.CustomerExternalLibrary).when(role).getName();
        doReturn(role).when(userMock).getRole();

        LendingDto.LendingDtoBuilder builder = new LendingDto.LendingDtoBuilder();
        builder.endDate(LocalDate.now())
            .renewalCount(0)
            .mediumCopyId(validCopyID)
            .returnDate(null)
            .startDate(LocalDate.now())
            .userId(validUserID);

        // Assertions
        MessageDto<LendingDto> messageDto = lendingService.createLending(builder.build());

        assertEquals(messageDto.getType(), MessageDto.MessageType.SUCCESS);
        assertNotNull(messageDto.getResult());
    }

    @Test
    void createLending_shouldNotReturnSuccess() {
        MediumCopy mediumCopyMock = mock(MediumCopy.class);
        Medium mediumMock = mock(Medium.class);
        MediumType mediumTypeMock = mock(MediumType.class);

        when(mediumCopyMock.isAvailable()).thenReturn(false);
        when(mediumCopyMock.getMedium()).thenReturn(mediumMock);
        when(mediumMock.getType()).thenReturn(mediumTypeMock);

        LendingService lendingService = spy(LendingService.class);
        doReturn(Optional.empty()).when(lendingService).findMediumCopyById(notValidCopyID);
        doReturn(Optional.empty()).when(lendingService).findUserById(notValidUserID);
        doReturn(Optional.empty()).when(lendingService).updateLending(any(Lending.class));
        doReturn(Optional.of(mediumCopyMock)).when(lendingService)
            .updateMediumCopy(any(MediumCopy.class));

        LendingDto.LendingDtoBuilder builder = new LendingDto.LendingDtoBuilder();
        builder.endDate(LocalDate.now())
            .renewalCount(0)
            .mediumCopyId(notValidCopyID)
            .returnDate(null)
            .startDate(LocalDate.now())
            .userId(notValidUserID);

        // Medium copy not found
        MessageDto<LendingDto> messageDto = lendingService.createLending(builder.build());
        assertEquals(MessageDto.MessageType.FAILURE, messageDto.getType());
        assertNull(messageDto.getResult());

        builder.endDate(LocalDate.now())
            .renewalCount(0)
            .mediumCopyId(validCopyID)
            .returnDate(null)
            .startDate(LocalDate.now())
            .userId(notValidUserID);

        // Medium copy not available
        doReturn(Optional.of(mediumCopyMock)).when(lendingService)
            .findMediumCopyById(validCopyID);
        messageDto = lendingService.createLending(builder.build());
        assertEquals(MessageDto.MessageType.FAILURE, messageDto.getType());
        assertNull(messageDto.getResult());

        // User not found
        when(mediumCopyMock.isAvailable()).thenReturn(true);
        messageDto = lendingService.createLending(builder.build());
        assertEquals(MessageDto.MessageType.ERROR, messageDto.getType());
        assertNull(messageDto.getResult());

        builder.endDate(LocalDate.now())
            .renewalCount(0)
            .mediumCopyId(validCopyID)
            .returnDate(null)
            .startDate(LocalDate.now())
            .userId(validUserID);
        doReturn(Optional.of(mock(User.class))).when(lendingService).findUserById(validUserID);

        // Lending update failed
        messageDto = lendingService.createLending(builder.build());
        assertEquals(MessageDto.MessageType.ERROR, messageDto.getType());
        assertNull(messageDto.getResult());
    }

    @Test
    void returnLending_shouldReturnSuccess() {
        // Mock currently active Lending
        Lending lendingMock = mock(Lending.class);
        when(lendingMock.getStartDate()).thenReturn(LocalDate.now().minusDays(1));
        when(lendingMock.getEndDate()).thenReturn(LocalDate.now().plusDays(1));

        Set<Lending> lendingSet = new HashSet<>();
        lendingSet.add(lendingMock);

        // Mock incoming DTO
        MediumCopyDto mediumCopyDtoMock = mock(MediumCopyDto.class);
        when(mediumCopyDtoMock.getId()).thenReturn(validCopyID);

        // Mock MediumCopy entity
        MediumCopy mediumCopyMock = mock(MediumCopy.class);
        when(mediumCopyMock.isAvailable()).thenReturn(false);
        when(mediumCopyMock.getLending()).thenReturn(lendingSet);

        //Mock Medium entity
        Medium mediumMock = mock(Medium.class);
        when(mediumCopyMock.getMedium()).thenReturn(mediumMock);

        //Mock Reservations
        when(mediumMock.getReservations()).thenReturn(new LinkedHashSet<>());

        // Mock Lending service
        LendingService lendingService = spy(LendingService.class);
        doReturn(Optional.of(mediumCopyMock)).when(lendingService).findMediumCopyById(validCopyID);
        doReturn(Optional.of(lendingMock)).when(lendingService).updateLending(lendingMock);
        doReturn(Optional.of(mediumCopyMock)).when(lendingService)
            .updateMediumCopy(mediumCopyMock);

        // Assertions
        MessageDto<EmptyDto> messageDto = lendingService.returnLending(mediumCopyDtoMock);

        assertEquals(MessageDto.MessageType.SUCCESS, messageDto.getType());
        // No result given due to EmptyDto
        assertNull(messageDto.getResult());
    }

    @Test
    void returnLending_shouldReturnFailure_whenMediumCopyNotFound() {
        // Mock incoming DTO
        MediumCopyDto mediumCopyDtoMock = mock(MediumCopyDto.class);
        when(mediumCopyDtoMock.getId()).thenReturn(notValidCopyID);

        // Mock Lending service
        LendingService lendingService = spy(LendingService.class);
        doReturn(Optional.empty()).when(lendingService).findMediumCopyById(notValidCopyID);

        // Assertions
        MessageDto<EmptyDto> messageDto = lendingService.returnLending(mediumCopyDtoMock);

        assertEquals(MessageDto.MessageType.FAILURE, messageDto.getType());
        assertNull(messageDto.getResult());
    }

    @Test
    void returnLending_shouldReturnFailure_whenMediumCopyAvailable() {
        // Mock incoming DTO
        MediumCopyDto mediumCopyDtoMock = mock(MediumCopyDto.class);
        when(mediumCopyDtoMock.getId()).thenReturn(validCopyID);

        // Mock MediumCopy entity
        MediumCopy mediumCopyMock = mock(MediumCopy.class);
        when(mediumCopyMock.isAvailable()).thenReturn(true);

        // Mock Lending service
        LendingService lendingService = spy(LendingService.class);
        doReturn(Optional.of(mediumCopyMock)).when(lendingService).findMediumCopyById(validCopyID);

        // Assertions
        MessageDto<EmptyDto> messageDto = lendingService.returnLending(mediumCopyDtoMock);

        assertEquals(MessageDto.MessageType.FAILURE, messageDto.getType());
        assertNull(messageDto.getResult());
    }

    @Test
    void returnLending_shouldReturnError_whenNoLendingFound() {
        // Mock currently active Lending
        Lending lendingMock = mock(Lending.class);
        when(lendingMock.getStartDate()).thenReturn(LocalDate.now().minusDays(1));
        when(lendingMock.getEndDate()).thenReturn(LocalDate.now().plusDays(1));

        Set<Lending> lendingSet = new HashSet<>();

        // Mock incoming DTO
        MediumCopyDto mediumCopyDtoMock = mock(MediumCopyDto.class);
        when(mediumCopyDtoMock.getId()).thenReturn(validCopyID);

        // Mock MediumCopy entity
        MediumCopy mediumCopyMock = mock(MediumCopy.class);
        when(mediumCopyMock.isAvailable()).thenReturn(false);
        when(mediumCopyMock.getLending()).thenReturn(lendingSet);

        // Mock Lending service
        LendingService lendingService = spy(LendingService.class);
        doReturn(Optional.of(mediumCopyMock)).when(lendingService).findMediumCopyById(validCopyID);

        // Assertions
        MessageDto<EmptyDto> messageDto = lendingService.returnLending(mediumCopyDtoMock);

        assertEquals(MessageDto.MessageType.ERROR, messageDto.getType());
        assertNull(messageDto.getResult());
    }

    @Test
    void returnLending_shouldReturnError_whenLendingUpdateFails() {
        // Mock currently active Lending
        Lending lendingMock = mock(Lending.class);
        when(lendingMock.getStartDate()).thenReturn(LocalDate.now().minusDays(1));
        when(lendingMock.getEndDate()).thenReturn(LocalDate.now().plusDays(1));

        Set<Lending> lendingSet = new HashSet<>();
        lendingSet.add(lendingMock);

        // Mock incoming DTO
        MediumCopyDto mediumCopyDtoMock = mock(MediumCopyDto.class);
        when(mediumCopyDtoMock.getId()).thenReturn(validCopyID);

        // Mock MediumCopy entity
        MediumCopy mediumCopyMock = mock(MediumCopy.class);
        when(mediumCopyMock.isAvailable()).thenReturn(false);
        when(mediumCopyMock.getLending()).thenReturn(lendingSet);

        // Mock Lending service
        LendingService lendingService = spy(LendingService.class);
        doReturn(Optional.of(mediumCopyMock)).when(lendingService).findMediumCopyById(validCopyID);
        doReturn(Optional.empty()).when(lendingService).updateLending(lendingMock);

        // Assertions
        MessageDto<EmptyDto> messageDto = lendingService.returnLending(mediumCopyDtoMock);

        assertEquals(MessageDto.MessageType.ERROR, messageDto.getType());
        assertNull(messageDto.getResult());
    }

    @Test
    void returnLending_shouldReturnError_whenMediumCopyUpdateFails() {
        // Mock currently active Lending
        Lending lendingMock = mock(Lending.class);
        when(lendingMock.getStartDate()).thenReturn(LocalDate.now().minusDays(1));
        when(lendingMock.getEndDate()).thenReturn(LocalDate.now().plusDays(1));

        Set<Lending> lendingSet = new HashSet<>();
        lendingSet.add(lendingMock);

        // Mock incoming DTO
        MediumCopyDto mediumCopyDtoMock = mock(MediumCopyDto.class);
        when(mediumCopyDtoMock.getId()).thenReturn(validCopyID);

        // Mock MediumCopy entity
        MediumCopy mediumCopyMock = mock(MediumCopy.class);
        when(mediumCopyMock.isAvailable()).thenReturn(false);
        when(mediumCopyMock.getLending()).thenReturn(lendingSet);

        // Mock Lending service
        LendingService lendingService = spy(LendingService.class);
        doReturn(Optional.of(mediumCopyMock)).when(lendingService).findMediumCopyById(validCopyID);
        doReturn(Optional.of(lendingMock)).when(lendingService).updateLending(lendingMock);
        doReturn(Optional.empty()).when(lendingService)
            .updateMediumCopy(mediumCopyMock);

        // Assertions
        MessageDto<EmptyDto> messageDto = lendingService.returnLending(mediumCopyDtoMock);

        assertEquals(MessageDto.MessageType.ERROR, messageDto.getType());
        assertNull(messageDto.getResult());
    }

    @Test
    void extendLending_shouldReturnSuccess() {
        // Mock currently active Lending
        Lending lendingMock = mock(Lending.class);
        when(lendingMock.getStartDate()).thenReturn(LocalDate.now().minusDays(1));
        when(lendingMock.getEndDate()).thenReturn(LocalDate.now().plusDays(1));
        when(lendingMock.getRenewalCount()).thenReturn(1);

        Set<Lending> lendingSet = new HashSet<>();
        lendingSet.add(lendingMock);

        // Mock incoming DTO
        MediumCopyDto mediumCopyDtoMock = mock(MediumCopyDto.class);
        when(mediumCopyDtoMock.getId()).thenReturn(validCopyID);

        // Mock Medium entity
        Medium mediumMock = mock(Medium.class);
        when(mediumMock.getReservations()).thenReturn(new HashSet<>());

        // Mock MediumCopy entity
        MediumCopy mediumCopyMock = mock(MediumCopy.class);
        when(mediumCopyMock.getMedium()).thenReturn(mediumMock);
        when(mediumCopyMock.isAvailable()).thenReturn(false);
        when(mediumCopyMock.getLending()).thenReturn(lendingSet);

        // Mock Lending service
        LendingService lendingService = spy(LendingService.class);
        doReturn(Optional.of(mediumCopyMock)).when(lendingService).findMediumCopyById(validCopyID);
        doReturn(Optional.of(lendingMock)).when(lendingService).updateLending(lendingMock);

        // Assertions
        MessageDto<EmptyDto> messageDto = lendingService.extendLending(mediumCopyDtoMock);

        assertEquals(MessageDto.MessageType.SUCCESS, messageDto.getType());
        // No result given due to EmptyDto
        assertNull(messageDto.getResult());
    }

    @Test
    void extendLending_shouldReturnFailure_whenMediumCopyNotFound() {
        // Mock incoming DTO
        MediumCopyDto mediumCopyDtoMock = mock(MediumCopyDto.class);
        when(mediumCopyDtoMock.getId()).thenReturn(notValidCopyID);

        // Mock Lending service
        LendingService lendingService = spy(LendingService.class);
        doReturn(Optional.empty()).when(lendingService).findMediumCopyById(notValidCopyID);

        // Assertions
        MessageDto<EmptyDto> messageDto = lendingService.extendLending(mediumCopyDtoMock);

        assertEquals(MessageDto.MessageType.FAILURE, messageDto.getType());
        assertNull(messageDto.getResult());
    }

    @Test
    void extendLending_shouldReturnFailure_whenMediumCopyAvailable() {
        // Mock incoming DTO
        MediumCopyDto mediumCopyDtoMock = mock(MediumCopyDto.class);
        when(mediumCopyDtoMock.getId()).thenReturn(validCopyID);

        // Mock MediumCopy entity
        MediumCopy mediumCopyMock = mock(MediumCopy.class);
        when(mediumCopyMock.isAvailable()).thenReturn(true);

        // Mock Lending service
        LendingService lendingService = spy(LendingService.class);
        doReturn(Optional.of(mediumCopyMock)).when(lendingService).findMediumCopyById(validCopyID);

        // Assertions
        MessageDto<EmptyDto> messageDto = lendingService.extendLending(mediumCopyDtoMock);

        assertEquals(MessageDto.MessageType.FAILURE, messageDto.getType());
        assertNull(messageDto.getResult());
    }

    @Test
    void extendLending_shouldReturnFailure_whenReservationsFound() {
        // Mock incoming DTO
        MediumCopyDto mediumCopyDtoMock = mock(MediumCopyDto.class);
        when(mediumCopyDtoMock.getId()).thenReturn(validCopyID);

        // Mock Reservation entity
        Reservation reservationMock = mock(Reservation.class);
        Set<Reservation> reservations = new HashSet<>();
        reservations.add(reservationMock);

        // Mock Medium entity
        Medium mediumMock = mock(Medium.class);
        when(mediumMock.getReservations()).thenReturn(reservations);

        // Mock MediumCopy entity
        MediumCopy mediumCopyMock = mock(MediumCopy.class);
        when(mediumCopyMock.getMedium()).thenReturn(mediumMock);
        when(mediumCopyMock.isAvailable()).thenReturn(false);

        // Mock Lending service
        LendingService lendingService = spy(LendingService.class);
        doReturn(Optional.of(mediumCopyMock)).when(lendingService).findMediumCopyById(validCopyID);

        // Assertions
        MessageDto<EmptyDto> messageDto = lendingService.extendLending(mediumCopyDtoMock);

        assertEquals(MessageDto.MessageType.FAILURE, messageDto.getType());
        assertNull(messageDto.getResult());
    }

    @Test
    void extendLending_shouldReturnError_whenNoLendingFound() {
        // Mock currently active Lending
        Lending lendingMock = mock(Lending.class);
        when(lendingMock.getStartDate()).thenReturn(LocalDate.now().minusDays(1));
        when(lendingMock.getEndDate()).thenReturn(LocalDate.now().plusDays(1));

        Set<Lending> lendingSet = new HashSet<>();

        // Mock incoming DTO
        MediumCopyDto mediumCopyDtoMock = mock(MediumCopyDto.class);
        when(mediumCopyDtoMock.getId()).thenReturn(validCopyID);

        // Mock Medium entity
        Medium mediumMock = mock(Medium.class);
        when(mediumMock.getReservations()).thenReturn(new HashSet<>());

        // Mock MediumCopy entity
        MediumCopy mediumCopyMock = mock(MediumCopy.class);
        when(mediumCopyMock.getMedium()).thenReturn(mediumMock);
        when(mediumCopyMock.isAvailable()).thenReturn(false);
        when(mediumCopyMock.getLending()).thenReturn(lendingSet);

        // Mock Lending service
        LendingService lendingService = spy(LendingService.class);
        doReturn(Optional.of(mediumCopyMock)).when(lendingService).findMediumCopyById(validCopyID);

        // Assertions
        MessageDto<EmptyDto> messageDto = lendingService.extendLending(mediumCopyDtoMock);

        assertEquals(MessageDto.MessageType.ERROR, messageDto.getType());
        assertNull(messageDto.getResult());
    }

    @Test
    void extendLending_shouldReturnFailure_whenMaxRenewalsReached() {
        // Mock currently active Lending
        Lending lendingMock = mock(Lending.class);
        when(lendingMock.getStartDate()).thenReturn(LocalDate.now().minusDays(1));
        when(lendingMock.getEndDate()).thenReturn(LocalDate.now().plusDays(1));
        when(lendingMock.getRenewalCount()).thenReturn(2);

        Set<Lending> lendingSet = new HashSet<>();
        lendingSet.add(lendingMock);

        // Mock incoming DTO
        MediumCopyDto mediumCopyDtoMock = mock(MediumCopyDto.class);
        when(mediumCopyDtoMock.getId()).thenReturn(validCopyID);

        // Mock Medium entity
        Medium mediumMock = mock(Medium.class);
        when(mediumMock.getReservations()).thenReturn(new HashSet<>());

        // Mock MediumCopy entity
        MediumCopy mediumCopyMock = mock(MediumCopy.class);
        when(mediumCopyMock.getMedium()).thenReturn(mediumMock);
        when(mediumCopyMock.isAvailable()).thenReturn(false);
        when(mediumCopyMock.getLending()).thenReturn(lendingSet);

        // Mock Lending service
        LendingService lendingService = spy(LendingService.class);
        doReturn(Optional.of(mediumCopyMock)).when(lendingService).findMediumCopyById(validCopyID);

        // Assertions
        MessageDto<EmptyDto> messageDto = lendingService.extendLending(mediumCopyDtoMock);

        assertEquals(MessageDto.MessageType.FAILURE, messageDto.getType());
        assertNull(messageDto.getResult());
    }

    @Test
    void extendLending_shouldReturnFailure_whenExtendingNotReasonable() {
        // Mock currently active Lending
        Lending lendingMock = mock(Lending.class);
        when(lendingMock.getStartDate()).thenReturn(LocalDate.now().minusDays(1));
        // 15 days > 2 weeks extending duration
        when(lendingMock.getEndDate()).thenReturn(LocalDate.now().plusDays(15));

        Set<Lending> lendingSet = new HashSet<>();
        lendingSet.add(lendingMock);

        // Mock incoming DTO
        MediumCopyDto mediumCopyDtoMock = mock(MediumCopyDto.class);
        when(mediumCopyDtoMock.getId()).thenReturn(validCopyID);

        // Mock Medium entity
        Medium mediumMock = mock(Medium.class);
        when(mediumMock.getReservations()).thenReturn(new HashSet<>());

        // Mock MediumCopy entity
        MediumCopy mediumCopyMock = mock(MediumCopy.class);
        when(mediumCopyMock.getMedium()).thenReturn(mediumMock);
        when(mediumCopyMock.isAvailable()).thenReturn(false);
        when(mediumCopyMock.getLending()).thenReturn(lendingSet);

        // Mock Lending service
        LendingService lendingService = spy(LendingService.class);
        doReturn(Optional.of(mediumCopyMock)).when(lendingService).findMediumCopyById(validCopyID);

        // Assertions - lending would end before current end date
        MessageDto<EmptyDto> messageDto = lendingService.extendLending(mediumCopyDtoMock);

        assertEquals(MessageDto.MessageType.FAILURE, messageDto.getType());
        assertNull(messageDto.getResult());

        // Assertions - lending would end on current end date
        when(lendingMock.getEndDate()).thenReturn(LocalDate.now().plusDays(14));
        messageDto = lendingService.extendLending(mediumCopyDtoMock);

        assertEquals(MessageDto.MessageType.FAILURE, messageDto.getType());
        assertNull(messageDto.getResult());
    }

    @Test
    void extendLending_shouldReturnError_whenLendingUpdateFails() {
        // Mock currently active Lending
        Lending lendingMock = mock(Lending.class);
        when(lendingMock.getStartDate()).thenReturn(LocalDate.now().minusDays(1));
        when(lendingMock.getEndDate()).thenReturn(LocalDate.now().plusDays(1));

        Set<Lending> lendingSet = new HashSet<>();
        lendingSet.add(lendingMock);

        // Mock incoming DTO
        MediumCopyDto mediumCopyDtoMock = mock(MediumCopyDto.class);
        when(mediumCopyDtoMock.getId()).thenReturn(validCopyID);

        // Mock Medium entity
        Medium mediumMock = mock(Medium.class);
        when(mediumMock.getReservations()).thenReturn(new HashSet<>());

        // Mock MediumCopy entity
        MediumCopy mediumCopyMock = mock(MediumCopy.class);
        when(mediumCopyMock.getMedium()).thenReturn(mediumMock);
        when(mediumCopyMock.isAvailable()).thenReturn(false);
        when(mediumCopyMock.getLending()).thenReturn(lendingSet);

        // Mock Lending service
        LendingService lendingService = spy(LendingService.class);
        doReturn(Optional.of(mediumCopyMock)).when(lendingService).findMediumCopyById(validCopyID);
        doReturn(Optional.empty()).when(lendingService).updateLending(lendingMock);

        // Assertions
        MessageDto<EmptyDto> messageDto = lendingService.extendLending(mediumCopyDtoMock);

        assertEquals(MessageDto.MessageType.ERROR, messageDto.getType());
        assertNull(messageDto.getResult());
    }

    @Test
    void getCurrentLending_shouldReturnLending_whenActiveLendingGiven() {
        // Mock lending
        Lending lendingMock = mock(Lending.class);
        when(lendingMock.getStartDate()).thenReturn(LocalDate.now().minusDays(1));
        when(lendingMock.getEndDate()).thenReturn(LocalDate.now().plusDays(1));

        Set<Lending> lendingSet = new HashSet<>();
        lendingSet.add(lendingMock);

        // Assertions
        assertTrue(new LendingService().getCurrentLending(lendingSet).isPresent());
    }

    @Test
    void getCurrentLending_shouldReturnLending_whenLendingStartsEndsToday() {
        // Mock lending
        Lending lendingMock = mock(Lending.class);
        when(lendingMock.getStartDate()).thenReturn(LocalDate.now());
        when(lendingMock.getEndDate()).thenReturn(LocalDate.now());

        Set<Lending> lendingSet = new HashSet<>();
        lendingSet.add(lendingMock);

        // Assertions
        assertTrue(new LendingService().getCurrentLending(lendingSet).isPresent());
    }

    @Test
    void getCurrentLending_shouldReturnLending_whenLendingStartsTomorrow() {
        // Mock lending
        Lending lendingMock = mock(Lending.class);
        when(lendingMock.getStartDate()).thenReturn(LocalDate.now().plusDays(1));
        when(lendingMock.getEndDate()).thenReturn(LocalDate.now().plusDays(2));

        Set<Lending> lendingSet = new HashSet<>();
        lendingSet.add(lendingMock);

        // Assertions
        assertFalse(new LendingService().getCurrentLending(lendingSet).isPresent());
    }

    @Test
    void getCurrentLending_shouldReturnEmpty_whenLendingEndedYesterday() {
        // Mock lending
        Lending lendingMock = mock(Lending.class);
        when(lendingMock.getStartDate()).thenReturn(LocalDate.now().minusDays(2));
        when(lendingMock.getEndDate()).thenReturn(LocalDate.now().minusDays(1));

        Set<Lending> lendingSet = new HashSet<>();
        lendingSet.add(lendingMock);

        // Assertions
        assertFalse(new LendingService().getCurrentLending(lendingSet).isPresent());
    }

    @Test
    void getCurrentLending_shouldReturnEmpty_whenReturnDateGiven() {
        // Mock lending
        Lending lendingMock = mock(Lending.class);
        when(lendingMock.getStartDate()).thenReturn(LocalDate.now().minusDays(2));
        when(lendingMock.getEndDate()).thenReturn(LocalDate.now().plusDays(2));
        when(lendingMock.getReturnDate()).thenReturn(LocalDate.now());

        Set<Lending> lendingSet = new HashSet<>();
        lendingSet.add(lendingMock);

        // Assertions
        assertFalse(new LendingService().getCurrentLending(lendingSet).isPresent());
    }

    @Test
    void getCurrentLending_shouldReturnEmpty_whenNoLendingGiven() {
        assertFalse(new LendingService().getCurrentLending(new HashSet<>()).isPresent());
    }
}
