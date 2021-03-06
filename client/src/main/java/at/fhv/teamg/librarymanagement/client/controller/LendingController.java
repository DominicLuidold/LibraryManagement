package at.fhv.teamg.librarymanagement.client.controller;

import at.fhv.teamg.librarymanagement.client.controller.internal.AlertHelper;
import at.fhv.teamg.librarymanagement.client.controller.internal.Parentable;
import at.fhv.teamg.librarymanagement.client.controller.internal.TabPaneEntry;
import at.fhv.teamg.librarymanagement.client.controller.internal.media.util.UserDropdown;
import at.fhv.teamg.librarymanagement.client.remote.RemoteClient;
import at.fhv.teamg.librarymanagement.shared.dto.BookDto;
import at.fhv.teamg.librarymanagement.shared.dto.DvdDto;
import at.fhv.teamg.librarymanagement.shared.dto.GameDto;
import at.fhv.teamg.librarymanagement.shared.dto.LendingDto;
import at.fhv.teamg.librarymanagement.shared.dto.MessageDto;
import at.fhv.teamg.librarymanagement.shared.dto.TopicDto;
import at.fhv.teamg.librarymanagement.shared.dto.UserDto;
import java.net.URL;
import java.rmi.RemoteException;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.UUID;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.controlsfx.control.textfield.TextFields;

public class LendingController implements Initializable, Parentable<MediaDetailsController> {
    private static final Logger LOG = LogManager.getLogger(LendingController.class);

    private MediaDetailsController parentController;
    private ResourceBundle resourceBundle;

    private MediumType currentMediumType;
    private BookDto currentBook;
    private DvdDto currentDvd;
    private GameDto currentGame;
    private UUID currentUuid;

    private UserDropdown dropdown;
    private List<UserDto> allCustomerList;

    @FXML
    private AnchorPane lendingPane;

    // Generic
    @FXML
    private Label lblTitle;
    @FXML
    private Label txtTitle;
    @FXML
    private Label lblLocation;
    @FXML
    private Label txtLocation;
    @FXML
    private Label lblTopic;
    @FXML
    private Label txtTopic;
    @FXML
    private Label lblReleaseDate;
    @FXML
    private Label txtReleaseDate;

    // Book
    @FXML
    private Label lblBookAuthor;
    @FXML
    private Label txtBookAuthor;
    @FXML
    private Label lblBookIsbn10;
    @FXML
    private Label txtBookIsbn10;
    @FXML
    private Label lblBookIsbn13;
    @FXML
    private Label txtBookIsbn13;
    @FXML
    private Label lblBookPublisher;
    @FXML
    private Label txtBookPublisher;
    @FXML
    private Label lblBookLanguage;
    @FXML
    private Label txtBookLanguage;

    // DVD
    @FXML
    private Label lblDvdDirector;
    @FXML
    private Label txtDvdDirector;
    @FXML
    private Label lblDvdDuration;
    @FXML
    private Label txtDvdDuration;
    @FXML
    private Label lblDvdActors;
    @FXML
    private Label txtDvdActors;
    @FXML
    private Label lblDvdStudio;
    @FXML
    private Label txtDvdStudio;
    @FXML
    private Label lblDvdAgeRestriction;
    @FXML
    private Label txtDvdAgeRestriction;

    // Game
    @FXML
    private Label lblGamePublisher;
    @FXML
    private Label txtGamePublisher;
    @FXML
    private Label lblGameDeveloper;
    @FXML
    private Label txtGameDeveloper;
    @FXML
    private Label lblGameAgeRestriction;
    @FXML
    private Label txtGameAgeRestriction;
    @FXML
    private Label lblGamePlatforms;
    @FXML
    private Label txtGamePlatforms;

    @FXML
    private Button btnReserve;
    @FXML
    private Button btnBack;


    @FXML
    private TextField txtUserSelect;

