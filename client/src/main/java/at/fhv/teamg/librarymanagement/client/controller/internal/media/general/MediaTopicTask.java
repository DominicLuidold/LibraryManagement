package at.fhv.teamg.librarymanagement.client.controller.internal.media.general;

import at.fhv.teamg.librarymanagement.client.controller.internal.AsyncTask;
import at.fhv.teamg.librarymanagement.client.remote.RemoteClient;
import at.fhv.teamg.librarymanagement.shared.dto.TopicDto;
import java.util.List;
import javafx.scene.layout.AnchorPane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Task for searching books.
 *
 * @author Valentin Goronjic
 */
public class MediaTopicTask extends AsyncTask<List<TopicDto>> {

    private static final Logger LOG = LogManager.getLogger(MediaTopicTask.class);

    public MediaTopicTask(AnchorPane pane) {
        super(pane);
    }

    @Override
    protected List<TopicDto> call() throws Exception {
        super.call();
        LOG.debug("Loading Book Detail");
        return RemoteClient.getInstance().getAllTopics();
    }

}
