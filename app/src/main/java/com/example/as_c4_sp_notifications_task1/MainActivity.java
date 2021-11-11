package com.example.as_c4_sp_notifications_task1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.util.AndroidException;
import android.view.View;

public class MainActivity extends AppCompatActivity {
private NotificationManagerCompat manager;
public static final String NORMAL_CHANNEL = "Normal_Channel";
    public static final String IMPORTANT_CHANNEL = "IMPORTANT_CHANNEL";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        manager = NotificationManagerCompat.from(this);
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O)
        {
            String name = getResources().getString(R.string.NOT_IMPORTANT_CHANNEL_NAME);
            NotificationChannel channel = new NotificationChannel(
                    NORMAL_CHANNEL,
                    name,
                    NotificationManager.IMPORTANCE_LOW);
            String description = getResources().getString(R.string.NOT_IMPORTANT_CHANNEL_DESCRIPTION);
            channel.setDescription(description);
            channel.enableVibration(false);

            manager.createNotificationChannel(channel);
        }
    }

    public void simpleNotification(View view)
    {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,NORMAL_CHANNEL);
        builder.setSmallIcon(android.R.drawable.btn_star).setContentTitle("Простое уведомление").setContentText("Что-то бесполезное произошло");
        builder.setLargeIcon
                (
                        BitmapFactory.decodeResource(getResources(),R.drawable.scal)
                );
        manager.notify(R.id.SIMPLE_NOTIFICATION_ID,builder.build());
    }

    public void simpleCancel(View view)
    {
        manager.cancel(R.id.SIMPLE_NOTIFICATION_ID);
    }
}