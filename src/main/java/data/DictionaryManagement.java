package data;

import jdk.nashorn.internal.scripts.JD;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;

public class DictionaryManagement extends Dictionary {
  /**
   * get list of English word
   *
   * @param pathName path of file need to import
   * @return list
   * @exception IOException if not found file
   */
  public static ArrayList<String> dictionaryImportFromFile(String pathName) {
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

  /**
   * find Word in another dictionary to add to "this" dictionary
   *
   * @param list list of key word
   * @param another dictionary to search
   * @exception NullPointerException if not found key word in the dictionary
   */
  public void handleExport(ArrayList<String> list, DictionaryManagement another) {
    for (String key : list) {
      try {
        addNode(another.findNode(key).getWord());
      } catch (NullPointerException e) {
        e.printStackTrace();
      }
    }
  }

  /**
   * insert db.
   *
   * @exception SQLException sql err
   */
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

  /**
   * Dictionary export key word to file.
   *
   * @param pathName path for export
   * @exception IOException if not found file
   * @throws RuntimeException err
   */
  public void dictionaryExportToFile(String pathName) {
    setResultsList(new ArrayList<>());
    getAllWord();
    File file = new File(pathName);
    try {
      FileWriter myWriter = new FileWriter(file);
      for (int i = 0; i < getResultsList().size(); i++) {
        myWriter.write(getResultsList().get(i).getWord() + "\n");
      }
      myWriter.close();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * add word to dictionary
   *
   * @param data Word need to add
   * @exception NullPointerException if not found data
   * @exception SQLException if err SQL
   */
  public void dictionaryAddWord(Word data) {
    try {
      JDBCConnect.insertToDatabase(data);
      addNode(data);
    } catch (NullPointerException | SQLException exception) {
      exception.printStackTrace();
    }
  }

  /**
   * delete Word String
   *
   * @param key key word of Word want delete
   * @exception NullPointerException if not found key
   * @exception SQLException if err SQL
   */
  public void dictionaryDeleteString(String key) {
    try {
      Trie delete = findNode(key);
      JDBCConnect.editDatabase(delete.getWord(), "delete");
      deleteNode(delete);
    } catch (NullPointerException | SQLException exception) {
      exception.printStackTrace();
    }
    return;
  }

  /**
   * delete Word
   *
   * @param data Word want delete
   * @exception NullPointerException if not found key
   * @exception SQLException if err SQL
   */
  public void dictionaryDeleteWord(Word data) {
    try {
      System.out.println(data.getWord());
      JDBCConnect.editDatabase(data, "delete");
      String key = data.getWord();
      Trie delete = findNode(key);
      deleteNode(delete);
    } catch (NullPointerException | SQLException exception) {
      exception.printStackTrace();
    }
    return;
  }

  /**
   * lookup word with string lookUp
   *
   * @param lookUp key word of Word want delete
   * @exception NullPointerException if not found key
   */
  public Word dictionaryLookup(String lookUp) {
    Word result = null;
    try {
      result = findNode(lookUp).getWord();
    } catch (NullPointerException exception) {
      exception.printStackTrace();
    }
    return result;
  }

  /**
   * delete Word String
   *
   * @param key key word of Word want delete
   * @exception NullPointerException if not found key
   */
  public ArrayList<Word> dictionaryLookupPrefix(String key) {
    try {
      setResultsList(new ArrayList<>());
      searchPrefixWord(key);
    } catch (NullPointerException exception) {
      exception.printStackTrace();
    }
    return getResultsList();
  }

  public void dictionaryEditWord(Word word) {
    try {
      dictionaryLookup(word.getWord()).edit(word);
      JDBCConnect.editDatabase(word, "edit");
    } catch (SQLException exception) {
      exception.printStackTrace();
    }
  }
}
