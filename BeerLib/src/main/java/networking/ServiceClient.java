package networking;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import javax.net.ssl.HttpsURLConnection;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Kuba
 */
public class ServiceClient {// extends AsyncTask<String,Void, String> {

    private class Parameters{
        public static final String USERNAME = "username";
        public static final String PASSWORD = "password";
        public static final String DISTANCE = "distance";
        public static final String LONGITUDE = "longitude";
        public static final String LATITUDE = "latitude";
    }

    private class Routes{
        public static final String GET_BUDDIES = "/getBuddies";
        public static final String UPDATE_LOCATION = "/updateLocation";
    }

    private static final String SERVER_ADDRESS = "http://localhost:3000";
    private static final int CONNECTION_TIMEOUT = 3000;
    private static final int READ_TIMEOUT = 3000;
    private static final boolean SSL_ENABLED = false;

    private String username = "juzek";
    private String password = "elohaselo";

    private void appendCredentials(HashMap<String, String> map){
        map.put(Parameters.USERNAME, this.username);
        map.put(Parameters.PASSWORD, this.password);
    }
    private String communicate(String route, HashMap<String,String> params){
        HttpURLConnection connection;
        try {
            URL url = new URL(ServiceClient.SERVER_ADDRESS + route);

            // create a post data object
            StringBuilder builder = new StringBuilder();
            for(Map.Entry<String, String> val : params.entrySet()){

                if(builder.length() != 0)
                    builder.append('&');

                builder.append(val.getKey());
                builder.append('=');
                builder.append(val.getValue());
            }

            byte[] postData = builder.toString().getBytes("UTF-8");

            if(ServiceClient.SSL_ENABLED) {
                connection = (HttpsURLConnection) url.openConnection();
            }
            else{
                connection = (HttpURLConnection) url.openConnection();
            }

            connection.setConnectTimeout(ServiceClient.CONNECTION_TIMEOUT);
            connection.setReadTimeout(ServiceClient.READ_TIMEOUT);
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setRequestProperty("Content-Length", String.valueOf(postData.length));
            connection.getOutputStream().write(postData);

            builder = new StringBuilder();
            InputStreamReader in =  new InputStreamReader(connection.getInputStream(), "UTF-8");
            for(int c; (c = in.read()) >= 0;){
                builder.append((char)c);
            }
            connection.disconnect();
            return builder.toString();

        }
        catch (MalformedURLException e){
            return null;
        }
        catch (IOException e){
            return null;
        }
    }

    public JSONArray getBuddies(Double distance) throws JSONException{
        HashMap<String,String> map = new HashMap<String,String>();
        this.appendCredentials(map);
        map.put(Parameters.DISTANCE, distance.toString());
        return new JSONArray(this.communicate(Routes.GET_BUDDIES, map));

    }

    public JSONObject updateLocation(Double longitude, Double latitude){
       HashMap<String, String> map = new HashMap<String, String>();
       this.appendCredentials(map);
       map.put(Parameters.LONGITUDE, longitude.toString());
       map.put(Parameters.LATITUDE, latitude.toString());
       return new JSONObject(this.communicate(Routes.UPDATE_LOCATION, map));
    }

    public void createMeeting(){

    }

    public void getMeetings(){

    }

    public void sendMessageOnMeeting(){

    }

    public void getMessagesForMeeting(){

    }

}
