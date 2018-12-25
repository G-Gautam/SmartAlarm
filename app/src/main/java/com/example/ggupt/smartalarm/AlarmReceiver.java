package com.example.ggupt.smartalarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        // Test
        // Log.e("HIT","lol");
        Intent serviceIntent = new Intent(context, AlarmSoundService.class);

        //Start the sound service
        context.startService(serviceIntent);
    }
}
