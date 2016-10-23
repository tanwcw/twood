package homefulfriends.twood;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ChildMainActivity extends AppCompatActivity {

    //defining a database reference
    private DatabaseReference databaseReference;

    //firebase auth object
    private FirebaseAuth firebaseAuth;

    //Object
    private FirebaseUser currentUser;

    TextView textView11;
    TextView textView12;

    User child;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Home");


        //initializing firebase authentication object
        firebaseAuth = FirebaseAuth.getInstance();

        //getting the database reference
        databaseReference = FirebaseDatabase.getInstance().getReference();

        //get current user
        currentUser = firebaseAuth.getCurrentUser();

        textView11 = (TextView) findViewById(R.id.textView11);
        textView12 = (TextView) findViewById(R.id.textView12);


        databaseReference.child("Users").child(currentUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                child = dataSnapshot.getValue(User.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        String checkingVaultId = child.getChildCheckingVaultID();
        String savingVaultId = child.getChildSavingVaultID();
        String checkingBal="";
        String savingBal="";
        try{
            GetBalance bg1 = new GetBalance();
            checkingBal = bg1.getBalance(checkingVaultId);
            GetBalance bg2 = new GetBalance();
            savingBal = bg2.getBalance(savingVaultId);

        }catch(Exception e){
            System.out.println(e);
        }
        textView11.setText(checkingBal);
        textView12.setText(savingBal);


    }
}