    private List<TopicDto> topics = new LinkedList<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.resourceBundle = resources;
        LOG.debug("Initialized UserController");
        addMediaTypeEventHandlers();
        loadAdditionalData();
        dropdown = new UserDropdown(allCustomerList);
        TextFields.bindAutoCompletion(txtUserSelect, dropdown.getAllUserString());

    }


    private void addMediaTypeEventHandlers() {
        this.btnReserve.setOnAction(e -> {
            if (txtUserSelect.getText().trim().length() == 0) {
                AlertHelper.showAlert(
                    Alert.AlertType.ERROR,
                    this.lendingPane.getScene().getWindow(),
                    "No user selected",
                    "Select a user first."
                );
                return;
            }

            UUID userId = dropdown.getUserID(txtUserSelect.getText().trim());

            if (userId != null) {
                LendingDto.LendingDtoBuilder builder = new LendingDto.LendingDtoBuilder();
                builder.userId(userId)
                    .startDate(LocalDate.now())
                    .endDate(LocalDate.now())
                    .mediumCopyId(currentUuid)
                    .renewalCount(0);

                RemoteClient client = RemoteClient.getInstance();

                MessageDto<LendingDto> response = null;
                try {
                    switch (currentMediumType) {
                        case DVD:
                            response = client.lendDvd(builder.build());
                            break;
                        case BOOK:
                            response = client.lendBook(builder.build());
                            break;
                        case GAME:
                            response = client.lendGame(builder.build());
                            break;
                        default:
                            LOG.error("no medium type");
                    }

                } catch (RemoteException remoteException) {
                    remoteException.printStackTrace();
                }

                if (response != null) {
                    if (response.getType().equals(MessageDto.MessageType.SUCCESS)) {
                        AlertHelper.showAlert(
                            Alert.AlertType.INFORMATION,
                            this.lendingPane.getScene().getWindow(),
                            "Lending successful",
                            response.getMessage()
                        );
                        this.getParentController().updateView();
                        this.txtUserSelect.clear();
                    } else {
                        AlertHelper.showAlert(
                            Alert.AlertType.ERROR,
                            this.lendingPane.getScene().getWindow(),
                            "Lending failed",
                            response.getMessage()
                        );
                    }
                } else {
                    AlertHelper.showAlert(
                        Alert.AlertType.ERROR,
                        this.lendingPane.getScene().getWindow(),
                        "Lending failed",
                        "Something went wrong. Lending failed."
                    );
                }

            } else {
                AlertHelper.showAlert(
                    Alert.AlertType.ERROR,
                    this.lendingPane.getScene().getWindow(),
                    "User not found",
                    "No user found. Please select a valid user."
                );
                return;
            }
        });

        this.btnBack.setOnAction(e -> {
            System.out.println("Cancel button pressed");
            this.parentController.getParentController().getParentController()
                .removeTab(TabPaneEntry.LENDING);
            this.parentController.getParentController().getParentController()
                .selectTab(TabPaneEntry.MEDIA_DETAIL);
        });

    }

    private void loadAdditionalData() {
        try {
            this.allCustomerList = RemoteClient.getInstance().getAllCustomers();
            this.topics = RemoteClient.getInstance().getAllTopics();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    /**
     * Set Current Medium to Book.
     *
     * @param bookDto selected Book
     */
    public void setCurrentMedium(BookDto bookDto) {
        currentMediumType = MediumType.BOOK;
        this.enableFieldsForMediumType(MediumType.BOOK);
        this.bindGenericProperties(
            bookDto.getTitle(),
            bookDto.getStorageLocation(),
            bookDto.getTopic(),
            bookDto.getReleaseDate()
        );
        currentBook = bookDto;
    }

    /**
     * Set Current Medium to Game.
     *
     * @param gameDto selected Game
     */
    public void setCurrentMedium(GameDto gameDto) {
        currentMediumType = MediumType.GAME;
        this.enableFieldsForMediumType(MediumType.GAME);
        this.bindGenericProperties(
            gameDto.getTitle(),
            gameDto.getStorageLocation(),
            gameDto.getTopic(),
            gameDto.getReleaseDate()
        );
        currentGame = gameDto;
    }

    /**
     * Set Current Medium to Dvd.
     *
     * @param dvdDto selected Dvd
     */
    public void setCurrentMedium(DvdDto dvdDto) {
        currentMediumType = MediumType.DVD;
        this.enableFieldsForMediumType(MediumType.DVD);
        this.bindGenericProperties(
            dvdDto.getTitle(),
            dvdDto.getStorageLocation(),
            dvdDto.getTopic(),
            dvdDto.getReleaseDate()
        );
        currentDvd = dvdDto;
    }

    private void enableFieldsForMediumType(MediumType type) {
        this.lblTitle.setVisible(true);
        this.txtTitle.setVisible(true);

        if (type.equals(MediumType.BOOK)) {
            this.handleBookFields(true);
            this.handleDvdFields(false);
            this.handleGameFields(false);
        } else if (type.equals(MediumType.DVD)) {
            this.handleBookFields(false);
            this.handleDvdFields(true);
            this.handleGameFields(false);
        } else if (type.equals(MediumType.GAME)) {
            this.handleBookFields(false);
            this.handleDvdFields(false);
            this.handleGameFields(true);
        }
    }

    private void handleBookFields(boolean visible) {
        this.lblBookAuthor.setVisible(visible);
        this.txtBookAuthor.setVisible(visible);
        this.lblBookIsbn10.setVisible(visible);
        this.txtBookIsbn10.setVisible(visible);
        this.lblBookIsbn13.setVisible(visible);
        this.txtBookIsbn13.setVisible(visible);
        this.lblBookPublisher.setVisible(visible);
        this.txtBookPublisher.setVisible(visible);
        this.lblBookLanguage.setVisible(visible);
        this.txtBookLanguage.setVisible(visible);
    }

    private void handleDvdFields(boolean visible) {
        this.lblDvdDirector.setVisible(visible);
        this.txtDvdDirector.setVisible(visible);
        this.lblDvdDuration.setVisible(visible);
        this.txtDvdDuration.setVisible(visible);
        this.lblDvdActors.setVisible(visible);
        this.txtDvdActors.setVisible(visible);
        this.lblDvdStudio.setVisible(visible);
        this.txtDvdStudio.setVisible(visible);
        this.lblDvdAgeRestriction.setVisible(visible);
        this.txtDvdAgeRestriction.setVisible(visible);
    }

    private void handleGameFields(boolean visible) {
        this.lblGamePublisher.setVisible(visible);
        this.txtGamePublisher.setVisible(visible);
        this.lblGameDeveloper.setVisible(visible);
        this.txtGameDeveloper.setVisible(visible);
        this.lblGameAgeRestriction.setVisible(visible);
        this.txtGameAgeRestriction.setVisible(visible);
        this.lblGamePlatforms.setVisible(visible);
        this.txtGamePlatforms.setVisible(visible);
    }

    public BookDto getCurrentBook() {
        return currentBook;
    }

    private void bindGenericProperties(
        String title,
        String storageLocation,
        UUID topicId,
        LocalDate releaseDate
    ) {
        this.txtTitle.textProperty().bind(new SimpleStringProperty(title));
        this.txtLocation.textProperty().bind(new SimpleStringProperty(storageLocation));
        this.txtTopic.textProperty().bind(new SimpleStringProperty(
            this.topics.stream()
                .filter(top -> topicId.equals(top.getId()))
                .findAny().orElse(null).getName()
        ));
        this.txtReleaseDate.textProperty().bind(new SimpleStringProperty(
            releaseDate != null ? releaseDate.toString() : ""
        ));
    }

    public DvdDto getCurrentDvd() {
        return currentDvd;
    }

    public GameDto getCurrentGame() {
        return currentGame;
    }

    public void setCurrentUuid(UUID currentUuid) {
        this.currentUuid = currentUuid;
    }

    @Override
    public MediaDetailsController getParentController() {
        return this.parentController;
    }

    @Override
    public void setParentController(MediaDetailsController controller) {
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
