package at.fhv.teamg.librarymanagement.client.controller.internal.media.details;

import at.fhv.teamg.librarymanagement.client.controller.internal.AsyncTask;
import at.fhv.teamg.librarymanagement.client.remote.RemoteClient;
import at.fhv.teamg.librarymanagement.client.remote.RmiClient;
import at.fhv.teamg.librarymanagement.shared.dto.BookDto;
import at.fhv.teamg.librarymanagement.shared.dto.MediumCopyDto;
import java.util.List;
import javafx.scene.layout.AnchorPane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Task for searching Book copies.
 *
 * @author Valentin Goronjic
 */
public class BookMediumCopyTask extends AsyncTask<List<MediumCopyDto>> {

    private static final Logger LOG = LogManager.getLogger(BookMediumCopyTask.class);
    private BookDto dto;

    public BookMediumCopyTask(BookDto dto, AnchorPane pane) {
        super(pane);
        this.dto = dto;
    }

    @Override
    protected List<MediumCopyDto> call() throws Exception {
        super.call();
        LOG.debug("Loading Book Copies");
        return RemoteClient.getInstance().getAllBookCopies(this.dto);
    }

}
