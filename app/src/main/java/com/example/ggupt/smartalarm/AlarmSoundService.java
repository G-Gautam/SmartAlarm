package com.example.ggupt.smartalarm;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

public class AlarmSoundService extends Service{
    MediaPlayer mediaPlayer;
    int start;
    boolean isRunning;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        String state = intent.getExtras().getString("Extra");
        if(state.equals("yes")){
            start = 1;
        }
        else if(state.equals("no")){
            start = 0;
        }
        else{
            start = 0;
        }

        if(!this.isRunning && start == 1){
            mediaPlayer = MediaPlayer.create(this, R.raw.dope);
            mediaPlayer.start();
            this.isRunning = true;
            this.start = 0;
        }
        else if(this.isRunning && start == 0){
            mediaPlayer.stop();
            mediaPlayer.reset();

            this.isRunning = false;
            this.start = 0;
        }
        else if(!this.isRunning && start == 0){

            this.isRunning = false;
            this.start = 0;
        }
        else if(this.isRunning && start == 1){

            this.isRunning = true;
            this.start = 0;
        }


        return START_NOT_STICKY;
    }
    @Override
    public void onDestroy(){
        Toast.makeText(this, "onDestroy Called", Toast.LENGTH_SHORT).show();
    }
}
