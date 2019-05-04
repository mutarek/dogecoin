package com.dogearn.dogemoney;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.doubleclick.PublisherAdRequest;
import com.google.android.gms.ads.doubleclick.PublisherAdView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

public class SigninActivity extends AppCompatActivity {

    private Button signin,signup;
    private EditText signinET,signPT;
    FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;
    private ProgressBar progressBar;
    FirebaseAuth.AuthStateListener authStateListener;
    private PublisherAdView mPublisherAdView;
    String user_id;
    private int myIntValue;
    DatabaseReference user_id_child;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        progressDialog = new ProgressDialog(SigninActivity.this);
        progressDialog.setMessage("Logging....");

        signinET = (EditText)findViewById(R.id.loginEmailET);

        signPT = (EditText)findViewById(R.id.loginPassET);

        signin = (Button)findViewById(R.id.signIn);

        signup = (Button)findViewById(R.id.createAcc);


        firebaseAuth = FirebaseAuth.getInstance();

        mPublisherAdView = findViewById(R.id.mAdviewLogin);
        PublisherAdRequest adRequest = new PublisherAdRequest.Builder().build();
        mPublisherAdView.loadAd(adRequest);

        mPublisherAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                mPublisherAdView.isShown();
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                Toast.makeText(SigninActivity.this, "Failed to load banner ads", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdOpened() {

            }

            @Override
            public void onAdLeftApplication() {

            }

            @Override
            public void onAdClosed() {
                mPublisherAdView.isShown();
            }
        });


        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String memail,mpassword;
                memail = signinET.getText().toString().trim();
                mpassword = signPT.getText().toString().trim();
                progressDialog.show();

                if(TextUtils.isEmpty(memail)   || TextUtils.isEmpty(mpassword)   ){

                    Toast.makeText(SigninActivity.this,"FILL ALL THE FIELDS",Toast.LENGTH_SHORT).show();
                    return;
                }

                firebaseAuth.signInWithEmailAndPassword(memail, mpassword)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    progressDialog.dismiss();
                                    Log.d("SUCCESS", "signInWithEmail:success");
                                    FirebaseUser user = firebaseAuth.getCurrentUser();

                                    Intent intent = new Intent(SigninActivity.this,HomeActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                                    finishAffinity();

                                    startActivity(intent);

                                } else {
                                    // If sign in fails, display a message to the user.
                                    progressDialog.dismiss();
                                    Log.w("FAILED", "signInWithEmail:failure", task.getException());
                                    Toast.makeText(SigninActivity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();

                                }

                                // ...
                            }
                        });




            }
        });




        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(SigninActivity.this,RegisterActivity.class);

                startActivity(intent);




            }
        });

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser()!=null){

                    startActivity(new Intent(SigninActivity.this,HomeActivity.class));


                }
            }
        };


    }

    @Override
    protected void onStart() {
        super.onStart();

        firebaseAuth.addAuthStateListener(authStateListener);


    }

    @Override
    public void finish() {
        super.finish();
        finish();
    }

}
