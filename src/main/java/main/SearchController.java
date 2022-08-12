package main;

import data.DictionaryManagement;
import data.Word;
import data.TextToSpeech;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import jdk.nashorn.internal.objects.annotations.Setter;

public class SearchController implements Initializable {
  static final String searchWordDefault = "Meaning";

  static final String searchNotFound = "Not Found";
  private String typeController;

  @FXML private static AnchorPane pane;
  @FXML private TextField searchField; // phần trong thanh tìm kiếm
  @FXML private ListView<Word> searchList; // danh sách khi nhập từ
  @FXML private Label searchWord; // hiển thị từ được tra

  @FXML private Button bookmarkButton;

  private DictionaryManagement searchDictionary;
  private DictionaryManagement bookmarkDictionary;
  private DictionaryManagement historyDictionary;

  public DictionaryManagement getBookmarkDictionary() {
    return bookmarkDictionary;
  }

  public DictionaryManagement getHistoryDictionary() {
    return historyDictionary;
  }

  public String getTypeController() {
    return typeController;
  }

  private static SearchController instance;

  public static SearchController getInstance() {
    if (instance == null) {
      SearchController.instance = new SearchController();
    }

    return SearchController.instance;
  }

  public static void setInstance(SearchController instance) {
    SearchController.instance = instance;
  }

  public static AnchorPane getPane() {
    return SearchController.pane;
  }

  public static void setPane(AnchorPane pane) {
    SearchController.pane = pane;
  }

  @Setter
  public void setTypeController(String typeController) {
    this.typeController = typeController;
    reset();
  }

  DictionaryManagement presentDictionary() {
    if (getTypeController().equals("search")) {
      return searchDictionary;
    } else if (getTypeController().equals("bookmark")) {
      return bookmarkDictionary;
    } else if (getTypeController().equals("history")) {
      return historyDictionary;
    } else {
      return null;
    }
  }

  public void reset() {
    searchWord.setText(searchWordDefault);
    searchField.setText("");
    searchList.getItems().clear();
    searchPressKeyBoard();
  }

  public void removeSelectedItemSearchList() {
    searchList.getItems().remove(searchList.getSelectionModel().getSelectedItem());
    searchList.getSelectionModel().clearSelection();
    searchWord.setText(searchWordDefault);
  }

  public void initializeEditWordPane() {
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/EditWord.fxml"));
      EditWordController.setPane(loader.load());
      EditWordController.setInstance(loader.getController());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    searchDictionary = new DictionaryManagement();
    bookmarkDictionary = new DictionaryManagement();
    historyDictionary = new DictionaryManagement();

    searchDictionary.dictionaryImportFromDatabase();

    bookmarkDictionary.handleExport(
        DictionaryManagement.dictonaryImportFromFile("src\\main\\resources\\data\\bookmarks.txt"),
        searchDictionary);

    historyDictionary.handleExport(
        DictionaryManagement.dictonaryImportFromFile("src\\main\\resources\\data\\history.txt"),
        searchDictionary);

    setTypeController("search");

    //  initializeEditWordPane();
  }

  @FXML
  public void searchPressKeyBoard() { // khi nhập từ
    searchList.getItems().clear();
    String lookUp = searchField.getText();
    searchList.getItems().addAll(presentDictionary().dictionaryLookupPrefix(lookUp));
  }

