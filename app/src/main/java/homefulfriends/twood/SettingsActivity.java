package homefulfriends.twood;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
//
        //App Drawer//
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
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
