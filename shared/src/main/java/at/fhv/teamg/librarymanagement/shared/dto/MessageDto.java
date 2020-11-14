package at.fhv.teamg.librarymanagement.shared.dto;

import java.io.Serializable;

public class MessageDto implements Serializable {
    private static final long serialVersionUID = 856519996238854377L;

    public enum MessageType {
        /**
         * Action was performed successfully.
         */
        SUCCESS,

        /**
         * Action failed due to user error/input.
         */
        FAILURE,

        /**
         * Action failed due to general error, not user related.
         */
        ERROR;
    }

    private final String message;
    private final MessageType type;

    private MessageDto(MessageDtoBuilder builder) {
        message = builder.message;
        type = builder.type;
    }

    public static class MessageDtoBuilder {
        private String message;
        private MessageType type;

        public MessageDtoBuilder withMessage(String message) {
            this.message = message;
            return this;
        }

        public MessageDtoBuilder withType(MessageType type) {
            this.type = type;
            return this;
        }

        public MessageDto build() {
            return new MessageDto(this);
        }
    }

    public String getMessage() {
        return message;
    }

    public MessageType getType() {
        return type;
    }
}
