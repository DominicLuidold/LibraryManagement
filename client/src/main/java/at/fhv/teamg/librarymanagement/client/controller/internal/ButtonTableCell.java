package at.fhv.teamg.librarymanagement.client.controller.internal;

import java.util.function.Function;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

/**
 * Button that can be used in a TableColumn's cell factory.
 *
 * @author Valentin Goronjic
 */
public class ButtonTableCell<T> extends TableCell<T, Button> {

    private final Button actionButton;

    /**
     * Constructs a new ButtonTableCell with a label and a function that is called on press.
     */
    public ButtonTableCell(String label, Function<T, T> function) {
        this.getStyleClass().add("action-button-table-cell");

        this.actionButton = new Button(label);
        this.actionButton.setOnAction((ActionEvent e) -> function.apply(getCurrentItem()));
        this.actionButton.setMaxWidth(Double.MAX_VALUE);
    }

    public T getCurrentItem() {
        return getTableView().getItems().get(getIndex());
    }

    public static <T> Callback<TableColumn<T, Button>, TableCell<T, Button>> forTableColumn(
        String label, Function<T, T> function) {
        return param -> new ButtonTableCell<>(label, function);
    }

    @Override
    public void updateItem(Button item, boolean empty) {
        super.updateItem(item, empty);

        if (empty) {
            setGraphic(null);
        } else {
            setGraphic(actionButton);
        }
    }
}