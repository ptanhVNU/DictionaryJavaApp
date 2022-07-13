import java.util.Scanner;

public class DictionaryManagement {

    public void insertFromCommandline(){
        Scanner scanner = new Scanner(System.in);
        System.out.printf("Nhap so luong tu ban muon them: ");
        int numsWord = 0;
        numsWord = Integer.parseInt(scanner.nextLine());

        for (int i = 0; i < numsWord; i++) {
            System.out.print("English: ");
            String target = scanner.nextLine();
            if (target.equals("")) {
                break;
            }
            System.out.print("Vietnamese: ");
            String explain = scanner.nextLine();
            if (explain.equals("")) {
                break;
            }
            Word word = new Word();
            word.setWord_target(target);
            word.setWord_explain(explain);
            Dictionary.listWord.add(word);

        }
    }
}
