package superapki.beerbuddies.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import superapki.beerbuddies.R;
import superapki.beerbuddies.global.BeerBuddies;
import superapki.beerbuddies.networking.NetworkTask;

public class MainActivity extends BeerBuddiesActivity {

    private class SendLocationUpdate extends NetworkTask{

        @Override
        protected JSONArray doWork(Object... params) throws JSONException {
            return beerBuddies.getClientInstance().updateLocation((Double)params[0], (Double)params[1]);
        }

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(!this.beerBuddies.isAuthenticated()) {
            this.startActivity(LoginActivity.class);
        }
        else{
            this.setContentView(R.layout.activity_main);
            // prepare controls
            TextView tvLogin = (TextView) findViewById(R.id.tvLogin);
            tvLogin.setText(this.beerBuddies.getUsername());
//            new SendLocationUpdate().execute(new Double(5.0), new Double(5.0));
            Button searchButton = (Button) findViewById(R.id.btnSearch);
            searchButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(MapsActivity.class);
                }
            });
        }
    }
}
