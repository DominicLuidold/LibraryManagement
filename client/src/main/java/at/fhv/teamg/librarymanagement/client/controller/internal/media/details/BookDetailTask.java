package at.fhv.teamg.librarymanagement.client.controller.internal.media.details;

import at.fhv.teamg.librarymanagement.client.controller.internal.AsyncTask;
import at.fhv.teamg.librarymanagement.client.rmi.RmiClient;
import at.fhv.teamg.librarymanagement.shared.dto.BookDto;
import javafx.scene.layout.AnchorPane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Task for searching books.
 *
 * @author Valentin Goronjic
 */
public class BookDetailTask extends AsyncTask<BookDto> {

    private static final Logger LOG = LogManager.getLogger(BookDetailTask.class);
    private BookDto dto;

    public BookDetailTask(BookDto dto, AnchorPane pane) {
        super(pane);
        this.dto = dto;
    }

    @Override
    protected BookDto call() throws Exception {
        super.call();
        LOG.debug("Loading Book Detail");
        return RmiClient.getInstance().getBookDetail(this.dto);
    }

}
