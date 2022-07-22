package main;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    @FXML
    private AnchorPane mainContent;
    @FXML
    private AnchorPane searchPane;
    @FXML
    private AnchorPane translatePane;
    @FXML
    private AnchorPane settingPane;

    @FXML
    private SearchController searchController;
    @FXML
    private TranslateController translateController;
    @FXML
    private SettingController settingController;

    @FXML
    public void showSearchPane() {
        mainContent.getChildren().setAll(searchPane);
    }
    @FXML
    public void showTranslatePane() {
        mainContent.getChildren().setAll(translatePane);
    }
    @FXML
    public void showBookmarkPane() { mainContent.getChildren().setAll(searchPane); }
    @FXML
    public void showHistoryPane() { mainContent.getChildren().setAll(searchPane); }
    @FXML
    public void showSettingPane() { mainContent.getChildren().setAll(settingPane); }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Search.fxml"));
            searchPane = loader.load();
            searchController = loader.getController();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Translate.fxml"));
            translatePane = loader.load();
            translateController = loader.getController();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Setting.fxml"));
            settingPane = loader.load();
            settingController = loader.getController();
        } catch (Exception e) {
            e.printStackTrace();
        }
        mainContent.getChildren().setAll(searchPane);
    }
}
