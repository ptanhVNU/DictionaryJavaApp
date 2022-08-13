package main;

import data.Word;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

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

  public void convertTreeViewToWord() {
//    editWord.getDetails().clear();
//
//    for (int i = 0; i < treeView.getRoot().getChildren().size(); ++i) {
//      TreeItem<String> Detail = (TreeItem<String>) treeView.getRoot().getChildren().get(i);
//      editWord.addDetail(
//          Detail.getChildren().get(0).getValue().substring(12),
//          Detail.getChildren().get(1).getValue().substring(15),
//          new ArrayList<String>());
//
//      for (int j = 0;
//          j < ((TreeItem<String>) Detail.getChildren().get(2)).getChildren().size();
//          ++j) {
//        editWord
//            .getDetails()
//            .get(i)
//            .getUsages()
//            .add(
//                ((TreeItem<String>) Detail.getChildren().get(2))
//                    .getChildren()
//                    .get(j)
//                    .getValue()
//                    .substring(1));
//      }
//    }
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
        return "Word Type";
      } else if (selectedItem.getValue().substring(0, 3).equals("Exp")) {
        return "Explanations";
      } else {
        return "Usages";
      }
    } else {
      return "-";
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
      buttonHBox.getChildren().add(button1);
    } else if (getTypeSeclectItem(selectedItem) == "Word Type") {
      button1.setText("Edit");
      buttonHBox.getChildren().add(button1);
    } else if (getTypeSeclectItem(selectedItem) == "Explanations") {
      button1.setText("Edit");
      buttonHBox.getChildren().add(button1);
    } else if (getTypeSeclectItem(selectedItem) == "Usages") {
      button1.setText("Add Usage");
      button2.setText("Clear");
      buttonHBox.getChildren().add(button1);
      buttonHBox.getChildren().add(button2);
    } else {
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
      TreeItem<String> newItem = new TreeItem<>("Detail : ");
      newItem.getChildren().add(new TreeItem<String>("Word Type : "));
      newItem.getChildren().add(new TreeItem<String>("Explanations : "));
      newItem.getChildren().add(new TreeItem<String>("Usages : none"));

      selectedItem.getChildren().add(newItem);
      treeView.getSelectionModel().clearAndSelect(treeView.getRow(newItem));
      selectAction();
    } else if (getTypeSeclectItem(selectedItem) == "Detail") {
      selectedItem.getParent().getChildren().remove(selectedItem);
      treeView.getSelectionModel().clearSelection();
    } else if (getTypeSeclectItem(selectedItem) == "Word Type") {
      editField.setText(selectedItem.getValue().substring(12));
      editHBox.getChildren().add(editField);
      editHBox.getChildren().add(saveButton);
    } else if (getTypeSeclectItem(selectedItem) == "Explanations") {
      editField.setText(selectedItem.getValue().substring(15));
      editHBox.getChildren().add(editField);
      editHBox.getChildren().add(saveButton);
    } else if (getTypeSeclectItem(selectedItem) == "Usages") {
      TreeItem<String> newItem = new TreeItem<>("");
      selectedItem.getChildren().add(newItem);
      treeView.getSelectionModel().clearAndSelect(treeView.getRow(newItem));
      selectAction();
    } else {
      editField.setText(selectedItem.getValue());
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
      return;
    } else if (getTypeSeclectItem(selectedItem) == "Word Type") {
      return;
    } else if (getTypeSeclectItem(selectedItem) == "Explanations") {
      return;
    } else if (getTypeSeclectItem(selectedItem) == "Usages") {
      selectedItem.getChildren().clear();
    } else {
      selectedItem.getParent().getChildren().remove(selectedItem);
    }
  }

  @FXML
  public void saveButtonAction() {
    TreeItem<String> selectedItem =
        (TreeItem<String>) treeView.getSelectionModel().getSelectedItem();
    selectedItem.setValue(getTypeSeclectItem(selectedItem) + " : " + editField.getText());
  }
}
