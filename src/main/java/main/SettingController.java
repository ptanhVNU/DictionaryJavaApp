package main;

import data.TextToSpeech;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.scene.control.*;

public class SettingController implements Initializable {
    @FXML
    private Slider volumeSlider;

    @FXML
    private Slider speedSlider;

    @FXML
    private RadioButton femaleRadioButton;

    @FXML
    private RadioButton maleRadioButton;

    @FXML
    private ToggleButton speakerToggleButton;
    public void initialize(URL location, ResourceBundle resources) {
        volumeSlider.setValue(TextToSpeech.getPercentOfVolume());
        speedSlider.setValue(TextToSpeech.getPercentOfRate());

        if (TextToSpeech.isFemale()) {
            femaleRadioButton.setSelected(true);
        } else {
            maleRadioButton.setSelected(true);
        }
    }

    @FXML
    public void slideVolumeAction() {
        TextToSpeech.setPercentOfVolume(volumeSlider.getValue());
    }

    @FXML
    public void slideRateAction() {
        TextToSpeech.setPercentOfRate(speedSlider.getValue());
    }

    @FXML
    public void getVoice() {
        if (femaleRadioButton.isSelected()) {
            TextToSpeech.setFemale(true);
        } else {
            TextToSpeech.setFemale(false);
        }
    }
}
