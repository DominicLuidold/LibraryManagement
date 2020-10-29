package at.fhv.teamg.librarymanagement.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class MainGui extends Application {
    private static final Logger LOG = LogManager.getLogger(MainGui.class);

    @Override
    public void start(Stage stage) {
        try {
            Locale locale = new Locale("en", "UK");
            Locale.setDefault(locale);
            ResourceBundle bundle = ResourceBundle.getBundle("bundles.language", locale);
            Parent root = FXMLLoader.load(getClass().getResource("/view/login.fxml"), bundle);

            double minHeight = 748;
            double minWidth = 1244;
            Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
            stage.setX((screenBounds.getWidth() - minWidth) / 2);
            stage.setY((screenBounds.getHeight() - minHeight) / 2);

            Scene scene = new Scene(root);
            scene.getStylesheets().add("css/styles.css");
            stage.setTitle("LibraryManagement");
            stage.setScene(scene);
            stage.setMinHeight(minHeight);
            stage.setMinWidth(minWidth);
            stage.getIcons().add(new Image("images/icon.png"));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            LOG.error(e);
        }
    }

    public static void main() {
        launch();
    }
}
