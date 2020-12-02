package at.fhv.teamg.librarymanagement.client.controller;

import at.fhv.teamg.librarymanagement.client.controller.internal.AlertHelper;
import at.fhv.teamg.librarymanagement.client.controller.internal.Parentable;
import at.fhv.teamg.librarymanagement.client.controller.internal.TabPaneEntry;
import at.fhv.teamg.librarymanagement.client.remote.RemoteClient;
import at.fhv.teamg.librarymanagement.shared.dto.BookDto;
import at.fhv.teamg.librarymanagement.shared.dto.DvdDto;
import at.fhv.teamg.librarymanagement.shared.dto.EmptyDto;
import at.fhv.teamg.librarymanagement.shared.dto.GameDto;
import at.fhv.teamg.librarymanagement.shared.dto.MediumCopyDto;
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
import javafx.scene.layout.AnchorPane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ReturningController implements Initializable, Parentable<MediaDetailsController> {
    private static final Logger LOG = LogManager.getLogger(ReturningController.class);

    private MediaDetailsController parentController;
    private ResourceBundle resourceBundle;

    private MediumType currentMediumType;
    private BookDto currentBook;
    private DvdDto currentDvd;
    private GameDto currentGame;
    private UUID currentUuid;

    private String currentUser = "";
    private List<UserDto> allUsers = null;

    @FXML
    private AnchorPane returningPane;

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
    private Button btnReturn;
    @FXML
    private Button btnBack;
    @FXML
    private Label userSelect;

    private List<TopicDto> topics = new LinkedList<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.resourceBundle = resources;
        LOG.debug("Initialized ReturningController");
        loadAdditionalData();
        addMediaTypeEventHandlers();
    }

    private void addMediaTypeEventHandlers() {
        this.btnReturn.setOnAction(e -> {

            MediumCopyDto.MediumCopyDtoBuilder builder =
                new MediumCopyDto.MediumCopyDtoBuilder(currentUuid);

            RmiClient client = RmiClient.getInstance();
            MessageDto<EmptyDto> response = null;
            try {
                switch (currentMediumType) {
                    case BOOK:
                        response = client.returnBook(builder.build());
                        break;
                    case DVD:
                        response = client.returnDvd(builder.build());
                        break;
                    case GAME:
                        response = client.returnGame(builder.build());
                        break;
                    default:
                        LOG.error("No medium type");
                }
            } catch (RemoteException remoteException) {
                LOG.error(remoteException);
            }

            if (response != null) {
                if (response.getType().equals(MessageDto.MessageType.SUCCESS)) {
                    AlertHelper.showAlert(
                        Alert.AlertType.INFORMATION,
                        this.returningPane.getScene().getWindow(),
                        "Returning successful",
                        response.getMessage()
                    );
                    this.getParentController().updateView();
                } else {
                    AlertHelper.showAlert(
                        Alert.AlertType.ERROR,
                        this.returningPane.getScene().getWindow(),
                        "Returning failed",
                        response.getMessage()
                    );
                }
            } else {
                AlertHelper.showAlert(
                    Alert.AlertType.ERROR,
                    this.returningPane.getScene().getWindow(),
                    "Returning failed",
                    "Something went wrong. Returning failed."
                );
            }


        });

        this.btnBack.setOnAction(e -> {
            System.out.println("Cancel button pressed");
            this.parentController.getParentController().getParentController()
                .removeTab(TabPaneEntry.RETURNING);
            this.parentController.getParentController().getParentController()
                .selectTab(TabPaneEntry.MEDIA_DETAIL);
        });
    }

    private void loadAdditionalData() {
        try {
            allUsers = RmiClient.getInstance().getAllUsers();
            topics = RmiClient.getInstance().getAllTopics();
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

    /**
     * Set current MediumCopyDto.
     *
     * @param dto dto from MediaDetailsController.
     */
    public void setCurrentMediumCopy(MediumCopyDto dto) {
        this.userSelect.textProperty().bind(new SimpleStringProperty(
            this.allUsers.stream()
                .filter(user -> dto.getCurrentLendingUser().equals(user.getId()))
                .findAny().orElse(null).getName()
        ));
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

    public String getCurrentUser() {
        return currentUser;
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
