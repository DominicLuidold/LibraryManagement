package at.fhv.teamg.librarymanagement.client.controller;

import at.fhv.teamg.librarymanagement.client.controller.internal.ButtonTableCell;
import at.fhv.teamg.librarymanagement.client.controller.internal.Parentable;
import at.fhv.teamg.librarymanagement.client.controller.internal.TabPaneEntry;
import at.fhv.teamg.librarymanagement.client.controller.internal.media.details.BookDetailTask;
import at.fhv.teamg.librarymanagement.client.controller.internal.media.details.BookMediumCopyTask;
import at.fhv.teamg.librarymanagement.client.controller.internal.media.details.DvdDetailTask;
import at.fhv.teamg.librarymanagement.client.controller.internal.media.details.DvdMediumCopyTask;
import at.fhv.teamg.librarymanagement.client.controller.internal.media.details.GameDetailTask;
import at.fhv.teamg.librarymanagement.shared.dto.BookDto;
import at.fhv.teamg.librarymanagement.shared.dto.DvdDto;
import at.fhv.teamg.librarymanagement.shared.dto.GameDto;
import at.fhv.teamg.librarymanagement.shared.dto.MediumCopyDto;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.UUID;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
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
    private Button btnCancel;

    @FXML
    private TableColumn<MediumCopyDto, String> columnCopyId;
    @FXML
    private TableColumn<MediumCopyDto, Boolean> columnLendTill;
    @FXML
    private TableColumn<MediumCopyDto, Button> columnLend;
    @FXML
    private TableColumn<MediumCopyDto, Button> columnExtend;
    @FXML
    private TableColumn<MediumCopyDto, Button> columnReturn;
    @FXML // TODO use generics
    private TableView<MediumCopyDto> tblResults;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.resourceBundle = resources;
        this.setCellFactories();
        this.addMediaTypeEventHandlers();
        LOG.debug("Initialized UserController");
    }

    private void setCellFactories() {
        // TODO
        this.columnLend.setCellFactory(
            ButtonTableCell.forTableColumn(
                "Lend",
                (MediumCopyDto dto) -> {
                    LOG.debug("Book details button has been pressed");

                    // change view
                    Parentable<?> controller =
                        this.getParentController()
                            .getParentController()
                            .addTab(TabPaneEntry.UNSUPPORTED, this).get();

                    MediaDetailsController mediaDetailsController =
                        (MediaDetailsController) controller;
                    mediaDetailsController.setCurrentMediumType(MediumType.BOOK, dto.getId());

                    return dto;
                }
            )
        );
        this.columnExtend.setCellFactory(
            ButtonTableCell.forTableColumn(
                "Extend",
                (MediumCopyDto dto) -> {
                    LOG.debug("Book details button has been pressed");

                    // change view
                    Parentable<?> controller =
                        this.getParentController()
                            .getParentController()
                            .addTab(TabPaneEntry.UNSUPPORTED, this).get();

                    MediaDetailsController mediaDetailsController =
                        (MediaDetailsController) controller;
                    mediaDetailsController.setCurrentMediumType(MediumType.BOOK, dto.getId());

                    return dto;
                }
            )
        );
        this.columnReturn.setCellFactory(
            ButtonTableCell.forTableColumn(
                "Return",
                (MediumCopyDto dto) -> {
                    LOG.debug("Book details button has been pressed");

                    // change view
                    Parentable<?> controller =
                        this.getParentController()
                            .getParentController()
                            .addTab(TabPaneEntry.UNSUPPORTED, this).get();

                    MediaDetailsController mediaDetailsController =
                        (MediaDetailsController) controller;
                    mediaDetailsController.setCurrentMediumType(MediumType.BOOK, dto.getId());

                    return dto;
                }
            )
        );
    }

    private void addMediaTypeEventHandlers() {
        this.btnReserve.setOnAction(e -> {
            System.out.println("Search button pressed");
            // change view
            Parentable<?> controller =
                    this.getParentController().getParentController().addTab(TabPaneEntry.RESERVATION,
                            this).get();
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

        this.btnCancel.setOnAction(e -> {
            System.out.println("Cancel button pressed");
            this.parentController.getParentController().removeTab(TabPaneEntry.MEDIA_DETAIL);
            this.parentController.getParentController().selectTab(TabPaneEntry.SEARCH);
        });

        this.columnLendTill.setCellValueFactory(tc -> {
            // p.getValue() returns the Person instance for a particular TableView row
            return new SimpleBooleanProperty(tc.getValue().isAvailable());
        });
    }

    /**
     * Internally sets the current MediumType and loads medium details for given UUID.
     *
     * @param type MediumType to load
     * @param uuid UUID of Medium to load
     */
    public void setCurrentMediumType(MediumType type, UUID uuid) {
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
                this.currentBook.getTopic().toString(),
                this.currentBook.getReleaseDate() != null
                    ? this.currentBook.getReleaseDate().toString()
                    : ""
            );
        });
        thread.start();

        BookMediumCopyTask task2 = new BookMediumCopyTask(
            new BookDto.BookDtoBuilder(this.currentUuid).build(),
            this.detailsPane);
        thread = new Thread(task2, "Book Medium Copy Task");
        task2.setOnSucceeded(event -> {
            List<MediumCopyDto> result = task2.getValue();
            this.tblResults.setItems(FXCollections.observableList(result));
            for (MediumCopyDto dto : result) {
                System.out.println(dto.getId().toString());
            }
        });
        thread.start();
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
                this.currentDvd.getTopic().toString(),
                this.currentDvd.getReleaseDate() != null
                    ? this.currentDvd.getReleaseDate().toString()
                    : ""
            );
        });
        thread.start();

        DvdMediumCopyTask task2 = new DvdMediumCopyTask(
            new DvdDto.DvdDtoBuilder(this.currentUuid).build(),
            this.detailsPane);
        thread = new Thread(task2, "DVD Medium Copy Task");
        task2.setOnSucceeded(event -> {
            List<MediumCopyDto> result = task2.getValue();
            this.tblResults.setItems(FXCollections.observableList(result));
            for (MediumCopyDto dto : result) {
                System.out.println(dto.getId().toString());
            }
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
                this.currentGame.getTopic().toString(),
                this.currentGame.getReleaseDate() != null
                    ? this.currentGame.getReleaseDate().toString()
                    : ""
            );
        });
        thread.start();
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
