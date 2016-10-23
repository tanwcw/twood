package homefulfriends.twood;

import java.util.concurrent.ExecutionException;

/**
 * Created by liao on 10/23/16.
 */

public class InterestTransfer {
    String result = "";
    public String sendInterest(String sender_id, String sender_vault_id, String recipient_id, String recipient_vault_id, int rate) throws InterruptedException, ExecutionException {
        ParentToChild pc = new ParentToChild();
        GetBalance gb = new GetBalance();
        result = pc.sendMoney(sender_id, sender_vault_id, recipient_id, recipient_vault_id, Integer.parseInt(gb.getBalance(recipient_vault_id)) * rate, "bank");
        return result;
    }    
}
