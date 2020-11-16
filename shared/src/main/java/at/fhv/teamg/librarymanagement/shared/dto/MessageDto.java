package at.fhv.teamg.librarymanagement.shared.dto;

import at.fhv.teamg.librarymanagement.shared.ifaces.Dto;
import java.io.Serializable;

public class MessageDto<T extends Dto> implements Serializable {
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
    private final T result;

    private MessageDto(MessageDtoBuilder<T> builder) {
        message = builder.message;
        type = builder.type;
        result = builder.result;
    }

    public static class MessageDtoBuilder<T extends Dto> {
        private String message;
        private MessageType type;
        private T result;

        public MessageDtoBuilder<T> withMessage(String message) {
            this.message = message;
            return this;
        }

        public MessageDtoBuilder<T> withType(MessageType type) {
            this.type = type;
            return this;
        }

        public MessageDtoBuilder<T> withResult(T result) {
            this.result = result;
            return this;
        }

        public MessageDto<T> build() {
            return new MessageDto<>(this);
        }
    }

    public String getMessage() {
        return message;
    }

    public MessageType getType() {
        return type;
    }

    public T getResult() {
        return result;
    }
}
