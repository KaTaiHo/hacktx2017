package com.example.kataiho.hacktx2017;

import android.content.Intent;
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
    }

    @Override
    public void onClick(View view) {
//        Intent changeToDisasterMap = new Intent(getApplicationContext(), DisasterMap.class);
//        startActivity(changeToDisasterMap);
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
    }
}
