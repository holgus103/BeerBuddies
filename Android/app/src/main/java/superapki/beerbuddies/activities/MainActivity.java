package superapki.beerbuddies.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONException;

import superapki.beerbuddies.R;
import superapki.beerbuddies.networking.ServiceClient;
import superapki.beerbuddies.global.BeerBuddies;

public class MainActivity extends AppCompatActivity {
    public static String login;
    public static String password;
    public static String email;
    public final static String EDIT_TEXT = "superapki.beerbuddies.MESSAGE";

    private class LoginSend extends AsyncTask<String, Void, JSONArray> {

        @Override
        protected JSONArray doInBackground(String... params) {
            try {
                return ((BeerBuddies) getApplication()).getClientInstance().register(params[0], params[1], params[2]);
            }
            catch(JSONException e){
                Log.d("EXCEPTION", e.getMessage());
                return null;
            }
        }

        @Override
        protected void onPostExecute(JSONArray arr){
            // do sucess / failure logic here
        }
    }

    private void register(){
        this.login = ((EditText) findViewById(R.id.etName)).getText().toString();
        this.password = ((EditText) findViewById(R.id.etPassword)).getText().toString();
        this.email = ((EditText) findViewById(R.id.etEmail)).getText().toString();
        new LoginSend().execute(this.login, this.password, this.email);


    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(MainActivity.login == null && MainActivity.password == null) {
            setContentView(R.layout.activity_login);
            ((Button) findViewById(R.id.btnRegister))
                    .setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            register();
                        }
                    });
        }
        else{
            setContentView(R.layout.activity_main);
        }
    }

    public void startMap(View view) {
        Intent intent = new Intent(this,MapsActivity.class);
//        EditText editText = (EditText) findViewById(R.id.acti);
//        intent.putExtra(EDIT_TEXT,editText.getText().toString());
        startActivity(intent);
    }

    public void startLogin(View view){
        Intent intent = new Intent(this,LoginActivity.class);
        startActivity(intent);
    }
}
