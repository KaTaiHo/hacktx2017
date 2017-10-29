package com.example.kataiho.hacktx2017;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class DisasterDetails extends AppCompatActivity {
    String disasterType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disaster_details);
        disasterType = getIntent().getStringExtra("Type");
        TextView header = (TextView) findViewById(R.id.typeHeader);
        header.setText(disasterType);


    }
}
