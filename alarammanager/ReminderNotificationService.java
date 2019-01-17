package com.example.alitamoor.alarammanager;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.view.View;

import java.util.Calendar;

import static com.example.alitamoor.alarammanager.App.CHANNEL_ID;

public class ReminderNotificationService extends IntentService {


    public ReminderNotificationService() {
        super("ReminderNotificationService");
    }

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public ReminderNotificationService(String name) {
        super(name);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String input = intent.getStringExtra("inputExtra");

        Intent notificationIntent = new Intent(this, MainActivity.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0, notificationIntent, 0);

        Calendar calendar = Calendar.getInstance();
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Update your vehicle")
                .setContentText(input)
                .setSmallIcon(R.drawable.ic_local_car_wash_black_24dp)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
        .setContentText("Notification was created at: " + calendar.get(Calendar.MINUTE) +":"+calendar.get(Calendar.SECOND)+":"+calendar.get(Calendar.MILLISECOND))
                .setContentIntent(pendingIntent)
                .build();

        startForeground(1, notification);

        //do heavy work on a background thread
        //stopSelf();

        return START_NOT_STICKY;
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        /*String ns = Context.NOTIFICATION_SERVICE;
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(ns);

        Context context = getApplicationContext();
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
                context);

        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        mBuilder.setContentTitle("Notification");
        Calendar calendar = Calendar.getInstance();
        mBuilder.setContentText("Notification Text Content: " + calendar.get(Calendar.MINUTE));
        mBuilder.setSmallIcon(R.drawable.ic_local_car_wash_black_24dp);
        mBuilder.setSound(uri);

        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
        mBuilder.setContentIntent(contentIntent);
        mNotificationManager.notify(2, mBuilder.build());*/
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}