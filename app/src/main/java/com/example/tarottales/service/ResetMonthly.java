package com.example.tarottales.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

public class ResetMonthly extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("tarotMonthly", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("week1", 0);
        editor.putInt("week2", 0);
        editor.putInt("week3", 0);
        editor.putInt("week4", 0);
        editor.putBoolean("isWeek1Open", false);
        editor.putBoolean("isWeek2Open", false);
        editor.putBoolean("isWeek3Open", false);
        editor.putBoolean("isWeek4Open", false);
        editor.commit();
    }
}
