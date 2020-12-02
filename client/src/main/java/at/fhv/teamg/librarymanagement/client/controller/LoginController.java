package at.fhv.teamg.librarymanagement.client.controller;

import at.fhv.teamg.librarymanagement.client.controller.internal.AlertHelper;
import at.fhv.teamg.librarymanagement.client.controller.internal.ConnectionType;
import at.fhv.teamg.librarymanagement.client.controller.internal.UserLoginTask;
import at.fhv.teamg.librarymanagement.client.remote.RemoteClient;
import at.fhv.teamg.librarymanagement.shared.dto.LoginDto;
import at.fhv.teamg.librarymanagement.shared.dto.MessageDto;
import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.controlsfx.validation.ValidationMessage;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;
import org.controlsfx.validation.decoration.StyleClassValidationDecoration;
import org.controlsfx.validation.decoration.ValidationDecoration;

/**
 * GUI Controller responsible for processing the Login.
 *
 * @author Valentin Goronjic
 * @author Tobias Moser
 */
public class LoginController implements Initializable {
    private static final Logger LOG = LogManager.getLogger(LoginController.class);

    private final ValidationSupport validationSupport = new ValidationSupport();
    private LoginDto loggedInUser;
    private ConnectionType connectionType;

    @FXML
    private AnchorPane pane;
    @FXML
    private GridPane grid;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button submitButton;
    @FXML
    private Button guestButton;
    @FXML
    private ComboBox<String> serverDropdown;
    @FXML
    private ComboBox<String> connectionTypeDropdown;

    private boolean isValid = false;
    private ResourceBundle resources;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.resources = resources;
        ValidationDecoration cssDecorator = new StyleClassValidationDecoration(
            "error",
            "warning"
        );
        this.validationSupport.setValidationDecorator(cssDecorator);
        this.validationSupport.registerValidator(this.usernameField,
            Validator.createEmptyValidator(resources.getString("login.error.username.missing"))
        );
        this.validationSupport.registerValidator(this.passwordField,
            Validator.createEmptyValidator(resources.getString("login.error.password.missing"))
        );
        this.validationSupport.validationResultProperty().addListener((o, oldVal, newVal) -> {
            this.isValid = newVal.getErrors().isEmpty();
            this.submitButton.setDisable(!this.isValid);
        });

        // do not focus user shortcut on first-time load
        // https://stackoverflow.com/a/29058225
        final BooleanProperty firstTime = new SimpleBooleanProperty(true);
        this.usernameField.focusedProperty().addListener(
            (observable, oldValue, newValue) -> {
                if (Boolean.TRUE.equals(newValue) && firstTime.get()) {
                    this.grid.requestFocus();
                    firstTime.setValue(false);
                }
            });

        // Make guest login button non-default to prevent strange behaviour
        this.guestButton.setDefaultButton(false);

        // Fill server list
        List<String> servers = new LinkedList<>();
        servers.add("vsts-team007.westeurope.cloudapp.azure.com");
        servers.add("localhost");

        this.serverDropdown.setItems(FXCollections.observableList(servers));
        this.serverDropdown.getSelectionModel().select(0);

        //Fill connectionType List
        List<String> connectionTypes = new LinkedList<>();
        connectionTypes.add("RMI");
        connectionTypes.add("EJB");

