package homefulfriends.twood;

import android.os.AsyncTask;

import com.auth0.jwt.JWTSigner;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

/**
 * Created by liao on 10/22/16.
 */

public class AddCard {

    public String addCardToModo(String account_id, String card_number, Boolean role) throws InterruptedException, ExecutionException {

        postSender ps = new postSender(account_id,card_number,role);
        return ps.execute().get();
    }

    private class postSender extends AsyncTask<String, Void, String> {
        public postSender(String account_id, String card_number, Boolean role) {
            super();
            this.account_id = account_id;
            this.card_number = card_number;
            this.role = role;
        }

        String result = "";
        String account_id;
        String card_number;
        Boolean role;

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
            final String apiURL = "https://hack.modoapi.com/1.0.0-dev/vault/add";
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
                //System.out.println(jwt);



                JSONObject subRequest = new JSONObject();
                if(role.equals(true)) {
                    subRequest.put("vault_type", "OPEN_CARD");
                }
                else{
                    subRequest.put("vault_type", "ESCROW");
                }
                subRequest.put("account_id",account_id);

                JSONObject ed = new JSONObject();
                ed.put("pan",card_number);
                ed.put("cvv","123");
                ed.put("exp_month",7);
                ed.put("name","Aaron Crash Wilkinson");
                ed.put("address","1234 LoooooooongName Rd");
                ed.put("zip","75075");
                ed.put("exp_year",2018);
                subRequest.put("encrypted_data",ed);

                JSONArray ja = new JSONArray();
                ja.put(subRequest);
                JSONObject request = new JSONObject();
                request.put("items",ja);

                OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
                //System.out.println(request.toString());
                out.write(request.toString());
                out.flush();
                //System.out.println(out.toString());

                is = new BufferedInputStream(connection.getInputStream());
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                String inputLine = "";
                while ((inputLine = br.readLine()) != null) {
                    sb.append(inputLine);
                }
                result = sb.toString();
                JSONObject outa = new JSONObject(result);
                result = outa.getJSONArray("response_data").getJSONObject(0).getString("vault_id");
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



