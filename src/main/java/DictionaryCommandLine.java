import java.util.ArrayList;

public class DictionaryCommandLine {

    DictionaryManagement dictionaryManagement = new DictionaryManagement();

    public void showAllWords() {
        ArrayList<Word> listWords = Dictionary.listWord;
        System.out.printf("%-7s| %-20s| %-50s\n", "No", "English", "Vietnamese");
        for (int i = 0; i < listWords.size(); i++) {
            System.out.printf("%-7d| %-20s| %-50s\n", i, listWords.get(i).getWord_target(),
                    listWords.get(i).getWord_explain());
        }
    }

    public void dictionaryBasic() {
        dictionaryManagement.insertFromCommandline();
        this.showAllWords();
    }

    public void dictionaryAdvanced() {
        dictionaryManagement.insertFromFile();
        this.showAllWords();
        dictionaryManagement.dictionaryLookup();
    }

    public static void main(String[] args) {
        DictionaryCommandLine dictionaryCommandLine = new DictionaryCommandLine();
        dictionaryCommandLine.dictionaryAdvanced();
    }
}
