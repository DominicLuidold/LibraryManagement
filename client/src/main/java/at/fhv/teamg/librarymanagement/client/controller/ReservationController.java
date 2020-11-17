package at.fhv.teamg.librarymanagement.client.controller;

import at.fhv.teamg.librarymanagement.client.controller.internal.AlertHelper;
import at.fhv.teamg.librarymanagement.client.controller.internal.Parentable;
import at.fhv.teamg.librarymanagement.client.controller.internal.TabPaneEntry;
import at.fhv.teamg.librarymanagement.client.controller.internal.media.util.UserDropdown;
import at.fhv.teamg.librarymanagement.client.rmi.RmiClient;
import at.fhv.teamg.librarymanagement.shared.dto.BookDto;
import at.fhv.teamg.librarymanagement.shared.dto.DvdDto;
import at.fhv.teamg.librarymanagement.shared.dto.GameDto;
import at.fhv.teamg.librarymanagement.shared.dto.MessageDto;
import at.fhv.teamg.librarymanagement.shared.dto.ReservationDto;
import at.fhv.teamg.librarymanagement.shared.dto.TopicDto;
import at.fhv.teamg.librarymanagement.shared.dto.UserDto;
import java.net.URL;
import java.rmi.RemoteException;
import java.time.LocalDate;
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

public class ReservationController implements Initializable, Parentable<MediaDetailsController> {
    private static final Logger LOG = LogManager.getLogger(ReservationController.class);

    private MediaDetailsController parentController;
    private ResourceBundle resourceBundle;

    private List<UserDto> allUserList;
    private UserDropdown dropdown;

    private BookDto currentBook = null;
    private DvdDto currentDvd = null;
    private GameDto currentGame = null;

    //Generics
    private UUID mediumUuid;
    private String mediumTitle;
    private String mediumLocation;
    private String mediumReleaseDate;
    private String mediumTopic;

    //TestData
    private MediumType type = MediumType.DVD;

    private UUID userUuidToReserve;
    private List<TopicDto> topics;

    //Generic
    @FXML
    private AnchorPane reservationPane;
    @FXML
    private Label lblTitle;
    @FXML
    private Label lblTitleContent;
    @FXML
    private Label lblLocationContent;
    @FXML
    private Label lblLocation;
    @FXML
    private Label lblReleaseContent;
    @FXML
    private Label lblReleaseDate;
    @FXML
    private Label lblTopic;
    @FXML
    private Label lblTopicContent;

    //Book
    @FXML
    private Label lblBookAuthor;
    @FXML
    private Label lblBookAuthorContent;
    @FXML
    private Label lblBookIsbn13;
    @FXML
    private Label lblBookIsbn13Content;
    @FXML
    private Label lblBookIsbn10;
    @FXML
    private Label lblBookIsbn10Content;
    @FXML
    private Label lblBookPublisher;
    @FXML
    private Label lblBookPublisherContent;
    @FXML
    private Label lblBookLanguage;
    @FXML
    private Label lblBookLanguageContent;

    //Dvd
    @FXML
    private Label lblDvdDirector;
    @FXML
    private Label lblDvdDirectorContent;
    @FXML
    private Label lblDvdDuration;
    @FXML
    private Label lblDvdDurationContent;
    @FXML
    private Label lblDvdActors;
    @FXML
    private Label lblDvdActorsContent;
    @FXML
    private Label lblDvdStudio;
    @FXML
    private Label lblDvdStudioContent;
    @FXML
    private Label lblDvdAgeRestriction;
    @FXML
    private Label lblDvdAgeRestrictionContent;

    //Game
    @FXML
    private Label lblGameDeveloper;
    @FXML
    private Label lblGameDeveloperContent;
    @FXML
    private Label lblGameAgeRestriction;
    @FXML
    private Label lblGameAgeRestrictionContent;
    @FXML
    private Label lblGamePlatforms;
    @FXML
    private Label lblGamePlatformsContent;
    @FXML
    private Label lblGamePublisher;
    @FXML
    private Label lblGamePublisherContent;

