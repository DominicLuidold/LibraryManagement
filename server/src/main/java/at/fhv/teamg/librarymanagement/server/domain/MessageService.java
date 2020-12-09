package at.fhv.teamg.librarymanagement.server.domain;

import at.fhv.teamg.librarymanagement.server.persistence.dao.MessageDao;
import at.fhv.teamg.librarymanagement.server.persistence.entity.Message;
import at.fhv.teamg.librarymanagement.server.persistence.entity.User;
import at.fhv.teamg.librarymanagement.shared.dto.CustomMessage;
import at.fhv.teamg.librarymanagement.shared.dto.MessageDto;
import at.fhv.teamg.librarymanagement.shared.ifaces.Dto;
import java.time.LocalDateTime;
import java.util.Optional;

public class MessageService extends BaseMediaService {
    /**
     * Archives a {@link Message}.
     *
     * @param message Message to be archived
     * @return MessageDto
     */
    public MessageDto<Dto> archiveMessage(CustomMessage message) {
        Optional<User> user = findUserById(message.userId);

        if (user.isEmpty()) {
            return new MessageDto.MessageDtoBuilder<>()
                .withType(MessageDto.MessageType.ERROR)
                .withMessage("Server error: User not found")
                .build();
        }

        Message m = new Message();
        m.setId(message.id);
        m.setMessage(message.message);
        m.setDateArchived(LocalDateTime.now());
        m.setDateCreated(message.dateTime);
        m.setUser(user.get());

        if (updateMessage(m).isEmpty()) {
            return new MessageDto.MessageDtoBuilder<>()
                .withType(MessageDto.MessageType.ERROR)
                .withMessage("Server error: Unable to archive message")
                .build();
        }

        return new MessageDto.MessageDtoBuilder<>()
            .withType(MessageDto.MessageType.SUCCESS)
            .withMessage("Message Archived")
            .build();
    }

    protected Optional<Message> updateMessage(Message message) {
        return new MessageDao().update(message);
    }
}
