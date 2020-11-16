package at.fhv.teamg.librarymanagement.client.controller.internal;

import at.fhv.teamg.librarymanagement.client.rmi.RmiClient;
import at.fhv.teamg.librarymanagement.shared.dto.LoginDto;
import at.fhv.teamg.librarymanagement.shared.dto.UserDto;
import javafx.scene.layout.AnchorPane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UserLoginTask extends AsyncTask<LoginDto> {
    private static final Logger LOG = LogManager.getLogger(UserLoginTask.class);
    private LoginDto loginUser;

    public UserLoginTask(LoginDto loginUser, AnchorPane pane) {
        super(pane);
        this.loginUser = loginUser;
    }

    @Override
    protected LoginDto call() throws Exception {
        super.call();
        LOG.debug("Loading Users..");
        return RmiClient.getInstance().loginUser(loginUser);
    }
}
