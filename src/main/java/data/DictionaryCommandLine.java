package data;

import java.util.ArrayList;
import java.util.Scanner;

public class DictionaryCommandLine {
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
        Scanner scanner = new Scanner(System.in);
        String search = scanner.nextLine();
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
        Scanner sc = new Scanner(System.in);
        String del;
        del = sc.nextLine();
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
}
