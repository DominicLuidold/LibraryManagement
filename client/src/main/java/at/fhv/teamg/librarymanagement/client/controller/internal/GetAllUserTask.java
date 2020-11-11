package at.fhv.teamg.librarymanagement.client.controller.internal;

import at.fhv.teamg.librarymanagement.client.rmi.RmiClient;
import at.fhv.teamg.librarymanagement.shared.dto.UserDto;
import java.util.List;
import javafx.scene.layout.AnchorPane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GetAllUserTask extends AsyncTask<List<UserDto>> {
    private static final Logger LOG = LogManager.getLogger(BookSearchTask.class);
    private List<UserDto> users;

    public GetAllUserTask(AnchorPane pane) {
        super(pane);
    }

    @Override
    protected List<UserDto> call() throws Exception {
        super.call();
        LOG.debug("Loading Users..");
        return RmiClient.getInstance().getAllUsers();
    }

}
