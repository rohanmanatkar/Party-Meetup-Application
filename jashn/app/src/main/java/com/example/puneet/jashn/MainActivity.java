package com.example.puneet.jashn;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.EditText;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    LoginButton fbloginButton;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private Button buttonLogin;
    private Button buttonSignUp;
    private Button buttonForgot;
    private static final String EMAIL = "email";
    CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                if(firebaseAuth.getCurrentUser()!= null){

                    Intent intent = new Intent(MainActivity.this,SearchEventActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
            }
        };

        editTextEmail = findViewById(R.id.emailButton);
        editTextPassword = findViewById(R.id.passwordButton);
        buttonSignUp = findViewById(R.id.signupButton);
        callbackManager = CallbackManager.Factory.create();
        fbloginButton = (LoginButton) findViewById(R.id.facebookLoginButton);
        fbloginButton.setReadPermissions("email", "public_profile");
        fbloginButton.setReadPermissions(Arrays.asList(EMAIL));
        // If you are using in a fragment, call loginButton.setFragment(this);



        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        boolean isLoggedIn = accessToken != null && !accessToken.isExpired();


        // Callback registration
        fbloginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
                Toast.makeText(getApplicationContext(), loginResult.getAccessToken().getUserId() + "\n" + loginResult.getAccessToken().getToken(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancel() {
                // App code
                Toast.makeText(getApplicationContext(), "Log in Cancelled", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
            }
        });


        mAuth = FirebaseAuth.getInstance();
        buttonSignUp = findViewById(R.id.signupButton);
        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = editTextEmail.getText().toString().trim();
                final String password = editTextPassword.getText().toString().trim();
                if (TextUtils.isEmpty(email)) {
                    //email field is empty
                    Toast.makeText(MainActivity.this, "Please enter email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!isEmailValid(email)){
                    Toast.makeText(MainActivity.this, "Please enter valid email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    //password field is empty
                    Toast.makeText(MainActivity.this, "Please enter password", Toast.LENGTH_SHORT).show();
                    return;
                }
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    editTextEmail.setText("");
                                    editTextPassword.setText("");
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d(TAG, "createUserWithEmail:success");
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    Toast.makeText(getApplicationContext(), "Registered Successfully", Toast.LENGTH_SHORT).show();
//                                    updateUI(user);
                                } else {
                                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                    Toast.makeText(getApplicationContext(), "Registered Not Successful", Toast.LENGTH_SHORT).show();
                                    editTextEmail.setText(" ");
                                    editTextPassword.setText(" ");
                                }
                            }
                        });
                sendMessage(email,password);

            }
        });


        buttonLogin = findViewById(R.id.loginButton);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = editTextEmail.getText().toString().trim();
                if (TextUtils.isEmpty(email)) {
                    //email field is empty
                    Toast.makeText(MainActivity.this, "Please enter email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!isEmailValid(email)){
                    Toast.makeText(MainActivity.this, "Please enter valid email", Toast.LENGTH_SHORT).show();
                    return;
                }
                final String password = editTextPassword.getText().toString().trim();
                if (TextUtils.isEmpty(password)) {
                    //password field is empty
                    Toast.makeText(MainActivity.this, "Please enter password", Toast.LENGTH_SHORT).show();
                    return;
                }

                Log.d(TAG, "onClick: email id :" + email);
                Log.d(TAG, "onClick: password :" + password);
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    editTextEmail.setText("");
                                    editTextPassword.setText("");
                                    Log.d(TAG, "signInWithEmail:success");
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    //startActivity(new Intent(MainActivity.this,EventActivity.class));
                                    if (user != null) {
                                        // Name, email address, and profile photo Url
                                        String name = user.getDisplayName();
                                        String email = user.getEmail();
                                        Uri photoUrl = user.getPhotoUrl();

                                        // Check if user's email is verified
                                        boolean emailVerified = user.isEmailVerified();

                                        // The user's ID, unique to the Firebase project. Do NOT use this value to
                                        // authenticate with your backend server, if you have one. Use
                                        // FirebaseUser.getToken() instead.
                                        String uid = user.getUid();
                                        Intent intent = new Intent(MainActivity.this,SearchEventActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);
                                    }



                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                    Log.d(TAG, "onComplete: password is " + password);
                                    Toast.makeText(getApplicationContext(), "Login Not Successful", Toast.LENGTH_SHORT).show();
                                    editTextEmail.setText("");
                                    editTextPassword.setText("");
                                }

                                // ...
                            }
                        });
            }
        });

        buttonForgot = findViewById(R.id.forgotpassButton);
        buttonForgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editTextEmail.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter your email!", Toast.LENGTH_LONG).show();
                    return;
                }
                mAuth.sendPasswordResetEmail(email)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Log.d(TAG, "Email sent.");
                                    Toast.makeText(MainActivity.this, "Email Sent", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            }
        });

    }

    public void sendMessage(String email,String password){
        Intent intent = new Intent(this,ProfileActivity.class);
        Bundle extras = new Bundle();
        extras.putString("EMAIL",email);
        extras.putString("PASSWORD",password);
        intent.putExtras(extras);
        startActivity(intent);
    }


    public  void goToHome(View v){
        startActivity(new Intent(MainActivity.this,EventActivity.class));

    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
//        updateUI(currentUser);
        mAuth.addAuthStateListener(mAuthStateListener);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }
    boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}
