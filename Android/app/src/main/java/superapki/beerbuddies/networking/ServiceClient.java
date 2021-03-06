package superapki.beerbuddies.networking;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
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
public class ServiceClient {

    private class Parameters{
        public static final String USERNAME = "username";
        public static final String PASSWORD = "password";
        public static final String EMAIL = "email";
        public static final String DISTANCE = "distance";
        public static final String LONGITUDE = "longitude";
        public static final String LATITUDE = "latitude";
        public static final String MEETING_START = "meetingStart";
        public static final String MEETING_STOP = "meetingStop";
        public static final String MEETING_TYPE = "meetingType";
        public static final String MEETING_ID = "meetingId";
             
        
        
    }

    private class Routes{
        public static final String GET_BUDDIES = "/getBuddies";
        public static final String UPDATE_LOCATION = "/updateLocation";
        public static final String REGISTER = "/register";
        public static final String SEND_MESSAGE = "/sendMessage";
        public static final String GET_MESSAGES = "/getMessage";        
        public static final String CREATE_MEETING = "/createMeeting";
        public static final String GET_MEETINGS = "/getMeetings";
        public static final String JOIN_MEETING = "/joinMeeting";
        
    }

    private static final int CONNECTION_TIMEOUT = 30000;
    private static final int READ_TIMEOUT = 30000;
    private static final boolean SSL_ENABLED = false;
    
    private String url = "http://localhost:3000";
    private String username;
    private String password;

    private void appendCredentials(HashMap<String, String> map){
        map.put(Parameters.USERNAME, this.username);
        map.put(Parameters.PASSWORD, this.password);
    }
    private String communicate(String route, HashMap<String,String> params){
        HttpURLConnection connection;
        try {
            URL url = new URL(this.url + route);

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

    public ServiceClient(){
        
    }
    
    public ServiceClient(String username, String password){
        this.username = username;
        this.password = password;
    }
    
    public ServiceClient(String username, String password, String url){
        this.username = username;
        this.password = password;
        this.url = url;
        
    }
    
    public JSONArray getBuddies(Double distance) throws JSONException{
        HashMap<String,String> map = new HashMap<String,String>();
        this.appendCredentials(map);
        map.put(Parameters.DISTANCE, distance.toString());
        return new JSONArray(this.communicate(Routes.GET_BUDDIES, map));

    }

    public JSONArray updateLocation(Double longitude, Double latitude) throws JSONException{
        JSONArray arr = new JSONArray();
        HashMap<String, String> map = new HashMap<String, String>();
        this.appendCredentials(map);
        map.put(Parameters.LONGITUDE, longitude.toString());
        map.put(Parameters.LATITUDE, latitude.toString());
        arr.put(new JSONObject(this.communicate(Routes.UPDATE_LOCATION, map)));
        return arr;
    }

    public JSONArray register(String username, String password, String email) throws JSONException{
       JSONArray arr = new JSONArray();
       HashMap<String, String> map = new HashMap<String, String>();
       map.put(Parameters.USERNAME, username);
       this.username = username;
       map.put(Parameters.PASSWORD, password);
       this.password = password;
       map.put(Parameters.EMAIL, email);
       arr.put(new JSONObject(this.communicate(Routes.REGISTER, map)));
       return arr;
    }
    
    public JSONArray createMeeting(Date start, Date end, Short type) throws JSONException{
       JSONArray arr = new JSONArray();
       if(!start.before(end)) return null;
       HashMap<String, String> map = new HashMap<String,String>();
       this.appendCredentials(map);
       map.put(Parameters.MEETING_START, start.toGMTString());
       map.put(Parameters.MEETING_STOP, end.toGMTString());
       map.put(Parameters.MEETING_TYPE, type.toString());
       arr.put(new JSONObject(this.communicate(Routes.CREATE_MEETING, map)));
       return arr;
    }

    public JSONArray getMeetings(Double distance) throws JSONException{
        HashMap<String, String> map = new HashMap<String, String>();
        this.appendCredentials(map);
        map.put(Parameters.DISTANCE, distance.toString());
        return new JSONArray(this.communicate(Routes.GET_MEETINGS, map));
    }
    
    public JSONArray joinMeeting(Integer meetingId) throws JSONException{
        JSONArray arr = new JSONArray();
        HashMap<String, String> map = new HashMap<String, String>();
        this.appendCredentials(map);
        map.put(Parameters.MEETING_ID, meetingId.toString());
        arr.put(new JSONObject(this.communicate(Routes.JOIN_MEETING, map)));
        return arr;
    }

    public void sendMessageOnMeeting(){

    }

    public void getMessagesForMeeting(){

    }

    public String getUsername(){
        return this.username;
    }

}
