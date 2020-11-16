package at.fhv.teamg.librarymanagement.client.controller;

import at.fhv.teamg.librarymanagement.client.controller.internal.Parentable;
import at.fhv.teamg.librarymanagement.client.controller.internal.TabPaneEntry;
import at.fhv.teamg.librarymanagement.client.rmi.RmiClient;
import at.fhv.teamg.librarymanagement.shared.dto.BookDto;
import at.fhv.teamg.librarymanagement.shared.dto.DvdDto;
import at.fhv.teamg.librarymanagement.shared.dto.GameDto;
import at.fhv.teamg.librarymanagement.shared.dto.LendingDto;
import at.fhv.teamg.librarymanagement.shared.dto.MediumCopyDto;
import at.fhv.teamg.librarymanagement.shared.dto.UserDto;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.UUID;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.util.StringConverter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ReturningController implements Initializable, Parentable<SearchController> {
    private static final Logger LOG = LogManager.getLogger(ReturningController.class);

    private SearchController parentController;
    private ResourceBundle resourceBundle;

    private MediumType currentMediumType;
    private BookDto currentBook;
    private DvdDto currentDvd;
    private GameDto currentGame;
    private UUID currentUuid;

    private String currentUser = "";
    private List<UserDto> allUsers = null;
    private List<MediumCopyDto> allMediumCopies = null;

    @FXML
    private AnchorPane detailsPane;

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
    private Button btnCancel;

    @FXML
    private Label userSelect;

    @FXML
    private Label confirm;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.resourceBundle = resources;
        LOG.debug("Initialized ReturningController");
        loadAdditionalData();
        addMediaTypeEventHandlers();
        /*StringConverter<UserDto> userConverter = new StringConverter<>() {
            @Override
            public String toString(UserDto userDto) {
                if (userDto == null) {
                    return "Select User";
                }
                return userDto.getName() + " (" + userDto.getUsername() + ")";
            }

            @Override
            public UserDto fromString(String user) {
                return new UserDto.UserDtoBuilder().name(user).build();
            }
        };*/

        /*userSelect.setConverter(userConverter);
        try {
            userSelect
                .setItems(FXCollections.observableArrayList(RmiClient.getInstance().getAllUsers()));
        } catch (RemoteException e) {
            e.printStackTrace();
        }*/
    }

    private void addMediaTypeEventHandlers() {
        this.btnReturn.setOnAction(e -> {
            /*if (userSelect.getSelectionModel().getSelectedItem() == null) {
                confirm.setText("Please select a user first!");
                return;
            }*/

            System.out.println(currentUuid);

            MediumCopyDto.MediumCopyDtoBuilder builder =
                new MediumCopyDto.MediumCopyDtoBuilder(currentUuid);

            RmiClient client = RmiClient.getInstance();
            boolean confirmedReturn = false;
            try {
                switch (currentMediumType) {
                    case BOOK:
                        confirmedReturn = client.returnBook(builder.build());
                        break;
                    case DVD:
                        confirmedReturn = client.returnDvd(builder.build());
                        break;
                    case GAME:
                        confirmedReturn = client.returnGame(builder.build());
                        break;
                    default:
                        LOG.error("No medium type");
                }
            } catch (RemoteException remoteException) {
                LOG.error(remoteException);
            }

            if (confirmedReturn) {
                confirm.setText("Returning confirmed");
            } else {
                confirm.setText("Something went wrong");
            }

        });

        this.btnCancel.setOnAction(e -> {
            System.out.println("Cancel button pressed");
            this.parentController.getParentController().removeTab(TabPaneEntry.RETURNING);
            this.parentController.getParentController().selectTab(TabPaneEntry.MEDIA_DETAIL);
        });
    }

    private void loadAdditionalData() {
        System.out.println("LOAD ADD DATA");
        System.out.println("current uuid " + currentUuid);
        System.out.println("list before: " + allMediumCopies);
        //TODO GetAllLendings or get lending.userName needed
        //List<MediumCopyDto> allMediumCopies = null;
        System.out.println("book: " + currentBook);
        try {
            switch (currentMediumType) {
                case BOOK:
                    System.out.println("BOOK RETURN");
                    this.allMediumCopies = RmiClient.getInstance().getAllBookCopies(currentBook);
                    System.out.println(allMediumCopies.toString());
                    break;
                case DVD:
                    this.allMediumCopies = RmiClient.getInstance().getAllDvdCopies(currentDvd);
                    break;
                case GAME:
                    this.allMediumCopies = RmiClient.getInstance().getAllGameCopies(currentGame);
                    break;
                default:
                    LOG.error("No medium type");
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        System.out.println("list after: " + allMediumCopies);
        if (this.allMediumCopies != null) {
            for (MediumCopyDto medium : this.allMediumCopies) {
                if (medium.getId().equals(currentUuid)) {
                    System.out.println(medium.getId() + " AND " + currentUuid);
                    System.out.println("User to be set........");
                    setUserName(currentUuid);
                    return;
                }
            }
        }
        /*try {
            this.allLendings = RmiClient.getInstance().getAll
        } catch (RemoteException e){
            e.printStackTrace();
        }*/
    }

    private void setUserName(UUID uuid) {
        try {
            allUsers = RmiClient.getInstance().getAllUsers();
            for (UserDto user : allUsers) {
                if (user.getId().equals(uuid)) {
                    this.currentUser = user.getName() + " (" + user.getUsername() + ")";
                    return;
                }
            }
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
        System.out.println("###########");
        System.out.println(dvdDto.getStorageLocation());
        System.out.println(dvdDto.getTitle());
        System.out.println(dvdDto.getDirector());
        System.out.println("###########");
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
        String topic,
        String releaseDate
    ) {
        this.txtTitle.textProperty().bind(new SimpleStringProperty(title));
        this.txtLocation.textProperty().bind(new SimpleStringProperty(storageLocation));
        this.txtTopic.textProperty().bind(new SimpleStringProperty(topic));
        this.txtReleaseDate.textProperty().bind(new SimpleStringProperty(releaseDate));
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
    public SearchController getParentController() {
        return this.parentController;
    }

    @Override
    public void setParentController(SearchController controller) {
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
