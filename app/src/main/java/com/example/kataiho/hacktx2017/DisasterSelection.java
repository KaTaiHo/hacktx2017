package com.example.kataiho.hacktx2017;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DisasterSelection extends AppCompatActivity implements View.OnClickListener{


    private FloatingActionButton blizzardButton, earthquakeButton, eruptionButton, floodButton,
    hurricaneButton, stormButton, tornadoButton, tsunamiButton, wildfireButton;

    private Button mapButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disaster_selection);

        blizzardButton = (FloatingActionButton) findViewById(R.id.blizzardButton);
        earthquakeButton = (FloatingActionButton) findViewById(R.id.earthquakeButton);
        eruptionButton = (FloatingActionButton) findViewById(R.id.eruptionButton);

        floodButton = (FloatingActionButton) findViewById(R.id.floodButton);
        hurricaneButton = (FloatingActionButton) findViewById(R.id.hurricaneButton);
        stormButton = (FloatingActionButton) findViewById(R.id.stormButton);

        tornadoButton = (FloatingActionButton) findViewById(R.id.tornadoButton);
        tsunamiButton = (FloatingActionButton) findViewById(R.id.tsunamiButton);
        wildfireButton = (FloatingActionButton) findViewById(R.id.wildfireButton);

        mapButton = (Button) findViewById(R.id.mapButton);

        blizzardButton.setOnClickListener(this);
        earthquakeButton.setOnClickListener(this);
        eruptionButton.setOnClickListener(this);

        floodButton.setOnClickListener(this);
        hurricaneButton.setOnClickListener(this);
        stormButton.setOnClickListener(this);

        tornadoButton.setOnClickListener(this);
        tsunamiButton.setOnClickListener(this);
        wildfireButton.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        if (view == blizzardButton) {
            Intent event = new Intent(getBaseContext(), DisasterMap.class);
            event.putExtra("Type", "Blizzard");
            startActivity(event);
        }
        else if (view == earthquakeButton) {
            Intent event = new Intent(getBaseContext(), DisasterMap.class);
            event.putExtra("Type", "Earthquake");
            startActivity(event);
        }
        else if (view == eruptionButton) {
            Intent event = new Intent(getBaseContext(), DisasterMap.class);
            event.putExtra("Type", "Eruption");
            startActivity(event);
        }
        else if(view == floodButton) {
            Intent event = new Intent(getBaseContext(), DisasterMap.class);
            event.putExtra("Type", "Flood");
            startActivity(event);
        }
        else if(view == hurricaneButton) {
            Intent event = new Intent(getBaseContext(), DisasterMap.class);
            event.putExtra("Type", "Hurricane");
            startActivity(event);
        }
        else if(view == stormButton) {
            Intent event = new Intent(getBaseContext(), DisasterMap.class);
            event.putExtra("Type", "Storm");
            startActivity(event);
        }
        else if(view == tornadoButton) {
            Intent event = new Intent(getBaseContext(), DisasterMap.class);
            event.putExtra("Type", "Tornado");
            startActivity(event);
        }
        else if(view == tsunamiButton) {
            Intent event = new Intent(getBaseContext(), DisasterMap.class);
            event.putExtra("Type", "Tsunami");
            startActivity(event);
        }
        else if(view == wildfireButton) {
            Intent event = new Intent(getBaseContext(), DisasterMap.class);
            event.putExtra("Type", "Wildfire");
            startActivity(event);
        }
    }
}
