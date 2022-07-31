package data;

import java.sql.*;
import java.util.ArrayList;


public class JDBCConnect {
    final String url = "jdbc:mysql://localhost:3306/dictionary_va";
    String password = "matkhau123";
    String username = "root";

    public ArrayList<Word> importDatabase() throws SQLException {
        Connection connection = DriverManager.getConnection(url, username, password);
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM english_vietnamese");
        Dictionary dictionary = new Dictionary();
        while (resultSet.next()) {
            String word_target = resultSet.getString("word");
            // tách cột detail trong data base thành pronounce và word_explain
            ResultSet resultSet_text = statement.executeQuery("select detail, substr(detail, 1, instr(detail, \">\")) as pronounce, " +
                    "substr(detail, instr(detail, \">\")) as word_explain from english_vietnamese;");
            int index = 0;
            Word word = new Word();
            word.setWord(word_target);
            while (resultSet_text.next()) {
                // set pronounce
                String pronounce = resultSet_text.getString(2);
                //
                String explain = resultSet_text.getString(3);
                word.details.get(index).setPronounciation(pronounce);
                word.details.get(index).setExplanations(explain);
                index++;
            }
            resultSet_text.close();
        }
        resultSet.close();
        statement.close();
        connection.close();
        return dictionary.wordList;
    }

    // hoan thien sau
    public void exportDatabase(){

    }
}
