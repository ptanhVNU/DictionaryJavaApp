package data;

import jdk.nashorn.internal.runtime.regexp.joni.ast.StringNode;

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
      data.addDetail(null, explain, null, null);
      wordList.add(data);
      addNode(data);
    }
  }

  /** insert From File. */
  public void insertFromFile(String file) {
    String dictionaryFilePath = "src/main/resources/data/" + file;
    try {
      BufferedReader reader = new BufferedReader(new FileReader(dictionaryFilePath));
      String line;
      while ((line = reader.readLine()) != null) {
        String[] words = line.split("\t");
        Word word = new Word();
        word.setWord(words[0]);
        word.addDetail(null, words[words.length - 1], null, null);
        wordList.add(word);
        addNode(word);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /** dictionary Lookup. */
  public Word dictionaryLookup(String lookUp) {
    Word result = new Word();
    result = searchWord(lookUp);
    return result;
  }

  /**
   * dictionary Export To File. TODO: flexiable file export
   * String pathname
   * = "E:\\ProjectJava\\Dictionary\\src\\main\\resources\\data\\dictionaries.txt";
   */
  public void dictionaryExportToFile(String pathName) {
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

  /** dictionary Import from File */
  public ArrayList<Word> dictonaryImportFromFile(String pathName) {
    String dictionaryFilePath = pathName;
    ArrayList<Word> list = new ArrayList<>();
    try {
      BufferedReader reader = new BufferedReader(new FileReader(dictionaryFilePath));
      String line;
      while ((line = reader.readLine()) != null) {
        Word data = searchWord(line);
        list.add(data);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return list;
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

  /** delete Word Word */
  public String dictionaryDeleteWord(Word data) {
    String key = data.getWord();
    Trie delelte = findNode(key);
    if (delelte == null) {
      return "Not found key!";
    }
    deleteNode(delelte);
    return "Completed";
  }

  /** speak */
  public void speak(String text) {
    TextToSpeech speech = new TextToSpeech(text);
    speech.speakText();
  }

  public void speakWord_target(int index) {
    TextToSpeech speech = new TextToSpeech(wordList.get(index).getWord());
    speech.speakText();
  }

  public ArrayList<Word> dictionaryLookupPrefix(String key) {
    setResultsList(new ArrayList<>());
    String msg = searchPrefixWord(key);
    setWordList();
    return wordList;
  }

  public void dictionaryAddWord(Word data) {
    addNode(data);
  }
}
