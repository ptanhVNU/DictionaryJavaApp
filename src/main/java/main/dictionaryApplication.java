package main;

import data.DictionaryManagement;

import data.TextToSpeech;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class dictionaryApplication extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        TextToSpeech.settingsImportToFile();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/Main.fxml")));
        primaryStage.setTitle("Dictionary");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        TextToSpeech.settingsExportToFile();
        SearchController.getInstance().getBookmarkDictionary().dictionaryExportToFile("src\\main\\resources\\data\\bookmarks.txt");
        SearchController.getInstance().getHistoryDictionary().dictionaryExportToFile("src\\main\\resources\\data\\history.txt");
    }
}