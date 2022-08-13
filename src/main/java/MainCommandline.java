import data.Dictionary;
import data.DictionaryCommandLine;
import data.DictionaryManagement;

import java.io.IOException;
import java.util.Scanner;

public class MainCommandline {
    public static void main(String[] args) throws IOException {
        DictionaryCommandLine dictionaryCMD = new DictionaryCommandLine();
        dictionaryCMD.dictionaryImportFromDatabase();
        boolean check = true;
        Scanner scanner = new Scanner(System.in);

        while(check) {
            System.out.printf("\n%-20s\n" +
                    "\t0. Thoát\n" +
                    "\t1. Xem từ điển\n" +
                    "\t2. Tìm từ\n" +
                    "\t3. toStringDetail\n" +
                    "\t4. check\n" +
                    "\t5. edit\n" +
                    "\t6. add\n" +
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
                    dictionaryCMD.testShowDetail();
                    break;
                case 4:
                    dictionaryCMD.check();
                    break;
                case 5:
                    dictionaryCMD.testEdit();
                    break;
                case 6:
                    dictionaryCMD.testAdd();
                    break;
                default:
                    check = false;
                    break;
            }
        }

    }


}