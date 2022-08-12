package data;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Dictionary {
  private Trie root = new Trie();

  private ArrayList<Word> resultsList = new ArrayList<>();

  public void setResultsList(ArrayList<Word> resultsList) {
    this.resultsList = resultsList;
  }

  public ArrayList<Word> getResultsList() {
    return resultsList;
  }

  /**
   *
   *
   * @param key keyword
   * @exception NullPointerException not found key
   */
  public void addNode(Word key) {
    try {
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
      pointer.sort();
    } catch (NullPointerException exception) {
      exception.printStackTrace();
    }
    return;
  }

  /**
   * Works just like {@link Dictionary#getAllWord()} except the node is always presumed to be root.
   *
   * @see Dictionary#getAllWord()
   */
  public void getAllWord() {
    getAllWord(root);
    return;
  }

  /**
   * Get all word (Word class) from node to end.
   *
   * @param node start mode
   * @exception NullPointerException not found node
   */
  public void getAllWord(Trie node) {
    try {
      if (node.getWord() != null) {
        resultsList.add(node.getWord());
      }

      if (node.getChildren() == null) {
        return;
      }

      for (Trie child : node.getChildren()) {
        getAllWord(child);
      }
    } catch (NullPointerException exception) {
      exception.printStackTrace();
    }
  }

  /**
   * Find out the node has word like key.
   *
   * @param key
   * @return pointer if can find, otherwise null
   */
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

  /**
   * Deleting a node if children of this node is empty, delete this node.
   *
   * @param node
   * @exception NullPointerException not found node
   */
  public void deleteNode(Trie node) {
    try {
      node.setWord(null);

      if (node.getChildren().isEmpty()) {
        node = new Trie();
      }
    } catch (NullPointerException exception) {
      exception.printStackTrace();
    }
  }

  /**
   * Find out all word have prefix key. List of word is resultsList (variable of class)
   *
   * @param key
   * @exception NullPointerException not found key
   */
  public void searchPrefixWord(String key) {
    try {
      Trie pointer = root;
      setResultsList(new ArrayList<>());

      for (int i = 0; i < key.length(); ++i) {
        char character = key.charAt(i);
        if (pointer.isExistInChildren(character) == null) {
          return;
        }

        pointer = pointer.isExistInChildren(character);
      }

      getAllWord(pointer);
    } catch (NullPointerException exception) {
      exception.printStackTrace();
    }
    return;
  }

  public static class Trie {
    private ArrayList<Trie> children = new ArrayList<>();

    private char character;

    private Word word;

    /** Constructor of Trie class. */
    public Trie() {}

    /**
     * Constructor of Trie class.
     *
     * @param character
     */
    public Trie(char character) {
      this.character = character;
      word = null;
    }

    /**
     * Constructor of Trie class.
     *
     * @param character
     * @param word
     */
    public Trie(char character, Word word) {
      this.character = character;
      this.word = word;
    }

    /** get-set-er. */
    public ArrayList<Trie> getChildren() {
      return children;
    }

    public char getCharacter() {
      return character;
    }

    public Word getWord() {
      return word;
    }

    public void setChildren(ArrayList<Trie> children) {
      this.children = children;
    }

    public void setCharacter(char character) {
      this.character = character;
    }

    public void setWord(Word word) {
      this.word = word;
    }

    /**
     * Find out Node has this character.
     *
     * @param character
     * @return child of this has character
     * @exception NullPointerException not found character
     */
    public Trie isExistInChildren(char character) {
      try {
        if (this.children == null) {
          return null;
        }
        for (Trie child : this.children) {
          if (child.getCharacter() == character) {
            return child;
          }
        }
      } catch (NullPointerException exception) {
        exception.printStackTrace();
      }
      return null;
    }

    /**
     * Add Node if this character is not exist.
     *
     * @param character
     * @exception NullPointerException not found character
     */
    public void addChild(char character) {
      try {
        children.add(new Trie(character));
      } catch (NullPointerException exception) {
        exception.printStackTrace();
      }
    }

    /**
     * Make max-heap
     *
     * @param list
     * @param n
     * @param i
     */
    public void heapify(ArrayList<Trie> list, int n, int i) {
      int largest = i;
      int l = 2 * i + 1;
      int r = 2 * i + 2;

      if (l < n && list.get(l).character > list.get(largest).character) {
        largest = l;
      }

      if (r < n && list.get(r).character > list.get(largest).character) {
        largest = r;
      }

      if (largest != i) {
        Collections.swap(list, i, largest);
        heapify(list, n, largest);
      }
    }

    /** Sort Children of this. */
    public void sort() {
      int n = children.size();
      for (int i = n / 2 - 1; i >= 0; --i) {
        heapify(children, n, i);
      }

      for (int i = n - 1; i > 0; --i) {
        Collections.swap(children, 0, i);
        heapify(children, i, 0);
      }
    }
  }
}
