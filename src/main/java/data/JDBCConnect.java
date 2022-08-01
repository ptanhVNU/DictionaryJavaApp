package data;

import jdk.nashorn.internal.scripts.JD;

import java.sql.*;
import java.util.ArrayList;


public class JDBCConnect {
    final String url = "jdbc:mysql:/tudien";
    String password = "matkhau123";
    String username = "root";
    static Dictionary dictionary = new Dictionary();

    public ArrayList<Word> importDatabase() throws SQLException {
        Connection connection = DriverManager.getConnection(url, username, password);
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM english_vietnamese");
        int index = 0;
        while (resultSet.next()) {

            String word_target = resultSet.getString("word");
            String detail = resultSet.getString("detail");


            StringBuilder explanations = new StringBuilder();
            StringBuilder pronunciation = new StringBuilder();
            ArrayList<String> usages = new ArrayList<>();
            String[] res;
            res = detail.split(">");
            pronunciation.append(res[0]);
            for (int i = 1; i < res.length; i++) {
                if (res[i].startsWith("*") || res[i].startsWith("-")) {
                    explanations.append(res[i]).append("\n");
                } else if (res[i].startsWith("=")){
                    usages.add((res[i] + "\n").replace("=", ""));
                }
            }
            Word word = new Word();

            word.setWord(word_target);
            word.details.get(index).setPronounciation(pronunciation.toString());
            word.details.get(index).setExplanations(explanations.toString());
            word.details.get(index).setUsages(usages);
            index++;
            dictionary.wordList.add(word);
        }
        resultSet.close();
        statement.close();
        connection.close();
        return dictionary.wordList;
    }

    // hoan thien sau
    public void exportDatabase() {

    }

    public static void main(String[] args) throws SQLException {
        JDBCConnect jdbcConnect = new JDBCConnect();
        ArrayList<Word> words = jdbcConnect.importDatabase();

        for (int i = 0; i < words.size(); i++) {
            System.out.println(words.get(i).getWord());
            System.out.println(words.get(i).getDetails());
        }
    }
}
