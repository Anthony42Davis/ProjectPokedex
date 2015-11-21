package http;

import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Anthony on 2015/10/01.
 * https://developer.android.com/training/basics/network-ops/connecting.html
 * https://en.wikipedia.org/wiki/List_of_HTTP_status_codes
 * Links to the api
 */
public class RequestObject
{
    String Url = "http://pokeapi.co/";

    public RequestObject(String url)
    {
       Url += url ;
    }

    // Reads an InputStream and converts it to a String.
    public String readResponse(InputStream stream) throws IOException {
        StringBuilder stringJSON = new StringBuilder();
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        String line;
        while ((line = reader.readLine()) != null) {
            stringJSON.append(line);
        }

        return stringJSON.toString();
    }

    public JSONObject getInformation()
    {
        JSONObject JObject = new JSONObject();
        InputStream is;

        try
        {
            URL url = new URL(Url);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            // Starts the query
            conn.connect();

            //checks for valid response from api
            int response = conn.getResponseCode();
            if(response == 200)
            {
                is = conn.getInputStream();
                // Convert the InputStream into a string
                String contentAsString = readResponse(is);
                JObject = new JSONObject(contentAsString);

                //Closes the input stream
                is.close();
            }
        }
        catch (IOException | JSONException e)
        {
            e.printStackTrace();
        }

        return JObject;
    }//ends jsonobject
}
