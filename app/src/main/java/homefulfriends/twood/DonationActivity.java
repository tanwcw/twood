package homefulfriends.twood;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.util.concurrent.ExecutionException;

public class DonationActivity extends AppCompatActivity {

    EditText donation_amount = (EditText) findViewById(R.id.donation_amount);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donation);
    }

    public void send(View v) {
        int amount = Integer.parseInt(donation_amount.getText().toString());
        try {
            ParentToChild pc = new ParentToChild();
            pc.sendMoney("bca6f521-26e7-4e4d-aa7f-8528c4d42e1e", "b21ceeb7-e1ef-42d5-99c6-6ce4d01a387b", "ff04c1f7-8009-4e66-b5c8-7c54fdbd5cce", "04304850-6f6e-4377-9951-96147a91eced", amount, "bank");
        } catch (Exception e) {
            System.out.print(e);
        }
    }
}