    //Functional
    @FXML
    private TextField txtUser;
    @FXML
    private Button btnReserve;
    @FXML
    private Button btnBack;
    @FXML
    private Label confirm;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.resourceBundle = resources;
        this.enableLabelsForMediumType(type);
        this.addMediaTypeEventHandlers();
        loadAdditionalData();
        dropdown = new UserDropdown(allUserList);
        TextFields.bindAutoCompletion(txtUser, dropdown.getAllUserString());
        LOG.debug("Initialized ReservationController");
    }

    private void addMediaTypeEventHandlers() {
        this.btnReserve.setOnAction(e -> {
            if (txtUser.getText().trim().length() != 0) {
                userUuidToReserve = dropdown.getUserID(txtUser.getText().trim());

                if (userUuidToReserve != null) {
                    ReservationDto.ReservationDtoBuilder dtoBuilder =
                        new ReservationDto.ReservationDtoBuilder();

                    RmiClient client = RmiClient.getInstance();
                    MessageDto<ReservationDto> response = null;


                    try {
                        switch (this.type) {
                            case BOOK:
                                response = client.reserveBook(buildReservation(dtoBuilder));
                                break;
                            case DVD:
                                response = client.reserveDvd(buildReservation(dtoBuilder));
                                break;
                            case GAME:
                                response = client.reserveGame(buildReservation(dtoBuilder));
                                break;
                            default:
                                LOG.error("no medium type");
                        }
                    } catch (RemoteException remoteException) {
                        remoteException.printStackTrace();
                    }

                    if (response != null) {
                        if (response.getType().equals(MessageDto.MessageType.SUCCESS)) {
                            confirm.setText(response.getMessage());
                            AlertHelper.showAlert(
                                Alert.AlertType.CONFIRMATION,
                                this.reservationPane.getScene().getWindow(),
                                "Reservation successful",
                                response.getMessage()
                            );
                        } else {
                            confirm.setText(response.getMessage());
                            AlertHelper.showAlert(
                                Alert.AlertType.ERROR,
                                this.reservationPane.getScene().getWindow(),
                                "Reservation failed",
                                response.getMessage()
                            );
                        }
                    } else {
                        confirm.setText("Something went wrong");
                        AlertHelper.showAlert(
                            Alert.AlertType.ERROR,
                            this.reservationPane.getScene().getWindow(),
                            "Returning failed",
                            "Something went wrong. Returning failed."
                        );
                    }
                } else {
                    confirm.setText("No valid user found");
                    AlertHelper.showAlert(
                        Alert.AlertType.ERROR,
                        this.reservationPane.getScene().getWindow(),
                        "User not found",
                        "No user found. Please select a valid user."
                    );
                    return;
                }

            }
        });

        this.btnBack.setOnAction(e -> {
            System.out.println("Cancel button pressed");
            this.parentController.getParentController().getParentController()
                .removeTab(TabPaneEntry.RESERVATION);
            this.parentController.getParentController().getParentController()
                .selectTab(TabPaneEntry.MEDIA_DETAIL);
        });
    }

    private ReservationDto buildReservation(ReservationDto.ReservationDtoBuilder dtoBuilder) {
        //dtoBuilder.userId(getUserUuidValue());    //map approach
        dtoBuilder.userId(userUuidToReserve);
        dtoBuilder.mediumId(mediumUuid);
        dtoBuilder.startDate(LocalDate.now());
        dtoBuilder.endDate(LocalDate.now().plusDays(60));
        return dtoBuilder.build();
    }


    /**
     * function to set the current book.
     *
     * @param dto current book.
     */
    public void setCurrentBook(BookDto dto) {
        this.currentBook = dto;
        this.type = MediumType.BOOK;
        enableLabelsForMediumType(this.type);
        setGenericParams(
            dto.getMediumId(),
            dto.getTitle(),
            dto.getStorageLocation(),
            dto.getTopic(),
            dto.getReleaseDate()
        );
        bindBookProperties(
            dto.getAuthor(),
            dto.getIsbn10(),
            dto.getIsbn13(),
            dto.getPublisher(),
            dto.getLanguageKey()
        );
    }

    /**
     * function to set the current dvd.
     *
     * @param dto current dvd.
     */
    public void setCurrentDvd(DvdDto dto) {
        this.currentDvd = dto;
        this.type = MediumType.DVD;
        enableLabelsForMediumType(this.type);
        setGenericParams(
            dto.getMediumId(),
            dto.getTitle(),
            dto.getStorageLocation(),
            dto.getTopic(),
            dto.getReleaseDate()
        );
        bindDvdProperties(
            dto.getDirector(),
            dto.getDurationMinutes(),
            dto.getActors(),
            dto.getStudio(),
            dto.getAgeRestriction()
        );
    }

    /**
     * function to set the current game.
     *
     * @param dto current game.
     */
    public void setCurrentGame(GameDto dto) {
        this.currentGame = dto;
        this.type = MediumType.GAME;
        enableLabelsForMediumType(this.type);
        setGenericParams(
            dto.getMediumId(),
            dto.getTitle(),
            dto.getStorageLocation(),
            dto.getTopic(),
            dto.getReleaseDate()
        );
        bindGameProperties(
            dto.getPublisher(),
            dto.getDeveloper(),
            dto.getAgeRestriction(),
            dto.getPlatforms()
        );
    }

    private void setGenericParams(UUID uuid, String title, String loc, UUID topicUuid,
                                  LocalDate date) {
        this.mediumUuid = uuid;
        this.mediumTitle = title;
        this.mediumLocation = loc;
        this.mediumTopic = topics.stream()
            .filter(top -> topicUuid.equals(top.getId())).findAny().orElse(null).getName();
        if (date != null) {
            this.mediumReleaseDate = date.toString();
        } else {
            this.mediumReleaseDate = "";
        }
    }

    private void enableLabelsForMediumType(MediumType type) {
        this.lblTitle.setVisible(true);
        this.lblTitleContent.setVisible(true);
        this.lblLocation.setVisible(true);
        this.lblLocationContent.setVisible(true);
        this.lblReleaseDate.setVisible(true);
        this.lblReleaseContent.setVisible(true);
        this.lblTopic.setVisible(true);
        this.lblTopicContent.setVisible(true);

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
        System.out.println("all books set to " + visible);
        this.lblBookAuthor.setVisible(visible);
        this.lblBookAuthorContent.setVisible(visible);
        this.lblBookIsbn10.setVisible(visible);
        this.lblBookIsbn10Content.setVisible(visible);
        this.lblBookIsbn13.setVisible(visible);
        this.lblBookIsbn13Content.setVisible(visible);
        this.lblBookPublisher.setVisible(visible);
        this.lblBookPublisherContent.setVisible(visible);
        this.lblBookLanguage.setVisible(visible);
        this.lblBookLanguageContent.setVisible(visible);
    }

    private void handleDvdFields(boolean visible) {
        System.out.println("all dvd set to " + visible);
        this.lblDvdDirector.setVisible(visible);
        this.lblDvdDirectorContent.setVisible(visible);
        this.lblDvdDuration.setVisible(visible);
        this.lblDvdDurationContent.setVisible(visible);
        this.lblDvdActors.setVisible(visible);
        this.lblDvdActorsContent.setVisible(visible);
        this.lblDvdStudio.setVisible(visible);
        this.lblDvdStudioContent.setVisible(visible);
        this.lblDvdAgeRestriction.setVisible(visible);
        this.lblDvdAgeRestrictionContent.setVisible(visible);
    }

    private void handleGameFields(boolean visible) {
        System.out.println("all games set to " + visible);
        this.lblGameDeveloper.setVisible(visible);
        this.lblGameDeveloperContent.setVisible(visible);
        this.lblGamePlatforms.setVisible(visible);
        this.lblGamePlatformsContent.setVisible(visible);
        this.lblGameAgeRestriction.setVisible(visible);
        this.lblGameAgeRestrictionContent.setVisible(visible);
        this.lblGamePublisher.setVisible(visible);
        this.lblGamePublisherContent.setVisible(visible);
    }

    private void loadAdditionalData() {
        try {
            this.topics = RmiClient.getInstance().getAllTopics();
            this.allUserList = RmiClient.getInstance().getAllUsers();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private void bindBookProperties(
        String author,
        String isbn10,
        String isbn13,
        String publisher,
        String language
    ) {
        this.lblBookAuthorContent.textProperty().bind(new SimpleStringProperty(author));
        this.lblBookIsbn10Content.textProperty().bind(new SimpleStringProperty(isbn10));
        this.lblBookIsbn13Content.textProperty().bind(new SimpleStringProperty(isbn13));
        this.lblBookPublisherContent.textProperty().bind(new SimpleStringProperty(publisher));
        this.lblBookLanguageContent.textProperty().bind(new SimpleStringProperty(language));
    }

    private void bindDvdProperties(
        String director,
        String duration,
        String actors,
        String studio,
        String ageRestriction
    ) {
        this.lblDvdDirectorContent.textProperty().bind(new SimpleStringProperty(director));
        this.lblDvdDurationContent.textProperty().bind(new SimpleStringProperty(duration));
        this.lblDvdActorsContent.textProperty().bind(new SimpleStringProperty(actors));
        this.lblDvdStudioContent.textProperty().bind(new SimpleStringProperty(studio));
        this.lblDvdAgeRestrictionContent.textProperty().bind(new SimpleStringProperty(
            ageRestriction)
        );
    }

    private void bindGameProperties(
        String publisher,
        String developer,
        String ageRestriction,
        String platforms
    ) {
        this.lblGamePublisherContent.textProperty().bind(new SimpleStringProperty(publisher));
        this.lblGameDeveloperContent.textProperty().bind(new SimpleStringProperty(developer));
        this.lblGameAgeRestrictionContent.textProperty().bind(new SimpleStringProperty(
            ageRestriction)
        );
        this.lblGamePlatformsContent.textProperty().bind(new SimpleStringProperty(platforms));
    }

    public String getMediumTitle() {
        return mediumTitle;
    }

    public String getMediumLocation() {
        return mediumLocation;
    }

    public String getMediumReleaseDate() {
        return mediumReleaseDate;
    }

    public String getMediumTopic() {
        return mediumTopic;
    }

    public BookDto getCurrentBook() {
        return currentBook;
    }

    public DvdDto getCurrentDvd() {
        return currentDvd;
    }

    public GameDto getCurrentGame() {
        return currentGame;
    }

    @Override
    public MediaDetailsController getParentController() {
        return this.getParentController();
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
        LOG.debug("Initialized ReservationController with parent");
    }

}

