package main.java.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;

/**
 * Controller which renders tabs.
 *
 * @author Bohdan Inhliziian (xinhli00)
 * @author Konetzný Jakub (xinhli00)
 */
public class MainController {
    @FXML
    private TabPane tabs;

    /**
     * New tab-button
     */
    @FXML
    private Tab addNewTab;

    @FXML
    private Tab firstTab;

    public void initialize() throws Exception
    {
        this.firstTab.setContent(getNewTabContent(firstTab));

        final Tooltip newTabTooltip = new Tooltip("Create new Game");
        this.addNewTab.setTooltip(newTabTooltip);
    }

    /**
     * Adds new tab with initial tab content with main menu after click on the new tab button
     *
     * @throws Exception
     */
    public void addNewTab() throws Exception
    {
        if (this.addNewTab.isSelected()) {
            int tabCount = this.tabs.getTabs().size();

            Tab newTab = new Tab("Chess " + tabCount);
            newTab.setContent(getNewTabContent(newTab));

            this.tabs.getTabs().add(tabCount - 1, newTab);
            this.tabs.getSelectionModel().select(newTab);
        }
    }

    /**
     * Loads main menu content for the tabs.
     *
     * @return
     * @throws Exception
     */
    private Node getNewTabContent(Tab tab) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/main/resources/views/tab_content_start_page.fxml"));
        fxmlLoader.setController(new StartPageController(this, tab));

        if (fxmlLoader.getLocation() == null) {
            throw new Exception("Error to load main menu content!\n");
        }

        return fxmlLoader.load();
    }

    /**
     * Close tab
     *
     * @param tab Tab to be closed
     */
    public void closeTab(Tab tab) {
        this.tabs.getTabs()
                .stream()
                .filter(tab::equals)
                .findAny().ifPresent(foundTab -> this.tabs.getTabs().remove(foundTab));
    }
}
