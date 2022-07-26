package data;

import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;

public class DictionaryManagement extends Dictionary {
    final String dictionaryFilePath = "src/main/resources/data/dictionaries.txt";

    /** insert From Command line. */
    public void insertFromCommandline() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Nhap so luong tu ban muon them: ");
        int numsWord;
        numsWord = Integer.parseInt(scanner.nextLine());

        for (int i = 0; i < numsWord; i++) {
            System.out.print("English: ");
            String target = scanner.nextLine();
            if (target.equals("")) {
                System.out.println("No target");
                break;
            }
            System.out.print("Vietnamese: ");
            String explain = scanner.nextLine();
            if (explain.equals("")) {
                System.out.println("No explain");
                break;
            }
            Word data = new Word();
            data.setWord(target);
            data.addDetail(null, explain, null, null);
            wordList.add(data);
            addNode(data);
        }
    }
    return;
  }

    /** insert From File. */
    public void insertFromFile() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(dictionaryFilePath));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] words = line.split("\t");
                Word word = new Word();
                word.setWord(words[0]);
                word.addDetail(null, words[words.length - 1], null, null);
                wordList.add(word);
                addNode(word);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
  }

    /** dictionary Lookup. */
    public Word dictionaryLookup(String lookUp) {
        Word result = new Word();
        result = searchNode(lookUp);
        return result;
    }
  }

    /** dictionary Export To File. */
    public void dictionaryExportToFile() {
        File file =
                new File("E:\\ProjectJava\\Dictionary\\src\\main\\resources\\data\\dictionaries.txt");
        try {
            FileWriter myWriter = new FileWriter(file);
            for (int i = 0; i < wordList.size(); i++) {
                myWriter.write(
                        wordList.get(i).getWord()
                                + "\t"
                                + wordList.get(i).details.get(0).getExplanations()
                                + "\n");
            }
            myWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /** delete Word */
    public String dictionaryDelete(String key) {
        Trie delelte = findNode(key);
        if (delelte == null) {
            return "Not found key!";
        }
        deleteNode(delelte);
        return "Completed";
    }

    public void speak(String text) {
        TextToSpeech speech = new TextToSpeech(text);
        speech.speakText();
    }

    public void speakWord_target(int index) {
        TextToSpeech speech = new TextToSpeech(wordList.get(index).getWord());
        speech.speakText();
    }

}
