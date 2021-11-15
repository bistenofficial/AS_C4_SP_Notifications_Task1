package com.example.as_c4_sp_notifications_task1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.AndroidException;
import android.view.View;
import android.widget.CalendarView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
private NotificationManagerCompat manager;
    ArrayList<BirthDate> birthList = new ArrayList();
public static final String NORMAL_CHANNEL = "Normal_Channel";
public static final String IMPORTANT_CHANNEL = "IMPORTANT_CHANNEL";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        manager = NotificationManagerCompat.from(this);
        SetBirthData();

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

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, NORMAL_CHANNEL);
        CalendarView calendar = findViewById(R.id.calendarView1);
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener()
        {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth)
            {
                int mYear = year;
                int mMonth = month + 1;
                int mDay = dayOfMonth;

                for (int i=0;i<birthList.size();i++) {
                    if ((birthList.get(i).getDayOfBirth() == mDay) && (birthList.get(i).getMonthOfBirth() == mMonth) && (birthList.get(i).getYearOfBirth() <= mYear)) {
                        Intent a2 = new Intent(MainActivity.this, MainActivity2.class);
                        PendingIntent pa2 = PendingIntent.getActivity(MainActivity.this,
                                R.id.BIRTHDAY_PENDING_ID, a2, PendingIntent.FLAG_UPDATE_CURRENT);
                        builder.setSmallIcon(android.R.drawable.sym_def_app_icon);
                        builder.setContentTitle("На этот день назачено мероприятие");
                        builder.setContentText(birthList.get(i).getName());
                        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.scal));
                        builder.setContentIntent(pa2);
                        builder.setAutoCancel(true);
                        manager.notify(R.id.BIRTHDAY_NOTIFICATION_ID, builder.build());
                    }
                }
                }
        });
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

    public void browserNotification(View view)
    {
        Intent a2 = new Intent(this, MainActivity2.class);
        PendingIntent pa2 = PendingIntent.getActivity(this,R.id.BROWSER_PENDING_ID,a2,PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,NORMAL_CHANNEL);
        builder.setSmallIcon(android.R.drawable.sym_def_app_icon);
        builder.setContentTitle("Запустить браузер");
        builder.setContentText("Просмотреть google.com");
        builder.setContentIntent(pa2);
        builder.setAutoCancel(true);
        manager.notify(R.id.GOOGLE_NOTIFICATION_ID,builder.build());
    }

    public void complexNotification(View view)
    {
        Intent browser= new Intent(Intent.ACTION_VIEW);
        browser.setData(Uri.parse("https://new.guap.ru"));
        PendingIntent browerPI = PendingIntent.getActivity(this,R.id.BROWSER_PENDING_ID,browser,PendingIntent.FLAG_UPDATE_CURRENT);
        Intent map= new Intent(Intent.ACTION_VIEW);
        map.setData(Uri.parse("geo:48.85,2.34"));
        PendingIntent mapPI = PendingIntent.getActivity(this,R.id.MAP_PENDING_ID,map,PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,NORMAL_CHANNEL);

        builder.setSmallIcon(android.R.drawable.sym_def_app_icon);
        builder.setContentTitle("Экскурсия");
        builder.setContentText("Начинается через 5 минут");

        builder.addAction(new NotificationCompat.Action(android.R.drawable.btn_star,"В браузере",browerPI));
        builder.addAction(new NotificationCompat.Action(android.R.drawable.btn_star,"На карте",mapPI));
        manager.notify(R.id.LOUVRE_NOTIFICATION_ID,builder.build());
    }
    public void SetBirthData()
    {
        birthList.add(new BirthDate(10,10,2010,"День рождения номер 1"));
        birthList.add(new BirthDate(16,11,1995,"День рождения номер 2"));
        birthList.add(new BirthDate(16,9,2009,"День рождения номер 3"));
        birthList.add(new BirthDate(30,6,2020,"День рождения номер 4"));
    }
}