package com.example.kataiho.hacktx2017;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.text.TextUtilsCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Button;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText emailField, passwordField;
    private Button loginButton, registerButton;
    private static final String TAG = "MainActivity";

    public FirebaseAuth mAuth;
    public FirebaseAuth.AuthStateListener mAuthListener;

    private DatabaseReference mDatabase;

    static private boolean isOkay;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        setContentView(R.layout.activity_main);

        registerButton = (Button) findViewById(R.id.registerButton);
        loginButton = (Button) findViewById(R.id.loginButton);
        emailField = (EditText) findViewById(R.id.emailField);
        passwordField = (EditText) findViewById(R.id.passwordField);

        registerButton.setOnClickListener(this);
        loginButton.setOnClickListener(this);

        isOkay = true;

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

    @Override
    public void onClick(View view) {
        if (view == registerButton) {
            //register the user
            if (registerUser() && isOkay) {



                Intent changeToDisasterSelection = new Intent(getApplicationContext(), DisasterSelection.class);
                startActivity(changeToDisasterSelection);
            }
        }
        else if (view == loginButton) {
            //login the user via firebase
            if (loginUser() && isOkay) {
                //switch screens
                Intent changeToDisasterSelection = new Intent(getApplicationContext(), DisasterSelection.class);
                startActivity(changeToDisasterSelection);
            }
        }
    }

    public boolean registerUser() {
        String userEmail = emailField.getText().toString().trim();
        String userPassword = emailField.getText().toString().trim();


        if (userEmail.isEmpty()) {
            Toast.makeText(this, "Please enter your email", Toast.LENGTH_SHORT).show();
            isOkay = false;
            return isOkay;
        }

        if (userPassword.isEmpty()) {
            Toast.makeText(this, "Please enter your password", Toast.LENGTH_SHORT).show();
            isOkay = false;
            return isOkay;
        }

        mAuth.createUserWithEmailAndPassword(userEmail, userPassword)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            MainActivity.isOkay = true;
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            MainActivity.isOkay = false;
                        }

                        // ...
                    }
                });

        return isOkay;
    }

    public boolean loginUser() {
        String userEmail = emailField.getText().toString().trim();
        String userPassword = emailField.getText().toString().trim();


        if (userEmail.isEmpty()) {
            Toast.makeText(this, "Please enter your email", Toast.LENGTH_SHORT).show();
            isOkay = false;
            return isOkay;
        }

        if (userPassword.isEmpty()) {
            Toast.makeText(this, "Please enter your password", Toast.LENGTH_SHORT).show();
            isOkay = false;
            return isOkay;
        }

        mAuth.signInWithEmailAndPassword(userEmail, userPassword)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            MainActivity.isOkay = true;
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            MainActivity.isOkay = false;
                        }

                        // ...
                    }
                });

        return isOkay;
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    private void writeNewUser(String userId, String name, String email) {
        User user = new User(name, email, false);
        mDatabase.child("users").child(userId).setValue(user);
    }
}