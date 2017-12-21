package com.codegym.batterywidget;

import android.app.KeyguardManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Han on 12/19/2017.
 */

public class ScreenMonitorService extends Service {
    private static BroadcastReceiver screenOffReceiver;
    private static BroadcastReceiver screenOnReceiver;
    private static BroadcastReceiver userPresentReceiver;


    @Override
    public void onCreate() {
        super.onCreate();

        registerScreenOffReceiver();
        registerScreenOnReceiver();
        registerUserPresentReceiver();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        unregisterReceiver(screenOffReceiver);
        unregisterReceiver(screenOnReceiver);
        unregisterReceiver(userPresentReceiver);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void registerScreenOffReceiver() {
        screenOffReceiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                BatteryWidget.turnAlarmOnOff(context, false);
            }

        };

        registerReceiver(screenOffReceiver, new IntentFilter(Intent.ACTION_SCREEN_OFF));

    }

    private void registerScreenOnReceiver() {
        screenOnReceiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                KeyguardManager keyguardManager = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
                if (keyguardManager.inKeyguardRestrictedInputMode()) {
                    BatteryWidget.turnAlarmOnOff(context, true);
                }
            }

        };

        registerReceiver(screenOnReceiver, new IntentFilter(Intent.ACTION_SCREEN_ON));
    }


    private void registerUserPresentReceiver() {
        userPresentReceiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                BatteryWidget.turnAlarmOnOff(context, true);
            }

        };

        registerReceiver(userPresentReceiver, new IntentFilter(Intent.ACTION_USER_PRESENT));
    }
}
