package at.fhv.teamg.librarymanagement.client.controller;

import at.fhv.teamg.librarymanagement.client.controller.internal.AlertHelper;
import at.fhv.teamg.librarymanagement.client.rmi.Cache;
import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
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
    private boolean isValid = false;
    private ResourceBundle resources;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //create cache instance to preload
        Cache.getInstance();

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
            LOG.debug("Is Login input valid? {}", this.isValid);
            this.submitButton.setDisable(!this.isValid);
        });

        // do not focus user shortcut on first-time load
        // https://stackoverflow.com/a/29058225
        final BooleanProperty firstTime = new SimpleBooleanProperty(true);
        this.usernameField.focusedProperty().addListener(
            (observable, oldValue, newValue)
                -> {
                if (Boolean.TRUE.equals(newValue) && firstTime.get()) {
                    this.grid.requestFocus();
                    firstTime.setValue(false);
                }
            });
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

        LOG.debug(
            "Login with Username: {} and Password {}",
            this.usernameField.getText(),
            "************"
        );
        this.loadMainScene();

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
            controller.setLoginUser(); // TODO: add user
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
