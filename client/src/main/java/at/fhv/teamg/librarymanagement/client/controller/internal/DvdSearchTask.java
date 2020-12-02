package at.fhv.teamg.librarymanagement.client.controller.internal;

import at.fhv.teamg.librarymanagement.client.remote.RemoteClient;
import at.fhv.teamg.librarymanagement.shared.dto.DvdDto;
import java.util.List;
import javafx.scene.layout.AnchorPane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Task for searching dvds.
 *
 * @author Valentin Goronjic
 */
public class DvdSearchTask extends AsyncTask<List<DvdDto>> {

    private static final Logger LOG = LogManager.getLogger(DvdSearchTask.class);
    private DvdDto dto;

    public DvdSearchTask(DvdDto dto, AnchorPane pane) {
        super(pane);
        this.dto = dto;
    }

    @Override
    protected List<DvdDto> call() throws Exception {
        super.call();
        LOG.debug("Loading DVDs..");
        return RemoteClient.getInstance().searchDvd(this.dto);
    }

}
