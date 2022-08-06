package main;

import data.TextToSpeech;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.scene.control.Slider;
import javafx.scene.control.ToggleButton;

public class SettingController implements Initializable {
    @FXML
    private Slider volumeSlider;

    @FXML
    private Slider speedSlider;

    @FXML
    private ToggleButton speakerToggleButton;
    public void initialize(URL location, ResourceBundle resources) {
        volumeSlider.setValue(TextToSpeech.getPercentOfVolume());
        speedSlider.setValue(TextToSpeech.getPercentOfRate());
    }

    @FXML
    public void slideVolumeAction() {
        TextToSpeech.setPercentOfVolume(volumeSlider.getValue());
    }

    @FXML
    public void slideRateAction() {
        TextToSpeech.setPercentOfRate(speedSlider.getValue());
    }
}
