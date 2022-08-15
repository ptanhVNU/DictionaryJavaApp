package data;

import javafx.util.Pair;
import jdk.nashorn.internal.runtime.regexp.joni.ast.StringNode;

import java.util.ArrayList;

public class Word {
  private String word;

  private String pronunciation;

  boolean bookmark;

  protected ArrayList<Detail> details = new ArrayList<>();

  /** constructor with null. */
  public Word() {
    this.word = "";
    details = new ArrayList<Detail>();
  }

  /**
   * constructor with String word only.
   *
   * @param word string
   */
  public Word(String word) {
    this.word = word;
    details = new ArrayList<Detail>();
  }

  /**
   * constructor with completed word.
   *
   * @param word Word
   */
  public Word(Word word) {
    this.setWord(word.getWord());
    this.setPronunciation(word.getPronunciation());
    this.setBookmark(word.isBookmark());
    this.setDetails(word.getDetails());
  }

  /**
   * edit this->word out new word.
   *
   * @param word edit old word out new word
   */
  public void edit(Word word) {
    this.setWord(word.getWord());
    this.setPronunciation(word.getPronunciation());
    this.setBookmark(word.isBookmark());
    this.setDetails(word.getDetails());
    return;
  }

  /** get/set-er. */
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

  /**
   * add new Detail for word by word type n list of explanations.
   *
   * @param word_type word type of word
   * @param explanations list of explanations n usages
   */
  public void addDetail(String word_type, ArrayList<Pair<String, ArrayList<String>>> explanations) {
    details.add(new Detail(word_type, explanations));
  }

  /**
   * add new Detail for word by a Detail.
   *
   * @param detail detail of this word
   */
  public void addDetail(Detail detail) {
    details.add(detail);
  }

  /**
   * get String word of this Word.
   *
   * @return String getWord
   */
  public String toString() {
    return this.getWord();
  }

  /**
   * generate string with detail db format.
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
   * show detail.
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

  /**
   * get html text of details.
   *
   * @return String answer has all of html text of details
   */
  public String getHtmlText() {
    StringBuilder answer = new StringBuilder();

    for (int i = 0; i < getDetails().size(); ++i) {
      answer.append(getDetails().get(i).getHtmlText());
    }

    return answer.toString();
  }

  public static class Detail {
    private String word_type;

    private ArrayList<Pair<String, ArrayList<String>>> explanations = new ArrayList<>();

    public Detail() {
      word_type = "";
    }

    public Detail(String word_type, ArrayList<Pair<String, ArrayList<String>>> explanations) {
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

    /**
     * get html text of this detail.
     *
     * @return StringBuilder of html text
     */
    public StringBuilder getHtmlText() {
      StringBuilder answer = new StringBuilder();

      answer
              .append("<p style=\"font-size:22px;font-family:consolas;\"><b>")
              .append(word_type)
              .append("</b></p>");
      for (int i = 0; i < explanations.size(); ++i) {
        answer
                .append(
                        "<p style=\"font-size:19px;color:#ff4dff;padding-left:15;font-family:consolas;\">")
                .append(explanations.get(i).getKey())
                .append("</p>");
        for (int j = 0; j < explanations.get(i).getValue().size(); ++j) {
          String s = explanations.get(i).getValue().get(j);
          String a;
          String b;

          if (s.indexOf("+") == -1) {
            a = s;
            b = "";
          } else {
            a = s.substring(0, s.indexOf("+"));
            b = explanations.get(i).getValue().get(j).substring(a.length() + 2);
          }

          answer
                  .append("<p style=\"font-size:17px;padding-left:30;font-family:consolas;\">")
                  .append("<noname style=\"color:#668cff;\">")
                  .append(a)
                  .append("</noname>")
                  .append("<br>")
                  .append(b)
                  .append("</p>");
        }
      }

      return answer;
    }
  }
}
