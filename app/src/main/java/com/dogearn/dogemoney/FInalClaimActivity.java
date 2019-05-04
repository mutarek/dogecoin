package com.dogearn.dogemoney;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.doubleclick.PublisherAdRequest;
import com.google.android.gms.ads.doubleclick.PublisherInterstitialAd;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static com.dogearn.dogemoney.Utilies.showAlertDialogue;

public class FInalClaimActivity extends AppCompatActivity {

    private EditText captca;
    private Button claim;
    private Button one, two, three, four, five, six, seven, eight, nine, zero, cut;
    private PublisherInterstitialAd mPublisherInterstitialAd;
    int myIntValue;
    DatabaseReference user_id_child;
    String user_id;
    DatabaseReference databaseReference;

    private AdView adView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_claim);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("users");
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        user_id = mAuth.getCurrentUser().getUid();

        user_id_child = databaseReference.child(user_id);

        MobileAds.initialize(this,
                "ca-app-pub-7300440519666493~2860439544");

        adView = findViewById(R.id.finalclainadView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        captca = findViewById(R.id.typehereID);
        claim = findViewById(R.id.finalClaimID);
        zero = findViewById(R.id.zero);
        one = findViewById(R.id.one);
        two = findViewById(R.id.two);
        three = findViewById(R.id.three);
        four = findViewById(R.id.four);
        five = findViewById(R.id.five);
        six = findViewById(R.id.six);
        seven = findViewById(R.id.seven);
        eight = findViewById(R.id.eight);
        nine = findViewById(R.id.nine);
        cut = findViewById(R.id.clearAll);

        SharedPreferences sp = getSharedPreferences("your_prefs", Activity.MODE_PRIVATE);
        myIntValue = sp.getInt("your_int_key", 0);

        MobileAds.initialize(this,
                "ca-app-pub-7300440519666493~2860439544");


        mPublisherInterstitialAd = new PublisherInterstitialAd(this);
        mPublisherInterstitialAd.setAdUnitId("ca-app-pub-7300440519666493/7312831629");
        mPublisherInterstitialAd.loadAd(new PublisherAdRequest.Builder().build());

        mPublisherInterstitialAd.show();

        zero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                captca.setText(captca.getText() + "0");
            }
        });

        one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                captca.setText(captca.getText() + "1");

            }
        });
        two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                captca.setText(captca.getText() + "2");

            }
        });
        three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                captca.setText(captca.getText() + "3");

            }
        });

        four.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                captca.setText(captca.getText() + "4");

            }
        });
        five.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                captca.setText(captca.getText() + "5");

            }
        });
        six.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                captca.setText(captca.getText() + "6");

            }
        });
        seven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                captca.setText(captca.getText() + "7");

            }
        });
        eight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                captca.setText(captca.getText() + "8");

            }
        });
        nine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                captca.setText(captca.getText() + "9");
            }
        });

        cut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                captca.setText("");

            }
        });

        claim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (captca.equals("")) {
                    claim.setVisibility(View.INVISIBLE);
                } else {
                    claim.setVisibility(View.VISIBLE);
                    if (mPublisherInterstitialAd.isLoaded()) {
                        showAlertDialogue("Rewarded", "Congratulation You earn 1 points", FInalClaimActivity.this);
                        Toast.makeText(FInalClaimActivity.this, "Congratulations, you earn 1 points", Toast.LENGTH_SHORT).show();

                        SharedPreferences sp = getSharedPreferences("your_prefs", Activity.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sp.edit();
                        int n = myIntValue + 1;
                        editor.putInt("your_int_key", n);
                        editor.commit();
                        user_id_child.child("scores").setValue(+n);
                        mPublisherInterstitialAd.show();
                    } else {
                        Toast.makeText(FInalClaimActivity.this, "Please try again after few minutes", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });



    }
}

