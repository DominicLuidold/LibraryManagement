package at.fhv.teamg.librarymanagement.shared.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class Message implements Serializable {
    public enum Status {
        Open,
        Working,
        Done
    }

    /**
     * Constructor for Message.
     *
     * @param id       uuid
     * @param message  text of the message
     * @param status   status of the message
     * @param dateTime datetime when the message was created
     */
    public Message(
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
}
