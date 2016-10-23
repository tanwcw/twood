package com.homeful.jayne.pearapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

//firebase auth object
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class IncludeRelativeActivity extends AppCompatActivity implements View.OnClickListener {

    //defining views
    private Button buttonAddChild;
    private Button buttonLogout;
    private EditText addChildEmail;

    //fdefine objects
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    private FirebaseUser currentUser;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_include_relative);

        //initializing views
        buttonAddChild = (Button) findViewById(R.id.buttonSignin);
        buttonLogout = (Button) findViewById(R.id.buttonLogout);
        addChildEmail  = (EditText) findViewById(R.id.addChildEmail);

        //instantiate require objects
        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        currentUser = firebaseAuth.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        //If user not login, redirect to login page
        if(firebaseAuth.getCurrentUser() == null){
            System.out.println("No current user :(");
            startActivity(new Intent(this, LoginActivity.class));
        }



        //attaching click listener
        buttonAddChild.setOnClickListener(this);
        buttonLogout.setOnClickListener(this);

    }

    public void addChild(){
        String childEmail = addChildEmail.getText().toString().trim();
        String parentemail = currentUser.getEmail();

        //checking if email and passwords are empty
        if(TextUtils.isEmpty(childEmail)){
            Toast.makeText(this,"Please enter child's email",Toast.LENGTH_LONG).show();
            return;
        }

        progressDialog.setMessage("Adding Child Please Wait...");
        progressDialog.show();

        //Add child into database
        ParentChild parentChild = new ParentChild(parentemail, childEmail);
        databaseReference.child("ParentChild").setValue(parentChild, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if(databaseError == null){
                    Toast.makeText(IncludeRelativeActivity.this, "Successfully updated", Toast.LENGTH_LONG).show();
                }
                else{
                    System.out.println("Error: "+ databaseError);
                    Toast.makeText(IncludeRelativeActivity.this, "Unsuccessful...", Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    @Override
    public void onClick(View view) {
        if(view == buttonAddChild){
            addChild();
            startActivity(new Intent(this, DirectoryActivity.class));
        }

        if(view == buttonLogout){
            finish();
            startActivity(new Intent(this, MainActivity.class));
        }
    }
}
