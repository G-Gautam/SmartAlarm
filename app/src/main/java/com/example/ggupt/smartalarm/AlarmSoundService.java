package com.example.ggupt.smartalarm;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

public class AlarmSoundService extends Service{
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId){

        return START_NOT_STICKY;
    }
    @Override
    public void onDestroy(){
        Toast.makeText(this, "onDestroy Called", Toast.LENGTH_SHORT).show();
    }
}
