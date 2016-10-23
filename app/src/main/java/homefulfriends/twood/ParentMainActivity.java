package homefulfriends.twood;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class ParentMainActivity extends AppCompatActivity implements View.OnClickListener {


    private Button newChild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_main);

        //Set title
        setTitle(R.string.title_home);

        newChild = (Button) findViewById(R.id.newChild);


        newChild.setOnClickListener((View.OnClickListener) this);



    }

    @Override
    public void onClick(View v) {
        startActivity(new Intent(getApplicationContext(), AddChildActivity.class));
    }
}
