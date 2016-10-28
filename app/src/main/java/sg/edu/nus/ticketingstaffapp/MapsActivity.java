package sg.edu.nus.ticketingstaffapp;

//import android.Manifest;

import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import static android.content.ContentValues.TAG;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,
        com.google.android.gms.location.LocationListener, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    private GoogleMap mMap;
    LocationRequest mLocationRequest;
    GoogleApiClient mGoogleApiClient;
    double lat = 0;
    double lon = 0;
    Location mCurrentLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000); //the unit here is every ten sec will have an update.
        mLocationRequest.setFastestInterval(5000); //max rate that you want to get updated
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    public void onStop() {
        super.onStop();
        mGoogleApiClient.disconnect();
    }

    @Override
    public void onPause() {
        super.onPause();
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mGoogleApiClient.isConnected()) {
            //put android.Manifest.permission ... if Manifest.permision is red in colour
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                PendingResult<Status> pendingResult = LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
            }
        }
    }

    @Override
    public void onConnected(Bundle bundle) {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            PendingResult<Status> pendingResult = LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.d(TAG, "Connection failed: " + connectionResult.toString());
    }

    @Override
    public void onLocationChanged(Location location) {

        mCurrentLocation = location;
        System.out.println("Hello 1");
        if (mCurrentLocation != null) {
            double latitude = mCurrentLocation.getLatitude();
            double longtitude = mCurrentLocation.getLongitude();

            lat = latitude;
            lon = longtitude;

            System.out.println("Hello 2");
            System.out.println("Latitude: " + lat);
            System.out.println("Longtitude: " + lon);
            onMapReady(mMap);
            /*
            LatLng latLng = new LatLng(latitude, longtitude);

            System.out.println("Hello 12");
            mMap.addMarker(new MarkerOptions().position(latLng).title("HERE"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            onMapReady(mMap); */
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng location = new LatLng(lat, lon);
        System.out.println("Hello 3");
        Marker marker = mMap.addMarker(new MarkerOptions().position(location).title("HERE").snippet("This is me"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(location));
        System.out.println("Hello 4");
        System.out.println("Latitude: " + lat);
        System.out.println("Longtitude: " + lon);
    }

    //in location services put it under services, then use sharedpreferences to share it
    //create another map and ask it to read the sharedpreferences

    //broadcast listener to poll the battery. control frequency of polling. broadcast receiver like for checking
    //check battery using broadcast listener. cos battery use stickyintent.
    //track battery. if battery fall below some perecnetage, tell location service not to post so often. or dont show the map at all.


}


