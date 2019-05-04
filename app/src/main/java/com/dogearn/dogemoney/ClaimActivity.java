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

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class ClaimActivity extends AppCompatActivity {

    private Button claim;
    private TextView textView;
    private AdView adView1,adView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_claim);
        textView=findViewById(R.id.countDownclaim);
        claim = findViewById(R.id.claimId);

        MobileAds.initialize(this,
                "ca-app-pub-7300440519666493~2860439544");

        adView1 = findViewById(R.id.claimbanner1adview);
        adView2=findViewById(R.id.claimbanner2adView);
        AdRequest adRequest1 = new AdRequest.Builder().build();
        AdRequest adRequest2 = new AdRequest.Builder().build();
        adView2.loadAd(adRequest2);
        adView1.loadAd(adRequest1);

        claim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (claim.isClickable()) {
                    startActivity(new Intent(ClaimActivity.this, FInalClaimActivity.class));
                    claim.setVisibility(View.INVISIBLE);
                    claim.postDelayed(new Runnable() {
                        public void run() {
                            claim.setVisibility(View.VISIBLE);


                        }
                    }, 300000);

                    new CountDownTimer(300000, 1000) {

                        public void onTick(long millisUntilFinished) {

                        }


                        public void onFinish() {
                            Vibrator vb = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                            vb.vibrate(200);

                        }

                    }.start();
                } else {

                }
                textView .setVisibility(View.INVISIBLE);
                textView .postDelayed(new Runnable() {
                    public void run() {
                        textView .setVisibility(View.VISIBLE);


                    }
                }, 300000);
            }

        });
    }
}
