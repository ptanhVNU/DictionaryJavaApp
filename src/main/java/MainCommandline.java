import javafx.css.PseudoClass;

import java.util.Scanner;

public class MainCommandline {
    public static void main(String[] args) {
        DictionaryCommandLine dictionaryCMD = new DictionaryCommandLine();
        Dictionary dictionary = new Dictionary();
        DictionaryManagement dictionaryMG = new DictionaryManagement();
        dictionary.setListWord(dictionaryMG.insertFromFile());
        boolean check = true;
        Scanner scanner = new Scanner(System.in);

        while(check) {
            System.out.printf("\n%-20s\n" +
                    "\t0. Thoát\n" +
                    "\t1. Xem từ điển\n" +
                    "\t2. Tìm từ\n" +
                    "\t3. Thêm từ mới\n" +
                    "\t4. Xoá từ\n" +
                    "Chọn thao tác: ", "------- DICTIONARY JAVA APP -------");
            int select;
            select = Integer.parseInt(scanner.nextLine());
            switch (select) {
                case 1:
                    dictionaryCMD.dictionaryAdvanced();
                    break;
                case 2:
                    dictionaryCMD.dictionarySearcher();
                    break;
                case 3:
                    dictionaryCMD.dictionaryBasic();
                    break;
                case 4:
                    dictionaryCMD.deleteWord();
                    break;
                default:
                    check = false;
                    break;
            }
        }
    }
}
