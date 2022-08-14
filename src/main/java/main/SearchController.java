package main;

import data.DictionaryManagement;
import data.Word;
import data.TextToSpeech;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;

import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import jdk.nashorn.internal.objects.annotations.Setter;

public class SearchController implements Initializable {
  static final String searchWordDefault = "Meaning";

  static final String searchNotFound = "Not found";
  private String typeController;

  @FXML private static AnchorPane pane;
  @FXML private TextField searchField;
  @FXML private WebView webView;
  @FXML private ListView<Word> searchList;
  @FXML private Label searchWord;
  @FXML private Button bookmarkButton;

  @FXML private Button editButton;

  @FXML private Button deleteButton;

  @FXML private Button speakButton;

  public void disableButton() {
    bookmarkButton.setDisable(true);
    deleteButton.setDisable(true);
    speakButton.setDisable(true);
  }

  public void ableButton() {
    bookmarkButton.setDisable(false);
    deleteButton.setDisable(false);
    speakButton.setDisable(false);
  }

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
    disableButton();
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
    webView.getEngine().loadContent("");
    searchPressKeyBoard();
  }

  public void removeSelectedItemSearchList() {
    searchList.getItems().remove(searchList.getSelectionModel().getSelectedItem());
    searchList.getSelectionModel().clearSelection();
    searchWord.setText(searchWordDefault);
    disableButton();
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    searchField.setOnKeyPressed(
        event -> {
          if (presentDictionary() != searchDictionary) {
            return;
          }

          if (event.getCode() == KeyCode.ENTER) {
            if (presentDictionary().dictionaryLookupPrefix(searchField.getText()).isEmpty()) {
              searchWord.setText(
                  searchNotFound + " '" + standardizationString(searchField.getText()) + "'");
            }
          }
        });

    searchDictionary = new DictionaryManagement();
    bookmarkDictionary = new DictionaryManagement();
    historyDictionary = new DictionaryManagement();

    searchDictionary.dictionaryImportFromDatabase();

    bookmarkDictionary.handleExport(
        DictionaryManagement.dictionaryImportFromFile("src\\main\\resources\\data\\bookmarks.txt"),
        searchDictionary);

    bookmarkDictionary.getAllWord();
    for (int i = 0; i < bookmarkDictionary.getResultsList().size(); ++i) {
      bookmarkDictionary.getResultsList().get(i).setBookmark(true);
    }

    historyDictionary.handleExport(
        DictionaryManagement.dictionaryImportFromFile("src\\main\\resources\\data\\history.txt"),
        searchDictionary);

    setTypeController("search");
  }

  String standardizationString(String s) {
    while (s.indexOf("  ") != -1) {
      s = s.replaceFirst("  ", " ");
    }

    if (!s.isEmpty() && s.charAt(0) == ' ') {
      s = s.substring(1);
    }

    if (!s.isEmpty() && s.charAt(s.length() - 1) == ' ') {
      s = s.substring(0, s.length() - 1);
    }

    return s;
  }

  @FXML
  public void searchPressKeyBoard() { // khi nhập từ
    searchList.getItems().clear();
    String lookUp = standardizationString(searchField.getText());
    searchList.getItems().addAll(presentDictionary().dictionaryLookupPrefix(lookUp));
  }

  @FXML
  public void choiceWordAction() {
    if (searchList.getSelectionModel().getSelectedItem() == null) {
      bookmarkButton.getStyleClass().removeAll("active");
      return;
    }

    ableButton();

    webView
        .getEngine()
        .loadContent(searchList.getSelectionModel().getSelectedItem().getHtmlText(), "text/html");

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
      bookmarkDictionary.removeNode(searchList.getSelectionModel().getSelectedItem());

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
    webView.getEngine().loadContent("");
    if (presentDictionary() == historyDictionary) {
      historyDictionary.removeNode(searchList.getSelectionModel().getSelectedItem());
    } else {
      searchDictionary.dictionaryDeleteWord(searchList.getSelectionModel().getSelectedItem());
      bookmarkDictionary.removeNode(searchList.getSelectionModel().getSelectedItem());
      historyDictionary.removeNode(searchList.getSelectionModel().getSelectedItem());
    }

    removeSelectedItemSearchList();
  }

  @FXML
  public void editAction() throws Exception {
    if (presentDictionary() != searchDictionary
        || searchWord.getText().equals(searchWordDefault)
        || EditWordController.getInstance().isRunning()) {
      return;
    }

    String lookup = searchWord.getText();

    if (searchWord.getText().indexOf(searchNotFound) != -1) {
      lookup = lookup.substring(searchNotFound.length() + 2, lookup.length() - 1);
      searchDictionary.dictionaryAddWord(new Word(lookup));
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
              searchDictionary.dictionaryEditWord(
                  EditWordController.getInstance().convertTreeViewToWord());
            }

            EditWordController.getInstance().setRunning(false);
          }
        });

    EditWordController.setInstance(loader.getController());
    EditWordController.getInstance().setEditWord(searchDictionary.dictionaryLookup(lookup));
    EditWordController.getInstance().setRunning(true);

    editStage.show();
  }
}
