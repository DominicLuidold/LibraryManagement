package at.fhv.teamg.librarymanagement.client.controller.internal;

import at.fhv.teamg.librarymanagement.client.controller.MainController;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.layout.AnchorPane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Async Task that enables a Loading Spinner on a given @{link {@link AnchorPane}.
 * The Spinner is shown before starting the task and disabled on success/failure.
 * Extend this class in your own Task so that you profit from a cool Loading Animation!
 *
 * @author Valentin Goronjic
 */
public abstract class AsyncTask<T> extends Task<T> {

    private static final Logger LOG = LogManager.getLogger(AsyncTask.class);
    private AnchorPane pane;

    public AsyncTask(AnchorPane pane) {
        this.pane = pane;
    }

    @Override
    protected T call() throws Exception {
        LOG.debug("Task called: {}", this.getClass().getName());
        LOG.debug("Enabling Spinner");
        Platform.runLater(
            () -> {
                MainController.enableSpinner(this.pane);
            }
        );
        return null;
    }

    @Override
    protected void succeeded() {
        LOG.debug("Task suceeded: {}", this.getClass().getName());
        // Avoid throwing IllegalStateException by running from a non-JavaFX thread.
        Platform.runLater(
            () -> {
                MainController.disableSpinner(this.pane);
            }
        );
        super.succeeded();
    }

    @Override
    protected void failed() {
        LOG.error("Task failed: {}", this.getClass().getName());
        Throwable t = this.getException();
        if (t != null) {
            LOG.error(t);
        }

        Platform.runLater(
            () -> MainController.disableSpinner(this.pane)
        );
        super.failed();
    }
}
