package com.example.tarottales.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.example.tarottales.MainActivity;
import com.example.tarottales.R;

public class ResetOpenDaily extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        SharedPreferences pref = context.getSharedPreferences("tarotDailyDay", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("isOpenToday", false);
        editor.commit();
        Log.d("Hieu", "Alarm triggered");
        sendNotification(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void sendNotification(Context context) {
        Intent i = new Intent(context, MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pi = PendingIntent.getActivity(context, 10, i, PendingIntent.FLAG_IMMUTABLE);
        Notification noti = new NotificationCompat.Builder(context, "Cart").
                setSmallIcon(R.drawable.tarot_back).setContentTitle("\"Lượt mở thẻ hôm nay của bạn đã sẵn sàng!♥").
                setPriority(NotificationCompat.PRIORITY_MAX)
                .setContentText("Chúc bạn một ngày mới tốt lành, khám phá ngay ngày hôm nay của bạn nào!!!♥♥♥♥♥♥♥♥♥♥")
                .setStyle(new NotificationCompat.BigTextStyle()
                .bigText("Chúc bạn một ngày mới tốt lành, khám phá ngay ngày hôm nay của bạn nào!!!♥♥♥♥♥♥♥♥♥♥"))
                .setAutoCancel(true).
                setContentIntent(pi).build();
        //
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel("Cart", "Daily Notification", NotificationManager.IMPORTANCE_HIGH);
            manager.createNotificationChannel(channel);
            manager.notify((int) System.currentTimeMillis(), noti);
        }
    }
}
