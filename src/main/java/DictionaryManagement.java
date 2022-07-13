import java.util.Scanner;
import java.io.*;

public class DictionaryManagement {
    final String dictionaryFilePath = "E:\\ProjectJava\\Dictionary\\src\\main\\resources\\data\\dictionaries.txt";

    public void insertFromCommandline() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Nhap so luong tu ban muon them: ");
        int numsWord;
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

    public void insertFromFile() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(dictionaryFilePath));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] words = line.split("\t");
                Word word = new Word();
                word.setWord_target(words[0]);
                word.setWord_explain(words[words.length - 1]);
                Dictionary.listWord.add(word);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void dictionaryLookup() {

    }
}
