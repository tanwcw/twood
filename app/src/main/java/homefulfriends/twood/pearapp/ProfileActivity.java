package com.homeful.jayne.pearapp;

import android.nfc.Tag;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
//import com.google.firebase.quickstart.database.models.Post;
//import com.google.firebase.quickstart.database.models.User;

public class ProfileActivity extends AppCompatActivity {

    //defining view
    private EditText editTextBankDetails;
    private DatabaseReference databaseRef;
    private User user;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Set up details");
        setSupportActionBar(toolbar);

        //initializing firebase auth object - JY
        databaseRef = FirebaseDatabase.getInstance().getReference();

        //initializing views
        editTextBankDetails = (EditText) findViewById(R.id.editTextBankDetails);


        FloatingActionButton submitButton = (FloatingActionButton) findViewById(R.id.fab);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerBankDetails();
                getBankDetails();
            }

        });

        System.out.println("came in");

//        ValueEventListener bankDetailListener = new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                // Get Post object and use the values to update the UI
//                User user = dataSnapshot.getValue(User.class);
//                if (user == null)
//                    System.out.println("its null:(");
//                System.out.println(user.getBankDetails());
//                System.out.println("Here are the bank details " + user.getBankDetails());
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                // Getting Post failed, log a message
//                //Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
//                System.out.println(databaseError.getMessage());
//            }
//        };
//        databaseRef.addValueEventListener(bankDetailListener);
    }

    private void registerBankDetails(){
        //Get value from UI
        final int bankDetails = Integer.parseInt(editTextBankDetails.getText().toString());
        User user = new User(bankDetails);

        //Set bank details
        databaseRef.child("users").setValue(user);
    }

    private void getBankDetails(){
        Query queryRef = databaseRef;
        queryRef.addChildEventListener(new ChildEventListener(){
            @Override
            public void onChildAdded(DataSnapshot snapshot, String previousChild) {
                System.out.println("Came into child");
                User user = snapshot.getValue(User.class);
                System.out.println("User" + user);
                System.out.println(user.toString());
                System.out.println(snapshot.getKey() + " was " + user.getBankDetails() + " meters tall");

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

//    private void getBankDetails(){
//
//    }

}
