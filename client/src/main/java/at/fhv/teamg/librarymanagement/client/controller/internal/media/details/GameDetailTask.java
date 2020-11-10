package at.fhv.teamg.librarymanagement.client.controller.internal.media.details;

import at.fhv.teamg.librarymanagement.client.controller.internal.AsyncTask;
import at.fhv.teamg.librarymanagement.client.rmi.RmiClient;
import at.fhv.teamg.librarymanagement.shared.dto.GameDto;
import javafx.scene.layout.AnchorPane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Task for searching books.
 *
 * @author Valentin Goronjic
 */
public class GameDetailTask extends AsyncTask<GameDto> {

    private static final Logger LOG = LogManager.getLogger(GameDetailTask.class);
    private GameDto dto;

    public GameDetailTask(GameDto dto, AnchorPane pane) {
        super(pane);
        this.dto = dto;
    }

    @Override
    protected GameDto call() throws Exception {
        super.call();
        LOG.debug("Loading Game Detail");
        return RmiClient.getInstance().getGameDetail(this.dto);
    }

}
