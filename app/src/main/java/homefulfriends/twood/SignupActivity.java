package homefulfriends.twood;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.ExecutionException;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SignupActivity extends AppCompatActivity {
    private static final String TAG = "SignupActivity";

    private FirebaseAuth firebaseAuth;

    private DatabaseReference databaseRef;

    @Bind(R.id.input_name)
    EditText _nameText;
    @Bind(R.id.input_address)
    EditText _addressText;
    @Bind(R.id.input_email)
    EditText _emailText;
    @Bind(R.id.input_mobile)
    EditText _mobileText;
    @Bind(R.id.input_password)
    EditText _passwordText;
    @Bind(R.id.input_reEnterPassword)
    EditText _reEnterPasswordText;
    @Bind(R.id.btn_signup)
    Button _signupButton;
    @Bind(R.id.link_login)
    TextView _loginLink;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);

        //initializing firebase auth object - JY
        firebaseAuth = FirebaseAuth.getInstance();
        databaseRef = FirebaseDatabase.getInstance().getReference();

        _signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

        _loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the registration screen and return to the Login activity
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });
    }

    public void signup() throws InterruptedException, ExecutionException {
        Log.d(TAG, "Signup");


        //Sign Up F
        if (!validate()) {
            onSignupFailed();
            return;
        }

        _signupButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(SignupActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account...");
        progressDialog.show();

        String name = _nameText.getText().toString();
        String address = _addressText.getText().toString();
        final String email = _emailText.getText().toString();
        String mobile = _mobileText.getText().toString();
        String password = _passwordText.getText().toString();
        String reEnterPassword = _reEnterPasswordText.getText().toString();



        progressDialog.setMessage("Registering Please Wait...");
        progressDialog.show();

        new signupRunnable(email, password, name).run();
    }

    public void onSignupSuccess() {
        _signupButton.setEnabled(true);
        setResult(RESULT_OK, null);
        Toast.makeText(getBaseContext(), "Successfully registered", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(getApplicationContext(), CreateUserActivity.class);
        finish();
        startActivity(intent);
    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        _signupButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String name = _nameText.getText().toString();
        String address = _addressText.getText().toString();
        String email = _emailText.getText().toString();
        String mobile = _mobileText.getText().toString();
        String password = _passwordText.getText().toString();
        String reEnterPassword = _reEnterPasswordText.getText().toString();

        if (name.isEmpty() || name.length() < 3) {
            _nameText.setError("at least 3 characters");
            valid = false;
        } else {
            _nameText.setError(null);
        }

        if (address.isEmpty()) {
            _addressText.setError("Enter Valid Address");
            valid = false;
        } else {
            _addressText.setError(null);
        }


        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("enter a valid email address");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (mobile.isEmpty() || mobile.length() != 10) {
            _mobileText.setError("Enter Valid Mobile Number");
            valid = false;
        } else {
            _mobileText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        if (reEnterPassword.isEmpty() || reEnterPassword.length() < 4 || reEnterPassword.length() > 10 || !(reEnterPassword.equals(password))) {
            _reEnterPasswordText.setError("Password Do not match");
            valid = false;
        } else {
            _reEnterPasswordText.setError(null);
        }

        return valid;
    }

    private class signupRunnable implements Runnable {
        String email;
        String password;
        String name;

        public signupRunnable(String email, String password, String name) {
            this.email = email;
            this.password = password;
            this.name = name;
        }

        public void run() {
            // On complete call either onSignupSuccess or onSignupFailed

            //creating a new user
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            //checking if success
                            if (task.isSuccessful()) {
                                //display some message here
                                firebaseAuth.signInWithEmailAndPassword(email, password);
                                FirebaseUser user = firebaseAuth.getCurrentUser();
                                User newUser = new User();
                                databaseRef.child("Users").child(user.getUid()).setValue(newUser, new DatabaseReference.CompletionListener() {
                                    @Override
                                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                                        if (databaseError == null) {
                                            Toast.makeText(SignupActivity.this, "Successfully updated", Toast.LENGTH_LONG).show();
                                        } else {
                                            System.out.println("Error: " + databaseError);
                                            Toast.makeText(SignupActivity.this, "Unsuccessful...", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });

                                onSignupSuccess();
                            } else {
                                //display some message here
                                onSignupFailed();

                                //Toast.makeText(MainActivity.this, "Registration Error", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }

//        public void saveUserInformation(String name, int BankDetails, String email, Boolean isParent) {
//            //Getting values from database
////            String name = editTextName.getText().toString().trim();
////            int bankDetails = Integer.parseInt(editTextBankDetails.getText().toString());
////            boolean isParent = checkBoxIsParent.isChecked();
//
//
//            //creating a new user object
//            User user = new User(name, bankDetails, currentUser.getEmail(), isParent);
//
////            System.out.println(user.toString());
////            System.out.println(user.getName());
////            System.out.println(user.getBankDetails());
////            System.out.println(currentUser.getUid());
//
//            databaseReference.child("Users").child(currentUser.getUid()).setValue(user, new DatabaseReference.CompletionListener() {
//                @Override
//                public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
//                    if(databaseError == null){
//                        Toast.makeText(CreateUserActivity.this, "Successfully updated", Toast.LENGTH_LONG).show();
//                    }
//                    else{
//                        System.out.println("Error: "+ databaseError);
//                        Toast.makeText(CreateUserActivity.this, "Unsuccessful...", Toast.LENGTH_LONG).show();
//                    }
//                }
//            });
    }
}