package homefulfriends.twood;

import android.os.AsyncTask;

import com.auth0.jwt.JWTSigner;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.JWTVerifyException;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.net.HttpURLConnection;
import java.util.concurrent.ExecutionException;

import static android.R.id.message;
import static com.auth0.jwt.internal.org.bouncycastle.asn1.ua.DSTU4145NamedCurves.params;

/**
 * Created by liao on 10/22/16.
 */

public class CheckingToSaving{

    public String save(String account_id, String checking_vault_id, String saving_vault_id, int amount) throws InterruptedException, ExecutionException {

        CheckingToSaving.postSender ps = new postSender(account_id,checking_vault_id,saving_vault_id,amount);
        return ps.execute().get();
    }

    private class postSender extends AsyncTask<String, Void, String> {
        public postSender(String account_id, String checking_vault_id, String saving_vault_id, int amount) {
            super();
            this.account_id = account_id;
            this.checking_vault_id = checking_vault_id;
            this.saving_vault_id = saving_vault_id;
            this.amount = amount;
        }

        String result = "";
        String account_id;
        String checking_vault_id;
        String saving_vault_id;
        Integer amount;

        @Override
        protected String doInBackground(String[] params) {
            // do above Server call here

            final String issuer = "https://hack.modoapi.com/";
            final String secret = "0BM0XkwUcpMXpPqJIKe5U8c3LC1/vkhyEr9F909JBiiAIcgjenzRDW/iXbD8Woc0gcHKnjX1WzB6fCp3BhXdKQ==";
            final String api_key = "a8676d08-63d5-4e5a-afb6-f334c6514b76";
            final long iat = System.currentTimeMillis() / 1000L; // issued at claim
            final long exp = iat + 60L; // expires claim. In this case the token expires in 60 seconds

            final JWTSigner signer = new JWTSigner(secret);
            final HashMap<String, Object> claims = new HashMap<String, Object>();
            claims.put("iss", issuer);
            claims.put("exp", exp);
            claims.put("iat", iat);
            claims.put("api_key", api_key);

            final String jwt = signer.sign(claims);

            String key = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ…HhJQe7-_VGNAUGp9WUO0P716kC0BufzKrk";
            final String apiURL = "https://hack.modoapi.com/1.0.0-dev/coin/mint";
            final String charset = "UTF-8";
            StringBuffer sb = new StringBuffer();
            InputStream is = null;
            try {
                URL url = new URL(apiURL);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type",
                        "application/json");
                connection.setRequestProperty("authorization",
                        "Token " + jwt);
                System.out.println(jwt);

                JSONObject request = new JSONObject();

                request.put("account_id", account_id);
                request.put("amount", amount);
                request.put("description", "a looooooong description");

                JSONObject inputs = new JSONObject();
                inputs.put("instrument_id", checking_vault_id);
                JSONArray inputsArray = new JSONArray();
                inputsArray.put(inputs);
                request.put("inputs", inputsArray);
                JSONObject outputs = new JSONObject();
                outputs.put("instrument_id", saving_vault_id);
                outputs.put("account_id", account_id);
                JSONArray outputsArray = new JSONArray();
                outputsArray.put(outputs);
                request.put("outputs", outputsArray);

                OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
                System.out.println(request.toString());
                out.write(request.toString());
                out.flush();
                System.out.println(out.toString());

                is = new BufferedInputStream(connection.getInputStream());
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                String inputLine = "";
                while ((inputLine = br.readLine()) != null) {
                    sb.append(inputLine);
                }
                result = sb.toString();
                JSONObject outa = new JSONObject(result);
                String coin_id = outa.getJSONObject("response_data").getString("coin_id");
                ConfirmTransaction ct= new ConfirmTransaction();
                ct.operate(coin_id);
                GetBalance gb = new GetBalance();
                String bal1 = gb.getBalance(checking_vault_id);
                String bal2 = gb.getBalance(saving_vault_id);
                result = bal2 + " " + bal2;
                System.out.println(result);
                return result;

            } catch (Exception e) {
                System.out.println(e);
                return e.toString();
            }
        }

        @Override
        protected void onPostExecute(String message) {
            //process message
        }
    }

}



