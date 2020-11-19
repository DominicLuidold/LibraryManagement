package at.fhv.teamg.librarymanagement.server.tasks;

import at.fhv.teamg.librarymanagement.server.persistance.entity.Lending;
import java.time.format.DateTimeFormatter;

public class Utils {
    /**
     * Creates an overdue lending message.
     *
     * @param lending lending entity used to create the message
     * @return message
     */
    public static String createOverdueMessage(Lending lending) {
        return new StringBuilder().append("Overdue ")
            .append(lending.getMediumCopy().getMedium().getType().getName())
            .append(" (")
            .append(lending.getEndDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
            .append(") ")
            .append(lending.getMediumCopy().getMedium().getTitle())
            .append(" by User ")
            .append(lending.getUser().getName())
            .append(" (")
            .append(lending.getUser().getUsername())
            .append(")")
            .toString();
    }
}
