package com.dogearn.dogemoney;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import static com.dogearn.dogemoney.Utilies.showAlertDialogue;

public class WithdrawActivity extends AppCompatActivity {
    EditText redeem_no, amount_no,paymenttype;
    private TextView wallet;
    Button submit;
    String user_no;
    String user_amount;
    String user_id;
    DatabaseReference user_id_child;
    String scores;
    int default_score = 0;
    int myIntValue;
    int ss;
    int threshold = 100;
    int currentScore = 0;
    String uID;
    private Button urgent,delay;

    FirebaseUser user;
    FirebaseDatabase db = FirebaseDatabase.getInstance();
    DatabaseReference root = db.getReference();
    DatabaseReference userRef = root.child("users");
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdraw);

        showAlertDialogue("Tips", "If you withdraw your payment in the end of the month,you get double bonus ", WithdrawActivity.this);


        redeem_no =findViewById(R.id.mobileText);
        amount_no = findViewById(R.id.amountText);
        submit = findViewById(R.id.withdrawButton);
        wallet = findViewById(R.id.walletBalanceText);
        urgent=findViewById(R.id.urgentWithdraw);
        delay=findViewById(R.id.delayWithdraw);
        paymenttype=findViewById(R.id.paymentType);


        mAdView = findViewById(R.id.qadView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        SharedPreferences sp = getSharedPreferences("your_prefs", Activity.MODE_PRIVATE);
        myIntValue = sp.getInt("your_int_key", 0);

        DatabaseReference databaseReference;
        databaseReference = FirebaseDatabase.getInstance().getReference().child("users");


        final FirebaseAuth mAuth = FirebaseAuth.getInstance();
        user_id = mAuth.getCurrentUser().getUid();


        user_id_child = databaseReference.child(user_id);


        SharedPreferences sharedPreferences = getSharedPreferences("your_prefs", Activity.MODE_PRIVATE);
        final int myIntValue = sharedPreferences.getInt("your_int_key", -1);




        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot users : dataSnapshot.getChildren()) {
                        String user_points = dataSnapshot.child("scores").getValue().toString();
                        wallet.setText("Your Earning :" + user_points+"$");

                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(WithdrawActivity.this, "Please earn atleast 1 points", Toast.LENGTH_SHORT).show();
            }
        });

        urgent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submit.setVisibility(View.INVISIBLE);
                delay.setVisibility(View.INVISIBLE);

                user_no = redeem_no.getText().toString();
                user_amount = amount_no.getText().toString();
               String user_type = paymenttype.getText().toString();

               if (TextUtils.isEmpty(user_type)){
                   Toast.makeText(WithdrawActivity.this, "Please enter payment type", Toast.LENGTH_SHORT).show();

               }


                if (TextUtils.isEmpty(user_amount)) {
                    Toast.makeText(WithdrawActivity.this, "Please enter your amount", Toast.LENGTH_SHORT).show();
                }

                if (TextUtils.isEmpty(user_no)) {

                    Toast.makeText(WithdrawActivity.this, " Please enter your Dogecoin ID", Toast.LENGTH_SHORT).show();

                } else if (myIntValue > threshold) {

                    SharedPreferences sp = getSharedPreferences("your_prefs", Activity.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();

                    currentScore = myIntValue - threshold;

                    editor.putInt("your_int_key", currentScore);
                    editor.commit();
                    Toast.makeText(WithdrawActivity.this, "Congratulations, Now please restart the app", Toast.LENGTH_LONG).show();

                    user_id_child.child("withdraw_phone").setValue(user_no);
                    user_id_child.child("withdraw_amount").setValue(user_amount);

                    checkScore();


                } else {

                    Toast.makeText(WithdrawActivity.this, "You need minimum " + threshold + " points", Toast.LENGTH_SHORT).show();
                }
            }
        });

        delay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submit.setVisibility(View.INVISIBLE);
                urgent.setVisibility(View.INVISIBLE);


                user_no = redeem_no.getText().toString();
                user_amount = amount_no.getText().toString();


                if (TextUtils.isEmpty(user_amount)) {
                    Toast.makeText(WithdrawActivity.this, "Please enter your amount", Toast.LENGTH_SHORT).show();
                }

                if (TextUtils.isEmpty(user_no)) {

                    Toast.makeText(WithdrawActivity.this, " Please enter your Dogecoin ID", Toast.LENGTH_SHORT).show();

                } else if (myIntValue > threshold) {

                    SharedPreferences sp = getSharedPreferences("your_prefs", Activity.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();

                    currentScore = myIntValue - threshold;

                    editor.putInt("your_int_key", currentScore);
                    editor.commit();
                    Toast.makeText(WithdrawActivity.this, "Congratulations, Now please restart the app", Toast.LENGTH_LONG).show();

                    user_id_child.child("withdraw_phone").setValue(user_no);
                    user_id_child.child("withdraw_amount").setValue(user_amount);

                    checkScore();


                } else {

                    Toast.makeText(WithdrawActivity.this, "You need minimum " + threshold + " points", Toast.LENGTH_SHORT).show();
                }
            }
        });


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delay.setVisibility(View.INVISIBLE);
                urgent.setVisibility(View.INVISIBLE);


                user_no = redeem_no.getText().toString();
                user_amount = amount_no.getText().toString();


                if (TextUtils.isEmpty(user_amount)) {
                    Toast.makeText(WithdrawActivity.this, "Please enter your amount", Toast.LENGTH_SHORT).show();
                }

                if (TextUtils.isEmpty(user_no)) {

                    Toast.makeText(WithdrawActivity.this, " Please enter your Dogecoin ID", Toast.LENGTH_SHORT).show();

                } else if (myIntValue > threshold) {

                    SharedPreferences sp = getSharedPreferences("your_prefs", Activity.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();

                    currentScore = myIntValue - threshold;

                    editor.putInt("your_int_key", currentScore);
                    editor.commit();
                    Toast.makeText(WithdrawActivity.this, "Congratulations, Now please restart the app", Toast.LENGTH_LONG).show();

                    user_id_child.child("withdraw_phone").setValue(user_no);
                    user_id_child.child("withdraw_amount").setValue(user_amount);

                    checkScore();


                } else {

                    Toast.makeText(WithdrawActivity.this, "You need minimum " + threshold + " points", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }

    public void checkScore() {

        userRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                String value = dataSnapshot.getValue().toString();
                try {
                    JSONObject object = new JSONObject(value);
                    scores = object.getString("scores");
                    ss = Integer.parseInt(scores);

                    if (ss >= threshold) {

                        submit.setEnabled(false);
                        String details = object.getString("details");


                        user_id_child.child("scores").setValue(+default_score);


                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();

    }
}
