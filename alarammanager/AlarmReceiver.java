package com.example.alitamoor.alarammanager;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.util.Log;

import java.util.Calendar;

public class AlarmReceiver extends BroadcastReceiver {
    String TAG = "Receiver";
    @Override
    public void onReceive(Context context, Intent intent) {

            Intent i = new Intent(context, ReminderNotificationService.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(i);
        }else {
            context.startService(i);
        }
        if(i.getAction() != null){
            if(i.getAction().equals("android.intent.action.BOOT_COMPLETED")){
                SetAlarm(context);
            }
        }
    }
    public void SetAlarm(Context context)
    {
        AlarmReceiver receiver = new AlarmReceiver();
        context.registerReceiver( receiver, new IntentFilter("com.blah.blah.somemessage") );
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        /*calendar.set(Calendar.HOUR_OF_DAY, 17);
        calendar.set(Calendar.MINUTE, 20);
        calendar.set(Calendar.SECOND, 00);*/
        Log.i(TAG, "SetAlarm: " + calendar.get(Calendar.HOUR_OF_DAY) +":" + calendar.get(Calendar.MINUTE)
        + ":" + calendar.get(Calendar.SECOND) + ":" + calendar.get(Calendar.MILLISECOND));

        Intent intent = new Intent(context,AlarmReceiver.class);
        PendingIntent pintent = PendingIntent.getBroadcast( context, 0, intent, 0 );
        AlarmManager manager = (AlarmManager)(context.getSystemService( Context.ALARM_SERVICE ));
        //manager.setRepeating( AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+60000, 60000, pintent );
        manager.setWindow( AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+60000, 1, pintent );
    }
}
