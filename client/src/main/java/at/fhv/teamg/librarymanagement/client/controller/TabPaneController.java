package at.fhv.teamg.librarymanagement.client.controller;

import at.fhv.teamg.librarymanagement.client.controller.internal.Parentable;
import at.fhv.teamg.librarymanagement.client.controller.internal.TabPaneEntry;
import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.Optional;
import java.util.Queue;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.controlsfx.control.StatusBar;

public class TabPaneController implements Initializable, Parentable<MainController> {
    private static final Logger LOG = LogManager.getLogger(TabPaneController.class);

    @FXML
    public AnchorPane calendar;
    @FXML
    private StatusBar statusBar;

    @FXML
    private TabPane tabPane;

    private MainController parentController;
    private ResourceBundle bundle;

    private void removeTab(String title) {
        ObservableList<Tab> tabs = this.tabPane.getTabs();
        ObservableList<Tab> newTabs = FXCollections.observableArrayList();

        // avoid concurrentModification
        for (Tab t : tabs) {
            if (!t.getText().equals(title)) {
                newTabs.add(t);
            }
        }
        this.tabPane.getTabs().setAll(newTabs);
    }

    public void removeTab(TabPaneEntry entry) {
        removeTab(entry.getTitle());
    }

    public Optional<Parentable<?>> addTab(TabPaneEntry entry) {
        return addTab(entry, null);
    }

    /**
     * Responsible for adding a new Tab to the Tabpane.
     *
     * @param entry  entry
     * @param parent parentTab of the entry
     * @return The Controller of the Tab if the tab is created, returns optional.empty
     *          if tab already exists
     */
    public Optional<Parentable<?>> addTab(
        TabPaneEntry entry,
        Parentable parent
    ) {
        LOG.debug("Adding tab");
        // Make sure we add dynamic tabs only once
        for (Tab t : this.tabPane.getTabs()) {
            if (t.getText().equals(entry.getTitle())) {
                LOG.debug("Duplicate tab found");
                this.tabPane.getSelectionModel().select(t);
                return Optional.empty();
            }
        }
        Tab tab = new Tab(entry.getTitle());
        FXMLLoader loader = new FXMLLoader(
            this.getClass().getResource(entry.getFxmlPath()),
            this.bundle
        );

        Parentable controller;
        try {
            Parent p = loader.load();
            tab.setContent(p);
            controller = loader.getController();
            // Set parent controller
            if (controller != null) {
                if (parent != null) {
                    controller.setParentController(parent);
                } else if (controller.getParentController() == null && parent == null) {
                    controller.setParentController(this);
                }
                controller.initializeWithParent();
            }
            if (entry.isTemporary()) {
                tab.setStyle(
                    "-fx-background-color: #00909e; "
                        + "-fx-text-fill: #093753; "
                        + "-fx-prompt-text-fill: #093753;"
                );
            }

            this.tabPane.getTabs().add(tab);
            // Only select temporary tabs -> always show tabs with order #1 when initializing
            if (entry.isTemporary()) {
                this.tabPane.getSelectionModel().select(tab);
            }

            // FXML has no controller defined
            return Optional.ofNullable(controller);
        } catch (IOException e) {
            // cannot load FXML
            LOG.error(e);

        }
        return Optional.empty();
    }

    /**
     * Initializes the TabPane menu, depending on the currently logged in user type.
     */
    protected void initializeTabMenu() {
        MainController parent = this.getParentController();

        Queue<TabPaneEntry> tabs = new LinkedList<>();
        tabs = parent.getPermittedTabs(parent.getUserRole());


        LOG.debug("Adding {} tabs to menu", tabs.size());
        while (!tabs.isEmpty()) {
            TabPaneEntry entry = tabs.poll();
            LOG.debug("Adding tab {}", entry.getTitle());

            addTab(entry, null);
        }
    }

    /**
     * Selects a Tab in the TabPane.
     *
     * @param tabPaneEntry TabPaneEntry to be selected.
     */
    public void selectTab(TabPaneEntry tabPaneEntry) {
        Tab tab = null;
        for (Tab t : this.tabPane.getTabs()) {
            if (t.getText().equals(tabPaneEntry.getTitle())) {
                tab = t;
                break;
            }
        }

        if (tab != null) {
            LOG.debug("Selecting tab: {}", tab.getText());
            this.tabPane.getSelectionModel().select(tab);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.bundle = resources;

        this.tabPane.getSelectionModel().clearSelection();
        this.tabPane.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) ->
                LOG.debug("Selected tab: {}", newValue.getText())
        );

        LOG.debug("Initialized TabPaneController");
    }

    @Override
    public MainController getParentController() {
        return this.parentController;
    }

    @Override
    public void setParentController(MainController controller) {
        LOG.debug("Initialized TabPaneParentController");
        this.parentController = controller;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void initializeWithParent() {
        // No implementation needed in this class.
        LOG.debug("Initialized TabPaneController with parent");
    }
}
