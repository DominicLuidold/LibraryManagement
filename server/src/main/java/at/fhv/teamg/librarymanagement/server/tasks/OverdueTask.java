package at.fhv.teamg.librarymanagement.server.tasks;

import at.fhv.teamg.librarymanagement.server.persistance.dao.LendingDao;
import at.fhv.teamg.librarymanagement.server.rmi.Library;
import at.fhv.teamg.librarymanagement.shared.dto.Message;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class OverdueTask extends Thread {
    private static final Logger LOG = LogManager.getLogger(OverdueTask.class);

    @Override
    public void run() {
        LOG.info("Checking for overdue lending");
        AtomicInteger count = new AtomicInteger();
        new LendingDao().getAll().forEach(lending -> {
            if (lending.getEndDate().isBefore(LocalDate.now())
                && lending.getReturnDate() == null) {
                count.incrementAndGet();
                Library.addMessage(new Message(
                    UUID.randomUUID(),
                    Utils.createOverdueMessage(lending),
                    Message.Status.Open,
                    LocalDateTime.now()
                ));
            }
        });
        LOG.info("Found [{}] overdue lends", count);
    }
}