  @FXML
  public void choiceWordAction() {
    System.out.println(
        "Word:           |" + searchList.getSelectionModel().getSelectedItem().getWord());
    System.out.println(
        "Pronounciation: |" + searchList.getSelectionModel().getSelectedItem().getPronunciation());
    for (int i = 0; i < searchList.getSelectionModel().getSelectedItem().getDetails().size(); ++i) {
      System.out.println(
          "Word_type:      |"
              + searchList
                  .getSelectionModel()
                  .getSelectedItem()
                  .getDetails()
                  .get(i)
                  .getWord_type());
      System.out.println(
          "Explanations:   |"
              + searchList
                  .getSelectionModel()
                  .getSelectedItem()
                  .getDetails()
                  .get(i)
                  .getExplanations());
      for (int j = 0;
          j
              < searchList
                  .getSelectionModel()
                  .getSelectedItem()
                  .getDetails()
                  .get(i)
                  .getUsages()
                  .size();
          ++j) {
        System.out.println(
            "Usages:         |"
                + searchList
                    .getSelectionModel()
                    .getSelectedItem()
                    .getDetails()
                    .get(i)
                    .getUsages()
                    .get(j));
      }
    }

    System.out.println("");

    if (searchList.getSelectionModel().getSelectedItem() == null) {
      bookmarkButton.getStyleClass().removeAll("active");
      return;
    }

    searchWord.setText(searchList.getSelectionModel().getSelectedItem().getWord());

    if (searchList.getSelectionModel().getSelectedItem().isBookmark()) {
      bookmarkButton.getStyleClass().add("active");
    } else {
      bookmarkButton.getStyleClass().removeAll("active");
    }

    if (typeController.equals("search")) {
      historyDictionary.addNode(searchList.getSelectionModel().getSelectedItem());
    } else if (typeController.equals("bookmark")) {
      System.out.println(searchList.getSelectionModel().getSelectedItem().isBookmark());
    }
  }

  @FXML
  public void speakAction() {
    if (!searchWord.getText().equals(searchWordDefault)) {
      try {
        TextToSpeech.speak(searchWord.getText());
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  @FXML
  public void bookmarkAction() {
    if (searchList.getSelectionModel().getSelectedItem() == null) {
      return;
    }

    if (searchList.getSelectionModel().getSelectedItem().isBookmark()) {
      searchList.getSelectionModel().getSelectedItem().setBookmark(false);
      bookmarkDictionary.dictionaryDeleteWord(searchList.getSelectionModel().getSelectedItem());

      bookmarkButton.getStyleClass().removeAll("active");

      if (typeController == "bookmark") {
        removeSelectedItemSearchList();
      }
    } else {
      searchList.getSelectionModel().getSelectedItem().setBookmark(true);
      bookmarkDictionary.addNode(searchList.getSelectionModel().getSelectedItem());

      bookmarkButton.getStyleClass().add("active");
    }
  }

  @FXML
  public void deleteAction() {
    searchDictionary.dictionaryDeleteWord(searchList.getSelectionModel().getSelectedItem());
    bookmarkDictionary.dictionaryDeleteWord(searchList.getSelectionModel().getSelectedItem());
    historyDictionary.dictionaryDeleteWord(searchList.getSelectionModel().getSelectedItem());

    removeSelectedItemSearchList();
  }

  @FXML
  public void editAction() throws Exception {
    if (presentDictionary() != searchDictionary
        || searchWord.getText().equals(searchWordDefault)
        || EditWordController.getInstance().isRunning()) {
      return;
    }

    if (searchDictionary.findNode(searchWord.getText()) == null) {
      searchDictionary.dictionaryAddWord(new Word(searchField.getText()));
    }

    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/EditWord.fxml"));
    Parent root = loader.load();
    Stage editStage = new Stage();
    editStage.setScene(new Scene(root, 600, 600));
    editStage.setTitle("Edit Word");

    editStage.setOnCloseRequest(
        new EventHandler<WindowEvent>() {
          @Override
          public void handle(WindowEvent event) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("");
            alert.setHeaderText(null);
            alert.setContentText("Do you want to save");

            if (alert.showAndWait().get() == ButtonType.OK) {
              EditWordController.getInstance().convertTreeViewToWord();
              EditWordController.getInstance().setRunning(false);
              editStage.close();
            }
          }
        });

    editStage.show();

    EditWordController.setInstance(loader.getController());
    EditWordController.getInstance()
        .setEditWord(searchDictionary.dictionaryLookup(searchWord.getText()));
    EditWordController.getInstance().setRunning(true);
  }
}
