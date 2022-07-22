package main;

import data.TranslateAPI;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.scene.control.TextArea;


public class TranslateController implements Initializable {
    @FXML
    private TextArea enterTextArea;            // văn bản nhập vào
    @FXML
    private TextArea translationTextArea;            // văn bản sau khi dịch

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    public void translateAction() {         // khi click dịch văn bản
        translationTextArea.setText(TranslateAPI.googleTranslate("en", "vi", enterTextArea.getText()));
    }
}
