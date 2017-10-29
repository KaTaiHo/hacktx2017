package com.example.kataiho.hacktx2017;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;



public class DisasterMap extends FragmentActivity implements OnMapReadyCallback, View.OnClickListener {

    private GoogleMap mMap;
    private Button button;


    private LocationManager locationManager;
    private LocationListener listener;

    public Double longitude;
    public Double latitude;

    public MarkerOptions marker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disaster_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        button = (Button) findViewById(R.id.nextButton);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View view){
        if(view == button){
            Intent event = new Intent(getBaseContext(), DisasterDetails.class);

           latitude =  marker.getPosition().latitude;
           longitude = marker.getPosition().longitude;

            event.putExtra("latitude", latitude);
            event.putExtra("longitude", longitude);
            startActivity(event);
        }
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
        mMap = googleMap;
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        listener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                latitude = location.getLatitude();
                longitude = location.getLongitude();
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {
                Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(i);
            }
        };

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{
                                android.Manifest.permission.ACCESS_COARSE_LOCATION,
                                android.Manifest.permission.ACCESS_FINE_LOCATION,
                                android.Manifest.permission.INTERNET}
                        ,10);
            }
        }
        Criteria criteria = new Criteria();
        String provider = locationManager.getBestProvider(criteria, false);
        final Location location = locationManager.getLastKnownLocation(provider);
        if (location != null) {
            onLocationChanged(location);
        } else {
            locationManager.requestSingleUpdate(provider, listener, null);
        }

//        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, listener);
        // Add a marker in Sydney and move the camera
        System.out.println("Lat: " + latitude);
        System.out.println("Long: " + longitude);

//        mMap.getUiSettings().setScrollGesturesEnabled(false);
        LatLng currLocation = new LatLng(latitude, longitude);
        mMap.setMinZoomPreference(1.0f);
        mMap.setMaxZoomPreference(20.0f);
        float zoom = 15.0f;
        marker = new MarkerOptions().position(currLocation).title("Here now!").draggable(true);
        mMap.addMarker(marker);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(currLocation));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currLocation, zoom), 2000, null);

        mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDragStart(Marker arg0) {
                // TODO Auto-generated method stub
                // Log.d("System out", "onMarkerDragStart..."+arg0.getPosition().latitude+"..."+arg0.getPosition().longitude);
            }

            @SuppressWarnings("unchecked")
            @Override
            public void onMarkerDragEnd(Marker arg0) {
                // TODO Auto-generated method stub
                // Log.d("System out", "onMarkerDragEnd..."+arg0.getPosition().latitude+"..."+arg0.getPosition().longitude);
//                arg0.setPosition(new LatLng(location.getLatitude(), location.getLongitude()));
                mMap.animateCamera(CameraUpdateFactory.newLatLng(arg0.getPosition()));
                marker = new MarkerOptions().position(new LatLng(arg0.getPosition().latitude, arg0.getPosition().longitude));
            }

            @Override
            public void onMarkerDrag(Marker arg0) {
                // TODO Auto-generated method stub
                Log.i("System out", "onMarkerDrag...");
            }
        });

    }

    public void onLocationChanged(Location location) {
        latitude = (Double) (location.getLatitude());
        longitude = (Double) (location.getLongitude());
    }
}
