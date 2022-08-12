package data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class TranslateAPI {
  /**
   * gg trans api
   *
   * @param langFrom lang initial
   * @param langTo lang final
   * @param text content
   * @return String text with lang final
   */
  public static String googleTranslate(String langFrom, String langTo, String text) {
    try {
      String urlScript =
          "https://script.google.com/macros/s/AKfycbw1qSfs1Hvfnoi3FzGuoDWijwQW69eGcMM_iGDF7p5vu1oN_CaFqIDFmCGzBuuGCk_N/exec"
              + "?q="
              + URLEncoder.encode(text, "UTF-8")
              + "&target="
              + langTo
              + "&source="
              + langFrom;
      URL url = new URL(urlScript);
      StringBuilder response = new StringBuilder();
      HttpURLConnection con = (HttpURLConnection) url.openConnection();
      con.setRequestProperty("User-Agent", "Mozilla/5.0");
      BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
      String inputLine;
      while ((inputLine = in.readLine()) != null) {
        response.append(inputLine);
      }
      in.close();
      return response.toString();
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }
}
