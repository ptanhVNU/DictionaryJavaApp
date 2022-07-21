package data;

import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;

public class DictionaryManagement {
    final String dictionaryFilePath = "src/main/resources/data/dictionaries.txt";
    ArrayList<Word> listWord = new ArrayList<>();
    public static void insertFromCommandline() {
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

    public ArrayList<Word> insertFromFile() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(dictionaryFilePath));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] words = line.split("\t");
                Word word = new Word();
                word.setWord_target(words[0]);
                word.setWord_explain(words[words.length - 1]);
                listWord.add(word);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return listWord;
    }

    public void dictionaryLookup() {
        System.out.print("Nhap tu ban muon tim kiem: ");
        Scanner scanner = new Scanner(System.in);
        String lookUp = scanner.nextLine();
        ArrayList<Word> listWords = Dictionary.getListWord();
        System.out.printf("%-7s| %-20s| %-50s\n", "No", "English", "Vietnamese");
        for (int i = 0; i < listWords.size(); i++) {
            if (listWords.get(i).getWord_target().equals(lookUp.toLowerCase())) {
                System.out.printf("%-7d| %-20s| %-50s\n", i, listWords.get(i).getWord_target(),
                        listWords.get(i).getWord_explain());
            }
        }
    }



    public void dictionaryExportToFile() {
        File file = new File("E:\\ProjectJava\\Dictionary\\src\\main\\resources\\data\\dictionaries.txt");
        try {
            FileWriter myWriter = new FileWriter(file);
            for (int i = 0; i < Dictionary.listWord.size(); i++) {
                myWriter.write(Dictionary.listWord.get(i).getWord_target()
                        + "\t" + Dictionary.listWord.get(i).getWord_explain() + "\n");
            }
            myWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
