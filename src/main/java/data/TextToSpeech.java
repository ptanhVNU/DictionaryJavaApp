package data;

import javax.sound.sampled.*;
import javax.sound.sampled.DataLine.Info;

import static javax.sound.sampled.AudioSystem.getAudioInputStream;
import static javax.sound.sampled.AudioFormat.Encoding.PCM_SIGNED;

import java.io.*;
import java.net.URL;
import java.net.URLEncoder;

public class TextToSpeech {
  private static SourceDataLine line;

  private static int rate = 0; //-10 -> 10

  private static String voice = "Linda"; //famale or male

  private static float volume = 50; // 0 -> 200

  public static float getVolume() {
    return volume;
  }

  public static void setVolume(float volume) {
    TextToSpeech.volume = volume;
  }

  public static void setRate(int rate) {
    if (rate < -10 || rate > 10) {
      throw new IllegalArgumentException("must be in the range -10 to 10");
    }
    TextToSpeech.rate = rate;
  }

  public static void setVoice(boolean female) {
    if (female) {
      voice = "Linda";
    } else {
      voice = "John";
    }
  }

  public static void generateMP3(String speakText, boolean female) throws IOException {
    String APIKey = "07722e7f894c46dd94e7f03e6bc21a5f";
    byte[] stream;
    setVoice(female);
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
                + URLEncoder.encode("en-us", "UTF-8")
                + "&r="
                + URLEncoder.encode(String.valueOf(rate), "UTF-8")
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

  public static void play(String filePath) {
    final File file = new File(filePath);

    try (final AudioInputStream in = getAudioInputStream(file)) {

      final AudioFormat outFormat = getOutFormat(in.getFormat());
      final Info info = new Info(SourceDataLine.class, outFormat);

      line = (SourceDataLine) AudioSystem.getLine(info);
      line.open(outFormat);
      final FloatControl volumeControl = (FloatControl) line.getControl( FloatControl.Type.MASTER_GAIN );
      volumeControl.setValue( 20.0f * (float) Math.log10( volume / 100.0 ) );
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

  private static AudioFormat getOutFormat(AudioFormat inFormat) {
    final int ch = inFormat.getChannels();
    final float rate = inFormat.getSampleRate();
    return new AudioFormat(PCM_SIGNED, rate, 16, ch, ch * 2, rate, false);
  }

  private static void stream(AudioInputStream in, SourceDataLine line) throws IOException {
    final byte[] buffer = new byte[4096];
    for (int n = 0; n != -1; n = in.read(buffer, 0, buffer.length)) {
      line.write(buffer, 0, n);
    }
  }

  public static void speak(String speakText, boolean female) throws IOException {
    generateMP3(speakText, female);
    play("..\\DictionaryJavaApp\\src\\main\\resources\\data\\testAudio.mp3");
  }

  public static void main(String[] argc) throws IOException {
    speak("hello world", false);
    setVolume(200);
    speak("hello world", false);
  }
}
