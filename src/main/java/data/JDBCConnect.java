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

    // export database được gọi ngay sau khi thao tác thêm 1 từ mới từ giao diện
    // thêm từ mới chỉ cần word_target và wơrd_explain
    // thêm vào cuối danh sách
    public static void exportDatabase(ArrayList<Word> wordList, String choice) throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/dictionary", username, password);
        connection.setAutoCommit(false);
        connection.getTransactionIsolation();
        PreparedStatement preparedStatement = null;
        switch (choice) {
            case "add":
                preparedStatement = connection.prepareStatement("INSERT INTO english_vietnamese (idx, word, detail) VALUES (?,?,?);");
                preparedStatement.setInt(1, wordList.size() + 1);
                preparedStatement.setString(2, wordList.get(wordList.size() - 1).getWord());
                preparedStatement.setString(3, wordList.get(wordList.size() - 1).details.get(0).getExplanations());
                preparedStatement.addBatch();
                break;
            case "edit":
                //TO DO: sử dụng câu lệnh UPDATE employees
                //SET
                //    email = 'mary.patterson@classicmodelcars.com'
                //WHERE
                //    employeeNumber = 1056;
                // cần tim được index của từ cần sửa1
                break;
            case "delete":
                break;
            default:
                System.out.println("error");
        }

        preparedStatement.executeBatch();
        connection.commit();
        preparedStatement.close();
        connection.close();
    }
}
