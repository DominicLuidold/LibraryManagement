package at.fhv.teamg.librarymanagement.client.controller;

import at.fhv.teamg.librarymanagement.client.controller.internal.Parentable;
import at.fhv.teamg.librarymanagement.client.rmi.IMessageSubscriber;
import at.fhv.teamg.librarymanagement.client.rmi.MessageClient;
import at.fhv.teamg.librarymanagement.client.rmi.RmiClient;
import at.fhv.teamg.librarymanagement.shared.dto.Message;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MessagesController implements Initializable, Parentable<TabPaneController>,
    IMessageSubscriber {
    private static final Logger LOG = LogManager.getLogger(MessagesController.class);

    private TabPaneController parentController;
    private ResourceBundle resourceBundle;

    @FXML
    private TableView<Message> messagesTable;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.resourceBundle = resources;
        LOG.debug("Initialized MessagesController");
        update(new LinkedList<>());

        MessageClient messageClient = null;
        try {
            messageClient = new MessageClient();
            messageClient.subscribe(this);
            RmiClient.getInstance().registerForMessages(messageClient);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
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

    @Override
    public void update(List<Message> messages) {
        messagesTable.setItems(FXCollections.observableList(messages));
    }
}
