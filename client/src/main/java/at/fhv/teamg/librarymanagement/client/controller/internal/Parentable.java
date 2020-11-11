package at.fhv.teamg.librarymanagement.client.controller.internal;

/**
 * Interface which allows a GUI controller to access it's parent controller.
 *
 * @author Valentin Goronjic
 */
public interface Parentable<T> {

    /**
     * Sets this controller's parent controller.
     * @param controller The controller to be set as parent
     */
    void setParentController(T controller);

    /**
     * Returns this controller's parent controller.
     *
     * @return Parent controller
     */
    T getParentController();

    /**
     * Calls the controller initialization AFTER the parent controller has been set by
     * {@link at.fhv.teamg.librarymanagement.client.controller.TabPaneController}.
     */
    void initializeWithParent();
}
