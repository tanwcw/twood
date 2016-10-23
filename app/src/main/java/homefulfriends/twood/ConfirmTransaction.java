package homefulfriends.twood;

import android.os.AsyncTask;

import com.auth0.jwt.JWTSigner;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.JWTVerifyException;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Array;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.net.HttpURLConnection;
import java.util.concurrent.ExecutionException;
import org.json.JSONArray;
import static android.R.id.message;
import static com.auth0.jwt.internal.org.bouncycastle.asn1.ua.DSTU4145NamedCurves.params;

/**
 * Created by liao on 10/22/16.
 */

public class ConfirmTransaction {

    public String operate(String coin_id) throws InterruptedException, ExecutionException {
        postSender ps = new postSender(coin_id);
        return ps.execute().get();
    }

    private class postSender extends AsyncTask<String, Void, String> {
        public postSender(String coin_id) {
            super();
            this.coin_id = coin_id;
        }

        String result = "";
        String coin_id;

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
            final String apiURL = "https://hack.modoapi.com/1.0.0-dev/coin/operate";
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

                JSONObject request = new JSONObject();
                request.put("coin_id",coin_id);
                request.put("reason","testtesttest");
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