        this.connectionTypeDropdown.setItems(FXCollections.observableList(connectionTypes));
        this.connectionTypeDropdown.getSelectionModel().select(0);

    }

    /**
     * This function processes the login data.
     */
    public void processLoginCredentials() {
        LOG.debug("Login btn pressed");
        Window owner = this.submitButton.getScene().getWindow();

        if (!this.isValid) {
            LOG.info("Login input is not valid (missing required fields)");
            StringBuilder sb = new StringBuilder();
            for (ValidationMessage vm : this.validationSupport.getValidationResult().getErrors()) {
                sb.append(vm.getText());
                sb.append("\n");
            }
            AlertHelper.showAlert(
                Alert.AlertType.ERROR,
                owner,
                this.resources.getString("login.error.missing.fields.title"),
                sb.toString()
            );
            return;
        }

        LoginDto loginUser = new LoginDto.LoginDtoBuilder()
            .withUsername(usernameField.getText())
            .withPassword(passwordField.getText())
            .build();

        if (connectionTypeDropdown.getSelectionModel().getSelectedItem().equals("RMI")) {
            this.connectionType = ConnectionType.RMI;
        } else {
            this.connectionType = ConnectionType.EJB;
        }
        RemoteClient remoteClient = RemoteClient.getInstance();
        remoteClient.setConnectionType(this.connectionType);

        UserLoginTask loginTask = new UserLoginTask(
            loginUser,
            this.serverDropdown.getSelectionModel().getSelectedItem(),
            this.connectionType,
            this.pane
        );

        Thread thread = new Thread(loginTask, "User login Task");
        thread.start();
        loginTask.setOnSucceeded(event -> {
            LOG.debug("Logging in with username: {}", this.usernameField.getText());
            MessageDto<LoginDto> response = loginTask.getValue();
            loggedInUser = response.getResult();

            if (response.getType().equals(MessageDto.MessageType.SUCCESS)
                && loggedInUser.getIsValid()
            ) {
                this.loadMainScene();
            } else {
                AlertHelper.showAlert(
                    Alert.AlertType.ERROR, owner,
                    this.resources.getString("login.error.failed.title"),
                    response.getMessage()
                );
            }
        });
    }

    /**
     * Logging in with a Guest Account. Guest have to permissions of a Customer.
     */
    public void loginAsGuest() {
        LOG.debug("LoginAsGuest btn pressed");
        Window owner = this.submitButton.getScene().getWindow();

        LoginDto loginUser = new LoginDto.LoginDtoBuilder()
            .withUsername("guest")
            .withPassword("")
            .build();

        if (connectionTypeDropdown.getSelectionModel().getSelectedItem().equals("RMI")) {
            this.connectionType = ConnectionType.RMI;
        } else {
            this.connectionType = ConnectionType.EJB;
        }
        RemoteClient remoteClient = RemoteClient.getInstance();
        remoteClient.setConnectionType(this.connectionType);

        UserLoginTask loginTask = new UserLoginTask(
            loginUser,
            this.serverDropdown.getSelectionModel().getSelectedItem(),
            this.connectionType,
            this.pane
        );

        Thread thread = new Thread(loginTask, "User login Task");
        thread.start();
        loginTask.setOnSucceeded(event -> {
            LOG.debug("Logging in with username: {}", this.usernameField.getText());
            MessageDto<LoginDto> response = loginTask.getValue();
            loggedInUser = response.getResult();

            if (response.getType().equals(MessageDto.MessageType.SUCCESS)
                && loggedInUser.getIsValid()
            ) {
                this.loadMainScene();
            } else {
                AlertHelper.showAlert(
                    Alert.AlertType.ERROR, owner,
                    this.resources.getString("login.error.failed.title"),
                    response.getMessage()
                );
            }
        });
    }

    private void loadMainScene() {
        Locale locale = new Locale("en", "UK");
        try {
            ResourceBundle bundle = ResourceBundle.getBundle("bundles.language", locale);
            MainController controller = MainController.switchSceneTo(
                "/view/mainWindow.fxml",
                bundle,
                this.submitButton
            );
            controller.setLoginUser(this.loggedInUser);
            LOG.debug("MainController is fully loaded now :-)");
        } catch (IOException e) {
            LOG.error("Cannot load main scene", e);
            Stage owner = (Stage) this.submitButton.getScene().getWindow();
            AlertHelper.showAlert(
                Alert.AlertType.ERROR,
                owner,
                this.resources.getString("login.error.failed.title"),
                this.resources.getString("login.error.technical.problems")
            );
        }
    }
}
