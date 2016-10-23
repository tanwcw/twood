package homefulfriends.twood;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class ParentMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_main);

        //Set title
        setTitle(R.string.title_home);

    }
}
