package at.fhv.teamg.librarymanagement.client.controller.internal;

import at.fhv.teamg.librarymanagement.client.rmi.RmiClient;
import at.fhv.teamg.librarymanagement.shared.dto.LoginDto;
import at.fhv.teamg.librarymanagement.shared.dto.MessageDto;
import javafx.scene.layout.AnchorPane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UserLoginTask extends AsyncTask<MessageDto<LoginDto>> {
    private static final Logger LOG = LogManager.getLogger(UserLoginTask.class);
    private final LoginDto loginUser;
    private final String server;

    /**
     * Sets all required values for the UserLoginTask.
     *
     * @param loginUser LoginDto
     * @param server    Server
     * @param pane      AnchorPane
     */
    public UserLoginTask(LoginDto loginUser, String server, AnchorPane pane) {
        super(pane);
        this.loginUser = loginUser;
        this.server = server;
    }

    @Override
    protected MessageDto<LoginDto> call() throws Exception {
        super.call();
        LOG.debug("Perform user login");
        RmiClient.setServerAddress(server);
        return RmiClient.getInstance().loginUser(loginUser);
    }
}
