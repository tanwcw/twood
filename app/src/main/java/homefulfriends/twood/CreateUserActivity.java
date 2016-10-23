package homefulfriends.twood;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class CreateUserActivity extends AppCompatActivity implements View.OnClickListener {

    //view objects
    private TextView textViewUserEmail;
    private Button buttonSave;

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

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Account Details");

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
        editTextBankDetails = (EditText) findViewById(R.id.editTextBankDetails);
        buttonSave = (Button) findViewById(R.id.buttonSave);
        checkBoxIsParent = (CheckBox) findViewById(R.id.checkBoxIsParent);


        //get current user
        currentUser = firebaseAuth.getCurrentUser();

        //adding listener to button
        buttonSave.setOnClickListener(this);
    }

    private void saveUserInformation() {
        //Getting values from database
        final int bankDetails = Integer.parseInt(editTextBankDetails.getText().toString());
        final boolean isParent = checkBoxIsParent.isChecked();


        //Retrive current user
        databaseReference.child("Users").child(currentUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                // adding values
                user.setBankDetails(bankDetails);
                user.setParent(isParent);
                String mobile = "" + user.getMobile();
                String name = user.getName();
                String email = user.getEmail();
                RegisterModoAccount ra = new RegisterModoAccount();
                try {
                    String modoID = ra.userRegister(mobile, name, "Taylor", email);
                    AddCard ad = new AddCard();
                    String vaultID = ad.addCardToModo(modoID, "4485660000000007" , isParent);
                    if (isParent) {
                        user.setParentVaultID(vaultID);
                    } else {
                        user.setChildCheckingVaultID(vaultID);
                        String vaultID2 = ad.addCardToModo(modoID, "5153641444444447", isParent);
                        user.setChildCheckingVaultID(vaultID2);
                    }
                    user.setModoId(modoID);
                } catch (Exception e) {
                    System.out.println(e);
                }


                databaseReference.child("Users").child(currentUser.getUid()).setValue(user, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                        if (databaseError == null) {
                            Toast.makeText(CreateUserActivity.this, "Successfully updated", Toast.LENGTH_LONG).show();
                        } else {
                            System.out.println("Error: " + databaseError);
                            Toast.makeText(CreateUserActivity.this, "Unsuccessful...", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        if(isParent){
            startActivity(new Intent(getBaseContext(), ParentMainActivity.class));
        }
        else{
            startActivity(new Intent(getBaseContext(), ChildMainActivity.class));
        }


    }

    @Override
    public void onClick(View view) {
            saveUserInformation();
//            databaseReference.child("Users").child(currentUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
//                @Override
//                public void onDataChange(DataSnapshot dataSnapshot) {
//                    User user = dataSnapshot.getValue(User.class);
//                    System.out.println(user);
//                    if (user.getParent()) {
//                        startActivity(new Intent(getBaseContext(), ParentMainActivity.class));
//                    } else {
//                        startActivity(new Intent(getBaseContext(), ChildMainActivity.class));
//                    }
//                }
//
//                @Override
//                public void onCancelled(DatabaseError databaseError) {
//
//                }
//            });

    }
}
