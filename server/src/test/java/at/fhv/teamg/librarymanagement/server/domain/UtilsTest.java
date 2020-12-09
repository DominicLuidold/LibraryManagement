package at.fhv.teamg.librarymanagement.server.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import at.fhv.teamg.librarymanagement.server.persistence.entity.Medium;
import at.fhv.teamg.librarymanagement.server.persistence.entity.MediumCopy;
import at.fhv.teamg.librarymanagement.shared.dto.EmptyDto;
import at.fhv.teamg.librarymanagement.shared.dto.LendingDto;
import at.fhv.teamg.librarymanagement.shared.dto.MessageDto;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import org.junit.jupiter.api.Test;

public class UtilsTest {

    @Test
    public void getAvailability_shouldReturnCorrectAvailability() {
        // Mock MediumCopy entities
        MediumCopy mediumCopyMock1 = mock(MediumCopy.class);
        when(mediumCopyMock1.isAvailable()).thenReturn(true);

        MediumCopy mediumCopyMock2 = mock(MediumCopy.class);
        when(mediumCopyMock2.isAvailable()).thenReturn(false);

        Set<MediumCopy> mediumCopyMocks = new HashSet<>();
        mediumCopyMocks.add(mediumCopyMock1);
        mediumCopyMocks.add(mediumCopyMock2);

        // Mock Medium entity
        Medium mediumMock = mock(Medium.class);
        when(mediumMock.getCopies()).thenReturn(mediumCopyMocks);

        // Assertions
        String result = Utils.getAvailability(mediumMock);

        assertEquals(result, "1/2", "Result should match expected pattern");
    }

    @Test
    void createMessageResponse_shouldReturnMessageDto() {
        String message = "Message";
        MessageDto.MessageType type = MessageDto.MessageType.ERROR;

        MessageDto<EmptyDto> result = Utils.createMessageResponse(message, type);

        assertEquals(message, result.getMessage());
        assertEquals(type, result.getType());
        assertNull(result.getResult());
    }

    @Test
    void createMessageResponse_shouldReturnMessageDtoWithResult() {
        String message = "Message";
        MessageDto.MessageType type = MessageDto.MessageType.SUCCESS;
        LendingDto lendingDto = new LendingDto.LendingDtoBuilder()
            .userId(UUID.randomUUID())
            .build();

        MessageDto<LendingDto> result = Utils.createMessageResponse(message, type, lendingDto);

        assertEquals(message, result.getMessage());
        assertEquals(type, result.getType());
        assertNotNull(result.getResult());
        assertSame(lendingDto, result.getResult());
    }
}
