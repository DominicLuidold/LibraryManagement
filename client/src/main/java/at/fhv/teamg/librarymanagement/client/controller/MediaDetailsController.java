package at.fhv.teamg.librarymanagement.client.controller;

import at.fhv.teamg.librarymanagement.client.controller.internal.AlertHelper;
import at.fhv.teamg.librarymanagement.client.controller.internal.ButtonTableCell;
import at.fhv.teamg.librarymanagement.client.controller.internal.Parentable;
import at.fhv.teamg.librarymanagement.client.controller.internal.TabPaneEntry;
import at.fhv.teamg.librarymanagement.client.controller.internal.media.details.BookDetailTask;
import at.fhv.teamg.librarymanagement.client.controller.internal.media.details.BookMediumCopyTask;
import at.fhv.teamg.librarymanagement.client.controller.internal.media.details.DvdDetailTask;
import at.fhv.teamg.librarymanagement.client.controller.internal.media.details.DvdMediumCopyTask;
import at.fhv.teamg.librarymanagement.client.controller.internal.media.details.GameDetailTask;
import at.fhv.teamg.librarymanagement.client.controller.internal.media.details.GameMediumCopyTask;
import at.fhv.teamg.librarymanagement.client.rmi.RmiClient;
import at.fhv.teamg.librarymanagement.shared.dto.BookDto;
import at.fhv.teamg.librarymanagement.shared.dto.DvdDto;
import at.fhv.teamg.librarymanagement.shared.dto.EmptyDto;
import at.fhv.teamg.librarymanagement.shared.dto.GameDto;
import at.fhv.teamg.librarymanagement.shared.dto.MediumCopyDto;
import at.fhv.teamg.librarymanagement.shared.dto.MessageDto;
import at.fhv.teamg.librarymanagement.shared.dto.ReservationDto;
import at.fhv.teamg.librarymanagement.shared.dto.TopicDto;
import at.fhv.teamg.librarymanagement.shared.dto.UserDto;
import at.fhv.teamg.librarymanagement.shared.enums.UserRoleName;
import java.net.URL;
import java.rmi.RemoteException;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.UUID;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MediaDetailsController implements Initializable, Parentable<SearchController> {
    private static final Logger LOG = LogManager.getLogger(MediaDetailsController.class);

    private SearchController parentController;
    private ResourceBundle resourceBundle;

    private MediumType currentMediumType;
    private BookDto currentBook;
    private DvdDto currentDvd;
    private GameDto currentGame;
    private UUID currentUuid;

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
    private Button btnReserve;
    @FXML
    private Button btnBack;

    @FXML
    private TableColumn<MediumCopyDto, String> columnCopyId;
    @FXML
    private TableColumn<MediumCopyDto, String> columnLendTill;
    @FXML
    private TableColumn<MediumCopyDto, Button> columnLend;
    @FXML
    private TableColumn<MediumCopyDto, Button> columnExtend;
    @FXML
    private TableColumn<MediumCopyDto, Button> columnReturn;
    @FXML
    private TableColumn<UserDto, Button> columnUserId;
    @FXML
    private TableColumn<ReservationDto, Button> columnRemoveReservation;
    @FXML
    private TableView<MediumCopyDto> tblResults;
    @FXML
    private TableView<ReservationDto> tblReservations;
    @FXML
    private VBox vboxReservations;
    @FXML
    private SplitPane splitPane;

    private List<TopicDto> topics = new LinkedList<>();

    private List<ReservationDto> reservations = new LinkedList<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.resourceBundle = resources;
        this.setCellFactories();
        this.addMediaTypeEventHandlers();
        LOG.debug("Initialized UserController");

        // Load Topics
        try {
            this.topics = RmiClient.getInstance().getAllTopics();
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }

    private void setCellFactories() {
        this.columnLend.setCellFactory(
            ButtonTableCell.forTableColumn(
                "Lend",
                (MediumCopyDto dto) -> {
                    LOG.debug("Lend button has been pressed");

                    if (!dto.isAvailable()) {
                        AlertHelper.showAlert(
                            Alert.AlertType.ERROR,
                            this.detailsPane.getScene().getWindow(),
                            "Medium is not available",
                            "Cannot lend a medium that has already been lent to another customer"
                        );
                        return dto;
                    }

                    // change view
                    Parentable<?> controller =
                        this.getParentController()
                            .getParentController()
                            .addTab(TabPaneEntry.LENDING, this.getParentController()).get();

                    LendingController lendingController =
                        (LendingController) controller;

                    if (currentMediumType != null) {
                        switch (currentMediumType) {
                            case DVD:
                                lendingController.setCurrentMedium(currentDvd);
                                break;
                            case BOOK:
                                lendingController.setCurrentMedium(currentBook);
                                break;
                            case GAME:
                                lendingController.setCurrentMedium(currentGame);
                                break;
                            default:
                                LOG.error("no medium type");
                        }
                        lendingController.setCurrentUuid(dto.getId());
                    }

                    return dto;
                }
            )
        );
        this.columnExtend.setCellFactory(
            ButtonTableCell.forTableColumn(
                "Extend",
                (MediumCopyDto dto) -> {
                    LOG.debug("Extend button has been pressed");

                    if (dto.isAvailable()) {
                        AlertHelper.showAlert(
                            Alert.AlertType.ERROR,
                            this.detailsPane.getScene().getWindow(),
                            "Medium is available",
                            "Cannot extend a medium that is not lent out"
                        );
                        return dto;
                    }

                    // change view
                    Parentable<?> controller =
                        this.getParentController()
                            .getParentController()
                            .addTab(TabPaneEntry.EXTEND_LENDING, this.getParentController()).get();

                    ExtendLendingController extendLendingController =
                        (ExtendLendingController) controller;

                    if (null != currentMediumType) {
                        switch (currentMediumType) {
                            case DVD:
                                extendLendingController.setCurrentMedium(currentDvd);
                                extendLendingController.setCurrentMediumCopy(dto);
                                break;
                            case BOOK:
                                extendLendingController.setCurrentMedium(currentBook);
                                extendLendingController.setCurrentMediumCopy(dto);
                                break;
                            case GAME:
                                extendLendingController.setCurrentMedium(currentGame);
                                extendLendingController.setCurrentMediumCopy(dto);
                                break;
                            default:
                                LOG.error("No medium type available.");
                        }
                        extendLendingController.setCurrentUuid(dto.getId());
                    }
                    return dto;
                }
            )
        );
        this.columnReturn.setCellFactory(
            ButtonTableCell.forTableColumn(
                "Return",
                (MediumCopyDto dto) -> {
                    LOG.debug("Return button has been pressed");

                    if (dto.isAvailable()) {
                        AlertHelper.showAlert(
                            Alert.AlertType.ERROR,
                            this.detailsPane.getScene().getWindow(),
                            "Medium is available",
                            "Cannot return a medium that has not been lent out"
                        );
                        return dto;
                    }

                    // change view
                    Parentable<?> controller =
                        this.getParentController()
                            .getParentController()
                            .addTab(TabPaneEntry.RETURNING, this.getParentController()).get();

                    ReturningController returningController = (ReturningController) controller;

                    if (null != currentMediumType) {
                        switch (currentMediumType) {
                            case DVD:
                                returningController.setCurrentMedium(currentDvd);
                                returningController.setCurrentMediumCopy(dto);
                                break;
                            case BOOK:
                                returningController.setCurrentMedium(currentBook);
                                returningController.setCurrentMediumCopy(dto);
                                break;
                            case GAME:
                                returningController.setCurrentMedium(currentGame);
                                returningController.setCurrentMediumCopy(dto);
                                break;
                            default:
                                LOG.error("No medium type available.");
                        }
                        returningController.setCurrentUuid(dto.getId());
                    }

                    return dto;
                }
            )
        );

        this.tblResults.setRowFactory(row -> new TableRow<>() {
            @Override
            protected void updateItem(MediumCopyDto item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setStyle("");
                } else {
                    if (getTableView().getSelectionModel().getSelectedItems().contains(item)) {
                        setStyle("");
                    } else if (item.isAvailable() == true) {
                        setStyle("-fx-background-color: lightseagreen");
                    } else if (item.isAvailable() == false) {
                        setStyle("-fx-background-color: lightcoral");
                    }
                }
            }
        });

        this.columnRemoveReservation.setCellFactory(
            ButtonTableCell.forTableColumn(
                "Remove",
                (ReservationDto dto) -> {
                    LOG.debug("Remove reservation button has been pressed");
                    try {
                        MessageDto<EmptyDto> msgDto = RmiClient.getInstance()
                            .removeReservation(dto);

                        if (msgDto.getType().equals(MessageDto.MessageType.SUCCESS)) {
                            AlertHelper.showAlert(
                                Alert.AlertType.INFORMATION,
                                this.detailsPane.getScene().getWindow(),
                                "Reservation removed successfully",
                                msgDto.getMessage()
                            );
                            this.updateView();
                        } else {
                            AlertHelper.showAlert(
                                Alert.AlertType.ERROR,
                                this.detailsPane.getScene().getWindow(),
                                "Could not remove reservation",
                                msgDto.getMessage()
                            );
                        }

                    } catch (RemoteException e) {
                        AlertHelper.showAlert(
                            Alert.AlertType.ERROR,
                            this.detailsPane.getScene().getWindow(),
                            "Could not remove reservation",
                            "An error occurred while trying to remove the reservation."
                        );
                        e.printStackTrace();
                    }

                    return dto;
                }
            )
        );
    }

    private void addMediaTypeEventHandlers() {
        this.btnReserve.setOnAction(e -> {
            System.out.println("Search button pressed");
            // change view
            Parentable<?> controller = this.getParentController().getParentController().addTab(
                TabPaneEntry.RESERVATION,
                this
            ).get();
            ReservationController reservationController =
                (ReservationController) controller;
            if (this.currentMediumType.equals(MediumType.BOOK)) {
                System.out.println("Reserve book");

                reservationController.setCurrentBook(currentBook);
            } else if (this.currentMediumType.equals(MediumType.DVD)) {
                System.out.println("Reserve DVD");
                reservationController.setCurrentDvd(currentDvd);
            } else if (this.currentMediumType.equals(MediumType.GAME)) {
                System.out.println("Reserve Game");
                reservationController.setCurrentGame(currentGame);
            }
        });

        this.btnBack.setOnAction(e -> {
            System.out.println("Back button pressed");
            this.parentController.getParentController().removeTab(TabPaneEntry.MEDIA_DETAIL);
            this.parentController.getParentController().selectTab(TabPaneEntry.SEARCH);
        });

        this.columnLendTill.setCellValueFactory(tc -> {
            // p.getValue() returns the Person instance for a particular TableView row
            return new SimpleStringProperty(
                tc.getValue().getLendTill() != null ? tc.getValue().getLendTill().toString() : ""
            );
        });
    }

    /**
     * Internally sets the current MediumType and loads medium details for given UUID.
     *
     * @param type MediumType to load
     * @param uuid UUID of Medium to load
     */
    public void setCurrentMediumType(MediumType type, UUID uuid) {
        currentMediumType = type;
        this.enableFieldsForMediumType(type);
        this.currentUuid = uuid;
        switch (type) {
            case BOOK:
                this.loadCurrentBook();
                break;
            case DVD:
                this.loadCurrentDvd();
                break;
            case GAME:
                this.loadCurrentGame();
                break;
            default:
                break;
        }

        // Disable Actions for non-librarian and admin
        UserRoleName role = this.getParentController() // SearchController
            .getParentController() // TabPaneController
            .getParentController() // MainController
            .getUserRole();
        boolean isReadOnly = MainController.isReadOnly(role);
        if (isReadOnly) {
            LOG.debug("Disabling columns lend, return, extend, button reserve,"
                + " reservation table because current user role is {}", role);
            this.columnLend.setVisible(false);
            this.columnReturn.setVisible(false);
            this.columnExtend.setVisible(false);
            this.btnReserve.setVisible(false);
            this.tblReservations.setVisible(false);
            this.vboxReservations.setVisible(false);
        }
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

    private void bindBookProperties(
        String author,
        String isbn10,
        String isbn13,
        String publisher,
        String language
    ) {
        this.txtBookAuthor.textProperty().bind(new SimpleStringProperty(author));
        this.txtBookIsbn10.textProperty().bind(new SimpleStringProperty(isbn10));
        this.txtBookIsbn13.textProperty().bind(new SimpleStringProperty(isbn13));
        this.txtBookPublisher.textProperty().bind(new SimpleStringProperty(publisher));
        this.txtBookLanguage.textProperty().bind(new SimpleStringProperty(language));
    }

    private void bindDvdProperties(
        String director,
        String duration,
        String actors,
        String studio,
        String ageRestriction
    ) {
        this.txtDvdDirector.textProperty().bind(new SimpleStringProperty(director));
        this.txtDvdDuration.textProperty().bind(new SimpleStringProperty(duration));
        this.txtDvdActors.textProperty().bind(new SimpleStringProperty(actors));
        this.txtDvdStudio.textProperty().bind(new SimpleStringProperty(studio));
        this.txtDvdAgeRestriction.textProperty().bind(new SimpleStringProperty(ageRestriction));
    }

    private void bindGameProperties(
        String publisher,
        String developer,
        String ageRestriction,
        String platforms
    ) {
        this.txtGamePublisher.textProperty().bind(new SimpleStringProperty(publisher));
        this.txtGameDeveloper.textProperty().bind(new SimpleStringProperty(developer));
        this.txtGameAgeRestriction.textProperty().bind(new SimpleStringProperty(ageRestriction));
        this.txtGamePlatforms.textProperty().bind(new SimpleStringProperty(platforms));
    }

    private void loadCurrentBook() {
        this.currentMediumType = MediumType.BOOK;
        BookDetailTask task = new BookDetailTask(
            new BookDto.BookDtoBuilder(this.currentUuid).build(),
            this.detailsPane
        );

        Thread thread = new Thread(task, "Book Details Task");
        task.setOnSucceeded(event -> {
            System.out.println("Book Details loaded");
            this.currentBook = task.getValue();
            System.out.println("####");
            System.out.println(this.currentBook.getStorageLocation());

            this.bindGenericProperties(
                this.currentBook.getTitle(),
                this.currentBook.getStorageLocation(),
                this.currentBook.getTopic(),
                this.currentBook.getReleaseDate()
            );

            this.bindBookProperties(
                this.currentBook.getAuthor(),
                this.currentBook.getIsbn10(),
                this.currentBook.getIsbn13(),
                this.currentBook.getPublisher(),
                this.currentBook.getLanguageKey()
            );

            BookMediumCopyTask task2 = new BookMediumCopyTask(
                new BookDto.BookDtoBuilder(this.currentUuid).build(),
                this.detailsPane);
            Thread thread2 = new Thread(task2, "Book Medium Copy Task");
            task2.setOnSucceeded(evt -> {
                List<MediumCopyDto> result = task2.getValue();
                this.tblResults.setItems(FXCollections.observableList(result));
                this.handleReserveButtonVisibility(result);
                for (MediumCopyDto dto : result) {
                    System.out.println(dto.getId().toString());
                }
                // Get Reservations
                this.loadReservations(MediumType.BOOK);
            });
            thread2.start();
        });
        thread.start();

    }

    private void loadReservations(MediumType type) {
        UserRoleName role = this.parentController.getParentController().getParentController()
                .getUserRole();
        if (MainController.isReadOnly(role)) {
            LOG.debug("Won't load reservations because role is {}", role);
            return;
        }
        List<ReservationDto> res = new LinkedList<>();

        try {
            switch (type) {
                case BOOK:
                    res = RmiClient.getInstance().getAllBookReservations(this.currentBook);
                    break;
                case DVD:
                    res = RmiClient.getInstance().getAllDvdReservations(this.currentDvd);
                    break;
                case GAME:
                    res = RmiClient.getInstance().getAllGameReservations(this.currentGame);
                    break;
                default:
                    break;
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        this.tblReservations.setItems(FXCollections.observableList(res));
    }

    private void handleReserveButtonVisibility(List<MediumCopyDto> copies) {
        boolean isAvailable = true;
        if (MainController.isReadOnly(
            this.parentController.getParentController().getParentController().getUserRole())
        ) {
            isAvailable = false;
        } else {
            for (MediumCopyDto dto : copies) {
                // if a copy is available, disable button
                if (dto.getLendTill() == null) {
                    isAvailable = false;
                    break;
                }
            }
        }


        this.btnReserve.setVisible(isAvailable);
    }

    public DvdDto getCurrentDvd() {
        return currentDvd;
    }

    private void loadCurrentDvd() {
        this.currentMediumType = MediumType.DVD;
        DvdDetailTask task = new DvdDetailTask(
            new DvdDto.DvdDtoBuilder(this.currentUuid).build(),
            this.detailsPane
        );

        Thread thread = new Thread(task, "DVD Details Task");
        task.setOnSucceeded(event -> {
            System.out.println("Dvd Details loaded");
            this.currentDvd = task.getValue();
            System.out.println(this.currentDvd.getStorageLocation());
            this.bindGenericProperties(
                this.currentDvd.getTitle(),
                this.currentDvd.getStorageLocation(),
                this.currentDvd.getTopic(),
                this.currentDvd.getReleaseDate()
            );

            this.bindDvdProperties(
                this.currentDvd.getDirector(),
                this.currentDvd.getDurationMinutes(),
                this.currentDvd.getActors(),
                this.currentDvd.getStudio(),
                this.currentDvd.getAgeRestriction()
            );

            DvdMediumCopyTask task2 = new DvdMediumCopyTask(
                new DvdDto.DvdDtoBuilder(this.currentUuid).build(),
                this.detailsPane);
            Thread thread2 = new Thread(task2, "DVD Medium Copy Task");
            task2.setOnSucceeded(evt -> {
                List<MediumCopyDto> result = task2.getValue();
                this.tblResults.setItems(FXCollections.observableList(result));
                this.handleReserveButtonVisibility(result);
                for (MediumCopyDto dto : result) {
                    System.out.println(dto.getId().toString());
                }

                // Get Reservations
                this.loadReservations(MediumType.DVD);
            });
            thread2.start();
        });
        thread.start();
    }

    public GameDto getCurrentGame() {
        return currentGame;
    }

    private void loadCurrentGame() {
        this.currentMediumType = MediumType.GAME;
        GameDetailTask task = new GameDetailTask(
            new GameDto.GameDtoBuilder(this.currentUuid).build(),
            this.detailsPane
        );

        Thread thread = new Thread(task, "Game Details Task");
        task.setOnSucceeded(event -> {
            System.out.println("Game Details loaded");
            this.currentGame = task.getValue();
            System.out.println(this.currentGame.getStorageLocation());
            this.bindGenericProperties(
                this.currentGame.getTitle(),
                this.currentGame.getStorageLocation(),
                this.currentGame.getTopic(),
                this.currentGame.getReleaseDate()
            );

            this.bindGameProperties(
                this.currentGame.getPublisher(),
                this.currentGame.getDeveloper(),
                this.currentGame.getAgeRestriction(),
                this.currentGame.getPlatforms()
            );

            GameMediumCopyTask task2 = new GameMediumCopyTask(
                new GameDto.GameDtoBuilder(this.currentUuid).build(),
                this.detailsPane);
            Thread thread2 = new Thread(task2, "Game Medium Copy Task");
            task2.setOnSucceeded(evt -> {
                List<MediumCopyDto> result = task2.getValue();
                this.tblResults.setItems(FXCollections.observableList(result));
                this.handleReserveButtonVisibility(result);
                for (MediumCopyDto dto : result) {
                    System.out.println(dto.getId().toString());
                }

                // Get Reservations
                this.loadReservations(MediumType.GAME);
            });
            thread2.start();
        });
        thread.start();
    }

    /**
     * updates the current medium.
     */
    public void updateView() {
        switch (this.currentMediumType) {
            case BOOK:
                this.loadCurrentBook();
                break;
            case DVD:
                this.loadCurrentDvd();
                break;
            case GAME:
                this.loadCurrentGame();
                break;
            default:
                LOG.error("no medium type");
        }
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
