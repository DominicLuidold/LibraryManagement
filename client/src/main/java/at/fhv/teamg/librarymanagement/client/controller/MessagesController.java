package at.fhv.teamg.librarymanagement.client.controller;

import at.fhv.teamg.librarymanagement.client.controller.internal.AlertHelper;
import at.fhv.teamg.librarymanagement.client.controller.internal.Parentable;
import at.fhv.teamg.librarymanagement.client.rmi.MessageClient;
import at.fhv.teamg.librarymanagement.client.rmi.RmiClient;
import at.fhv.teamg.librarymanagement.shared.dto.CustomMessage;
import java.net.URL;
import java.rmi.RemoteException;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
import java.util.UUID;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MessagesController implements Initializable, Parentable<TabPaneController> {
    private static final Logger LOG = LogManager.getLogger(MessagesController.class);

    private TabPaneController parentController;
    private ResourceBundle resourceBundle;

    @FXML
    private TableView<CustomMessage> messagesTable;
    @FXML
    private TableColumn<CustomMessage, Void> actionCol;
    @FXML
    private AnchorPane messagePane;
    @FXML
    private Button btnManualMessage;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.resourceBundle = resources;
        LOG.debug("Initialized MessagesController");

        MessageClient messageClient = null;
        try {
            messageClient = new MessageClient(true);
            messageClient.onUpdate(
                messages -> {
                    messagesTable.setItems(FXCollections.observableList(messages));
                    actionCol.setCellFactory(createActionCellFactory());
                }
            );

            RmiClient.getInstance().registerForMessages(messageClient);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        this.btnManualMessage.setOnAction(evt -> {
            TextInputDialog dlg = new TextInputDialog();
            dlg.setTitle("Send message");
            dlg.setContentText("Enter message:");
            dlg.setHeaderText("Here you can manually add a message to other Librarians.");
            dlg.setWidth(500);
            dlg.setOnCloseRequest((event) -> {
                try {
                    RmiClient.getInstance().addMessage(new CustomMessage(
                        UUID.randomUUID(),
                        dlg.getEditor().getText(),
                        CustomMessage.Status.Open,
                        LocalDateTime.now()
                    ));
                } catch (RemoteException e) {
                    LOG.error("Cannot send message", e);
                    AlertHelper.showAlert(
                        Alert.AlertType.ERROR,
                        this.messagePane.getScene().getWindow(),
                        "Cannot send message",
                        "Unable to send message because of networking "
                            + "errors. Please try again soon."
                    );
                }
            });
            dlg.show();
        });
    }

    private Callback<TableColumn
        <CustomMessage, Void>, TableCell<CustomMessage, Void>> createActionCellFactory() {

        Callback<TableColumn<CustomMessage, Void>, TableCell<CustomMessage, Void>> cellFactory =
            new Callback<>() {
                @Override
                public TableCell<CustomMessage, Void> call(
                    final TableColumn<CustomMessage, Void> param) {
                    final TableCell<CustomMessage, Void> cell =
                        new TableCell<>() {

                            private final Button btnTake = new Button("Take");
                            private final Button btnComplete = new Button("Complete");
                            private final Button btnArchive = new Button("Archive");

                            {
                                btnTake.setPrefSize(120, 10);
                                btnTake.setAlignment(Pos.CENTER);
                                btnComplete.setPrefSize(120, 10);
                                btnComplete.setAlignment(Pos.CENTER);
                                btnArchive.setPrefSize(120, 10);
                                btnArchive.setAlignment(Pos.CENTER);

                                RmiClient client = RmiClient.getInstance();

                                btnTake.setOnAction((ActionEvent event) -> {
                                    CustomMessage msg = getTableView()
                                        .getItems().get(getIndex());
                                    LOG.debug("Take button pressed for message: " + msg.id);
                                    msg.status = CustomMessage.Status.Working;
                                    try {
                                        client.updateMessageStatus(msg);
                                    } catch (RemoteException e) {
                                        e.printStackTrace();
                                    }
                                });

                                btnComplete.setOnAction((ActionEvent event) -> {
                                    CustomMessage msg = getTableView()
                                        .getItems().get(getIndex());
                                    LOG.debug("Complete button pressed for message: " + msg.id);
                                    msg.status = CustomMessage.Status.Done;
                                    try {
                                        client.updateMessageStatus(msg);
                                    } catch (RemoteException e) {
                                        e.printStackTrace();
                                    }
                                });

                                btnArchive.setOnAction((ActionEvent event) -> {
                                    CustomMessage msg = getTableView()
                                        .getItems().get(getIndex());
                                    LOG.debug("Archive button pressed for message: " + msg.id);
                                    msg.status = CustomMessage.Status.Archived;
                                    try {
                                        client.updateMessageStatus(msg);
                                    } catch (RemoteException e) {
                                        e.printStackTrace();
                                    }
                                });

                            }

                            @Override
                            public void updateItem(Void item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty) {
                                    setGraphic(null);
                                } else {
                                    CustomMessage current =
                                        getTableView().getItems().get(getIndex());
                                    if (current.getStatus()
                                        .equals(CustomMessage.Status.Open)) {
                                        setGraphic(btnTake);
                                    } else if (current.getStatus()
                                        .equals(CustomMessage.Status.Working)) {
                                        setGraphic(btnComplete);
                                    } else if (current.getStatus()
                                        .equals(CustomMessage.Status.Done)) {
                                        setGraphic(btnArchive);
                                    }
                                }
                            }
                        };
                    return cell;
                }
            };

        return cellFactory;
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
