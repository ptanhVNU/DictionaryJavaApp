package main;

import data.TextToSpeech;
import data.TranslateAPI;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.HashMap;
import java.util.Map;

import javafx.scene.control.TextArea;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Button;

public class TranslateController implements Initializable {

  private Map<String, String> exchange;
  @FXML
  private TextArea enterTextArea;
  @FXML
  private TextArea translationTextArea;

  private String lastValueLangFromChoice;
  private String lastValueLangToChoice;
  @FXML
  private ChoiceBox<String> langFromChoice;
  @FXML
  private ChoiceBox<String> langToChoice;

  @FXML
  private Button swapLanguageButton;

  private void offAction(ActionEvent event) {
    return;
  }
  private void setValueLangFromChoice(String value) {
    langFromChoice.setOnAction(this::offAction);
    langFromChoice.setValue(value);
    langFromChoice.setOnAction(this::setValueLangFromChoiceAction);
  }

  private void setValueLangToChoice(String value) {
    langToChoice.setOnAction(this::offAction);
    langToChoice.setValue(value);
    langToChoice.setOnAction(this::setValueLangToChoiceAction);
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    langFromChoice.getItems().add("Phát hiện ngôn ngữ");
    langFromChoice.getItems().add("English");
    langFromChoice.getItems().add("Vietnamese");
    langFromChoice.getItems().add("French");
    langFromChoice.getItems().add("Japanese");
    langFromChoice.getItems().add("Korean");
    setValueLangFromChoice("Phát hiện ngôn ngữ");
    lastValueLangFromChoice = langFromChoice.getValue();

    langToChoice.getItems().add("English");
    langToChoice.getItems().add("Vietnamese");
    langToChoice.getItems().add("French");
    langToChoice.getItems().add("Japanese");
    langToChoice.getItems().add("Korean");
    setValueLangToChoice("Vietnamese");
    lastValueLangToChoice = langToChoice.getValue();

    swapLanguageButton.getStyleClass().add("disable");

    exchange = new HashMap<String, String>();
    exchange.put("Phát hiện ngôn ngữ", "");
    exchange.put("English", "en");
    exchange.put("Vietnamese", "vi");
    exchange.put("French", "fr");
    exchange.put("Japanese", "ja");
    exchange.put("Korean", "ko");
  }

  @FXML
  public void swapLanguageAction() { // khi click dịch văn bản
    if (!swapLanguageButton.getStyleClass().contains("disable")) {
      String temp = langFromChoice.getValue();
      setValueLangFromChoice(langToChoice.getValue());
      setValueLangToChoice(temp);
    }
  }

  @FXML
  public void setValueLangFromChoiceAction(ActionEvent event) {
    if (langFromChoice.getValue() == langToChoice.getValue()) {
      if (lastValueLangFromChoice == langFromChoice.getItems().get(0)) {
        if (langFromChoice.getValue() != langToChoice.getItems().get(0)) {
          setValueLangToChoice(langToChoice.getItems().get(0));
        } else {
          setValueLangToChoice(langToChoice.getItems().get(1));
        }
      } else {
        setValueLangToChoice(lastValueLangFromChoice);
      }
    }

    if (langFromChoice.getValue() == langFromChoice.getItems().get(0)) {
      swapLanguageButton.getStyleClass().add("disable");
    }
    else if (lastValueLangFromChoice == langFromChoice.getItems().get(0)) {
      swapLanguageButton.getStyleClass().removeAll("disable");
    }

    lastValueLangFromChoice = langFromChoice.getValue();
  }

  @FXML
  public void setValueLangToChoiceAction(ActionEvent event) {
    translationTextArea.setText("");
    
    if (langToChoice.getValue() == langFromChoice.getValue()) {
      setValueLangFromChoice(lastValueLangToChoice);
    }

    lastValueLangToChoice = langToChoice.getValue();
  }

  @FXML
  public void translateAction() {
    translationTextArea.setText(
        TranslateAPI.googleTranslate(
            exchange.get(langFromChoice.getValue()),
            exchange.get(langToChoice.getValue()),
            enterTextArea.getText()));
  }

  @FXML
  public void speakLangFromAction() {
      try {
        TextToSpeech.speak(enterTextArea.getText(), langFromChoice.getValue());
      } catch (IOException e) {
        e.printStackTrace();
      }
  }

  @FXML
  public void speakLangToAction() {
    try {
      TextToSpeech.speak(translationTextArea.getText(), langToChoice.getValue());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
