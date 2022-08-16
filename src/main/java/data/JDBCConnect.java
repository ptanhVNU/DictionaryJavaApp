package data;

import javafx.util.Pair;

import java.sql.*;
import java.util.ArrayList;

public class JDBCConnect {
  static final String url = "jdbc:mysql://localhost:3306/dictionary";
  static String password = "matkhau123";
  static String username = "root";
  static ArrayList<Word> words = new ArrayList<>();

  private static int idx = 0;

  /**
   * Import Database for dictionary.
   *
   * @return list of words to add other dictionary
   * @throws SQLException sql err
   */
  public static ArrayList<Word> importDatabase() throws SQLException {
    Connection connection = DriverManager.getConnection(url, username, password);
    Statement statement = connection.createStatement();
    ResultSet resultSet = statement.executeQuery("SELECT * from english_vietnamese order by idx ASC;");

    while (resultSet.next()) {
      String idx = resultSet.getString("idx");
      String word_target = resultSet.getString("word");
      String detail = resultSet.getString("detail");
      String[] res;
      res = detail.split(">");
      Word word = new Word();
      word.setWord(word_target);
      String[] parts = res[0].split(" ");
      int start = 0;
      for (String part : parts) {
        if (!part.startsWith("/")) {
          start = start + part.length() + 1;
        } else break;
      }
      word.setPronunciation(res[0].substring(start - 1).trim());
      Word.Detail temporaryDetail = new Word.Detail();
      Pair<String, ArrayList<String>> pair = new Pair<>("", new ArrayList<>());
      ArrayList<String> list = new ArrayList<>();
      String tmp = "";
      for (int i = 1; i < res.length; i++) {
        if (res[i].startsWith("*")) {
          if (i > 1) {
            word.addDetail(temporaryDetail);
          }
          temporaryDetail = new Word.Detail();
          temporaryDetail.setWord_type(res[i].substring(1).trim());
        } else if (res[i].startsWith("-")) {
          if (!tmp.equals("")) {
            temporaryDetail.addExplanations(new Pair<>(tmp, list));
            list = new ArrayList<>();
          }
          tmp = res[i].substring(1).trim();
        } else if (res[i].startsWith("=")) {
          list.add(res[i].substring(1).trim());
        }
      }
      temporaryDetail.addExplanations(new Pair<>(tmp, list));
      word.addDetail(temporaryDetail);
      words.add(word);
      ++JDBCConnect.idx;
    }
    resultSet.close();
    statement.close();
    connection.close();
    return words;
  }

  /**
   * import data.
   *
   * @param word import word to database
   * @throws SQLException sql err
   */
  public static void insertToDatabase(Word word) throws SQLException {
    JDBCConnect.idx++;
    Connection connection = DriverManager.getConnection(url, username, password);
    connection.setAutoCommit(false);
    connection.getTransactionIsolation();

    PreparedStatement preparedStatement =
        connection.prepareStatement(
            "INSERT INTO english_vietnamese (idx, word, detail) VALUES (?,?,?);");
    preparedStatement.setInt(1, JDBCConnect.idx);
    preparedStatement.setString(2, word.getWord());
    preparedStatement.setString(3, word.toStringDetail());
    preparedStatement.addBatch();

    preparedStatement.executeBatch();
    connection.commit();
    preparedStatement.close();
    connection.close();
  }

  /**
   * edit or delete on db.
   *
   * @param word add
   * @param choice edit or delete
   * @throws SQLException SQL err
   */
  public static void editDatabase(Word word, String choice) throws SQLException {
    Connection connection = DriverManager.getConnection(url, username, password);
    connection.setAutoCommit(false);
    connection.getTransactionIsolation();
    PreparedStatement preparedStatement = null;
    System.out.println(choice);
    switch (choice) {
      case "edit":
        String query = "UPDATE english_vietnamese SET detail = ? WHERE word = ?";
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, word.toStringDetail());
        preparedStatement.setString(2, word.getWord());
        preparedStatement.addBatch();
        preparedStatement.executeBatch();
        break;
      case "delete":
        String sql = "DELETE FROM english_vietnamese WHERE word = ?";
        System.out.println(sql);
        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, word.getWord());
        preparedStatement.addBatch();
        preparedStatement.executeBatch();
        break;
      default:
        System.out.println("ERROR");
    }
    connection.commit();
    preparedStatement.close();
    connection.close();
  }
}
