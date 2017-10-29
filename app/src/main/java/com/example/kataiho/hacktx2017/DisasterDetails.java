package com.example.kataiho.hacktx2017;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DisasterDetails extends AppCompatActivity implements View.OnClickListener {
    String disasterType;

    private Button submitDisasterButton;
    private DatabaseReference mDatabase;

    public FirebaseAuth mAuth;

    private RadioGroup emergencyGroup;
    private RadioGroup typeOfEmergencyGroup;
    private Button submitDisaster;

    private EditText details;

    private LocationManager locationManager;
    private LocationListener locationListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disaster_details);
        disasterType = getIntent().getStringExtra("Type");
        TextView header = (TextView) findViewById(R.id.typeHeader);
        header.setText("Report a " + disasterType + " in your area");

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference("users");

        submitDisasterButton = (Button) findViewById(R.id.submitDisaster);
        submitDisasterButton.setOnClickListener(this);

        emergencyGroup = (RadioGroup) findViewById(R.id.emergencyGroup);
        typeOfEmergencyGroup = (RadioGroup) findViewById(R.id.typeOfEmergencyGroup);

        details = (EditText) findViewById(R.id.details);

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                FirebaseUser user = mAuth.getCurrentUser();
                String uid = user.getUid();
                mDatabase.child(uid).child("post").child("latitude").setValue(location.getLatitude());
                mDatabase.child(uid).child("post").child("Longitude").setValue(location.getLongitude());
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

            }
        };
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.

                requestPermissions(new String[]{
                        android.Manifest.permission.ACCESS_FINE_LOCATION,
                        android.Manifest.permission.ACCESS_COARSE_LOCATION,
                        android.Manifest.permission.INTERNET
                }, 10);
                return;
            }
        }
        else {
            locationManager.requestLocationUpdates("gps", 5000, 0, locationListener);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 10:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    configureButton();
                return;
        }
    }

    @Override
    public void onClick(View view) {
        FirebaseUser user = mAuth.getCurrentUser();
        String uid = user.getUid();

        if (emergencyGroup.getCheckedRadioButtonId() == -1 || typeOfEmergencyGroup.getCheckedRadioButtonId() == -1) {
            return;
        }

        int selectedId = emergencyGroup.getCheckedRadioButtonId();
        // find the radiobutton by returned id
        RadioButton selectedRadioButton = (RadioButton) findViewById(selectedId);
        String data = selectedRadioButton.getText().toString();

        mDatabase.child(uid).child("post").child("emergencyNeeded").setValue(data);

        selectedId = typeOfEmergencyGroup.getCheckedRadioButtonId();
        selectedRadioButton = (RadioButton) findViewById(selectedId);
        data = selectedRadioButton.getText().toString();

        mDatabase.child(uid).child("post").child("emergencyType").setValue(data);
        mDatabase.child(uid).child("post").child("details").setValue(details.getText().toString());

//        Intent changeToDisasterMap = new Intent(getApplicationContext(), DisasterMap.class);
//        startActivity(changeToDisasterMap);
    }
}
