package at.fhv.teamg.librarymanagement.server.tasks;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TaskRunner {
    private static final Logger LOG = LogManager.getLogger(TaskRunner.class);
    private static final ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

    /**
     * Run tasks now and schedule them to be run at their interval.
     */
    public static void run() {
        LOG.info("Running tasks");
        new OverdueTask().start();

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime runAt = now.withHour(6).withMinute(0).withSecond(0);

        if (now.isAfter(runAt)) {
            runAt = runAt.plusDays(1);
        }

        Duration duration = Duration.between(now, runAt);
        LOG.debug("Nex overdue check at {}", now.plusSeconds(duration.getSeconds()));
        executor.scheduleAtFixedRate(new OverdueTask(),
            duration.getSeconds(),
            TimeUnit.DAYS.toSeconds(1),
            TimeUnit.SECONDS
        );
    }
}
