package utilities.networking;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;
import utilities.errorPopup.ErrorPopup;


/**
 * 
 * Modified code from http://www.mkyong.com/java/how-to-send-http-request-getpost-in-java/
 * 
 * @author Chase Malik
 *
 */
public class HTTPConnection {
    private String myServer;
    private final String USER_AGENT = "Mozilla/5.0";

    public HTTPConnection (String serverURL) {
        myServer = serverURL;
    }

    public String sendGet (String url) {

        URL obj;
        int responseCode = 0;
        try {
            obj = new URL(myServer + url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            setRequestMethodAndProperties(con, "GET", "User-Agent", USER_AGENT);

            responseCode = con.getResponseCode();

            return responseToString(con);
        }
        catch (Exception e) {
            new ErrorPopup("Failed to get");
            return "";
        }

    }

    private String responseToString (HttpURLConnection con) throws IOException {
        BufferedReader in = new BufferedReader(
                                               new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        return response.toString();
    }

    private void setRequestMethodAndProperties (HttpURLConnection con, String s, String ... props)
                                                                                                  throws ProtocolException {
        con.setRequestMethod(s);
        for (int i = 0; i < props.length; i += 2) {
            con.setRequestProperty(props[i], props[i + 1]);
        }
    }

    // HTTP POST request
    public String sendPost (String url, String urlParameters) {

        URL obj;
        int responseCode = 0;
        try {
            obj = new URL(myServer + url);
            HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
            setRequestMethodAndProperties(con, "POST", "User-Agent", USER_AGENT, "Accept-Language",
                                          "en-US,en;q=0.5");

            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(urlParameters);
            wr.flush();
            wr.close();
            responseCode = con.getResponseCode();
            return responseToString(con);
        }
        catch (Exception e) {
            new ErrorPopup("Failed to post");
            return "";
        }
    }
}
