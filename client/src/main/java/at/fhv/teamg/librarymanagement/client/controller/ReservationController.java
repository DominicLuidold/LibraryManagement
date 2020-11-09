package at.fhv.teamg.librarymanagement.client.controller;

import at.fhv.teamg.librarymanagement.client.controller.internal.Parentable;
import at.fhv.teamg.librarymanagement.client.controller.internal.TabPaneEntry;
import at.fhv.teamg.librarymanagement.client.rmi.RmiClient;
import at.fhv.teamg.librarymanagement.shared.dto.BookDto;
import at.fhv.teamg.librarymanagement.shared.dto.DvdDto;
import at.fhv.teamg.librarymanagement.shared.dto.GameDto;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;
import org.w3c.dom.Text;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.*;

public class ReservationController implements Initializable, Parentable<TabPaneController> {
    private static final Logger LOG = LogManager.getLogger(ReservationController.class);

    private TabPaneController parentController;
    private ResourceBundle resourceBundle;

    //TODO getAllUsers of DB
    private String[] allUsers = {"Anton", "Antonia", "Abraham", "Bertram", "Berta"};

    private BookDto currentBook = null;
    private DvdDto currentDvd = null;
    private GameDto currentGame = null;
    private MediumType type = MediumType.DVD;


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
        this.enableLablesForMediumType(type);
        this.addMediaTypeEventHandlers();
        TextFields.bindAutoCompletion(txtUser, allUsers);
        LOG.debug("Initialized ReservationController");
    }

    private void addMediaTypeEventHandlers(){
        this.btnOK.setOnAction(e -> {
            if(this.type.equals(MediumType.BOOK)){
                try {
                    RmiClient.getInstance().reserveBook(currentBook);
                } catch (RemoteException ex) {
                    ex.printStackTrace();
                }
            }else if(this.type.equals(MediumType.DVD)){
                try {
                    RmiClient.getInstance().reserveDvd(currentDvd);
                } catch (RemoteException ex) {
                    ex.printStackTrace();
                }
            }else if(this.type.equals(MediumType.GAME)){
                try {
                    RmiClient.getInstance().reserveGame(currentGame);
                } catch (RemoteException ex) {
                    ex.printStackTrace();
                }
            }
        });

        /*this.btnCancel.setOnAction(e -> {
            System.out.println("Cancel button pressed");
            //TODO add reservation to TabPaneEntry
            this.parentController.getParentController().removeTab(TabPaneEntry.RESERVATION);
            this.parentController.getParentController().selectTab(TabPaneEntry.MEDIA_DETAIL);
        });*/
    }

    public void setCurrentBook(BookDto currentBook) {
        this.currentBook = currentBook;
        this.type = MediumType.BOOK;
    }

    public void setCurrentDvd(DvdDto currentDvd) {
        this.currentDvd = currentDvd;
        this.type = MediumType.DVD;
    }

    public void setCurrentGame(GameDto currentGame) {
        this.currentGame = currentGame;
        this.type = MediumType.GAME;
    }

    private void enableLablesForMediumType(MediumType type) {
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

    private void handleBookFields(boolean visible){
        System.out.println("all books set to "+ visible);
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
        LOG.debug("Initialized ReservationController with parent");
    }

}

