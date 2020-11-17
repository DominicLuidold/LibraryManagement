package at.fhv.teamg.librarymanagement.server.tasks;

import at.fhv.teamg.librarymanagement.server.persistance.dao.LendingDao;
import at.fhv.teamg.librarymanagement.server.rmi.Library;
import at.fhv.teamg.librarymanagement.shared.dto.Message;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class OverdueTask extends Thread {
    private static final Logger LOG = LogManager.getLogger(OverdueTask.class);

    @Override
    public void run() {
        LOG.info("checking for overdue lends");
        AtomicInteger count = new AtomicInteger();
        new LendingDao().getAll().forEach(lending -> {
            if (lending.getEndDate().isBefore(LocalDate.now())
                && lending.getReturnDate() == null) {
                count.incrementAndGet();
                StringBuilder builder = new StringBuilder();
                builder.append("Overdue ")
                    .append(lending.getMediumCopy().getMedium().getType().getName())
                    .append(" (")
                    .append(
                        lending.getEndDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                    )
                    .append(") ")
                    .append(lending.getMediumCopy().getMedium().getTitle())
                    .append(" by User ")
                    .append(lending.getUser().getName())
                    .append(" (")
                    .append(lending.getUser().getUsername())
                    .append(")");

                Library.addMessage(new Message(
                    UUID.randomUUID(),
                    builder.toString(),
                    Message.Status.Open,
                    LocalDateTime.now()
                ));
            }
        });
        LOG.info("Found " + count + " overdue lends");
    }
}
