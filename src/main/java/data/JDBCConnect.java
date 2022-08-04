package data;

import java.sql.*;
import java.util.ArrayList;

public class JDBCConnect {
  final static String url = "jdbc:mysql:/tudien";
  static String password = "";
  static String username = "root";
  static ArrayList<Word> words = new ArrayList<>();

  public static ArrayList<Word> importDatabase() throws SQLException {
    Connection connection = DriverManager.getConnection(url, username, password);
    Statement statement = connection.createStatement();
    ResultSet resultSet = statement.executeQuery("SELECT * FROM english_vietnamese");
    int index = 0;
    int test = 10;
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

  // hoan thien sau
  public void exportDatabase() {}

  public static void main(String[] args) throws SQLException {
    JDBCConnect jdbcConnect = new JDBCConnect();
    ArrayList<Word> words = jdbcConnect.importDatabase();

    for (int i = 0; i < words.size(); i++) {
      // System.out.println(words.get(i).showDetail());
    }
  }
}
