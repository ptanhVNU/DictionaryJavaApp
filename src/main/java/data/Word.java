package data;

import javafx.util.Pair;
import jdk.nashorn.internal.runtime.regexp.joni.ast.StringNode;

import java.util.ArrayList;

public class Word {
  private String word;

  private String pronunciation;

  boolean bookmark;

  protected ArrayList<Detail> details = new ArrayList<>();

  public Word() {
    this.word = "";
    details = new ArrayList<>();
  }

  public Word(String word) {
    this.word = word;
    details = new ArrayList<>();
  }

  public Word(Word word) {
    this.setWord(word.getWord());
    this.setPronunciation(word.getPronunciation());
    this.setBookmark(word.isBookmark());
    this.setDetails(word.getDetails());
  }

  public void edit(Word word) {
    this.setWord(word.getWord());
    this.setPronunciation(word.getPronunciation());
    this.setBookmark(word.isBookmark());
    this.setDetails(word.getDetails());
    return;
  }

  public String getWord() {
    return word;
  }

  public ArrayList<Detail> getDetails() {
    return details;
  }

  public String getPronunciation() {
    return pronunciation;
  }

  public void setWord(String word) {
    this.word = word;
  }

  public void setDetails(ArrayList<Detail> details) {
    this.details = details;
  }

  public void setPronunciation(String pronunciation) {
    this.pronunciation = pronunciation;
  }

  public boolean isBookmark() {
    return bookmark;
  }

  public void setBookmark(boolean bookmark) {
    this.bookmark = bookmark;
  }

  public void addDetail(
          String word_type, ArrayList<Pair<String, ArrayList<String>>> usages) {
    details.add(new Detail(word_type, usages));
  }

  public void addDetail(Detail detail) {
    details.add(detail);
  }

  public String toString() {
    return this.getWord();
  }

  /**
   * generate string with detail db format
   *
   * @return String detail db format
   */
  public String toStringDetail() {
    StringBuilder response = new StringBuilder();
    response.append("@").append(this.getWord());
    response.append(" ").append(this.getPronunciation());
    response.append(">");
    for (Detail detail : details) {
      response.append("* ").append(detail.getWord_type());
      for (Pair<String, ArrayList<String>> u : detail.getExplanations()) {
        response.append(">- ").append(u.getKey());
        for (String usage : u.getValue()) {
          response.append(">= ").append(usage);
        }
      }
    }
    return response.toString();
  }

  /**
   * show detail
   *
   * @return String detail
   */
  public String showDetail() {
    StringBuilder response = new StringBuilder();
    response.append(getWord() + '\n');
    response.append(getPronunciation() + '\n');
    for (Detail detail : getDetails()) {
      response.append(detail.toString() + '\n');
    }
    return response.toString();
  }

  @Override
  protected Object clone() throws CloneNotSupportedException {
    return super.clone();
  }

  public static class Detail {
    private String word_type;

    private ArrayList<Pair<String, ArrayList<String>>> explanations = new ArrayList<>();

    public Detail() {
    }

    public Detail(
            String word_type, ArrayList<Pair<String, ArrayList<String>>> explanations) {
      this.word_type = word_type;
      this.explanations = explanations;
    }

    public Detail(Detail detail) {
      this.word_type = detail.getWord_type();
      this.explanations = detail.getExplanations();
    }

    public String getWord_type() {
      return word_type;
    }

    public ArrayList<Pair<String, ArrayList<String>>> getExplanations() {
      return explanations;
    }

    public void setWord_type(String word_type) {
      this.word_type = word_type;
    }


    public void setExplanations(ArrayList<Pair<String, ArrayList<String>>> explanations) {
      this.explanations = explanations;
    }

    public void addExplanations(Pair<String, ArrayList<String>> pair) {
      explanations.add(pair);
    }

    @Override
    public String toString() {
      StringBuilder response = new StringBuilder();
      response.append(getWord_type()).append('\n');
      for (Pair<String, ArrayList<String>> u : explanations) {
        response.append(u.getKey()).append('\n');
        for (String usage : u.getValue()) {
          response.append(usage).append('\n');
        }
      }
      return response.toString();
    }
  }
}
