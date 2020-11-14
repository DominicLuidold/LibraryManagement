package at.fhv.teamg.librarymanagement.server.domain.common;

import static org.junit.jupiter.api.Assertions.assertEquals;

import at.fhv.teamg.librarymanagement.shared.dto.MessageDto;
import org.junit.jupiter.api.Test;

public class UtilsTest {

    @Test
    void createMessageResponse_shouldReturnMessageDto() {
        String message = "Message";
        MessageDto.MessageType type = MessageDto.MessageType.ERROR;

        MessageDto result = Utils.createMessageResponse(message, type);

        assertEquals(message, result.getMessage());
        assertEquals(type, result.getType());
    }
}
