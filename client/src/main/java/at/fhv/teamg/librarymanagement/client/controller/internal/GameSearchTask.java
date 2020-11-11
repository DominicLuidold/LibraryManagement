package at.fhv.teamg.librarymanagement.client.controller.internal;

import at.fhv.teamg.librarymanagement.client.rmi.RmiClient;
import at.fhv.teamg.librarymanagement.shared.dto.GameDto;
import java.util.List;
import javafx.scene.layout.AnchorPane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Task for searching games.
 *
 * @author Valentin Goronjic
 */
public class GameSearchTask extends AsyncTask<List<GameDto>> {

    private static final Logger LOG = LogManager.getLogger(GameSearchTask.class);
    private GameDto dto;

    public GameSearchTask(GameDto dto, AnchorPane pane) {
        super(pane);
        this.dto = dto;
    }

    @Override
    protected List<GameDto> call() throws Exception {
        super.call();
        LOG.debug("Loading Games..");
        return RmiClient.getInstance().searchGame(this.dto);
    }

}
