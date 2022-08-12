package data;

import java.util.ArrayList;

public class Word {
    private String word;

    private String pronunciation = "";

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

    public void edit(Word word) {
        this.setWord(word.getWord());
        this.setPronunciation(word.getPronunciation());
        this.setBookmark(word.isBookmark());
        this.setDetails(word.getDetails());
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
            String word_type, String explanations, ArrayList<String> usages) {
        details.add(new Detail(word_type, explanations, usages));
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
        StringBuilder reponse = new StringBuilder();
        reponse.append(this.getWord());
        reponse.append(" ").append(this.getPronunciation());
        reponse.append(">");
        for (Detail detail : details) {
            reponse.append("* ").append(detail.getWord_type());
            reponse.append(">- ").append(detail.getExplanations());
            detail.getUsages().forEach(e -> reponse.append(">= ").append(e));
        }
        return reponse.toString();
    }

    /**
     * show detail
     *
     * @return String detail
     */
    public String showDetail() {
        StringBuilder reponse = new StringBuilder();
        reponse.append(getWord() + '\n');
        reponse.append(getPronunciation() + '\n');
        for (Detail detail : getDetails()) {
            reponse.append(detail.toString() + '\n');
        }
        return reponse.toString();
    }

    public static class Detail {
        private String word_type = "";

        private String explanations;

        private ArrayList<String> usages = new ArrayList<>();

        public Detail() {
            this.word_type = "";
            this.explanations = "";
            this.usages = new ArrayList<>();
        }

        public Detail(
                String word_type, String explanations, ArrayList<String> usages) {
            this.word_type = word_type;
            this.explanations = explanations;
            this.usages = usages;
        }

        public Detail(Detail detail) {
            this.word_type = detail.getWord_type();
            this.explanations = detail.getExplanations();
            this.usages = detail.getUsages();
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

        public void setWord_type(String word_type) {
            this.word_type = word_type;
        }

        public void setUsages(ArrayList<String> usages) {
            this.usages = usages;
        }

        public void addUsages(String usages) {
            this.usages.add(usages);
        }

        public void setExplanations(String explanations) {
            this.explanations = explanations;
        }

        @Override
        public String toString() {
            StringBuilder reponse = new StringBuilder();
            reponse.append(getWord_type() + '\n');
            reponse.append(getExplanations() + '\n');
            for (String request : this.usages) {
                reponse.append(request);
            }
            return reponse.toString();
        }
    }
}
