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
//    public class AsyncRequest extends AsyncTask<NetworkTask<Void>, Void, Void> {
//
//        @Override
//        protected Void doInBackground(NetworkTask<Void>... params) {
//            switch(params[0].getType()){
//                case REGISTER: client.register();
//            }
//        }
//    }
    private ServiceClient client = new ServiceClient(null, null, "http://192.168.18.120:3000");
    public ServiceClient getClientInstance(){
        return this.client;
    }

}
