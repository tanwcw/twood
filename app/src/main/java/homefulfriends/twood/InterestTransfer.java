package homefulfriends.twood;

import java.util.concurrent.ExecutionException;

/**
 * Created by liao on 10/23/16.
 */

public class InterestTransfer {
    public String sendMoney(String sender_id, String sender_vault_id, String recipient_id, String recipient_vault_id, int amount, String type) throws InterruptedException, ExecutionException {
        ParentToChild.postSender ps = new ParentToChild.postSender(sender_id, sender_vault_id, recipient_id, recipient_vault_id, amount, type);
        return ps.execute().get();
    }
}
