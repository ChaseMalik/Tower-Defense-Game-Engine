package utilities.networking;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
 
import javax.net.ssl.HttpsURLConnection;
import utilities.errorPopup.ErrorPopup;
 
public class HTTPConnection {
        private String myServer;
        private final String USER_AGENT = "Mozilla/5.0";
        
        public HTTPConnection(String serverURL){
            myServer = serverURL;
        }
        // HTTP GET request
        public String sendGet(String url) {
 
                URL obj;
                int responseCode = 0;
                try {
                    obj = new URL(myServer + url);
                    HttpURLConnection con = (HttpURLConnection) obj.openConnection();
                    
                    // optional default is GET
                    con.setRequestMethod("GET");
     
                    //add request header
                    con.setRequestProperty("User-Agent", USER_AGENT);
     
                    responseCode = con.getResponseCode();
                    System.out.println("\nSending 'GET' request to URL : " + url);
                    System.out.println("Response Code : " + responseCode);
     
                    BufferedReader in = new BufferedReader(
                            new InputStreamReader(con.getInputStream()));
                    String inputLine;
                    StringBuffer response = new StringBuffer();
     
                    while ((inputLine = in.readLine()) != null) {
                            response.append(inputLine);
                    }
                    in.close();
     
                    //print result
                    System.out.println(response.toString());
                    return response.toString();
                }
                catch (Exception e) {
                    new ErrorPopup("Unsuccesful post request" + responseCode);
                    return "";
                }
                
 
        }
 
        // HTTP POST request
        public String sendPost(String url, String urlParameters){
            
                URL obj;
                int responseCode = 0;
                try {
                    obj = new URL(myServer + url);
                    HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
                    
                    //add request header
                    con.setRequestMethod("POST");
                    con.setRequestProperty("User-Agent", USER_AGENT);
                    con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
     
                    // Send post request
                    con.setDoOutput(true);
                    DataOutputStream wr = new DataOutputStream(con.getOutputStream());
                    wr.writeBytes(urlParameters);
                    wr.flush();
                    wr.close();
     
                    responseCode = con.getResponseCode();
                    System.out.println("\nSending 'POST' request to URL : " + url);
                    System.out.println("Post parameters : " + urlParameters);
                    System.out.println("Response Code : " + responseCode);
     
                    BufferedReader in = new BufferedReader(
                            new InputStreamReader(con.getInputStream()));
                    String inputLine;
                    StringBuffer response = new StringBuffer();
     
                    while ((inputLine = in.readLine()) != null) {
                            response.append(inputLine);
                    }
                    in.close();
     
                    //print result
                    System.out.println(response.toString());
                    return response.toString();
                }
                catch (Exception e) {
                    new ErrorPopup("Unsuccesful get request" + responseCode);
                    return "";
                }
                
 
        }
 
}