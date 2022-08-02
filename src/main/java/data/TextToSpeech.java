// https://advancedtestautomation.blogspot.com/2016/08/text-to-speech-using-voice-rss-api.html
package data;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;
import java.io.File;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class TextToSpeech {
  public static void generateMP3(String speakText) throws IOException {
    String APIKey = "07722e7f894c46dd94e7f03e6bc21a5f";
    byte[] stream;

    URL url =
        new URL(
            "http://api.voicerss.org/?"
                + "key="
                + URLEncoder.encode(APIKey, "UTF-8")
                + "&src="
                + URLEncoder.encode(speakText, "UTF-8")
                + "&hl="
                + URLEncoder.encode("en-us", "UTF-8")
                + "&f="
                + URLEncoder.encode("44khz_16bit_mono", "UTF-8"));

    InputStream inps = new BufferedInputStream(url.openStream());
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    byte[] buffer = new byte[1024];
    int i = 0;
    while (-1 != (i = inps.read(buffer))) {
      out.write(buffer, 0, i);
    }
    out.close();
    inps.close();

    stream = out.toByteArray();

    FileOutputStream fos =
        new FileOutputStream("..\\DictionaryJavaApp\\src\\main\\resources\\data\\testAudio.mp3");
    fos.write(stream);
    fos.close();
  }

  public static void speak(String speakText) throws IOException {
    generateMP3(speakText);
    AudioFilePlayer.play("..\\DictionaryJavaApp\\src\\main\\resources\\data\\testAudio.mp3");
  }
}
