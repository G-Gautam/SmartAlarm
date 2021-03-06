package com.example.ggupt.smartalarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Camera;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import java.io.IOException;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    AlarmManager alarmManager;
    TimePicker timePicker;
    TextView status;
    Button setAlarm;
    Button stopAlarm;
    Calendar calendar;
    Context context;
    PendingIntent pendingIntent;
    boolean isAlarmSet = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        timePicker = (TimePicker) findViewById(R.id.timePicker);
        status = (TextView) findViewById(R.id.status);
        setAlarm = (Button) findViewById(R.id.set);
        stopAlarm = (Button) findViewById(R.id.check);
        this.context = this;

        calendar = Calendar.getInstance();

        //Creating an intent for the alarm receiver class
        final Intent myIntent = new Intent(this.context, AlarmReceiver.class);

        //On-click listeners for both buttons
        setAlarm.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(isAlarmSet == false) {
                    calendar.set(Calendar.HOUR_OF_DAY, timePicker.getHour());
                    calendar.set(Calendar.MINUTE, timePicker.getMinute());

                    String time;
                    int hour;
                    int minute;
                    String stringHour;
                    String stringMinute;

                    //Convert to 12 hour display
                    hour = timePicker.getHour();
                    minute = timePicker.getMinute();
                    stringHour = String.valueOf(hour);
                    stringMinute = String.valueOf(minute);

                    if (minute < 10) {
                        stringMinute = "0" + String.valueOf(minute);
                    }
                    if (hour > 12) {
                        hour = hour - 12;
                        stringHour = String.valueOf(hour);
                        time = stringHour + ":" + stringMinute + "PM";
                    } else {
                        time = stringHour + ":" + stringMinute + "AM";
                    }

                    myIntent.putExtra("Extra", "yes");
                    pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0,
                            myIntent, PendingIntent.FLAG_UPDATE_CURRENT);

                    //Set the alarm manager
                    alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                            pendingIntent);

                    setStatus("Alarm set for " + time);
                    setAlarm.setText("UNSET ALARM");

                    isAlarmSet = true;
                }

                else if(isAlarmSet == true){
                    setStatus("No Alarm Set");
                    setAlarm.setText("SET ALARM");
                    alarmManager.cancel(pendingIntent);

                    myIntent.putExtra("Extra", "no");

                    sendBroadcast(myIntent);

                    isAlarmSet = false;
                }


            }
        });


        stopAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setStatus("Time to take the picture");
                takePicture();
            }

        });

    }
    private void takePicture() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 0);
    }


    private void setStatus(String s) {
        status.setText(s);
    }


}
