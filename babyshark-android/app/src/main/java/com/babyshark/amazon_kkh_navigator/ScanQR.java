package com.babyshark.amazon_kkh_navigator;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class ScanQR extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_qr);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();

        if(actionBar!=null){
            actionBar.setTitle(R.string.title_activity_scan_qr);
            actionBar.setCustomView(R.layout.actionbar_custom);
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayShowCustomEnabled(true);
            actionBar.setDisplayUseLogoEnabled(false);
            actionBar.setDisplayShowHomeEnabled(false);
            actionBar.setIcon(R.drawable.kkh_logo);
        }

        Button scanner = findViewById(R.id.scanner);

        if(scanner!=null){
            scanner.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    ScanQRCode();
                }
            });
        }
    }

    public void ScanQRCode(){

        Intent mainactivity_intent = new Intent(ScanQR.this, MainActivity.class);
        startActivity(mainactivity_intent);

        finish();
    }
}
