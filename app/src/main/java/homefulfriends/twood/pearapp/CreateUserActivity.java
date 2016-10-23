package com.homeful.jayne.pearapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class CreateUserActivity extends AppCompatActivity implements View.OnClickListener{

    //view objects
    private TextView textViewUserEmail;
    private Button buttonLogout;
    private Button buttonSave;

    private EditText editTextName;
    private EditText editTextBankDetails;

    private CheckBox checkBoxIsParent;

    //defining a database reference
    private DatabaseReference databaseReference;

    //firebase auth object
    private FirebaseAuth firebaseAuth;

    //Object
    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);

        //initializing firebase authentication object
        firebaseAuth = FirebaseAuth.getInstance();

        //if the user is not logged in
        //that means current user will return null
        if (firebaseAuth.getCurrentUser() == null) {
            //closing this activity
            finish();
            //starting login activity
            startActivity(new Intent(this, LoginActivity.class));
        }

        //getting the database reference
        databaseReference = FirebaseDatabase.getInstance().getReference();

        //getting the view from xml
        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextBankDetails = (EditText) findViewById(R.id.editTextBankDetails);
        buttonSave = (Button) findViewById(R.id.buttonSave);
        buttonSave = (Button) findViewById(R.id.buttonSave);
        textViewUserEmail = (TextView) findViewById(R.id.textViewUserEmail);
        buttonLogout = (Button) findViewById(R.id.buttonLogout);
        checkBoxIsParent = (CheckBox) findViewById(R.id.checkBoxIsParent);


        //get current user
        currentUser = firebaseAuth.getCurrentUser();


        //adding listener to button
        buttonLogout.setOnClickListener(this);
        buttonSave.setOnClickListener(this);
    }

    private void saveUserInformation() {
        //Getting values from database
        String name = editTextName.getText().toString().trim();
        int bankDetails = Integer.parseInt(editTextBankDetails.getText().toString());
        boolean isParent = checkBoxIsParent.isChecked();


        //creating a new user object
        User user = new User(name, bankDetails, currentUser.getEmail(), isParent);

        System.out.println(user.toString());
        System.out.println(user.getName());
        System.out.println(user.getBankDetails());
        System.out.println(currentUser.getUid());

        databaseReference.child("Users").child(currentUser.getUid()).setValue(user, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if(databaseError == null){
                    Toast.makeText(CreateUserActivity.this, "Successfully updated", Toast.LENGTH_LONG).show();
                }
                else{
                    System.out.println("Error: "+ databaseError);
                    Toast.makeText(CreateUserActivity.this, "Unsuccessful...", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        //if logout is pressed
        if (view == buttonLogout) {
            //logging out the user
            firebaseAuth.signOut();
            //closing activity
            finish();
            //starting login activity
            startActivity(new Intent(this, LoginActivity.class));
        }

        if(view == buttonSave){
            saveUserInformation();
        }

    }
}
