package sg.edu.nus.ticketingstaffapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class Map extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        double lat = Double.parseDouble(pref.getString("Latitude", null));
        double lng = Double.parseDouble(pref.getString("Longtitude", null));

        LatLng myLocation = new LatLng(lat, lng);

        Marker myMarker = mMap.addMarker(new MarkerOptions().position(myLocation).title("Marker").snippet("HERE"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myLocation,20));
    }
}
