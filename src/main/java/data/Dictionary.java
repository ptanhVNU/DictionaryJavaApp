package data;

import com.sun.org.apache.xerces.internal.util.SynchronizedSymbolTable;

import java.util.ArrayList;

public class Dictionary {
  protected ArrayList<Word> wordList = new ArrayList<>();

  public static class Trie {
    private ArrayList<Trie> children = new ArrayList<>();

    private char character;

    private Word word;

    /** Contrucstor */
    public Trie() {}

    /** Contrucstor */
    public Trie(char character) {
      this.character = character;
      word = null;
    }

    /** Contrucstor */
    public Trie(char character, Word word) {
      this.character = character;
      this.word = word;
    }

    /** get-set-er */
    public ArrayList<Trie> getChildren() {
      return children;
    }

    public char getCharacter() {
      return character;
    }

    public Word getWord() {
      return word;
    }

    public void setChildren(ArrayList<Trie> chirld) {

      this.children = chirld;
    }

    public void setCharacter(char character) {
      this.character = character;
    }

    public void setWord(Word word) {
      this.word = word;
    }

    /** Find out Node has a character of key */
    public Trie isExistInChildren(char character) {
      if (this.children == null) {
        return null;
      }
      for (Trie child : this.children) {
        if (child.getCharacter() == character) {
          return child;
        }
      }
      return null;
    }

    /** Add Node if this character is not exist */
    public void addChild(char character) {
      if (children == null) {
        return;
      }
      children.add(new Trie(character));
    }
  }

  public Trie root = new Trie();

  private ArrayList<Word> resultsList = new ArrayList<>();

  public void setResultsList(ArrayList<Word> resultsList) {
    this.resultsList = resultsList;
  }

  public ArrayList<Word> getResultsList() {
    return resultsList;
  }

  /** Adding a new word to dictionary. */
  public void addNode(Word key) {
    if (key == null) {
      return;
    }

    String key_word = key.getWord();
    Trie pointer = root;

    for (int i = 0; i < key_word.length(); ++i) {
      char character = key_word.charAt(i);
      if (pointer.isExistInChildren(character) == null) {
        pointer.addChild(character);
      }
      pointer = pointer.isExistInChildren(character);
    }

    pointer.setWord(key);
    return;
  }

  /** Searching a word from dictionary. */
  public Word searchNode(String key) {
    Trie pointer = root;

    for (int i = 0; i < key.length(); ++i) {
      char character = key.charAt(i);
      if (pointer.isExistInChildren(character) == null) {
        return null;
      }
      pointer = pointer.isExistInChildren(character);
    }
    return pointer.word;
  }

  /** Getting all of word in the Node of dictionary */
  public void getAllWord(Trie node) {
    if (node == null) {
      return;
    }

    if (node.getWord() != null) {
      resultsList.add(node.getWord());
    }

    if (node.getChildren() == null) {
      return;
    }

    for (Trie child : node.getChildren()) {
      getAllWord(child);
    }
  }

  /** Find out the node of word. */
  public Trie findNode(String key) {
    Trie pointer = root;

    for (int i = 0; i < key.length(); ++i) {
      char character = key.charAt(i);
      if (pointer.isExistInChildren(character) == null) {
        return null;
      }
      pointer = pointer.isExistInChildren(character);
    }

    return pointer;
  }

  /** Deleting a node (if this node.children empty, delete this node */
  public void deleteNode(Trie node) {
    if (node == null) {
      return;
    }

    node.setWord(null);

    if (node.getChildren().isEmpty()) {
      node = null;
    }
  }

  public void setWordList() {
    wordList = resultsList;
  }
}
