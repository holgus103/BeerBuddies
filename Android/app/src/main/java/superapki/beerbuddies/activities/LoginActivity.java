package superapki.beerbuddies.activities;

import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.facebook.FacebookSdk;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import superapki.beerbuddies.R;
import superapki.beerbuddies.global.BeerBuddies;
import superapki.beerbuddies.networking.NetworkTask;

public class LoginActivity extends BeerBuddiesActivity implements GoogleApiClient.OnConnectionFailedListener {

    private BeerBuddies beerbuddies = (BeerBuddies) getApplication();
    private String login;
    private String password;
    private String email;

    private class LoginSend extends NetworkTask {

        @Override
        protected JSONArray doWork(Object... params) throws JSONException {
            return beerBuddies.getClientInstance().register((String)params[0], (String)params[1], (String)params[2]);
        }

        @Override
        protected void onSuccess(JSONArray arr){
            startActivity(MainActivity.class);
        }
    }

    private void register(){
        this.login = ((EditText) findViewById(R.id.etName)).getText().toString();
        this.password = ((EditText) findViewById(R.id.etPassword)).getText().toString();
        this.email = ((EditText) findViewById(R.id.etEmail)).getText().toString();
        new LoginSend().execute(this.login, this.password, this.email);


    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ((Button) findViewById(R.id.btnRegister))
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        register();
                    }
                });

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }


}
