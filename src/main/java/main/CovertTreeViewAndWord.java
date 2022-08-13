package main;

import data.Word;
import javafx.scene.control.TreeItem;
import javafx.util.Pair;

import java.util.ArrayList;

public class CovertTreeViewAndWord {
    public static TreeItem<String> covertWordToTreeItem(Word word) {
        TreeItem<String> treeItem = new TreeItem<>(word.getWord());

        for (int i = 0; i < word.getDetails().size(); ++i) {
            TreeItem<String> childItem = covertDetailToTreeItem(word.getDetails().get(i));
            treeItem.getChildren().add(childItem);
        }

        return treeItem;
    }

    public static TreeItem<String> covertDetailToTreeItem(Word.Detail detail) {
        TreeItem<String> treeItem = new TreeItem<>("Detail : ");

        treeItem.getChildren().add(new TreeItem<String>("Word type : " + detail.getWord_type()));

        for (int i = 0; i < detail.getExplanations().size(); ++i) {
            TreeItem<String> childItem = covertExplanationToTreeItem(detail.getExplanations().get(i));
            treeItem.getChildren().add(childItem);
        }

        return treeItem;
    }

    /**
     * covert Explanation(Pair<String, ArrayList<String>>) to Tree Item
     * @return Tree Item
     */
    public static TreeItem<String> covertExplanationToTreeItem(Pair<String, ArrayList<String>> explanation) {
        TreeItem<String> treeItem = new TreeItem<>("Explanation : " + explanation.getKey());

        for (int i = 0; i < explanation.getValue().size(); ++i) {
            treeItem.getChildren().add(new TreeItem<String>("Usage : " + explanation.getValue().get(i)));
        }

        return treeItem;
    }
}
