package superapki.beerbuddies.networking;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Kuba on 31/07/2016.
*/
public abstract class NetworkTask extends AsyncTask<String, Void, JSONArray>{
    protected abstract JSONArray doWork(String... params) throws JSONException;
    protected void onSuccess(JSONArray arr){

    }

    protected void onFailure(JSONArray arr){

    }

    protected void onException(Exception e){

    }

    @Override
    protected void onPostExecute(JSONArray arr){
        try {
            JSONObject obj = (JSONObject) arr.get(0);
            if(1 == (int) obj.get("status")){
                this.onSuccess(arr);
            }
            else{
                this.onFailure(arr);
            }
        }
        catch(JSONException e){
            this.onException(e);
        }

    }

    @Override
    protected JSONArray doInBackground(String... params) {
        try {
            return this.doWork(params);
        }
        catch(JSONException e){
            Log.d("EXCEPTION", e.getMessage());
            return null;
        }
    }

}