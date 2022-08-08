package data;

import java.sql.*;
import java.util.ArrayList;

public class JDBCConnect {
    static final String url = "jdbc:mysql://localhost:3306/dictionary";
    static String password = "matkhau123";
    static String username = "root";
    static ArrayList<Word> words = new ArrayList<>();

    public static ArrayList<Word> importDatabase() throws SQLException {
        Connection connection = DriverManager.getConnection(url, username, password);
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM english_vietnamese");
        int index = 0;
        int test = 140000;
        while (resultSet.next()) {
            --test;
            if (test == 0) break;
            String idx = resultSet.getString("idx");
            String word_target = resultSet.getString("word");
            String detail = resultSet.getString("detail");

            ArrayList<String> usages = new ArrayList<>();
            String[] res;
            res = detail.split(">");
            Word word = new Word();
            word.setWord(word_target);
            word.setPronounciation(res[0]);
            Word.Detail temporaryDetail = new Word.Detail();
            for (int i = 1; i < res.length; i++) {
                if (res[i].startsWith("*")) {
                    temporaryDetail = new Word.Detail();
                    temporaryDetail.setWord_type(res[i]);
                } else if (res[i].startsWith("-")) {
                    temporaryDetail.setExplanations(res[i]);
                } else if (res[i].startsWith("=")) {
                    temporaryDetail.addUsages(res[i]);
                }
            }
            word.addDetail(temporaryDetail);
            words.add(word);
        }
        resultSet.close();
        statement.close();
        connection.close();
        return words;
    }
    resultSet.close();
    statement.close();
    connection.close();
    return words;
  }

    public void exportDatabase(long size) throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/dictionary_va", username, password);
        connection.setAutoCommit(false);
        connection.getTransactionIsolation();
        PreparedStatement preparedStatement =
                connection.prepareStatement("INSERT INTO english_vietnamese (idx, word, detail) VALUES (?,?,?);");
        for (int i = Dictionary.wordList.size() - 1; i >= size; i--) {
            preparedStatement.setInt(1, i-1);
            preparedStatement.setString(2, Dictionary.wordList.get(i).getWord());
            preparedStatement.setString(3, Dictionary.wordList.get(i).getDetails().toString());
            preparedStatement.addBatch();
        }
        preparedStatement.executeBatch();
        connection.commit();
        preparedStatement.close();
        connection.close();
    }

    public static void main(String[] args) throws SQLException {
        JDBCConnect jdbcConnect = new JDBCConnect();
        Dictionary.wordList = importDatabase();
        long size = Dictionary.wordList.size();
        jdbcConnect.exportDatabase(size);
    }
}
