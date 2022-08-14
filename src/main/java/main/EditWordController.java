package main;

import data.Word;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.util.Pair;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class EditWordController implements Initializable {
  boolean running;
  private Word editWord;
  private static AnchorPane pane;
  private static EditWordController instance;

  @FXML private TreeView treeView;

  @FXML private TextField editField;

  @FXML private Button button1;

  @FXML private Button button2;

  @FXML private Button button3;

  @FXML private Button saveButton;

  @FXML private HBox buttonHBox;

  @FXML private HBox editHBox;

  public static EditWordController getInstance() {
    if (instance == null) {
      EditWordController.instance = new EditWordController();
    }

    return EditWordController.instance;
  }

  public static void setInstance(EditWordController instance) {
    EditWordController.instance = instance;
  }

  public static AnchorPane getPane() {
    return EditWordController.pane;
  }

  public static void setPane(AnchorPane pane) {
    EditWordController.pane = pane;
  }

  public boolean isRunning() {
    return running;
  }

  public void setRunning(boolean running) {
    this.running = running;
  }

  public Word getEditWord() {
    return editWord;
  }

  public void setEditWord(Word editWord) {
    this.editWord = editWord;
    convertWordToTreeView(editWord);
  }

  public void convertWordToTreeView(Word word) {
    TreeItem<String> rootItem = CovertTreeViewAndWord.covertWordToTreeItem(word);
    treeView.setRoot(rootItem);
  }

  public Word convertTreeViewToWord() {
    return CovertTreeViewAndWord.covertTreeItemToWord(treeView.getRoot());
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    buttonHBox.getChildren().clear();
    editHBox.getChildren().clear();
  }

  public String getTypeSeclectItem(TreeItem<String> selectedItem) {
    if (selectedItem == null) {
      return "null";
    } else if (selectedItem == treeView.getRoot()) {
      return "";
    } else if (selectedItem.getParent() == treeView.getRoot()) {
      return "Detail";
    } else if (selectedItem.getParent().getParent() == treeView.getRoot()) {
      if (selectedItem.getValue().substring(0, 3).equals("Wor")) {
        return "Word type";
      } else {
        return "Explanation";
      }
    } else {
      return "Usage";
    }
  }

  @FXML
  public void selectAction() {
    buttonHBox.getChildren().clear();
    editHBox.getChildren().clear();

    TreeItem selectedItem = (TreeItem<String>) treeView.getSelectionModel().getSelectedItem();
    if (getTypeSeclectItem(selectedItem) == "null") {
      return;
    } else if (getTypeSeclectItem(selectedItem) == "") {
      button1.setText("Add Detail");
      buttonHBox.getChildren().add(button1);
    } else if (getTypeSeclectItem(selectedItem) == "Detail") {
      button1.setText("Delete");
      button2.setText("Add Explanation");
      buttonHBox.getChildren().add(button1);
      buttonHBox.getChildren().add(button2);
    } else if (getTypeSeclectItem(selectedItem) == "Word type") {
      button1.setText("Edit");
      buttonHBox.getChildren().add(button1);
    } else if (getTypeSeclectItem(selectedItem) == "Explanation") {
      button1.setText("Edit");
      button2.setText("Delete");
      button3.setText("Add Usage");
      buttonHBox.getChildren().add(button1);
      buttonHBox.getChildren().add(button2);
      buttonHBox.getChildren().add(button3);
    } else if (getTypeSeclectItem(selectedItem) == "Usage") {
      button1.setText("Edit");
      button2.setText("Delete");
      buttonHBox.getChildren().add(button1);
      buttonHBox.getChildren().add(button2);
    }
  }

  @FXML
  public void button1Action() {
    TreeItem<String> selectedItem =
        (TreeItem<String>) treeView.getSelectionModel().getSelectedItem();

    if (getTypeSeclectItem(selectedItem) == "null") {
      return;
    } else if (getTypeSeclectItem(selectedItem) == "") {
      TreeItem<String> newItem = CovertTreeViewAndWord.covertDetailToTreeItem(new Word.Detail());
      selectedItem.getChildren().add(newItem);
      treeView.getSelectionModel().clearAndSelect(treeView.getRow(newItem));
      selectAction();
    } else if (getTypeSeclectItem(selectedItem) == "Detail") {
      selectedItem.getParent().getChildren().remove(selectedItem);
      treeView.getSelectionModel().clearSelection();
    } else if (getTypeSeclectItem(selectedItem) == "Word type") {
      if (editHBox.getChildren().contains(editField)) {
        return;
      }
      editField.setText(selectedItem.getValue().substring(12));
      editHBox.getChildren().add(editField);
      editHBox.getChildren().add(saveButton);
    } else if (getTypeSeclectItem(selectedItem) == "Explanation") {
      if (editHBox.getChildren().contains(editField)) {
        return;
      }
      editField.setText(selectedItem.getValue().substring(14));
      editHBox.getChildren().add(editField);
      editHBox.getChildren().add(saveButton);
    } else if (getTypeSeclectItem(selectedItem) == "Usage") {
      if (editHBox.getChildren().contains(editField)) {
        return;
      }
      editField.setText(selectedItem.getValue().substring(8));
      editHBox.getChildren().add(editField);
      editHBox.getChildren().add(saveButton);
    }
  }

  @FXML
  public void button2Action() {
    TreeItem<String> selectedItem =
        (TreeItem<String>) treeView.getSelectionModel().getSelectedItem();

    if (getTypeSeclectItem(selectedItem) == "null") {
      return;
    } else if (getTypeSeclectItem(selectedItem) == "") {
      return;
    } else if (getTypeSeclectItem(selectedItem) == "Detail") {
      TreeItem<String> newItem =
          CovertTreeViewAndWord.covertExplanationToTreeItem(
              new Pair<String, ArrayList<String>>("", new ArrayList<String>()));
      selectedItem.getChildren().add(newItem);
      treeView.getSelectionModel().clearAndSelect(treeView.getRow(newItem));
      selectAction();
    } else if (getTypeSeclectItem(selectedItem) == "Word type") {
      return;
    } else if (getTypeSeclectItem(selectedItem) == "Explanation") {
      selectedItem.getParent().getChildren().remove(selectedItem);
      treeView.getSelectionModel().clearSelection();
    } else if (getTypeSeclectItem(selectedItem) == "Usage") {
      selectedItem.getParent().getChildren().remove(selectedItem);
      treeView.getSelectionModel().clearSelection();
    }
  }

  @FXML
  public void button3Action() {
    TreeItem<String> selectedItem =
        (TreeItem<String>) treeView.getSelectionModel().getSelectedItem();

    if (getTypeSeclectItem(selectedItem) == "null") {
      return;
    } else if (getTypeSeclectItem(selectedItem) == "") {
      return;
    } else if (getTypeSeclectItem(selectedItem) == "Detail") {
      return;
    } else if (getTypeSeclectItem(selectedItem) == "Word type") {
      return;
    } else if (getTypeSeclectItem(selectedItem) == "Explanation") {
      TreeItem<String> newItem = new TreeItem<>("Usages : ");
      selectedItem.getChildren().add(newItem);
      treeView.getSelectionModel().clearAndSelect(treeView.getRow(newItem));
      selectAction();
    } else if (getTypeSeclectItem(selectedItem) == "Usage") {
      return;
    }
  }

  @FXML
  public void saveButtonAction() {
    TreeItem<String> selectedItem =
        (TreeItem<String>) treeView.getSelectionModel().getSelectedItem();
    selectedItem.setValue(getTypeSeclectItem(selectedItem) + " : " + editField.getText());
    editHBox.getChildren().clear();
  }
}
