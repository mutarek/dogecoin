package com.dogearn.dogemoney;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity {

    private TextView firstname, lastname,  phone, email, dogeran;

    private Button button, refer,invalid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        firstname = findViewById(R.id.firstname);
        lastname = findViewById(R.id.lastname);
        email = findViewById(R.id.email);
        phone = findViewById(R.id.phone);
        dogeran = findViewById(R.id.dogeearn);
        button = findViewById(R.id.scoreWalletID);
        refer = findViewById(R.id.referemail);
        invalid=findViewById(R.id.invalidclick);

        invalid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProfileActivity.this,InvalidClickActivity.class));
            }
        });

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot users : dataSnapshot.getChildren()) {
                       try {
                           String user_first_name = dataSnapshot.child("First Name").getValue().toString();
                           String user_last_name = dataSnapshot.child("Last Name").getValue().toString();
                           String user_email = dataSnapshot.child("Email").getValue().toString();
                           String user_phone = dataSnapshot.child("Phone").getValue().toString();
                           String user_dogearn = dataSnapshot.child("Register Dogeearn").getValue().toString();
                           String user_points = dataSnapshot.child("scores").getValue().toString();
                           String user_refer = dataSnapshot.child("Phone").getValue().toString();
                           String user_referID = dataSnapshot.child("referID").getValue().toString();
                           firstname.setText(user_first_name);
                           lastname.setText(user_last_name);
                           email.setText("Email:" + user_email);
                           phone.setText("Phone:" + user_phone);
                           dogeran.setText("Dog_Earn:" +
                                   "" + user_dogearn);
                           if (user_points.isEmpty()) {
                               button.setText("Earning: 0 $");
                           } else {
                               button.setText("Earning:" + user_points + "$");
                           }

                           refer.setText("Refer:" + user_referID);
                       }catch (Exception e){
                           e.printStackTrace();
                           Toast.makeText(ProfileActivity.this, "Database error", Toast.LENGTH_SHORT).show();
                       }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

}
