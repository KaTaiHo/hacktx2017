package com.example.kataiho.hacktx2017;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.security.Provider;

public class DisasterDetails extends AppCompatActivity implements View.OnClickListener {
    String disasterType;

    private Button submitDisasterButton;
    private DatabaseReference mDatabase;

    public FirebaseAuth mAuth;

    private RadioGroup emergencyGroup;
    private RadioGroup typeOfEmergencyGroup;
    private Button submitDisaster;

    private EditText details;

    private double longitude;
    private double latitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_disaster_details);
        disasterType = getIntent().getStringExtra("Type");
        TextView header = (TextView) findViewById(R.id.typeHeader);
        header.setText("Report a " + disasterType + " in your area");

        longitude = getIntent().getDoubleExtra("longitude", 0.0);
        latitude = getIntent().getDoubleExtra("latitude", 0.0);

        System.out.println("longitude : " + longitude);
        System.out.println("latitude : " + latitude);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        submitDisasterButton = (Button) findViewById(R.id.submitDisaster);
        submitDisasterButton.setOnClickListener(this);

        emergencyGroup = (RadioGroup) findViewById(R.id.emergencyGroup);
        typeOfEmergencyGroup = (RadioGroup) findViewById(R.id.typeOfEmergencyGroup);

        details = (EditText) findViewById(R.id.details);
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

        mDatabase.child("emergencies").child(uid).child("emergencyNeeded").setValue(data);

        selectedId = typeOfEmergencyGroup.getCheckedRadioButtonId();
        selectedRadioButton = (RadioButton) findViewById(selectedId);
        data = selectedRadioButton.getText().toString();

        mDatabase.child("emergencies").child(uid).child("severity").setValue(data);
        mDatabase.child("emergencies").child(uid).child("details").setValue(details.getText().toString());
        mDatabase.child("emergencies").child(uid).child("latitude").setValue(latitude);
        mDatabase.child("emergencies").child(uid).child("longitude").setValue(longitude);
        mDatabase.child("emergencies").child(uid).child("emergencyType").setValue(disasterType);

//        Intent changeToDisasterMap = new Intent(getApplicationContext(), DisasterMap.class);
//        startActivity(changeToDisasterMap);
    }
}
