package com.example.kataiho.hacktx2017;

/**
 * Created by KaTaiHo on 10/28/17.
 */

public class User {
    public String username;
    public String email;
    public boolean isFirstResponder;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String username, String email, boolean isFirstResponder) {
        this.username = username;
        this.email = email;
        this.isFirstResponder = isFirstResponder;
    }
}

