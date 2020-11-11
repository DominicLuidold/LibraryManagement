package at.fhv.teamg.librarymanagement.client.controller.internal;

import at.fhv.teamg.librarymanagement.client.rmi.RmiClient;
import at.fhv.teamg.librarymanagement.shared.dto.BookDto;
import java.util.List;
import javafx.scene.layout.AnchorPane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Task for searching books.
 *
 * @author Valentin Goronjic
 */
public class BookSearchTask extends AsyncTask<List<BookDto>> {

    private static final Logger LOG = LogManager.getLogger(BookSearchTask.class);
    private BookDto dto;

    public BookSearchTask(BookDto dto, AnchorPane pane) {
        super(pane);
        this.dto = dto;
    }

    @Override
    protected List<BookDto> call() throws Exception {
        super.call();
        LOG.debug("Loading Books..");
        return RmiClient.getInstance().searchBook(this.dto);
    }

}
