package com.example.kataiho.hacktx2017;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SubmitSuccesful extends AppCompatActivity implements View.OnClickListener {
    Button returnToMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_succesful);
        returnToMenu = (Button) findViewById(R.id.returnToMenu);
        returnToMenu.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent changeToDisasterSelection = new Intent(getApplicationContext(), DisasterSelection.class);
        startActivity(changeToDisasterSelection);
    }
}
