package homefulfriends.twood;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String sender_id = "bca6f521-26e7-4e4d-aa7f-8528c4d42e1e";
        String recipient_id = "ff04c1f7-8009-4e66-b5c8-7c54fdbd5cce";
        String sender_vault_id = "b21ceeb7-e1ef-42d5-99c6-6ce4d01a387b";
        String recipient_vault_id = "04304850-6f6e-4377-9951-96147a91eced";

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String result = null;
        try {
//              AddCard ac = new AddCard();
//              result = ac.addCardToModo(recipient_id,"4485660000000007");

//              result = RegisterModoAccount.userRegister();

              //ParentToChild pc = new ParentToChild();
              //result = pc.sendMoney(sender_id, sender_vault_id,recipient_id, recipient_vault_id, 10, "card");
              //String coin = "e78191c8-eb46-41c7-9210-c8d9fc5a812f"
            // Barnes and Noble Booksellers  Caribou Coffee
            //"defc815a-98fd-4231-b79b-5a95c6c6602e"  "0bf5abfe-d7ae-4ee8-aa14-a0d7f66dc3c6"
//            GiftCardMerchant gm = new GiftCardMerchant();
//            result = gm.findMerchantID("merchant");

            GetBalance gb = new GetBalance();
            result = gb.getBalance("04304850-6f6e-4377-9951-96147a91eced");

        } catch (Exception e) {
            e.printStackTrace();
        }
        TextView textView = (TextView) findViewById(R.id.op);
        textView.setText(result);
    }
}
