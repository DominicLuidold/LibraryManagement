package at.fhv.teamg.librarymanagement.server.domain.common;

import at.fhv.teamg.librarymanagement.shared.dto.MessageDto;

/**
 * A class containing common functionalities that can be used without any specific context.
 */
public class Utils {

    /**
     * Creates a new {@link MessageDto} with given input.
     *
     * @param message The message
     * @param type    The type of response
     * @return A MessageDto
     */
    public static MessageDto createMessageResponse(String message, MessageDto.MessageType type) {
        return new MessageDto.MessageDtoBuilder()
            .withMessage(message)
            .withType(type)
            .build();
    }
}
