package main;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.scene.control.TextField;
import javafx.scene.control.ListView;
import javafx.scene.control.Label;

public class SearchController implements Initializable {
    @FXML
    private TextField searchField;         // phần trong thanh tìm kiếm
    @FXML
    private ListView<String> searchList;  // danh sách khi nhập từ
    @FXML
    private Label searchWord;             // hiển thị từ được tra

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    void searchPressKeyBoard() throws IOException {    // khi nhập từ
        searchList.getItems().clear();
        String lookUp = searchField.getText();
    }
}
