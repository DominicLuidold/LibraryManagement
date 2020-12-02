package at.fhv.teamg.librarymanagement.client.controller.internal.media.details;

import at.fhv.teamg.librarymanagement.client.controller.internal.AsyncTask;
import at.fhv.teamg.librarymanagement.client.remote.RemoteClient;
import at.fhv.teamg.librarymanagement.shared.dto.GameDto;
import at.fhv.teamg.librarymanagement.shared.dto.MediumCopyDto;
import java.util.List;
import javafx.scene.layout.AnchorPane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Task for searching Game copies.
 *
 * @author Valentin Goronjic
 */
public class GameMediumCopyTask extends AsyncTask<List<MediumCopyDto>> {

    private static final Logger LOG = LogManager.getLogger(GameMediumCopyTask.class);
    private GameDto dto;

    public GameMediumCopyTask(GameDto dto, AnchorPane pane) {
        super(pane);
        this.dto = dto;
    }

    @Override
    protected List<MediumCopyDto> call() throws Exception {
        super.call();
        LOG.debug("Loading Game Copies");
        return RemoteClient.getInstance().getAllGameCopies(this.dto);
    }

}
