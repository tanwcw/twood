package homefulfriends.twood;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;


import org.w3c.dom.Text;

public class TransferActivity extends AppCompatActivity {

    TextView child_name;
    Button button2;
    EditText currencyEditText2;

    //firebase auth object
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;

    //Object
    private FirebaseUser currentUser;

    User parent;
    User child;
    ParentChild parentChild;
    String childEmail;

    public void button2click(View v) {
        transferMoney(parent.getModoId(), parent.getParentVaultID(), child.getModoId(), child.getChildCheckingVaultID(), Integer.parseInt(currencyEditText2.getText().toString()), "bank");
    }

    public void transferMoney(String sendersID, String sendersVaultID, String receiversID, String receiversVaultID, int amount, String type) {
        ParentToChild ptc = new ParentToChild();
        try {
            String result = ptc.sendMoney(sendersID, sendersVaultID, receiversID, receiversVaultID, amount, type);
            ConfirmTransaction ct = new ConfirmTransaction();
            ct.operate(result);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer);

        child_name = (TextView) findViewById(R.id.child_name);
        button2 = (Button) findViewById(R.id.button2);
        currencyEditText2 = (EditText) findViewById(R.id.currencyEditText2);

        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference();


        databaseReference.child("Users").child(currentUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                parent = dataSnapshot.getValue(User.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        databaseReference.child("Users").child("mBIUqtJjefWPMIib5Pjox9gAAkx1").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                child = dataSnapshot.getValue(User.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

//        Query parentChildQuery = databaseReference.child("Users");
//        parentChildQuery.addListenerForSingleValueEvent()(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                for(DataSnapshot pair: dataSnapshot.getChildren()){
//                    System.out.println(pair);
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        })
//    }

//        child = new User("William",12344546,"williantan@gmail.com",false,null,"bca6f521-26e7-4e4d-aa7f-852864d42ele","b21ceeb7-e1ef-42d5-99c6-6ce4d01a387b","");

    //App Drawer
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

    setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Transfer money");


    setTitle("Transfer Money");

    PrimaryDrawerItem item1 = new PrimaryDrawerItem().withIdentifier(0).withName(R.string.drawer_item_home);
    SecondaryDrawerItem item2 = new SecondaryDrawerItem().withIdentifier(1).withName(R.string.drawer_item_transfer);
    SecondaryDrawerItem item3 = new SecondaryDrawerItem().withIdentifier(2).withName(R.string.drawer_item_see_child);
    SecondaryDrawerItem item4 = new SecondaryDrawerItem().withIdentifier(3).withName(R.string.drawer_item_settings);

    Drawer result = new DrawerBuilder()
            .withActivity(this)
            .withToolbar(toolbar)
            .addDrawerItems(
                    item1,
                    new DividerDrawerItem(),
                    item2,
                    item3,
                    new DividerDrawerItem(),
                    item4
            )
            .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                @Override
                public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                    if (drawerItem != null) {
                        Intent intent = null;
                        if (drawerItem.getIdentifier() == 1) {
                            intent = new Intent(getBaseContext(), TransferActivity.class);
                        } else if (drawerItem.getIdentifier() == 2) {
                            intent = new Intent(getBaseContext(), ManageChildActivity.class);
                        } else if (drawerItem.getIdentifier() == 3) {
                            intent = new Intent(getBaseContext(), SettingsActivity.class);
                        } else if (drawerItem.getIdentifier() == 0) {
                            intent = new Intent(getBaseContext(), ParentMainActivity.class);
                        }

                        if (intent != null) {
                            startActivity(intent);
                        }
                    }
                    return false;

                }
            })
            .build();
}
}
