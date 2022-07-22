package data;

import com.sun.scenario.animation.shared.TimerReceiver;

import java.util.ArrayList;

public class Dictionary {
  static ArrayList<Word> listWord = new ArrayList<Word>();

  public static class Trie {
    private ArrayList<Trie> children;

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

    /** Find out Node has a character of key*/
    public Trie isExistInChildren(char character) {
      for (Trie child : this.children) {
        if (child.character == character) {
          return child;
        }
      }
      return null;
    }

    /** Add Node if this character is not exist */
    public void addChild(char character) {
      children.add(new Trie(character));
    }
  }

  public Trie root = new Trie();

  public ArrayList<Word> resultsList = new ArrayList<>();

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

  /** Getting all of word in the dictionary */
  public void getAllWord(Trie node) {
    if (node == null) {
      return;
    }

    if (node.getWord() != null) {
      resultsList.add(node.getWord());
    }

    for (Trie child : node.getChildren()) {
      getAllWord(child);
    }
  }

  /** Deleting a word (if this node.children empty, delete this node */
  public void deleteNode(Trie node) {
    if (node == null) {
      return;
    }

    node.setWord(null);

    if (node.getChildren().isEmpty()) {
      node = null;
    }
  }
}
