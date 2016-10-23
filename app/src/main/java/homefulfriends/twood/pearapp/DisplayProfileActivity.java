package com.homeful.jayne.pearapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class DisplayProfileActivity extends AppCompatActivity implements View.OnClickListener{

    //firebase auth object
    private FirebaseAuth firebaseAuth;

    //view objects
    private TextView textViewUserEmail;
    private Button buttonLogout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_profile);

        //initializing firebase authentication object
        firebaseAuth = FirebaseAuth.getInstance();

        //If user not login, redirect to login page
        if(firebaseAuth.getCurrentUser() == null){
            System.out.println("No current user :(");
            startActivity(new Intent(this, LoginActivity.class));
        }

        //getting current user.
        FirebaseUser user = firebaseAuth.getCurrentUser();


        //initializing views
        textViewUserEmail = (TextView) findViewById(R.id.textViewUserEmail);
        buttonLogout = (Button) findViewById(R.id.buttonLogout);

        //displaying logged in name
        textViewUserEmail.setText("Welcome "+user.getEmail());

        //adding listener to button
        buttonLogout.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        //if logout is pressed
        if (view == buttonLogout) {
            //logging out the user
            firebaseAuth.signOut();
            //closing activity
            finish();
            System.out.println("Logging out");
            //starting login activity
            startActivity(new Intent(this, LoginActivity.class));
        }
    }
}
