package com.babyshark.amazon_kkh_navigator;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ScanQR extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_qr);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();

        if(actionBar!=null){
            LayoutInflater inflator = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflator.inflate(R.layout.actionbar_custom, null);
            Resources res = (Resources) getResources();
            TextView textview = view.findViewById(R.id.actionbar_title);
            textview.setText(res.getString(R.string.title_activity_scan_qr));

            actionBar.setCustomView(view);
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

    private void ScanQRCode(){

        Intent login_intent = new Intent(ScanQR.this, Login.class);
        startActivity(login_intent);

        finish();
    }
}
