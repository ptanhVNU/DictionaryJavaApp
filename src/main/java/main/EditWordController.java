package main;

import data.Word;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class EditWordController implements Initializable {
    private Word editWord;
    private static AnchorPane pane;
    private static EditWordController instance;

    @FXML
    private TreeView treeView;

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

    public Word getEditWord() {
        return editWord;
    }

    public void setEditWord(Word editWord) {
        this.editWord = editWord;
        convertWordToTreeView(editWord);
    }

    public void convertWordToTreeView(Word word) {
        TreeItem<String> rootItem = new TreeItem<>(word.getWord());
        treeView.setRoot(rootItem);

        for (int i = 0; i < word.getDetails().size(); ++i) {
            TreeItem<String> treeItem = new TreeItem<String>("Details : " + (i + 1));
            treeItem.getChildren().add(new TreeItem<String>("Word Type : " + word.getDetails().get(i).getWord_type()));
            treeItem.getChildren().add(new TreeItem<String>("Explanations : " + word.getDetails().get(i).getExplanations()));

            if (word.getDetails().get(i).getUsages().isEmpty()) {
                treeItem.getChildren().add(new TreeItem<String>("Usages : none"));
            } else {
                TreeItem<String> usagesItem = new TreeItem<String>("Usages :");

                for (int j = 0; j < word.getDetails().get(i).getUsages().size(); ++j) {
                    usagesItem.getChildren().add(new TreeItem<String>(word.getDetails().get(i).getUsages().get(j)));
                }

                treeItem.getChildren().add(usagesItem);
            }

            rootItem.getChildren().add(treeItem);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
}
