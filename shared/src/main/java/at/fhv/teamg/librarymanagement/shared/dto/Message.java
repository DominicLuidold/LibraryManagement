package at.fhv.teamg.librarymanagement.shared.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

public class Message implements Serializable {
    public enum Status {
        Open,
        Working,
        Done
    }

    public Message(UUID id, String message,
                   Status status, LocalDateTime dateTime) {
        this.id = id;
        this.message = message;
        this.status = status;
        this.dateTime = dateTime;
    }

    public UUID id;
    public String message;
    public Status status;
    public LocalDateTime dateTime;
}
