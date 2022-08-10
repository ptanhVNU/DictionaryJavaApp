package main;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.sql.PreparedStatement;
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
    private TranslateController translateController;
    @FXML
    private SettingController settingController;

    @FXML
    private Button searchButton;
    @FXML
    private Button translateButton;
    @FXML
    private Button bookmarkButton;
    @FXML
    private Button historyButton;
    @FXML
    private Button settingButton;

    public void inactiveAllButton() {
        searchButton.getStyleClass().removeAll("active");
        translateButton.getStyleClass().removeAll("active");
        bookmarkButton.getStyleClass().removeAll("active");
        historyButton.getStyleClass().removeAll("active");
        settingButton.getStyleClass().removeAll("active");
    }

    @FXML
    public void showSearchPane() {
        mainContent.getChildren().setAll(searchPane);
        inactiveAllButton();
        searchButton.getStyleClass().add("active");
        SearchController.getInstance().setTypeController("search");
    }
    @FXML
    public void showTranslatePane() {
        mainContent.getChildren().setAll(translatePane);
        inactiveAllButton();
        translateButton.getStyleClass().add("active");
    }
    @FXML
    public void showBookmarkPane() {
        mainContent.getChildren().setAll(searchPane);
        inactiveAllButton();
        bookmarkButton.getStyleClass().add("active");
        SearchController.getInstance().setTypeController("bookmark");
    }
    @FXML
    public void showHistoryPane() {
        mainContent.getChildren().setAll(searchPane);
        inactiveAllButton();
        historyButton.getStyleClass().add("active");
        SearchController.getInstance().setTypeController("history");
    }
    @FXML
    public void showSettingPane() {
        mainContent.getChildren().setAll(settingPane);
        inactiveAllButton();
        settingButton.getStyleClass().add("active");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Search.fxml"));
            searchPane = loader.load();
            SearchController.setInstance(loader.getController());
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

        searchButton.getStyleClass().add("active");
        mainContent.getChildren().setAll(searchPane);
    }
}