package com.babyshark.amazon_kkh_navigator;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.jsibbold.zoomage.ZoomageView;

import java.util.List;

public class Navigation {
    final int wifiScanInterval = 3;

    Activity activity;
    List<ScanResult> results;
    WifiManager wifiManager;
    TextView tv;
    ZoomageView currMap;

    Button btn_level1;
    Button btn_level2;
    Button btn_level3;

    public Navigation(Activity activity) {
        this.activity = activity;

//        tv = activity.findViewById(R.id.wifi_testing);
        currMap = activity.findViewById(R.id.indoor_map_view);

        initButtons();

//        wifiManager = (WifiManager) activity.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
//        refreshWifiResults(wifiScanInterval);
//        movePosition(5);


    }

    private void initButtons() {
        btn_level1 = activity.findViewById(R.id.btn_level2);
        btn_level2 = activity.findViewById(R.id.btn_level1);
        btn_level3 = activity.findViewById(R.id.btn_levelB1);

        btn_level1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currMap.setImageResource(R.drawable.level1);
            }
        });

        btn_level2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currMap.setImageResource(R.drawable.level2);
            }
        });

        btn_level3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currMap.setImageResource(R.drawable.level3);
            }
        });
    }


    /**
     * For demo purpose I am going to move the position via signal strength
     */
    public void movePosition(int y) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(currMap, "y", 0, 1000, 500);
        objectAnimator.setDuration(10000);
        objectAnimator.start();

//        TranslateAnimation ts = new TranslateAnimation(0,3,3,33,3,3,3,3);
    }

    public void getScanResult() {
        wifiManager.startScan();
        results = wifiManager.getScanResults();
    }

    public int getSignalStrength(int rssi) {
        return WifiManager.calculateSignalLevel(rssi, 100);
    }

    public void logScanResult() {
        Log.d("Start", "----------------------------------------------------------------------------------------------------------------------------------------------");
//        for (ScanResult result : results) {
//            Log.d("WifiScanResult", "Name:" + result.SSID + "\tlevel:" + getSignalStrength(result.level));
//        }
        tv.setText("Signal:" + getSignalStrength(wifiManager.getConnectionInfo().getRssi()));
        Log.d("Scan", "Signal:" + getSignalStrength(wifiManager.getConnectionInfo().getRssi()));
        Log.d("End", "----------------------------------------------------------------------------------------------------------------------------------------------");
    }

    /**
     * @param interval in Seconds
     */
    public void refreshWifiResults(int interval) {
        final int timeInterval = interval * 1000;

        final Handler h = new Handler();
        h.post(new Runnable() {
            @Override
            public void run() {
                getScanResult();
                logScanResult();
                h.postDelayed(this, timeInterval);
            }
        });
    }


}
