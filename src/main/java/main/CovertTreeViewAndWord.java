package main;

import data.Word;
import javafx.scene.control.TreeItem;
import javafx.util.Pair;

import java.util.ArrayList;

public class CovertTreeViewAndWord {
  public static TreeItem<String> covertWordToTreeItem(Word word) {
    TreeItem<String> treeItem = new TreeItem<>(word.getWord());

    if (word.getDetails().isEmpty()) {
      treeItem.getChildren().add(covertDetailToTreeItem(new Word.Detail()));
    } else {
      for (int i = 0; i < word.getDetails().size(); ++i) {
        TreeItem<String> childItem = covertDetailToTreeItem(word.getDetails().get(i));
        treeItem.getChildren().add(childItem);
      }
    }

    return treeItem;
  }

  public static TreeItem<String> covertDetailToTreeItem(Word.Detail detail) {
    TreeItem<String> treeItem = new TreeItem<>("Detail : ");

    treeItem.getChildren().add(new TreeItem<String>("Word type : " + detail.getWord_type()));

    if (detail.getExplanations().isEmpty()) {
      treeItem.getChildren().add(covertExplanationToTreeItem(new Pair<String, ArrayList<String>>("", new ArrayList<>())));
    } else {
      for (int i = 0; i < detail.getExplanations().size(); ++i) {
        TreeItem<String> childItem = covertExplanationToTreeItem(detail.getExplanations().get(i));
        treeItem.getChildren().add(childItem);
      }
    }

    return treeItem;
  }

  /**
   * covert Explanation(Pair<String, ArrayList<String>>) to Tree Item
   *
   * @return Tree Item
   */
  public static TreeItem<String> covertExplanationToTreeItem(
      Pair<String, ArrayList<String>> explanation) {
    TreeItem<String> treeItem = new TreeItem<>("Explanation : " + explanation.getKey());

    for (int i = 0; i < explanation.getValue().size(); ++i) {
      treeItem.getChildren().add(new TreeItem<String>("Usage : " + explanation.getValue().get(i)));
    }

    return treeItem;
  }

  public static Word covertTreeItemToWord(TreeItem<String> treeItem) {
    Word word = new Word(treeItem.getValue());

    for (int i = 0; i < treeItem.getChildren().size(); ++i) {
      word.getDetails().add(covertTreeItemToDetail(treeItem.getChildren().get(i)));
    }

    return word;
  }

  public static Word.Detail covertTreeItemToDetail(TreeItem<String> treeItem) {
    Word.Detail detail = new Word.Detail();
    detail.setWord_type(treeItem.getChildren().get(0).getValue().substring(12));

    for (int i = 1; i < treeItem.getChildren().size(); ++i) {
      detail.getExplanations().add(covertTreeItemToExplanation(treeItem.getChildren().get(i)));
    }

    return detail;
  }

  public static Pair<String, ArrayList<String>> covertTreeItemToExplanation(
      TreeItem<String> treeItem) {
    Pair<String, ArrayList<String>> expalnation =
        new Pair<>(treeItem.getValue().substring(14), new ArrayList<String>());

    for (int i = 0; i < treeItem.getChildren().size(); ++i) {
      expalnation.getValue().add(treeItem.getChildren().get(i).getValue().substring(8));
    }

    return expalnation;
  }
}
