package app.dragdrop.com.countriesinfotask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class JSONParser {

    static InputStream in = null;
    int resCode = -1;
    static String json = "";

    public JSONParser() {

    }

    public String getJSONFromUrl(String urlStr) {
        try {

            URL url = new URL(urlStr);//use a proper url instead of onlineUrl
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("User-Agent", "");
            connection.setRequestMethod("GET");//we can use POST instead of GET method also.
            connection.setDoInput(true);
            connection.connect();
            InputStream inputStream = connection.getInputStream();
            if (inputStream != null) {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line = "";
                StringBuilder result = new StringBuilder();
                while ((line = bufferedReader.readLine()) != null)
                    result.append(line);
                json = result.toString();
                inputStream.close();

            }


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return json;

    }
}
