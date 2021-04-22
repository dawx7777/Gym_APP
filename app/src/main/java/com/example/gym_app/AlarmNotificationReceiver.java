package com.example.gym_app;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class AlarmNotificationReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        NotificationCompat.Builder builder=new NotificationCompat.Builder(context,"Channel");
builder.setAutoCancel(true)

                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.logo)
                .setContentText("Czas żeby ćwiczyć")
                .setContentTitle("To już ten moment")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
               .setDefaults(Notification.DEFAULT_ALL)
                .setContentInfo("Info");
        NotificationManager notificationManager= (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1,builder.build());
    }
}
