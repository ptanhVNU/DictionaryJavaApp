package data;

import java.util.ArrayList;

public class Word {
  private String word;

  public static class Detail {
    private String word_type;

    private String explanations;

    private ArrayList<String> usages;

    private String pronounciation;

    public Detail() {
      this.word_type = "";
      this.explanations = "";
      this.usages = new ArrayList<>();
      this.pronounciation = "";
    }

    public Detail(
        String word_type, String explanations, ArrayList<String> usages, String pronounciation) {
      this.word_type = word_type;
      this.explanations = explanations;
      this.usages = usages;
      this.pronounciation = pronounciation;
    }

    public String getWord_type() {
      return word_type;
    }

    public ArrayList<String> getUsages() {
      return usages;
    }

    public String getExplanations() {
      return explanations;
    }

    public String getPronounciation() {
      return pronounciation;
    }

    public void setWord_type(String word_type) {
      this.word_type = word_type;
    }

    public void setUsages(ArrayList<String> usages) {
      this.usages = usages;
    }

    public void setExplanations(String explanations) {
      this.explanations = explanations;
    }

    public void setPronounciation(String pronounciation) {
      this.pronounciation = pronounciation;
    }
  }

  protected ArrayList<Detail> details;

  public Word() {
    this.word = "";
    details = new ArrayList<>();
  }

  public Word(String word) {
    this.word = word;
    details = new ArrayList<>();
  }

  public String getWord() {
    return word;
  }

  public ArrayList<Detail> getDetails() {
    return details;
  }

  public void setWord(String word) {
    this.word = word;
  }

  public void setDetails(ArrayList<Detail> details) {
    this.details = details;
  }

  public void addDetail(
      String word_type, String explanations, ArrayList<String> usages, String pronounciation) {
    details.add(new Detail(word_type, explanations, usages, pronounciation));
  }
}
