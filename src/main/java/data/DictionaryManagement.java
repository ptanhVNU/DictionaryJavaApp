package data;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;

public class DictionaryManagement extends Dictionary {
  /** insert From Command line. */
  public void insertFromCommandline() {
    Scanner scanner = new Scanner(System.in);
    System.out.print("Nhap so luong tu ban muon them: ");
    int numsWord;
    numsWord = Integer.parseInt(scanner.nextLine());

    for (int i = 0; i < numsWord; i++) {
      System.out.print("English: ");
      String target = scanner.nextLine();
      if (target.equals("")) {
        System.out.println("No target");
        break;
      }
      System.out.print("Vietnamese: ");
      String explain = scanner.nextLine();
      if (explain.equals("")) {
        System.out.println("No explain");
        break;
      }
      Word data = new Word();
      data.setWord(target);
      data.addDetail(null, explain, null);
      wordList.add(data);
      addNode(data);
    }
  }

  /** insert From File. */
  public void importFromFile(String file) {
    String dictionaryFilePath = "src/main/resources/data/" + file;
    try {
      BufferedReader reader = new BufferedReader(new FileReader(dictionaryFilePath));
      String line;
      while ((line = reader.readLine()) != null) {
        String[] words = line.split("\t");
        Word word = new Word();
        word.setWord(words[0]);
        word.addDetail(null, words[words.length - 1], null);
        wordList.add(word);
        addNode(word);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /** dictionary Import from File. */
  public static ArrayList<String> dictonaryImportFromFile(String pathName) {
    String dictionaryFilePath = pathName;
    ArrayList<String> list = new ArrayList<>();
    try {
      BufferedReader reader = new BufferedReader(new FileReader(dictionaryFilePath));
      String line;
      while ((line = reader.readLine()) != null) {
        list.add(line);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return list;
  }

  /** find out on another Dictionary */
  public void handleExport(ArrayList<String> list, DictionaryManagement another) {
    for (String key : list) {
      try {
        dictionaryAddWord(another.findNode(key).getWord());
      } catch (NullPointerException e) {
        e.printStackTrace();
      }
    }
  }

  /** insert db. */
  public void dictionaryImportFromDatabase() {
    try {
      ArrayList<Word> list = JDBCConnect.importDatabase();
      for (Word word : list) {
        addNode(word);
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  /** dictionary Export To File. */
  public void dictionaryExportToFile(String pathName) {
    setResultsList(new ArrayList<>());
    getAllWord(root);
    setWordList();
    File file = new File(pathName);
    try {
      FileWriter myWriter = new FileWriter(file);
      for (int i = 0; i < wordList.size(); i++) {
        myWriter.write(wordList.get(i).getWord() + "\n");
      }
      myWriter.close();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  /** dictionary Export To Database. */
  public void dictionaryExportToDatabase(String pathName) {

  }

  /** add word to dictionary */
  public void dictionaryAddWord(Word data) {
    addNode(data);
  }

  /** delete Word String */
  public String dictionaryDeleteString(String key) {
    Trie delelte = findNode(key);
    if (delelte == null) {
      return "Not found key!";
    }
    deleteNode(delelte);
    return "Completed";
  }

  /** delete Word. */
  public String dictionaryDeleteWord(Word data) {
    String key = data.getWord();
    Trie delelte = findNode(key);
    if (delelte == null) {
      return "Not found key!";
    }
    deleteNode(delelte);
    return "Completed";
  }

  /** dictionary Lookup. */
  public Word dictionaryLookup(String lookUp) {
    Word result = new Word();
    result = findNode(lookUp).getWord();
    return result;
  }

  /** dictionary Lookup. */
  public ArrayList<Word> dictionaryLookupPrefix(String key) {
    setResultsList(new ArrayList<>());
    String msg = searchPrefixWord(key);
    setWordList();
    return wordList;
  }

}
