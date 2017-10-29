package com.example.kataiho.hacktx2017;

import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainMapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    public FirebaseAuth mAuth;
    public FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference mDatabase;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };
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

        final ArrayList<List<Double>> myEmergCoordinates = new ArrayList<List<Double>>();
        final ArrayList<String> typeOfEmergency = new ArrayList<String>();

        final HashMap<String, Float> colorMap = new HashMap<>();
        colorMap.put("Blizzard", new Float(BitmapDescriptorFactory.HUE_AZURE));
        colorMap.put("Eruption", new Float(BitmapDescriptorFactory.HUE_RED));
        colorMap.put("Earthquake", new Float(BitmapDescriptorFactory.HUE_YELLOW));
        colorMap.put("Flood", new Float(BitmapDescriptorFactory.HUE_BLUE));
        colorMap.put("Hurricane", new Float(BitmapDescriptorFactory.HUE_CYAN));
        colorMap.put("Storm", new Float(BitmapDescriptorFactory.HUE_MAGENTA));
        colorMap.put("Tsunami", new Float(BitmapDescriptorFactory.HUE_GREEN));
        colorMap.put("Tornado", new Float(BitmapDescriptorFactory.HUE_VIOLET));
        colorMap.put("Wildfire", new Float(BitmapDescriptorFactory.HUE_ORANGE));

        mDatabase.child("emergencies").addListenerForSingleValueEvent(new ValueEventListener() {

            String severity;
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot issue : dataSnapshot.getChildren()) {
                        // do something with the individual "issues"
                        System.out.println(issue.toString());
                        System.out.println(issue.getValue());
                        System.out.println(issue.getValue().getClass());
                        ArrayList<Double> temp = new ArrayList<Double>(2);
                        HashMap<String, Object> dataTemp = (HashMap)(issue.getValue());
                        Double d1 = (Double)(dataTemp.get("latitude"));
                        Double d2 = (Double)(dataTemp.get("longitude"));
                        severity = (String)dataTemp.get("severity");
                        temp.add(d1);
                        temp.add(d2);

                        String type = (String)(dataTemp.get("emergencyType"));

                        typeOfEmergency.add(type);
                        myEmergCoordinates.add(temp);
                    }
                }
                else {
                    System.out.println("THE USER IDENT IS WRONG");
                }
                System.out.println("size of this is " + myEmergCoordinates.size());
                for (int i = 0; i < myEmergCoordinates.size(); i++) {
                    System.out.println("Severity Level: " + severity);
                    LatLng tempCoord = new LatLng(myEmergCoordinates.get(i).get(0), myEmergCoordinates.get(i).get(1));
                    mMap.addMarker((new MarkerOptions().position(tempCoord).title("emergency " + i).icon(BitmapDescriptorFactory.defaultMarker(
                            colorMap.get(typeOfEmergency.get(i))
                    ))));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(tempCoord));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}
