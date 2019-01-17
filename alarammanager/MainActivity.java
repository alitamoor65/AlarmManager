package com.example.alitamoor.alarammanager;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Build;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    AlarmManager alarmManager;
    PendingIntent pendingIntent;
    int counter = 0;
    AlarmReceiver receiver;
    String TAG = "ReceiverMain";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //stopService("empty");
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetAlarm();
            }
        });
    }

    public void SetAlarm()
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        /*calendar.set(Calendar.HOUR_OF_DAY, 17);
        calendar.set(Calendar.MINUTE, 20);
        calendar.set(Calendar.SECOND, 00);*/
        String time = calendar.get(Calendar.HOUR_OF_DAY) +":" + calendar.get(Calendar.MINUTE)
                + ":" + calendar.get(Calendar.SECOND) + ":" + calendar.get(Calendar.MILLISECOND);
        Log.i(TAG, "SetAlarm: " +time);
        ((TextView)findViewById(R.id.textViewID)).setText(time);
        receiver = new AlarmReceiver();
        this.registerReceiver( receiver, new IntentFilter("com.blah.blah.somemessage") );
        Intent intent = new Intent(this,AlarmReceiver.class);
        PendingIntent pintent = PendingIntent.getBroadcast( getApplicationContext(), 0, intent, 0 );
        AlarmManager manager = (AlarmManager)(getSystemService( Context.ALARM_SERVICE ));
        manager.setRepeating( AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+(3*60000), (3*60000), pintent );
        //manager.setExact( AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+60000, pintent );
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(receiver != null){
            unregisterReceiver(receiver);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(receiver != null){
            receiver = new AlarmReceiver();
            this.registerReceiver( receiver, new IntentFilter("com.blah.blah.somemessage") );
        }
    }

    public void stopService(String string) {
        Intent serviceIntent = new Intent(this, ReminderNotificationService.class);
        stopService(serviceIntent);
    }
}
