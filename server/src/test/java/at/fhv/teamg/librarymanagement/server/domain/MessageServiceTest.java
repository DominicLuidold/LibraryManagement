package at.fhv.teamg.librarymanagement.server.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

import at.fhv.teamg.librarymanagement.server.persistence.entity.Message;
import at.fhv.teamg.librarymanagement.server.persistence.entity.User;
import at.fhv.teamg.librarymanagement.shared.dto.CustomMessage;
import at.fhv.teamg.librarymanagement.shared.dto.MessageDto;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;

public class MessageServiceTest {
    @Test
    void archiveMessage_shouldSuccess() {
        var message = new CustomMessage(
            UUID.randomUUID(),
            "test message",
            CustomMessage.Status.Archived,
            LocalDateTime.now()
        );
        message.userId = UUID.randomUUID();

        var service = spy(MessageService.class);
        var userMock = mock(User.class);
        var messageMock = mock(Message.class);
        doReturn(Optional.of(userMock)).when(service).findUserById(any(UUID.class));
        doReturn(Optional.of(messageMock)).when(service).updateMessage(any(Message.class));

        assertEquals(MessageDto.MessageType.SUCCESS, service.archiveMessage(message).getType());
    }

    @Test
    void archiveMessage_shouldError() {
        var message = new CustomMessage(
            UUID.randomUUID(),
            "test message",
            CustomMessage.Status.Archived,
            LocalDateTime.now()
        );
        message.userId = UUID.randomUUID();

        var service = spy(MessageService.class);
        doReturn(Optional.empty()).when(service).findUserById(any(UUID.class));
        doReturn(Optional.empty()).when(service).updateMessage(any(Message.class));

        var userMock = mock(User.class);
        assertEquals(MessageDto.MessageType.ERROR, service.archiveMessage(message).getType());
        doReturn(Optional.of(userMock)).when(service).findUserById(any(UUID.class));
        assertEquals(MessageDto.MessageType.ERROR, service.archiveMessage(message).getType());
    }
}
