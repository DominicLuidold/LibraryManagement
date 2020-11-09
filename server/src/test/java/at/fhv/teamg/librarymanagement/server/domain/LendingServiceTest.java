package at.fhv.teamg.librarymanagement.server.domain;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import at.fhv.teamg.librarymanagement.server.persistance.entity.Lending;
import at.fhv.teamg.librarymanagement.server.persistance.entity.MediumCopy;
import at.fhv.teamg.librarymanagement.server.persistance.entity.User;
import at.fhv.teamg.librarymanagement.shared.dto.LendingDto;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;

public class LendingServiceTest {
    private static final UUID validCopyID = UUID.randomUUID();
    private static final UUID notValidCopyID = UUID.randomUUID();
    private static final UUID validUserID = UUID.randomUUID();
    private static final UUID notValidUserID = UUID.randomUUID();

    @Test
    void createLending_shouldReturnDto() {
        MediumCopy mediumCopyMock = mock(MediumCopy.class);
        User userMock = mock(User.class);
        Lending lendingMock = mock(Lending.class);

        when(mediumCopyMock.isAvailable()).thenReturn(true);

        LendingService lendingService = spy(LendingService.class);
        doReturn(Optional.of(mediumCopyMock)).when(lendingService).findMediumCopyById(validCopyID);
        doReturn(Optional.of(userMock)).when(lendingService).findUserById(validUserID);
        doReturn(Optional.of(lendingMock)).when(lendingService).updateLending(any(Lending.class));
        doReturn(Optional.of(mediumCopyMock)).when(lendingService)
            .updateMediumCopy(any(MediumCopy.class));

        LendingDto.LendingDtoBuilder builder = new LendingDto.LendingDtoBuilder();
        builder.endDate(LocalDate.now())
            .renewalCount(0)
            .mediumCopyId(validCopyID)
            .returnDate(null)
            .startDate(LocalDate.now())
            .userId(validUserID);

        assertTrue(lendingService.createLending(builder.build()).isPresent());
    }

    @Test
    void createLending_shouldReturnEmpty() {
        MediumCopy mediumCopyMock = mock(MediumCopy.class);

        Lending lendingMock = mock(Lending.class);

        when(mediumCopyMock.isAvailable()).thenReturn(false);

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

        assertFalse(lendingService.createLending(builder.build()).isPresent());

        builder.endDate(LocalDate.now())
            .renewalCount(0)
            .mediumCopyId(validCopyID)
            .returnDate(null)
            .startDate(LocalDate.now())
            .userId(notValidUserID);

        doReturn(Optional.of(mediumCopyMock)).when(lendingService)
            .findMediumCopyById(validCopyID);
        assertFalse(lendingService.createLending(builder.build()).isPresent());

        when(mediumCopyMock.isAvailable()).thenReturn(true);
        assertFalse(lendingService.createLending(builder.build()).isPresent());

        builder.endDate(LocalDate.now())
            .renewalCount(0)
            .mediumCopyId(validCopyID)
            .returnDate(null)
            .startDate(LocalDate.now())
            .userId(validUserID);
        doReturn(Optional.of(mock(User.class))).when(lendingService).findUserById(validUserID);
        assertFalse(lendingService.createLending(builder.build()).isPresent());
    }
}
