import java.util.ArrayList;

public class Dictionary {
    static ArrayList<Word> listWord = new ArrayList<Word>();

    public static ArrayList<Word> getListWord() {
        return listWord;
    }

    public void setListWord(ArrayList<Word> listWord) {
        this.listWord = listWord;
    }
}
