package homefulfriends.twood;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

public class ParentMainActivity extends AppCompatActivity {

    //firebase auth object
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_main);

        firebaseAuth = FirebaseAuth.getInstance();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Home");
//cc//
        //App Drawer
        PrimaryDrawerItem item1 = new PrimaryDrawerItem().withIdentifier(0).withName(R.string.drawer_item_home);
        SecondaryDrawerItem item2 = new SecondaryDrawerItem().withIdentifier(1).withName(R.string.drawer_item_transfer);
        SecondaryDrawerItem item3 = new SecondaryDrawerItem().withIdentifier(2).withName(R.string.drawer_item_see_child);
        SecondaryDrawerItem item4 = new SecondaryDrawerItem().withIdentifier(3).withName(R.string.drawer_item_settings);
        SecondaryDrawerItem item5 = new SecondaryDrawerItem().withIdentifier(4).withName(R.string.drawer_item_signout);
        SecondaryDrawerItem item6 = new SecondaryDrawerItem().withIdentifier(5).withName(R.string.drawer_item_add_child);

        Drawer result = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .addDrawerItems(
                        item1,
                        new DividerDrawerItem(),
                        item2, //TODO: if parent has child, item2 else item5
                        item3,
                        new DividerDrawerItem(),
                        item4,
                        new DividerDrawerItem(),
                        item5
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
                            } else if (drawerItem.getIdentifier() == 4) {
                                firebaseAuth.signOut();
                                Intent i = new Intent(getBaseContext(), LoginActivity.class);
                                startActivity(i);

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
