package com.dogearn.dogemoney;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class ContactActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
    }

    public void dogEarn(View view) {
        Uri uri = Uri.parse("https://www.dogearn.info/"); // missing 'http://' will cause crashed
        Intent intenti = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intenti);
    }

    public void faceBook(View view) {
        Uri uri = Uri.parse("https://www.dogearn.info/"); // missing 'http://' will cause crashed
        Intent intentm = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intentm);
    }

    public void telegram(View view) {
        Uri uri = Uri.parse("https://t.me/dogearn_info"); // missing 'http://' will cause crashed
        Intent intentj = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intentj);
    }

    public void liveSupport(View view) {
        Uri uri = Uri.parse("https://www.dogearn.info/"); // missing 'http://' will cause crashed
        Intent intentk = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intentk);
    }
}
