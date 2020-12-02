package at.fhv.teamg.librarymanagement.client.controller.internal.media.details;

import at.fhv.teamg.librarymanagement.client.controller.internal.AsyncTask;
import at.fhv.teamg.librarymanagement.client.remote.RemoteClient;
import at.fhv.teamg.librarymanagement.shared.dto.DvdDto;
import at.fhv.teamg.librarymanagement.shared.dto.MediumCopyDto;
import java.util.List;
import javafx.scene.layout.AnchorPane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Task for searching Dvd copies.
 *
 * @author Valentin Goronjic
 */
public class DvdMediumCopyTask extends AsyncTask<List<MediumCopyDto>> {

    private static final Logger LOG = LogManager.getLogger(DvdMediumCopyTask.class);
    private DvdDto dto;

    public DvdMediumCopyTask(DvdDto dto, AnchorPane pane) {
        super(pane);
        this.dto = dto;
    }

    @Override
    protected List<MediumCopyDto> call() throws Exception {
        super.call();
        LOG.debug("Loading Dvd Copies");
        return RemoteClient.getInstance().getAllDvdCopies(this.dto);
    }

}
