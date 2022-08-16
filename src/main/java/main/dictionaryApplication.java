package main;

import data.TextToSpeech;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.Objects;

public class dictionaryApplication extends Application {
  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    TextToSpeech.settingsImportToFile();
    Parent root =
        FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/Main.fxml")));
    primaryStage.getIcons().add(new Image("graphics/image/icon_app.png"));
    primaryStage.setTitle("Dictionary");
    primaryStage.setScene(new Scene(root, 900, 600));

    primaryStage.show();

    primaryStage.setMaxWidth(primaryStage.getWidth());
    primaryStage.setMinWidth(primaryStage.getWidth());
    primaryStage.setMaxHeight(primaryStage.getHeight());
    primaryStage.setMinHeight(primaryStage.getHeight());

    primaryStage.show();
  }

  @Override
  public void stop() throws Exception {
    TextToSpeech.settingsExportToFile();
    SearchController.getInstance()
        .getBookmarkDictionary()
        .dictionaryExportToFile("src\\main\\resources\\data\\bookmarks.txt");
    SearchController.getInstance()
        .getHistoryDictionary()
        .dictionaryExportToFile("src\\main\\resources\\data\\history.txt");
  }
}
