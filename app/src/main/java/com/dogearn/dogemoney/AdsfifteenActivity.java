package com.dogearn.dogemoney;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.doubleclick.PublisherAdRequest;
import com.google.android.gms.ads.doubleclick.PublisherInterstitialAd;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static com.dogearn.dogemoney.Utilies.showAlertDialogue;

public class AdsfifteenActivity extends AppCompatActivity {

    private Button finish;
    private PublisherInterstitialAd mPublisherInterstitialAd;
    String user_id;
    int myIntValue;
    DatabaseReference user_id_child;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adsfifteen);

        finish=findViewById(R.id.finishBtn);

        showAlertDialogue("Tips", "You have to click this ads and wait here 1 minute", AdsfifteenActivity.this);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("users");
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        user_id = mAuth.getCurrentUser().getUid();

        user_id_child = databaseReference.child(user_id);

        SharedPreferences sp = getSharedPreferences("your_prefs", Activity.MODE_PRIVATE);
        myIntValue = sp.getInt("your_int_key", 0);


        MobileAds.initialize(this,"ca-app-pub-9062980565707854~1156398682");

        mPublisherInterstitialAd = new PublisherInterstitialAd(this);
        mPublisherInterstitialAd.setAdUnitId("ca-app-pub-9062980565707854/8629058978");
        mPublisherInterstitialAd.loadAd(new PublisherAdRequest.Builder().build());


        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdsfifteenActivity.this,HomeActivity.class));
            }
        });


        mPublisherInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                mPublisherInterstitialAd.show();
                Toast.makeText(AdsfifteenActivity.this, "Click this ads now ", Toast.LENGTH_SHORT).show();
                Toast.makeText(AdsfifteenActivity.this, "Wait 20 second here", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {

                Toast.makeText(AdsfifteenActivity.this, "Please try again after few minutes", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdOpened() {
                Toast.makeText(AdsfifteenActivity.this, "Click This Ads", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdLeftApplication() {
                Toast.makeText(AdsfifteenActivity.this,"Congratulations, you  earn 10 points", Toast.LENGTH_SHORT).show();
                SharedPreferences sp = getSharedPreferences("your_prefs", Activity.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                int n =  myIntValue+10;
                editor.putInt("your_int_key", n);
                editor.commit();
                user_id_child.child("scores").setValue(+n);
            }

            @Override
            public void onAdClosed() {
                Toast.makeText(AdsfifteenActivity.this, "You have to click this ads", Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(AdsfifteenActivity.this,HomeActivity.class));
        finish();
    }
}
