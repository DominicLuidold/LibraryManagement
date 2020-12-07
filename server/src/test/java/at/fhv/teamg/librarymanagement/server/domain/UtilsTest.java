package at.fhv.teamg.librarymanagement.server.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

import at.fhv.teamg.librarymanagement.server.domain.Utils;
import at.fhv.teamg.librarymanagement.shared.dto.EmptyDto;
import at.fhv.teamg.librarymanagement.shared.dto.LendingDto;
import at.fhv.teamg.librarymanagement.shared.dto.MessageDto;
import java.util.UUID;
import org.junit.jupiter.api.Test;

public class UtilsTest {

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
