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

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.doubleclick.PublisherAdRequest;
import com.google.android.gms.ads.doubleclick.PublisherInterstitialAd;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AdstenActivity extends AppCompatActivity {

    private Button button;
    private TextView countdown;
    private PublisherInterstitialAd mPublisherInterstitialAd;
    String user_id;
    private int myIntValue;
    DatabaseReference user_id_child;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adsten);

        button = findViewById(R.id.nextTaskTen);
        countdown = findViewById(R.id.countDownTen);


        databaseReference = FirebaseDatabase.getInstance().getReference().child("users");
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        user_id = mAuth.getCurrentUser().getUid();

        user_id_child = databaseReference.child(user_id);


        mPublisherInterstitialAd = new PublisherInterstitialAd(this);
        mPublisherInterstitialAd.setAdUnitId("ca-app-pub-9062980565707854/4012951725");
        mPublisherInterstitialAd.loadAd(new PublisherAdRequest.Builder().build());

        new CountDownTimer(10000, 1000) {

            public void onTick(long millisUntilFinished) {
                countdown.setText(" " + millisUntilFinished / 1000);
            }


            public void onFinish() {
                countdown.setText("done!");
                Vibrator vb = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                vb.vibrate(200);

            }

        }.start();

        button.setVisibility(View.INVISIBLE);
        button.postDelayed(new Runnable() {
            public void run() {
                button.setVisibility(View.VISIBLE);


            }
        }, 10000);

        mPublisherInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                mPublisherInterstitialAd.show();

            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                Toast.makeText(AdstenActivity.this, "Ad failed to load", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdOpened() {

            }

            @Override
            public void onAdLeftApplication() {
                Toast.makeText(AdstenActivity.this, "You made a wrong click in Task Ten", Toast.LENGTH_SHORT).show();
                int n = myIntValue + 1;
                user_id_child.child("Invadil").setValue(+n);

            }

            @Override
            public void onAdClosed() {
                mPublisherInterstitialAd.show();
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdstenActivity.this, AdselevenActivity.class));
            }
        });
    }
}
