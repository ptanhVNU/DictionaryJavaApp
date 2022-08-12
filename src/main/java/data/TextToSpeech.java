package data;

import javax.sound.sampled.*;
import javax.sound.sampled.DataLine.Info;

import static javax.sound.sampled.AudioSystem.getAudioInputStream;
import static javax.sound.sampled.AudioFormat.Encoding.PCM_SIGNED;

import java.io.*;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TextToSpeech {
  private static Map<String, String> langCode;
  private static SourceDataLine line;

  private static int rate; // -10 -> 10

  private static String voice = "Linda"; // famale or male

  private static float volume; // 0 -> 200

  private static boolean female;

  static {
    langCode = new HashMap<String, String>();
    langCode.put("Phát hiện ngôn ngữ", "en-us");
    langCode.put("English", "en-us");
    langCode.put("Vietnamese", "vi-vn");
    langCode.put("French", "fr-fr");
    langCode.put("Japanese", "ja-jp");
    langCode.put("Korean", "ko-kr");
  }

  public static float getVolume() {
    return volume;
  }

  public static void setVolume(float volume) {
    TextToSpeech.volume = volume;
  }

  public static float getPercentOfVolume() {
    return getVolume() / 2;
  }

  public static void setPercentOfVolume(double percentOfVolume) {
    setVolume((float) percentOfVolume * 2);
  }

  public static int getRate() {
    return rate;
  }

  public static int getPercentOfRate() {
    return (getRate() + 10) * 5;
  }

  /**
   * set speed speech
   *
   * @param rate
   * @throws IllegalArgumentException err range tts
   */
  public static void setRate(int rate) {
    if (rate < -10 || rate > 10) {
      throw new IllegalArgumentException("must be in the range -10 to 10");
    }
    TextToSpeech.rate = rate;
  }

  public static void setPercentOfRate(double percentOfRate) {
    setRate((int) percentOfRate / 5 - 10);
  }

  public static void setVoice(String lang) {
    if (isFemale()) {
      voice = "Linda";
    } else {
      voice = "John";
    }
  }

  public static boolean isFemale() {
    return female;
  }

  public static void setFemale(boolean female) {
    TextToSpeech.female = female;
  }

  /**
   * @param speakText content tts
   * @param lang voice name
   * @throws IOException err generate mp3 file
   */
  public static void generateMP3(String speakText, String lang) throws IOException {
    String APIKey = "07722e7f894c46dd94e7f03e6bc21a5f";
    byte[] stream;
    setVoice(lang);
    URL url =
        new URL(
            "http://api.voicerss.org/?"
                + "key="
                + URLEncoder.encode(APIKey, "UTF-8")
                + "&src="
                + URLEncoder.encode(speakText, "UTF-8")
                + "&v="
                + URLEncoder.encode(voice, "UTF-8")
                + "&hl="
                + URLEncoder.encode(langCode.get(lang), "UTF-8")
                + "&r="
                + URLEncoder.encode(String.valueOf(getRate()), "UTF-8")
                + "&f="
                + URLEncoder.encode("44khz_16bit_stereo", "UTF-8"));

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

  /**
   * play file mp3 was be generated
   *
   * @param filePath
   */
  public static void play(String filePath) {
    final File file = new File(filePath);

    try (final AudioInputStream in = getAudioInputStream(file)) {

      final AudioFormat outFormat = getOutFormat(in.getFormat());
      final Info info = new Info(SourceDataLine.class, outFormat);

      line = (SourceDataLine) AudioSystem.getLine(info);
      line.open(outFormat);
      final FloatControl volumeControl =
          (FloatControl) line.getControl(FloatControl.Type.MASTER_GAIN);
      volumeControl.setValue(20.0f * (float) Math.log10(volume / 100.0));
      if (line != null) {
        line.open(outFormat);
        line.start();
        stream(getAudioInputStream(outFormat, in), line);
        line.drain();
        line.stop();
      }

    } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
      throw new IllegalStateException(e);
    }
  }

  /**
   * fix format audio
   *
   * @param inFormat inp format to fix
   * @return new audio format
   */
  private static AudioFormat getOutFormat(AudioFormat inFormat) {
    final int ch = inFormat.getChannels();
    final float rate = inFormat.getSampleRate();
    return new AudioFormat(PCM_SIGNED, rate, 16, ch, ch * 2, rate, false);
  }

  /**
   * stream
   *
   * @param in audio inp
   * @param line line
   * @throws IOException tts err
   */
  private static void stream(AudioInputStream in, SourceDataLine line) throws IOException {
    final byte[] buffer = new byte[4096];
    for (int n = 0; n != -1; n = in.read(buffer, 0, buffer.length)) {
      line.write(buffer, 0, n);
    }
  }

  /**
   * enter speakText and lang to speech
   *
   * @param speakText content ts
   * @param lang voiceName
   * @throws IOException err tss
   */
  public static void speak(String speakText, String lang) throws IOException {
    generateMP3(speakText, lang);
    play("..\\DictionaryJavaApp\\src\\main\\resources\\data\\testAudio.mp3");
  }

  /**
   * Works just like {@link TextToSpeech#speak(String, String)} except the lang is always presumed
   * to be English.
   *
   * @see TextToSpeech#speak(String, String)
   */
  public static void speak(String speakText) throws IOException {
    generateMP3(speakText, "English");
    play("..\\DictionaryJavaApp\\src\\main\\resources\\data\\testAudio.mp3");
  }

  /**
   * export file setting content.
   *
   * @exception IOException not found file
   */
  public static void settingsExportToFile() {
    File file = new File("src\\main\\resources\\data\\settings.txt");
    try {
      FileWriter myWriter = new FileWriter(file);
      myWriter.write(isFemale() + "\n");
      myWriter.write(getVolume() + "\n");
      myWriter.write(getRate() + "\n");
      myWriter.close();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  /** import file setting content to change setting setup tss. */
  public static void settingsImportToFile() {
    try {
      BufferedReader reader =
          new BufferedReader(new FileReader(("src\\main\\resources\\data\\settings.txt")));
      String s = reader.readLine();
      if (s.equals("true")) {
        setFemale(true);
      } else {
        setFemale(false);
      }

      setVolume(Float.parseFloat(reader.readLine()));
      setRate(Integer.parseInt(reader.readLine()));

    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public static void main(String[] argc) throws IOException {
    settingsImportToFile();
    System.out.println(isFemale());
    System.out.println(getVolume());
    System.out.println(getRate());
  }
}
