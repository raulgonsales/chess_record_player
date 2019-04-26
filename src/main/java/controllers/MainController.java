package main.java.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;

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
        this.firstTab.setContent(getNewTabContent());

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

            Tab newTab = new Tab("Chess");
            newTab.setContent(getNewTabContent());

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
    private Node getNewTabContent() throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../../resources/views/tab_content.fxml"));

        if (fxmlLoader.getLocation() == null) {
            throw new Exception("Error to load main menu content!\n");
        }

        return fxmlLoader.load();
    }
}
