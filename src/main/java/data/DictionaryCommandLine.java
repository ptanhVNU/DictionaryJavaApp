package data;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class DictionaryCommandLine extends DictionaryManagement {
  /**
   * scan String.
   *
   * @return inpString.
   */
  public String stringScanner() {
    Scanner scanner = new Scanner(System.in);
    return scanner.nextLine();
  }

  /** show all word current in dictionary. */
  public void dictionaryAdvanced() {
    setResultsList(new ArrayList<>());
    getAllWord();
    System.out.printf("%-7s| %-20s| %-50s\n", "No", "English", "Vietnamese");
    for (int i = 0; i < getResultsList().size(); i++) {
      System.out.printf(
          "%-7d| %-20s| %-50s\n",
          i,
          getResultsList().get(i).getWord(),
          getResultsList().get(i).details.get(0).getExplanations());
    }
  }

  /** insert from cmd. */
  public void dictionaryBasic() {
    insertFromCommandline();
  }

  /** search word with key. */
  public void dictionarySearcher() {
    System.out.print("Nhap tu ban muon tim kiem: ");
    String search = stringScanner();

    Word result = dictionaryLookup(search);
    System.out.println(result.showDetail());
  }

  /** delete word with key. */
  public void deleteWord() {
    System.out.println("Nhập từ cần xóa: ");
    String del = stringScanner();
    dictionaryDeleteString(del);
  }

  /** google translate api. */
  public void showTranslateText() {
    String text = stringScanner();
    System.out.println(TranslateAPI.googleTranslate("", "vi", text));
  }

  /** show list words have prefix equal key */
  public void showPrefix() {
    String key = stringScanner();
    ArrayList<Word> list = dictionaryLookupPrefix(key);
    System.out.printf("%-7s| %-20s| %-50s\n", "No", "English", "Vietnamese");
    for (int i = 0; i < list.size(); i++) {
      System.out.printf(
          "%-7d| %-20s| %-50s\n",
          i, list.get(i).getWord(), list.get(i).details.get(0).getExplanations());
    }
    if (list.size() == 0) {
      System.out.println("Not found");
    }
  }

  /**
   * speak text scan.
   *
   * @exception IOException if err speak
   */
  public void speak() {
    try {
      String text = stringScanner();
      TextToSpeech.speak(text);
    } catch (IOException exception) {
      exception.printStackTrace();
    }
  }

  /**
   * export to file
   */
  public void export() {
    dictionaryExportToFile("src\\main\\resources\\data\\bookmarks.txt");
  }

  /**
   * method test api
   */
  public void test() {
    try {
    Word word = dictionaryLookup("a");
    word.addDetail(dictionaryLookup("a b c").getDetails().get(0));
    System.out.println(word.showDetail());
    JDBCConnect.editDatabase(word, "edit");
    } catch (SQLException exception) {
      System.out.println("err");
    }
  }
}
