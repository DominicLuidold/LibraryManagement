package at.fhv.teamg.librarymanagement.shared.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.UUID;

public class CustomMessage implements Serializable {
    public enum Status {
        Open,
        Working,
        Done,
        Archived
    }

    /**
     * Constructor for Message.
     *
     * @param id       uuid
     * @param message  text of the message
     * @param status   status of the message
     * @param dateTime datetime when the message was created
     */
    public CustomMessage(
        UUID id,
        String message,
        Status status,
        LocalDateTime dateTime
    ) {
        this.id = id;
        this.message = message;
        this.status = status;
        this.dateTime = dateTime;
    }

    public UUID id;
    public String message;
    public Status status;
    public LocalDateTime dateTime;
    public UUID userId;

    public UUID getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public Status getStatus() {
        return status;
    }

    public String getDateTime() {
        return dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }

    public UUID getUserId() {
        return userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CustomMessage that = (CustomMessage) o;
        return Objects.equals(message, that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(message);
    }
}
