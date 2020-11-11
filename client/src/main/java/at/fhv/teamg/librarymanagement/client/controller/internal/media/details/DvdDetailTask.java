package at.fhv.teamg.librarymanagement.client.controller.internal.media.details;

import at.fhv.teamg.librarymanagement.client.controller.internal.AsyncTask;
import at.fhv.teamg.librarymanagement.client.rmi.RmiClient;
import at.fhv.teamg.librarymanagement.shared.dto.DvdDto;
import javafx.scene.layout.AnchorPane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Task for searching books.
 *
 * @author Valentin Goronjic
 */
public class DvdDetailTask extends AsyncTask<DvdDto> {

    private static final Logger LOG = LogManager.getLogger(DvdDetailTask.class);
    private DvdDto dto;

    public DvdDetailTask(DvdDto dto, AnchorPane pane) {
        super(pane);
        this.dto = dto;
    }

    @Override
    protected DvdDto call() throws Exception {
        super.call();
        LOG.debug("Loading Dvd Detail");
        return RmiClient.getInstance().getDvdDetail(this.dto);
    }

}
