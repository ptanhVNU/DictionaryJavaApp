package main;

import data.DictionaryManagement;
import data.Word;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.beans.EventHandler;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.control.ListView;
import javafx.scene.control.Label;
import javafx.scene.control.Button;

import jdk.nashorn.internal.objects.annotations.Setter;

public class SearchController implements Initializable {
    static final String searchWordDefault = "Nghĩa của từ";
    private String typeController;
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        searchDictionary = new DictionaryManagement();
        bookmarkDictionary = new DictionaryManagement();
        historyDictionary = new DictionaryManagement();

        for (int i = 0; i < 1000; ++i) {
            Word word = new Word();
            searchDictionary.addNode(new Word("" + i));
        }

        bookmarkDictionary.handleExport(
                DictionaryManagement.dictonaryImportFromFile("src\\main\\resources\\data\\bookmarks.txt"),
                searchDictionary);

        historyDictionary.handleExport(
                DictionaryManagement.dictonaryImportFromFile("src\\main\\resources\\data\\history.txt"),
                searchDictionary);

        setTypeController("search");
    }

    @FXML
    public void searchPressKeyBoard() { // khi nhập từ
        searchList.getItems().clear();
        String lookUp = searchField.getText();
        searchList.getItems().addAll(presentDictionary().dictionaryLookupPrefix(lookUp));
    }

    @FXML
    void choiceWordAction() {
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
    void bookmarkAction() {
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
    void deleteAction() {
        searchDictionary.dictionaryDeleteWord(searchList.getSelectionModel().getSelectedItem());
        bookmarkDictionary.dictionaryDeleteWord(searchList.getSelectionModel().getSelectedItem());
        historyDictionary.dictionaryDeleteWord(searchList.getSelectionModel().getSelectedItem());

        removeSelectedItemSearchList();
    }
}