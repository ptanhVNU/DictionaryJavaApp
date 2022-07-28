package data;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class DictionaryCommandLine extends DictionaryManagement {
  public String stringScanner() {
    Scanner scanner = new Scanner(System.in);
    String s = scanner.nextLine();
    return s;
  }

  public void showAllWords() {
    setResultsList(new ArrayList<>());
    getAllWord(root);
    setWordList();
    System.out.printf("%-7s| %-20s| %-50s\n", "No", "English", "Vietnamese");
    for (int i = 0; i < wordList.size(); i++) {
      System.out.printf(
          "%-7d| %-20s| %-50s\n",
          i, wordList.get(i).getWord(), wordList.get(i).details.get(0).getExplanations());
    }
  }

  public void dictionaryBasic() {
    insertFromCommandline();
  }

  public void dictionaryAdvanced() {
    showAllWords();
  }

  public void dictionarySearcher() {
    System.out.print("Nhap tu ban muon tim kiem: ");
    String search = stringScanner();

    Word result = new Word();
    result = dictionaryLookup(search);
    if (result == null) {
      System.out.println("Not found");
    } else {
      System.out.printf("%-7s| %-20s| %-50s\n", "No", "English", "Vietnamese");
      System.out.printf(
          "%-7d| %-20s| %-50s\n", 1, result.getWord(), result.details.get(0).getExplanations());
    }
  }

  public void deleteWord() {
    boolean check = false;
    System.out.println("Nhập từ cần xóa: ");
    String del = stringScanner();
    String msg = dictionaryDeleteString(del);
    System.out.println(msg);
  }

  public void showTranslateText() {
    String text = stringScanner();
    System.out.println(TranslateAPI.googleTranslate("", "vi", text));
  }

  public void showPrefix() {
    String key = stringScanner();
    ArrayList<Word> list = dictionaryLookupPrefix(key);
    System.out.printf("%-7s| %-20s| %-50s\n", "No", "English", "Vietnamese");
    for (int i = 0; i < wordList.size(); i++) {
      System.out.printf(
              "%-7d| %-20s| %-50s\n",
              i, wordList.get(i).getWord(), wordList.get(i).details.get(0).getExplanations());
    }
    if (wordList.size() == 0) {
      System.out.println("Not found");
    }
  }

  public void speak() {
    String text = stringScanner();
    speak(text);
  }
}
