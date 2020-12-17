package at.fhv.teamg.librarymanagement.client.controller.internal;

import at.fhv.teamg.librarymanagement.client.remote.RemoteClient;
import at.fhv.teamg.librarymanagement.shared.dto.LoginDto;
import at.fhv.teamg.librarymanagement.shared.dto.MessageDto;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UserLoginTask extends AsyncTask<MessageDto<LoginDto>> {
    private static final Logger LOG = LogManager.getLogger(UserLoginTask.class);
    private final LoginDto loginUser;
    private final ConnectionType connectionType;
    private final AnchorPane pane;

    /**
     * Sets all required values for the UserLoginTask.
     *
     * @param loginUser      LoginDto
     * @param connectionType Type to fetch data (RMI or EJB)
     * @param pane           AnchorPane
     */
    public UserLoginTask(LoginDto loginUser, ConnectionType connectionType, AnchorPane pane) {
        super(pane);
        this.loginUser = loginUser;
        this.connectionType = connectionType;
        this.pane = pane;
    }

    @Override
    protected MessageDto<LoginDto> call() throws Exception {
        super.call();
        LOG.debug("Perform user login");
        return RemoteClient.getInstance().loginUser(loginUser);
    }

    @Override
    protected void failed() {
        super.failed();
        if (connectionType.equals(ConnectionType.RMI)) {
            AlertHelper.showAlert(
                Alert.AlertType.ERROR,
                this.pane.getScene().getWindow(),
                "RMI server unreachable",
                "Could not connect to RMI server. Please make sure the server is up and running!"
            );
        } else {
            AlertHelper.showAlert(
                Alert.AlertType.ERROR,
                this.pane.getScene().getWindow(),
                "EJB server unreachable",
                "Could not connect to EJB server. Please make sure the server is up and running!"
            );
        }
    }
}
