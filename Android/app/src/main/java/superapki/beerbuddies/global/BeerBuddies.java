package superapki.beerbuddies.global;

import android.app.Application;
import android.location.Location;
import android.location.LocationListener;
import android.os.AsyncTask;
import android.os.Bundle;

import org.json.JSONArray;
import org.json.JSONException;

import superapki.beerbuddies.networking.ServiceClient;
import superapki.beerbuddies.networking.NetworkTask;

/**
 * Created by Kuba on 2016-07-09.
 */
public class BeerBuddies extends Application implements LocationListener {

    private class SendLocationUpdate extends NetworkTask{

        @Override
        protected JSONArray doWork(Object... params) throws JSONException {
            return client.updateLocation((Double)params[0], (Double)params[1]);
        }

    }
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

    private void updateLocation(Double longitude, Double latitude){
        new SendLocationUpdate().execute(longitude, latitude);
    }

    @Override
    public void onLocationChanged(Location location) {
            this.updateLocation(location.getLongitude(), location.getLatitude());

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
