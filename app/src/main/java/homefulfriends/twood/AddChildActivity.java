package homefulfriends.twood;


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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AddChildActivity extends AppCompatActivity implements View.OnClickListener {

    //defining views
    private Button buttonAddChild;
    private Button buttonLogout;
    private EditText addChildEmail;

    //define objects
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    private FirebaseUser currentUser;
    private DatabaseReference databaseReference;
    private User userParent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_child);

        //initializing views
        buttonAddChild = (Button) findViewById(R.id.buttonAddChild);
        buttonLogout = (Button) findViewById(R.id.buttonLogout);
        addChildEmail = (EditText) findViewById(R.id.addChildEmail);

        //instantiate require objects
        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        currentUser = firebaseAuth.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("Users").child(currentUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                userParent = dataSnapshot.getValue(User.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println(databaseError);
            }
        });


        //If user not login, redirect to login page
        if (firebaseAuth.getCurrentUser() == null) {
            System.out.println("No current user :(");
            startActivity(new Intent(this, LoginActivity.class));
        }

        //attaching click listener
        buttonAddChild.setOnClickListener(this);
        buttonLogout.setOnClickListener(this);

    }

    public void addChild() {
        String childEmail = addChildEmail.getText().toString().trim();
        String parentEmail = currentUser.getEmail();

        //checking if email are empty
        if (TextUtils.isEmpty(childEmail)) {
            Toast.makeText(this, "Please enter child's email", Toast.LENGTH_LONG).show();
            return;
        }

        progressDialog.setMessage("Adding Child Please Wait...");
        progressDialog.show();
//
//        //Add child into database
//        ParentChild parentChild = new ParentChild(parentEmail, childEmail);
//        databaseReference.child("ParentChild").setValue(parentChild, new DatabaseReference.CompletionListener() {
//            @Override
//            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
//                if(databaseError == null){
//                    Toast.makeText(IncludeRelativeActivity.this, "Successfully updated", Toast.LENGTH_LONG).show();
//                }
//                else{
//                    System.out.println("Error: "+ databaseError);
//                    Toast.makeText(IncludeRelativeActivity.this, "Unsuccessful...", Toast.LENGTH_LONG).show();
//                }
//            }
//        });

        //
        //Add child into database
        ParentChild parentChild = new ParentChild(parentEmail, childEmail);
        String key = databaseReference.child("ParentChild").push().getKey();
        Map<String, Object> addParentChild = new HashMap<>();
        addParentChild.put("/ParentChild/" + key, parentChild);
        try {
            databaseReference.updateChildren(addParentChild);
            userParent.setChildKey(key);
            databaseReference.child("Users").child(currentUser.getUid()).setValue(userParent);
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }

    }


    @Override
    public void onClick(View view) {
        if (view == buttonAddChild) {
            addChild();
            startActivity(new Intent(this, ParentMainActivity.class));
        }

        if (view == buttonLogout) {
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }
    }
}

