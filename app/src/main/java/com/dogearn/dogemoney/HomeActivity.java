package com.dogearn.dogemoney;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.doubleclick.PublisherAdRequest;
import com.google.android.gms.ads.doubleclick.PublisherInterstitialAd;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.Date;

import static com.dogearn.dogemoney.StaticNames.sharedTableName;
import static com.dogearn.dogemoney.Utilies.showAlertDialogue;

public class HomeActivity extends AppCompatActivity {

    private LinearLayout taskone, tasktwo, taskthree, taskfour;
    private TextView scorewalet, sample, countdownone,countDown;
    private Button button, profilebtn;
    private InterstitialAd interstitialAd;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    Date internetDate = null;

    ProgressDialog dialog;

    private PublisherInterstitialAd mPublisherInterstitialAd;


    FirebaseDatabase db = FirebaseDatabase.getInstance();
    DatabaseReference root = db.getReference();
    DatabaseReference userRef = root.child("users");
    String scores;
    DatabaseReference user_id_child;
    DatabaseReference databaseReference;
    String user_id;

    private static final int TIME_INTERVAL = 2000; // # milliseconds, desired time passed between two back presses.
    private long mBackPressed;

    private AdView mAdView;

    long lastclick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("users");
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        user_id = mAuth.getCurrentUser().getUid();
        user_id_child = databaseReference.child(user_id);

        taskone = findViewById(R.id.taskOneID);
        tasktwo = findViewById(R.id.taskTwoID);
        taskthree = findViewById(R.id.taskThreeID);
        taskfour = findViewById(R.id.taskFourID);
        scorewalet = findViewById(R.id.scoreWallet);
        button = findViewById(R.id.withbtn);
        profilebtn = findViewById(R.id.profileBTN);

        SharedPreferences sharedPreferences = getSharedPreferences("mypref",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String lastclicktime = sharedPreferences.getString("lastclicktime","");
        if(!lastclicktime.isEmpty()){
            try {
                //SimpleDateFormat dateFormat= new SimpleDateFormat("mm/dd/yyyy hh:mm:ss aa");
                long currenttime= Calendar.getInstance().getTimeInMillis();
                lastclick=Long.parseLong(lastclicktime);
                if (currenttime<lastclick+30000){
                    taskone.setClickable(false);
                    taskone.setEnabled(false);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }



        showAlertDialogue("Warning!", "Using VPN while using this app is strictly prohibited. Your account can be suspended if found so.", HomeActivity.this);
        preferences = HomeActivity.this.getSharedPreferences(sharedTableName, MODE_PRIVATE);


        MobileAds.initialize(this,"ca-app-pub-7300440519666493~2860439544");

        mAdView = findViewById(R.id.homebanneradView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        mPublisherInterstitialAd = new PublisherInterstitialAd(this);
        mPublisherInterstitialAd.setAdUnitId("ca-app-pub-7300440519666493/9754025691");
        mPublisherInterstitialAd.loadAd(new PublisherAdRequest.Builder().build());

        profilebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mPublisherInterstitialAd.isLoaded()) {
                    startActivity(new Intent(HomeActivity.this, ProfileActivity.class));
                    mPublisherInterstitialAd.show();
                } else {
                    startActivity(new Intent(HomeActivity.this, ProfileActivity.class));
                    Log.d("TAG", "The interstitial wasn't loaded yet.");
                }
            }
        });


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mPublisherInterstitialAd.isLoaded()) {
                    startActivity(new Intent(HomeActivity.this, WithdrawActivity.class));
                    mPublisherInterstitialAd.show();
                } else {
                    startActivity(new Intent(HomeActivity.this, WithdrawActivity.class));
                    Log.d("TAG", "The interstitial wasn't loaded yet.");
                }
            }
        });


        taskthree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (taskthree.isClickable()) {
                    startActivity(new Intent(HomeActivity.this, AdsoneActivity.class));
                    taskthree.setEnabled(false);
                    taskthree.postDelayed(new Runnable() {
                        public void run() {
                            taskthree.setEnabled(true);


                        }
                    }, 1200000000);

                    new CountDownTimer(1200000000, 1000) {

                        public void onTick(long millisUntilFinished) {

                        }


                        public void onFinish() {
                            Vibrator vb = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                            vb.vibrate(200);

                        }

                    }.start();
                } else {
                    Toast.makeText(HomeActivity.this, "Try After One hour", Toast.LENGTH_SHORT).show();
                }
            }
        });


        taskone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (taskone.isClickable()) {
                    SharedPreferences sharedPreferences = getSharedPreferences("mypref", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    long currenttime = Calendar.getInstance().getTimeInMillis();
                    editor.putString("lastclicktime", String.valueOf(currenttime));
                    editor.apply();

                    startActivity(new Intent(HomeActivity.this, ClaimActivity.class));
                    taskone.setEnabled(false);
                }
            }
        });

        tasktwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tasktwo.isClickable()) {
                    startActivity(new Intent(HomeActivity.this, VideoRewardActivity.class));
                    tasktwo.setEnabled(false);
                    tasktwo.postDelayed(new Runnable() {
                        public void run() {
                            tasktwo.setEnabled(true);


                        }
                    }, 6000000);

                    new CountDownTimer(6000000, 1000) {

                        public void onTick(long millisUntilFinished) {

                        }


                        public void onFinish() {
                            Vibrator vb = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                            vb.vibrate(200);

                        }

                    }.start();
                } else {
                    Toast.makeText(HomeActivity.this, "Please try again after 5 minutes ", Toast.LENGTH_SHORT).show();
                }
            }
        });

        taskfour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, ContactActivity.class);

                startActivity(intent);
            }
        });


        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot users : dataSnapshot.getChildren()) {
                        String user_points = dataSnapshot.child("News").getValue().toString();
                        scorewalet.setText("Notice:" + user_points);

                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(HomeActivity.this, "Please earn atleast 1 points", Toast.LENGTH_SHORT).show();
            }
        });

    }


    @Override
    public void onBackPressed() {
        finishAffinity();
    }

}
