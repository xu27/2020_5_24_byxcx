package com.example.xcxlibrary.Util;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;

import java.io.File;

import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

public class NoticeHelper {


    public final String NAME = "my_channel_01";
    public final String NAME_ID = "my_channel_01";


    public NoticeHelper() {
    }

    public void suspension_notice(Context context, int icon, String title, String value, PendingIntent intent) {
        NotificationManager manager;
        manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, NAME_ID);
        //构造安卓8.0Channel
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            addChannel(manager, NAME_ID);
        }
        //加入默认操作
        builder.setSmallIcon(icon);
        builder.setAutoCancel(true);
        builder.setContentTitle(title);
        builder.setTicker(value);
        builder.setContentText(value);
        builder.setWhen(System.currentTimeMillis());
        builder.setOngoing(true);
        if(intent!=null){
            builder.setContentIntent(intent);
        }
        manager.notify((int) System.currentTimeMillis(),builder.build());

    }

    @TargetApi(Build.VERSION_CODES.O)
    public void addChannel(NotificationManager manager,String id) {
        NotificationChannel channel = manager.getNotificationChannel(NAME_ID);
        if (channel != null) {
            channel = new NotificationChannel(id, NAME, manager.IMPORTANCE_HIGH);
        }
        channel.setShowBadge(true);
        manager.createNotificationChannel(channel);
    }

}
