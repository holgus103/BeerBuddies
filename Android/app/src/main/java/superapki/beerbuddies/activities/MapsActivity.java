package superapki.beerbuddies.activities;

import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import superapki.beerbuddies.R;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private GoogleMap mMap;
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;

    protected synchronized void buildGoogleApiClient() {
        this.mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.buildGoogleApiClient();
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }
    @Override
    protected void onStart(){
        super.onStart();
        this.mGoogleApiClient.connect();
    }
    @Override
    protected void onStop(){
        super.onStop();
        this.mGoogleApiClient.disconnect();
    }
    @Override
    public void onConnected(Bundle var1) {
        this.mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        Log.d("success","success");
        if (this.mLastLocation != null) {
            LatLng myLoc = new LatLng(this.mLastLocation.getLatitude(), this.mLastLocation.getLongitude());
            this.mMap.addMarker(new MarkerOptions().position(myLoc).title("Marker in mLocation"));
            this.mMap.moveCamera(CameraUpdateFactory.newLatLng(myLoc));
        }
        }

    @Override
    public void onConnectionSuspended(int var1) {
        Log.d("suspended","suspended");
    }

    @Override
    public void onConnectionFailed(ConnectionResult var1) {
        Log.d("fail","fail");
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.mMap = googleMap;
//        this.loadMarkers();
        // Add a marker in Sydney and move the camera
//        getLastLo
    }

}
