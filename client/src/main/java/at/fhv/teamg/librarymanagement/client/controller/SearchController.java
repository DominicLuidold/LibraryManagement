package at.fhv.teamg.librarymanagement.client.controller;

import at.fhv.teamg.librarymanagement.client.controller.internal.Parentable;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SearchController implements Initializable, Parentable<MainController> {
    private static final Logger LOG = LogManager.getLogger(SearchController.class);

    private MainController parentController;
    private ResourceBundle resourceBundle;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.resourceBundle = resources;
        LOG.debug("Initialized UserController");
    }

    @Override
    public void setParentController(MainController controller) {
        this.parentController = controller;
    }

    @Override
    public MainController getParentController() {
        return this.parentController;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void initializeWithParent() {
        LOG.debug("Initialized SearchController with parent");
    }

}
