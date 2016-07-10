package superapki.beerbuddies.networking;

import android.app.Service;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.ClientInfoStatus;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Kuba on 2016-07-05.
 */
public class ServiceClient extends AsyncTask<String,Void, String> {

    private class Parameters{
        public static final String USERNAME = "Username";
        public static final String PASSWORD = "Password";
        public static final String DISTANCE = "Distance";
    }

    private class Routes{
        public static final String GET_BUDDIES = "/getBuddies";
    }

    private static final String SERVER_ADDRESS = "localhost:3000";
    private static final int CONNECTION_TIMEOUT = 3000;
    private static final int READ_TIMEOUT = 3000;
    private static final boolean SSL_ENABLED = false;

    private String username;
    private String password;

    private String communicate(String route, HashMap<String,String> params){
        try {
            URL url = new URL(ServiceClient.SERVER_ADDRESS + route);
            HttpURLConnection connection;

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
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setRequestProperty("Content-Length", String.valueOf(postData.length));
            connection.getOutputStream().write(postData);

            builder = new StringBuilder();
            InputStreamReader in =  new InputStreamReader(connection.getInputStream(), "UTF-8");
            for(int c; (c = in.read()) >= 0;){
                builder.append((char)c);
            }

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
        map.put(Parameters.USERNAME, this.username);
        map.put(Parameters.PASSWORD, this.password);
        map.put(Parameters.DISTANCE, distance.toString());
        return new JSONArray(this.communicate(Routes.GET_BUDDIES, map));

    }

    public void updateLocation(){

    }

    public void createMeeting(){

    }

    public void getMeetings(){

    }

    public void sendMessageOnMeeting(){

    }

    public void getMessagesForMeeting(){

    }

    @Override
    protected String doInBackground(String... params) {
        return null;
    }
}
