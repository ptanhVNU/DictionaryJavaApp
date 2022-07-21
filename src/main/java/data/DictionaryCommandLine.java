package data;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class DictionaryCommandLine {
    public String stringScanner() {
        Scanner scanner = new Scanner(System.in);
        String s = scanner.nextLine();
        return s;
    }
    public void showAllWords() {
        ArrayList<Word> listWords = Dictionary.listWord;
        System.out.printf("%-7s| %-20s| %-50s\n", "No", "English", "Vietnamese");
        for (int i = 0; i < listWords.size(); i++) {
            System.out.printf("%-7d| %-20s| %-50s\n", i, listWords.get(i).getWord_target(),
                    listWords.get(i).getWord_explain());
        }
    }

    public void dictionaryBasic() {
        DictionaryManagement.insertFromCommandline();
        showAllWords();
    }

    public void dictionaryAdvanced() {
        showAllWords();
    }

    public void dictionarySearcher() {
        System.out.print("Nhap tu ban muon tim kiem: ");
        String search = stringScanner();

        ArrayList<Word> listWords = Dictionary.getListWord();
        System.out.printf("%-7s| %-20s| %-50s\n", "No", "English", "Vietnamese");
        for (int i = 0; i < listWords.size(); i++) {
            if(listWords.get(i).getWord_target().startsWith(search.toLowerCase())) {
                System.out.printf("%-7d| %-20s| %-50s\n", i, listWords.get(i).getWord_target(),
                        listWords.get(i).getWord_explain());
            }
        }
    }

    public void deleteWord() {
        boolean check = false;
        System.out.println("Nhập từ cần xóa: ");
        String del = stringScanner();

        for (int i = 0; i < Dictionary.listWord.size(); i++) {
            if (Dictionary.listWord.get(i).getWord_target().equals(del)) {
                Dictionary.listWord.remove(i);
                System.out.println("Xóa từ thành công!");
                check = true;
            }
        }
        if (!check) {
            System.out.println("Không tìm thấy từ để xóa!");
        }
    }

    public void showTranslateText() {
        String text = stringScanner();
        try {
            System.out.println(TranslateAPI.googleTranslate("", "vi", text));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
