package com.dogearn.dogemoney;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.doubleclick.PublisherAdRequest;
import com.google.android.gms.ads.doubleclick.PublisherInterstitialAd;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AdsoneActivity extends AppCompatActivity {
    private TextView countdowntxt, txt;
    private Button taskone;
    private PublisherInterstitialAd mPublisherInterstitialAd;
    String user_id;
    private int myIntValue;
    DatabaseReference user_id_child;
    DatabaseReference databaseReference;
    private AdView adView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adsone);

        countdowntxt = findViewById(R.id.countDownID);
        taskone = findViewById(R.id.task1);
        txt = findViewById(R.id.textString);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("users");
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        user_id = mAuth.getCurrentUser().getUid();

        user_id_child = databaseReference.child(user_id);

        MobileAds.initialize(this,
                "ca-app-pub-7300440519666493~2860439544");

        adView = findViewById(R.id.adsoneadView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);


        mPublisherInterstitialAd = new PublisherInterstitialAd(this);
        mPublisherInterstitialAd.setAdUnitId("ca-app-pub-7300440519666493/2453546751");
        mPublisherInterstitialAd.loadAd(new PublisherAdRequest.Builder().build());

        taskone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        new CountDownTimer(10000, 1000) {

            public void onTick(long millisUntilFinished) {
                countdowntxt.setText(" " + millisUntilFinished / 1000);
            }


            public void onFinish() {
                countdowntxt.setText("done!");
                Vibrator vb = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                vb.vibrate(200);

            }

        }.start();

        taskone.setVisibility(View.INVISIBLE);
        taskone.postDelayed(new Runnable() {
            public void run() {
                taskone.setVisibility(View.VISIBLE);


            }
        }, 10000);

        taskone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mPublisherInterstitialAd.isLoaded()) {
                    startActivity(new Intent(AdsoneActivity.this,AdstwoActivity.class));
                    mPublisherInterstitialAd.show();
                } else {
                    Toast.makeText(AdsoneActivity.this, "Not ads at that time", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
