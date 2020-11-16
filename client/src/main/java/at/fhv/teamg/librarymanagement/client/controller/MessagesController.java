package at.fhv.teamg.librarymanagement.client.controller;

import at.fhv.teamg.librarymanagement.client.controller.internal.Parentable;
import at.fhv.teamg.librarymanagement.shared.dto.Message;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.UUID;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MessagesController implements Initializable, Parentable<TabPaneController> {
    private static final Logger LOG = LogManager.getLogger(MessagesController.class);

    private TabPaneController parentController;
    private ResourceBundle resourceBundle;

    @FXML
    private TableView<Message> messagesTable;


    public void updateMessageView(List<Message> messages) {
        messages.add(new Message(
            UUID.randomUUID(),
            "Test Message from Client",
            Message.Status.Open,
            LocalDateTime.now()
        ));
        messagesTable.setItems(FXCollections.observableList(messages));
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.resourceBundle = resources;
        LOG.debug("Initialized MessagesController");
        updateMessageView(new LinkedList<>());
    }

    @Override
    public TabPaneController getParentController() {
        return this.parentController;
    }

    @Override
    public void setParentController(TabPaneController controller) {
        this.parentController = controller;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void initializeWithParent() {
        LOG.debug("Initialized SearchController with parent");
    }
}
