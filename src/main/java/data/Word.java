package data;

import java.util.ArrayList;

public class Word {
    private String word;

    private String word_type;

    private ArrayList<String> explanations;

    private String usages;

    private String pronounciation;

    public Word() {
        this.word = "";
        this.word_type = "";
        this.explanations = new ArrayList<>();
        this.usages = "";
        this.pronounciation = "";
    }

    public Word(String word, String word_type, ArrayList<String> explanations, String usages, String pronounciation) {
        this.word = word;
        this.word_type = word_type;
        this.explanations = explanations;
        this.usages = usages;
        this.pronounciation = pronounciation;
    }

    public String getWord() {
        return word;
    }

    public String getWord_type() {
        return word_type;
    }

    public String getUsages() {
        return usages;
    }

    public ArrayList<String> getExplanations() {
        return explanations;
    }

    public String getPronounciation() {
        return pronounciation;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public void setWord_type(String word_type) {
        this.word_type = word_type;
    }

    public void setUsages(String usages) {
        this.usages = usages;
    }

    public void setExplanations(ArrayList<String> explanations) {
        this.explanations = explanations;
    }

    public void setPronounciation(String pronounciation) {
        this.pronounciation = pronounciation;
    }


}
