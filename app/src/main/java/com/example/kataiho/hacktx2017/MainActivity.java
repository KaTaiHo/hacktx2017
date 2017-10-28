package com.example.kataiho.hacktx2017;

import android.support.v4.text.TextUtilsCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Button;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText emailField, passwordField;
    private Button loginButton, registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        registerButton = (Button) findViewById(R.id.registerButton);
        loginButton = (Button) findViewById(R.id.loginButton);
        emailField = (EditText) findViewById(R.id.emailField);
        passwordField = (EditText) findViewById(R.id.passwordField);

        registerButton.setOnClickListener(this);
        loginButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == registerButton) {
            //register the user
            registerUser();
        }
        else if (view == loginButton) {
            //login the user via firebase

        }
    }

    private boolean registerUser() {
        String userEmail = emailField.getText().toString().trim();
        String userPassword = emailField.getText().toString().trim();

        if (TextUtils.isEmpty(email)){

        }
    }

    private boolean loginUser() {

    }
}
