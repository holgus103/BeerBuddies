package superapki.beerbuddies;

import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.facebook.FacebookSdk;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, View.OnClickListener {
    private GoogleApiClient mApiClient;
    private static int RC_SIGN_IN = 9988;
    private GoogleSignInAccount mUserAcc;

    private void signIn(){
        Intent intent = Auth.GoogleSignInApi.getSignInIntent(mApiClient);
        startActivityForResult(intent, this.RC_SIGN_IN);

    }

    private void handleSignInResult(GoogleSignInResult result){
        Log.d("google authentication", "result " + result.isSuccess());
        TextView info = (TextView) findViewById(R.id.login_tvMsg);
        if(result.isSuccess()){
            this.mUserAcc = result.getSignInAccount();
            info.setText(getString(R.string.authentication_success) + " " + this.mUserAcc.getDisplayName());
        }
        else{
            info.setText(getString(R.string.authentication_failure));
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode == this.RC_SIGN_IN){
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            this.handleSignInResult(result);
        }
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //Configure Google API
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        this.mApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        findViewById(R.id.btnSignIn).setOnClickListener(this);
        //Facebook login leftovers
        //FacebookSdk.sdkInitialize(getApplicationContext());

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btnSignIn:
                this.signIn();
                break;
        }
    }
}
