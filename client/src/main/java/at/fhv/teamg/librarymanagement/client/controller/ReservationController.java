package at.fhv.teamg.librarymanagement.client.controller;

import at.fhv.teamg.librarymanagement.client.controller.internal.AlertHelper;
import at.fhv.teamg.librarymanagement.client.controller.internal.Parentable;
import at.fhv.teamg.librarymanagement.client.controller.internal.TabPaneEntry;
import at.fhv.teamg.librarymanagement.client.controller.internal.media.general.MediaTopicTask;
import at.fhv.teamg.librarymanagement.client.rmi.RmiClient;
import at.fhv.teamg.librarymanagement.shared.dto.*;
import javafx.collections.FXCollections;
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

import java.net.URL;
import java.rmi.RemoteException;
import java.time.LocalDate;
import java.util.*;

public class ReservationController implements Initializable, Parentable<MediaDetailsController> {
    private static final Logger LOG = LogManager.getLogger(ReservationController.class);

    private MediaDetailsController parentController;
    private ResourceBundle resourceBundle;

    //TODO getAllUsers of DB
    private List<UserDto> allUserList;
    private ArrayList<String> allUsers = new ArrayList<>();
    //{"Anton", "Antonia", "Abraham", "Bertram", "Berta"};
    private HashMap<UUID, String> usersMap = new HashMap<>();

    private BookDto currentBook = null;
    private DvdDto currentDvd = null;
    private GameDto currentGame = null;

    //Generics
    private UUID mediumUUID;
    private String mediumTitle;
    private String mediumLocation;
    private String mediumReleaseDate;
    private String mediumTopic;

    //TestData
    private MediumType type = MediumType.DVD;

    //private UUID userUUIDToReserve;
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

    //Functional
    @FXML
    private TextField txtUser;
    @FXML
    private Button btnOK;
    @FXML
    private Button btnCancel;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.resourceBundle = resources;
        this.enableLabelsForMediumType(type);
        this.addMediaTypeEventHandlers();
        loadAdditionalData();
        //this.allUserList = RmiClient.getInstance().getAllUsers();
        //this.createUsersString(allUserList);
        try {
            fillMap(RmiClient.getInstance().getAllUsers());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        TextFields.bindAutoCompletion(txtUser, allUsers);
        LOG.debug("Initialized ReservationController");
    }

    private void fillMap(List<UserDto> users) {
        usersMap.clear();
        for (UserDto dto : users) {
            usersMap.put(dto.getId(), dto.getName());
        }
    }

    private void createUsersString(List<UserDto> usersList) {
        allUsers = new ArrayList<>();
        for (UserDto user : usersList) {
            allUsers.add(user.getUsername());
        }
    }

    private void addMediaTypeEventHandlers() {
        this.btnOK.setOnAction(e -> {
            if (getUserName().length() != 0) {
                //userUUIDToReserve = getUserID(getUserName());

                //TODO hand userName to backend
                ReservationDto.ReservationDtoBuilder dtoBuilder = new ReservationDto.ReservationDtoBuilder();

                if (this.type.equals(MediumType.BOOK)) {
                    ReservationDto resDto = buildReservation(dtoBuilder);
                    try {
                        RmiClient.getInstance().reserveBook(resDto);
                        AlertHelper.showAlert(Alert.AlertType.INFORMATION, this.reservationPane.getScene().getWindow(), "Reservation", "Reservation successful.");
                    } catch (RemoteException ex) {
                        ex.printStackTrace();
                    }
                } else if (this.type.equals(MediumType.DVD)) {
                    ReservationDto resDto = buildReservation(dtoBuilder);
                    try {
                        RmiClient.getInstance().reserveDvd(resDto);
                        AlertHelper.showAlert(Alert.AlertType.INFORMATION, this.reservationPane.getScene().getWindow(), "Reservation", "Reservation successful.");
                    } catch (RemoteException ex) {
                        ex.printStackTrace();
                    }
                } else if (this.type.equals(MediumType.GAME)) {
                    ReservationDto resDto = buildReservation(dtoBuilder);
                    try {
                        RmiClient.getInstance().reserveGame(resDto);
                        AlertHelper.showAlert(Alert.AlertType.INFORMATION, this.reservationPane.getScene().getWindow(), "Reservation", "Reservation successful.");
                    } catch (RemoteException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        this.btnCancel.setOnAction(e -> {
            System.out.println("Cancel button pressed");
            this.parentController.getParentController().getParentController().removeTab(TabPaneEntry.RESERVATION);
            this.parentController.getParentController().getParentController().selectTab(TabPaneEntry.MEDIA_DETAIL);
        });
    }

    private ReservationDto buildReservation(ReservationDto.ReservationDtoBuilder dtoBuilder) {
        dtoBuilder.userId(getUserUuidValue());
        //dtoBuilder.userId(userUUIDToReserve);
        dtoBuilder.mediumId(mediumUUID);
        dtoBuilder.startDate(LocalDate.now());
        dtoBuilder.endDate(LocalDate.now().plusDays(60));
        return dtoBuilder.build();
    }

    private UUID getUserUuidValue() {
        for (Map.Entry<UUID, String> entry : usersMap.entrySet()) {
            if (Objects.equals(getUserName(), entry.getValue())) {
                return entry.getKey();
            }
        }
        return null;
    }

    private String getUserName() {
        return this.txtUser.getText().trim();
    }

    /*private UUID getUserID(String userName){
        for (UserDto user: allUserList){
            if(user.getName().equals(userName)){
                return user.getId();
            }
        }
        return null;
    }*/

    public void setCurrentBook(BookDto dto) {
        this.currentBook = dto;
        this.type = MediumType.BOOK;
        setGenericParams(dto.getId(), dto.getTitle(), dto.getStorageLocation(), dto.getTopic(), dto.getReleaseDate());
    }

    public void setCurrentDvd(DvdDto dto) {
        this.currentDvd = dto;
        this.type = MediumType.DVD;
        setGenericParams(dto.getId(), dto.getTitle(), dto.getStorageLocation(), dto.getTopic(), dto.getReleaseDate());
    }

    public void setCurrentGame(GameDto dto) {
        this.currentGame = dto;
        this.type = MediumType.GAME;
        setGenericParams(dto.getId(), dto.getTitle(), dto.getStorageLocation(), dto.getTopic(), dto.getReleaseDate());
    }

    private void setGenericParams(UUID uuid, String title, String loc, UUID topicUuid, LocalDate date) {
        this.mediumUUID = uuid;
        this.mediumTitle = title;
        this.mediumLocation = loc;
        this.mediumTopic = topics.stream()
                .filter(top ->topicUuid.equals(top.getId())).findAny().orElse(null).getName();
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
    }

    private void loadAdditionalData() {
        MediaTopicTask task = new MediaTopicTask(this.reservationPane);
        Thread thread = new Thread(task, "Load All Topics Task");
        task.setOnSucceeded(event -> {
            System.out.println("Loaded all Topics");
            topics = task.getValue();
        });
        thread.start();
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

