package com.homeful.jayne.pearapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class DirectoryActivity extends AppCompatActivity implements View.OnClickListener {

    private Button registerWithFirAuth;
    private Button createUser;
    private Button LoginUser;
    private Button updateUser;
    private Button deleteUser;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_directory);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Define view
        registerWithFirAuth = (Button) findViewById(R.id.registerWithFirAuth);
        createUser = (Button) findViewById(R.id.createUser);
        LoginUser = (Button) findViewById(R.id.LoginUser);
        updateUser = (Button) findViewById(R.id.updateUser);
        deleteUser = (Button) findViewById(R.id.deleteUser);


        registerWithFirAuth.setOnClickListener((View.OnClickListener) this);
        createUser.setOnClickListener((View.OnClickListener) this);
        LoginUser.setOnClickListener((View.OnClickListener) this);
        updateUser.setOnClickListener((View.OnClickListener) this);
        deleteUser.setOnClickListener((View.OnClickListener) this);

    }

    public void onClick(View view) {
        //calling register method on click
        Intent intent;
        switch (view.getId()) {

            case R.id.registerWithFirAuth:
                intent = new Intent(getApplicationContext(), MainActivity.class);
                break;

            case R.id.createUser:
                intent = new Intent(getApplicationContext(), CreateUserActivity.class);
                break;

            case R.id.LoginUser:
                intent = new Intent(getApplicationContext(), LoginActivity.class);
                break;

            case R.id.updateUser:
                intent = new Intent(getApplicationContext(), ProfileActivity.class);
                break;

            case R.id.deleteUser:
                intent = new Intent(getApplicationContext(), ProfileActivity.class);
                break;

            default:
                intent = new Intent(getApplicationContext(), ProfileActivity.class);
                break;
        }
        startActivity(intent);
    }

}
