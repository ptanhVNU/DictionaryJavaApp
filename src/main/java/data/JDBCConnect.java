package data;

import java.sql.*;
import java.util.ArrayList;

public class JDBCConnect {
    static final String url = "jdbc:mysql://localhost:3306/dictionary";
    static String password = "matkhau123";
    static String username = "root";
    static ArrayList<Word> words = new ArrayList<>();

    /**
     * Import Database for dictionary
     *
     * @return list of words to add other dictionary
     * @throws SQLException
     */
    public static ArrayList<Word> importDatabase() throws SQLException {
        Connection connection = DriverManager.getConnection(url, username, password);
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * from english_vietnamese order by idx ASC;");
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
            word.setPronunciation(res[0]);
            Word.Detail temporaryDetail = new Word.Detail();
            for (int i = 1; i < res.length; i++) {
                if (res[i].startsWith("*")) {
                    if (i > 1) {
                        word.addDetail(temporaryDetail);
                    }
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

    /**
     * import data
     *
     * @param word
     * @throws SQLException
     */
    public static void insertToDatabase(Word word) throws SQLException {
        Connection connection = DriverManager.getConnection(url, username, password);
        Statement statement = connection.createStatement();
        ResultSet resultSet =
                statement.executeQuery("SELECT COUNT(detail) FROM english_vietnamese WHERE 1");
        resultSet.next();
        String count = resultSet.getString("COUNT(detail)");
        connection.setAutoCommit(false);
        connection.getTransactionIsolation();

        PreparedStatement preparedStatement =
                connection.prepareStatement(
                        "INSERT INTO english_vietnamese (idx, word, detail) VALUES (?,?,?);");
        preparedStatement.setInt(1, Integer.parseInt(count) + 1);
        preparedStatement.setString(2, word.getWord());
        preparedStatement.setString(3, word.toStringDetail());
        preparedStatement.addBatch();

        resultSet.close();
        statement.close();
        preparedStatement.executeBatch();
        connection.commit();
        preparedStatement.close();
        connection.close();
    }

    /**
     * edit or delete on db
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
