package com.dogearn.dogemoney;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class OrginalActivity extends AppCompatActivity {

    private Button websiteid,freeincomeid;
    private AdView mAdView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orginal);

        websiteid=findViewById(R.id.websiteID);
        freeincomeid=findViewById(R.id.freeincomeID);

        MobileAds.initialize(this,
                "ca-app-pub-7300440519666493~2860439544");

        mAdView = findViewById(R.id.homeadView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        websiteid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(OrginalActivity.this,WebsiteActivity.class));
            }
        });


        freeincomeid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(OrginalActivity.this,SplashActivity.class));
            }
        });


    }

    @Override
    public void onBackPressed() {
        finishAffinity();
    }
}
