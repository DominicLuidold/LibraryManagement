package at.fhv.teamg.librarymanagement.client.controller;

import at.fhv.teamg.librarymanagement.client.controller.internal.BookSearchTask;
import at.fhv.teamg.librarymanagement.client.controller.internal.ButtonTableCell;
import at.fhv.teamg.librarymanagement.client.controller.internal.DvdSearchTask;
import at.fhv.teamg.librarymanagement.client.controller.internal.GameSearchTask;
import at.fhv.teamg.librarymanagement.client.controller.internal.Parentable;
import at.fhv.teamg.librarymanagement.shared.dto.BookDto;
import at.fhv.teamg.librarymanagement.shared.dto.DvdDto;
import at.fhv.teamg.librarymanagement.shared.dto.GameDto;
import com.jfoenix.controls.JFXDatePicker;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.UUID;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.AnchorPane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.controlsfx.control.SegmentedButton;

public class SearchController implements Initializable, Parentable<TabPaneController> {
    private static final Logger LOG = LogManager.getLogger(SearchController.class);

    private TabPaneController parentController;
    private ResourceBundle resourceBundle;

    @FXML
    private AnchorPane searchPane;
    @FXML
    private SegmentedButton mediaTypeBtn;
    @FXML
    private ToggleButton toggleBtnBook;
    @FXML
    private ToggleButton toggleBtnDvd;
    @FXML
    private ToggleButton toggleBtnGame;
    @FXML
    private TextField txtTitle;
    @FXML
    private TextField txtBookAuthor;
    @FXML
    private TextField txtDvdDirector;
    @FXML
    private TextField txtGameDeveloper;
    @FXML
    private TextField txtBookIsbn13;
    @FXML
    private JFXDatePicker txtDvdYearOfPublication;
    @FXML
    private TextField txtGamePlatform;
    @FXML
    private ComboBox txtBookTopic; // TODO use generics
    @FXML
    private ComboBox txtDvdTopic; // TODO use generics
    @FXML
    private ComboBox txtGameTopic; // TODO use generics
    @FXML
    private Button btnSearch;
    @FXML
    private Label lblMediaType;
    @FXML
    private Label lblTitle;
    @FXML
    private Label lblBookAuthor;
    @FXML
    private Label lblDvdDirector;
    @FXML
    private Label lblGameDeveloper;
    @FXML
    private Label lblBookIsbn13;
    @FXML
    private Label lblDvdYearOfPublication;
    @FXML
    private Label lblBookTopic;
    @FXML
    private Label lblDvdTopic;
    @FXML
    private Label lblGamePlatform;
    @FXML
    private Label lblGameTopic;
    @FXML
    private TableColumn<BookDto, Button> columnDetailsBook;
    @FXML
    private TableColumn<DvdDto, Button> columnDetailsDvd;
    @FXML
    private TableColumn<GameDto, Button> columnDetailsGame;
    @FXML // TODO use generics
    private TableView<BookDto> tblResultsBook;
    @FXML // TODO use generics
    private TableView<DvdDto> tblResultsDvd;
    @FXML // TODO use generics
    private TableView<GameDto> tblResultsGame;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.resourceBundle = resources;
        this.setCellFactories();
        this.addMediaTypeEventHandlers();
        LOG.debug("Initialized UserController");
    }

    private void setCellFactories() {
        this.columnDetailsBook.setCellFactory(
            ButtonTableCell.forTableColumn(
                "Details",
                (BookDto dto) -> {
                    LOG.debug("Book details button has been pressed");
                    // TODO change view
                    return dto;
                }
            )
        );
        this.columnDetailsDvd.setCellFactory(
            ButtonTableCell.forTableColumn(
                "Details",
                (DvdDto dto) -> {
                    LOG.debug("Dvd details button has been pressed");
                    // TODO change view
                    return dto;
                }
            )
        );
        this.columnDetailsGame.setCellFactory(
            ButtonTableCell.forTableColumn(
                "Details",
                (GameDto dto) -> {
                    LOG.debug("Game details button has been pressed");
                    // TODO change view
                    return dto;
                }
            )
        );
    }

    private void addMediaTypeEventHandlers() {
        this.toggleBtnBook.setOnAction(e -> {
            System.out.println("Show Search fields for: Book");
            this.enableSearchFieldsForMediumType(MediumType.BOOK);
        });
        this.toggleBtnDvd.setOnAction(e -> {
            System.out.println("Show Search fields for: DVD");
            this.enableSearchFieldsForMediumType(MediumType.DVD);
        });
        this.toggleBtnGame.setOnAction(e -> {
            System.out.println("Show Search fields for: Game");
            this.enableSearchFieldsForMediumType(MediumType.GAME);
        });

        this.btnSearch.setOnAction(e -> {
            System.out.println("Search button pressed");


            if (this.toggleBtnBook.isSelected()) {
                BookDto dto = new BookDto.BookDtoBuilder(UUID.randomUUID())
                    .title(this.txtTitle.getText())
                    .isbn13(this.txtBookIsbn13.getText())
                    // TODO add topic
                    .build();

                BookSearchTask task = new BookSearchTask(dto, this.searchPane);
                Thread thread = new Thread(task, "Search Book Task");
                task.setOnSucceeded(event -> {
                    List<BookDto> result = task.getValue();
                    this.tblResultsBook.setItems(FXCollections.observableList(result));
                });
                thread.start();
            } else if (this.toggleBtnDvd.isSelected()) {
                DvdDto dto = new DvdDto.DvdDtoBuilder(UUID.randomUUID())
                    .title(this.txtTitle.getText())
                    .releaseDate(this.txtDvdYearOfPublication.getValue())
                    // TODO add topic
                    .build();

                DvdSearchTask task = new DvdSearchTask(dto, this.searchPane);
                Thread thread = new Thread(task, "Search DVD Task");
                task.setOnSucceeded(event -> {
                    List<DvdDto> result = task.getValue();
                    this.tblResultsDvd.setItems(FXCollections.observableList(result));
                });
                thread.start();
            } else if (this.toggleBtnGame.isSelected()) {
                GameDto dto = new GameDto.GameDtoBuilder(UUID.randomUUID())
                    .title(this.txtTitle.getText())
                    .releaseDate(this.txtDvdYearOfPublication.getValue())
                    // TODO add topic
                    .build();

                GameSearchTask task = new GameSearchTask(dto, this.searchPane);
                Thread thread = new Thread(task, "Search Game Task");
                task.setOnSucceeded(event -> {
                    List<GameDto> result = task.getValue();
                    this.tblResultsGame.setItems(FXCollections.observableList(result));
                });
                thread.start();
            }

        });

        // Initially search for books
        this.toggleBtnBook.fire();
        this.toggleBtnBook.setSelected(true);
    }

    private void enableSearchFieldsForMediumType(MediumType type) {
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
        this.lblBookIsbn13.setVisible(visible);
        this.txtBookIsbn13.setVisible(visible);
        this.lblBookTopic.setVisible(visible);
        this.txtBookTopic.setVisible(visible);
        this.tblResultsBook.setVisible(visible);
    }

    private void handleDvdFields(boolean visible) {
        this.lblDvdDirector.setVisible(visible);
        this.txtDvdDirector.setVisible(visible);
        this.lblDvdYearOfPublication.setVisible(visible);
        this.txtDvdYearOfPublication.setVisible(visible);
        this.lblDvdTopic.setVisible(visible);
        this.txtDvdTopic.setVisible(visible);
        this.tblResultsDvd.setVisible(visible);
    }

    private void handleGameFields(boolean visible) {
        this.lblGameDeveloper.setVisible(visible);
        this.txtGameDeveloper.setVisible(visible);
        this.lblGamePlatform.setVisible(visible);
        this.txtGamePlatform.setVisible(visible);
        this.lblGameTopic.setVisible(visible);
        this.txtGameTopic.setVisible(visible);
        this.tblResultsGame.setVisible(visible);
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

    private enum MediumType {
        BOOK, DVD, GAME
    }

}
