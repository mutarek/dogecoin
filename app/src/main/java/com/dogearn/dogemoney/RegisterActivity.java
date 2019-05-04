package com.dogearn.dogemoney;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.ads.doubleclick.PublisherAdRequest;
import com.google.android.gms.ads.doubleclick.PublisherAdView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;

public class RegisterActivity extends AppCompatActivity {
    private Button signupbtn;
    private EditText firstnameEt, lastnameET, emailEt, passwordEt, referEt, phone, dogeearn, news, countryET;
    CheckBox checkBox;
    private FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListerer;
    private DatabaseReference databaseReference;
    ProgressDialog progressDialog;
    String user_id;
    private static final String user_dr = "users";
    private PublisherAdView mPublisherAdView;
    TelephonyManager telephonyManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference().child(user_dr);

        signupbtn = findViewById(R.id.signupbtn);
        firstnameEt = findViewById(R.id.firstnameET);
        lastnameET = findViewById(R.id.lastnameET);
        emailEt = findViewById(R.id.userEmailET);
        passwordEt = findViewById(R.id.userpasswordET);
        referEt = findViewById(R.id.userReferET);
        phone = findViewById(R.id.userPhoneET);
        checkBox = findViewById(R.id.termsAndCondition);
        dogeearn = findViewById(R.id.userDogearnID);
        news = findViewById(R.id.newsID);
        countryET = findViewById(R.id.usercountryET);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading....");

        mPublisherAdView = findViewById(R.id.mAdviewregister);
        PublisherAdRequest adRequest = new PublisherAdRequest.Builder().build();
        mPublisherAdView.loadAd(adRequest);


        deviceId();


        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startRegister();
            }
        });
    }



    public void startRegister() {

        progressDialog.show();
        final String user_first_name = firstnameEt.getText().toString();
        final String user_last_name = lastnameET.getText().toString();
        final String user_email = emailEt.getText().toString();
        final String user_pass = passwordEt.getText().toString();
        final String user_phone = phone.getText().toString();
        final String user_refer = referEt.getText().toString();
        final String user_dogeearnID = dogeearn.getText().toString();
        final String user_News = news.getText().toString();
        final String user_country = countryET.getText().toString();

        if (user_first_name.isEmpty()) {
            firstnameEt.setError("First Name is required");
            firstnameEt.requestFocus();
            return;
        }
        if (user_last_name.isEmpty()) {
            lastnameET.setError("Last Name is required");
            lastnameET.requestFocus();
            return;
        }
        if (user_pass.isEmpty()) {
            passwordEt.setError("Password is required");
            passwordEt.requestFocus();
            return;
        }
        if (user_email.isEmpty()) {
            emailEt.setError("Email is required");
            emailEt.requestFocus();
            return;
        }
        if (user_phone.isEmpty()) {
            phone.setError("Phone is required");
            phone.requestFocus();
            return;
        }
        if (user_dogeearnID.isEmpty()) {
            dogeearn.setError("Dogearn is required");
            dogeearn.requestFocus();
        }
        if (user_country.isEmpty()) {
            countryET.setError("Country is required");
            countryET.requestFocus();
        }

        mAuth.createUserWithEmailAndPassword(user_email, user_pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            progressDialog.dismiss();
                            Intent intent = new Intent(RegisterActivity.this, HomeActivity.class);
                            startActivity(intent);
                            Toast.makeText(RegisterActivity.this, "SUCCESS.", Toast.LENGTH_SHORT).show();
                            Log.d("SUCCESS", "createUserWithEmail:success");
                            String user_id = mAuth.getCurrentUser().getUid();

                            DatabaseReference user_id_child = databaseReference.child(user_id);

                            user_id_child.child("First Name").setValue(user_first_name);
                            user_id_child.child("Last Name").setValue(user_last_name);
                            user_id_child.child("Email").setValue(user_email);
                            user_id_child.child("Password").setValue(user_pass);
                            user_id_child.child("Refer").setValue(user_refer);
                            user_id_child.child("Phone").setValue(user_phone);
                            user_id_child.child("Register Dogeearn").setValue(user_dogeearnID);
                            user_id_child.child("News").setValue(user_News);
                            user_id_child.child("Country").setValue(user_country);
                            //Generate 6 digit unique refer id
                            String zeroes = "000000";
                            Random rand = new Random();
                            String s = Integer.toString(rand.nextInt(0X1000000), 16);
                            s = zeroes.substring(s.length()) + s;
                            user_id_child.child("referID").setValue(s);
                            updateUserWallet(user_refer);


                        } else {
                            progressDialog.dismiss();

                            Log.w("FAIL", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(RegisterActivity.this, "" + task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });


    }



    private void updateUserWallet(String referID) {


        Query ref = FirebaseDatabase.getInstance().getReference()
                .child("users").orderByChild("referID").equalTo(referID).limitToFirst(1);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {

                    for (DataSnapshot data: dataSnapshot.getChildren()){

                        if (data.child("scores").exists()) {
                            int score = Integer.valueOf(data.child("scores").getValue().toString()) + 10;
                            data.getRef().child("scores").setValue(score);
                        }
                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void deviceId() {
        telephonyManager = (TelephonyManager) getSystemService(this.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, 101);
            return;
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 101:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, 101);
                        return;
                    }
                    String imeiNumber = telephonyManager.getDeviceId();
                    DatabaseReference user_id_child = databaseReference.child(user_id);
                    user_id_child.child("emi_number").setValue(imeiNumber);

                } else {
                    Toast.makeText(RegisterActivity.this,"Without permission we check",Toast.LENGTH_LONG).show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}
