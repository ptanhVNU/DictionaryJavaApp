package data;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

public class TextToSpeech {
    private static String text;

    private static float speed = 150;

    private static float volume = 1.0f;

    private static float pitchShift = 1.0f;

    public static void setSpeed(float speed) {
        TextToSpeech.speed = speed;
    }

    public static String getText() {
        return text;
    }

    public static void setText(String text) {
        TextToSpeech.text = text;
    }

    public static double getSpeed() {
        return speed;
    }

    public static double getVolume() {
        return volume;
    }

    public static void setVolume(float volume) {
        TextToSpeech.volume = volume;
    }

    public static float getPitchShift() {
        return pitchShift;
    }

    public static void setPitchShift(float pitchShift) {
        TextToSpeech.pitchShift = pitchShift;
    }

    public static void speakText() {
        System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");
        Voice voice = VoiceManager.getInstance().getVoice("kevin16");
        if (voice != null) {
            voice.allocate();
        }
        try {
            assert voice != null;
            voice.setRate(speed); //50 -> 150 -> 400
            voice.setPitch(100);
            voice.setVolume(volume); //0.6 -> 1 -> 1
            voice.setPitchRange(5);
            voice.setPitchShift(3); // 0 or 1 or 2 or 3
            voice.speak(text);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
