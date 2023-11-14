package com.example.exerciseapp.mResource;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.exerciseapp.SettingsActivity;

public class ReminderBroadcast extends BroadcastReceiver {

    public static String NOTIFICATION_ID = "notification_id";
    public static String NOTIFICATION = "notification";

    @Override
    public void onReceive(Context context, Intent intent) {

        NotificationManager notificationManager = (NotificationManager) context.
                getSystemService(context.NOTIFICATION_SERVICE);
        Notification notification = intent.getParcelableExtra(NOTIFICATION);
        int id = intent.getIntExtra(NOTIFICATION_ID, 0);

        createNotificationChannel(notificationManager);

        if (notification != null) {
            notificationManager.notify(id, notification);
        }
    }

    private void createNotificationChannel(NotificationManager notificationManager) {
        int importance = NotificationManager.IMPORTANCE_HIGH;
        NotificationChannel channel = new NotificationChannel(
                SettingsActivity.NOTIFICATION_CHANNEL_ID, "NOTIFICATION_NAME", importance);
        notificationManager.createNotificationChannel(channel);
    }
}
