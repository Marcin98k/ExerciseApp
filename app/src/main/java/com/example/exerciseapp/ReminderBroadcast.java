package com.example.exerciseapp;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class ReminderBroadcast extends BroadcastReceiver {

    public static String NOTIFICATION_ID = "notification_id";
    public static String NOTIFICATION = "notification";

    @Override
    public void onReceive(Context context, Intent intent) {

        NotificationManager notificationManager = (NotificationManager) context.
                getSystemService(context.NOTIFICATION_SERVICE);
        Notification notification = intent.getParcelableExtra(NOTIFICATION);
        int importance = NotificationManager.IMPORTANCE_HIGH;
        NotificationChannel channel = new NotificationChannel(
                SettingsActivity.NOTIFICATION_CHANNEL_ID, "NOTIFICATION_NAME", importance);
        assert notificationManager != null;
        notificationManager.createNotificationChannel(channel);
        int id = intent.getIntExtra(NOTIFICATION_ID, 0);
        assert notification != null;
        notificationManager.notify(id, notification);
    }
}
