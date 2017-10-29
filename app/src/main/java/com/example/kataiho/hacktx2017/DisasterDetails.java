package com.example.kataiho.hacktx2017;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DisasterDetails extends AppCompatActivity implements View.OnClickListener {
    String disasterType;
    private Button submitDisasterButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disaster_details);
        disasterType = getIntent().getStringExtra("Type");
        TextView header = (TextView) findViewById(R.id.typeHeader);
        header.setText("Report a " + disasterType + " in your area");

        submitDisasterButton = (Button) findViewById(R.id.submitDisaster);
        submitDisasterButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent changeToDisasterMap = new Intent(getApplicationContext(), DisasterMap.class);
        startActivity(changeToDisasterMap);
    }
}
