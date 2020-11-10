package at.fhv.teamg.librarymanagement.client.controller.internal;

/**
 * A Tab Pane entry which is mainly needed for
 * {@link at.fhv.teamg.librarymanagement.client.controller.MainController}
 * to build the Tab Pane Header menu.
 *
 * @author Valentin Goronjic
 */
public enum TabPaneEntry {
    UNSUPPORTED(1, "Unsupported", "/view/unsupportedTab.fxml", false),
    SEARCH(1, "Search", "/view/search.fxml", false),
    MEDIA_DETAIL(1, "Media Details", "/view/mediaDetails.fxml", true),
    RESERVATION(1, "Reservation", "/view/reservation.fxml", true);

    private final int order;
    private final String title;
    private final String fxmlPath;
    private final boolean isTemporary;

    /**
     * Constructs a new TabPaneEntry.
     *
     * @param order       The order in which this FXML should be loaded (1 = first)
     * @param title       Title of Tab
     * @param fxmlPath    Path to FXML file including /view/ as prefix
     * @param isTemporary Whether this tab is just temporary (different color)
     */
    TabPaneEntry(int order, String title, String fxmlPath, boolean isTemporary) {
        this.order = order;
        this.title = title;
        this.fxmlPath = fxmlPath;
        this.isTemporary = isTemporary;
    }

    public int getOrder() {
        return this.order;
    }

    public String getTitle() {
        return this.title;
    }

    public String getFxmlPath() {
        return this.fxmlPath;
    }

    public boolean isTemporary() {
        return this.isTemporary;
    }

}
