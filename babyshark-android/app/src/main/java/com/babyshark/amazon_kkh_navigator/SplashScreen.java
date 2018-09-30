package com.babyshark.amazon_kkh_navigator;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class SplashScreen extends AppCompatActivity {

    private static int SPLASH_TIME = 1000; // 4 seconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {

                //Do any action here.
                Intent mySuperIntent = new Intent(SplashScreen.this, ScanQR.class);
                startActivity(mySuperIntent);

                /* The finish() is for exiting the app when back button pressed
                   from Home page which is ActivityHome
                   https://github.com/SabithPkcMnr/SplashScreen/blob/master/app/src/main/java/com/sabithpkcmnr/splashscreen/ActivitySplash.java */

                finish();
            }

        }, SPLASH_TIME);

    }

}
