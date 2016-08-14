package superapki.beerbuddies.global;

import android.app.Application;
import android.os.AsyncTask;

import org.json.JSONArray;

import superapki.beerbuddies.networking.ServiceClient;
import superapki.beerbuddies.networking.NetworkTask;

/**
 * Created by Kuba on 2016-07-09.
 */
public class BeerBuddies extends Application {
    private static BeerBuddies instance;
    private ServiceClient client = new ServiceClient("TurboJude", "Jude" , "http://192.168.18.120:3000");
    public ServiceClient getClientInstance(){
        return this.client;
    }
    public boolean isAuthenticated(){
        return true;
    }

    public String getUsername(){
        return this.client.getUsername();
    }

    public static BeerBuddies getInstance(){
        if(BeerBuddies.instance == null){
            BeerBuddies.instance = new BeerBuddies();
        }
        return BeerBuddies.instance;
    }

}
