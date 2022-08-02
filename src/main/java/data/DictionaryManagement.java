package data;

import jdk.nashorn.internal.runtime.regexp.joni.ast.StringNode;

import javax.speech.Central;
import javax.speech.EngineException;
import javax.speech.synthesis.Synthesizer;
import javax.speech.synthesis.SynthesizerModeDesc;
import java.util.ArrayList;
import java.util.Locale;
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

  /** dictionary Import from File */
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
    ArrayList<Word> words;
    for (String key : list) {
      dictionaryAddWord(another.searchWord(key));
    }
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
  public void speak(String text) throws IOException {
    TextToSpeech.speak(text);
  }

  public void speakWord_target(int index) throws IOException {
      TextToSpeech.speak(wordList.get(index).getWord());
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
