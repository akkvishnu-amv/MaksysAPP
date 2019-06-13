package com.maksystechnologies.maksys.Utilities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.maksystechnologies.maksys.Activities.MainEngineerActivity;

public class AttendanceBroadCastReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
//        AudioManager am = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
//        am.setRingerMode(AudioManager.RINGER_MODE_SILENT);
//        Toast.makeText(context, "Alarm", Toast.LENGTH_LONG).show();
        SharedPrefManager.getInstance(context).clearEngineerAttendedTodayCount();
//        Toast.makeText(context, "Time Up... Now Vibrating !!!",
//                Toast.LENGTH_LONG).show();
        MainEngineerActivity mainEngineerActivity=new MainEngineerActivity();
        mainEngineerActivity.setSwichStatus();
//        Vibrator vibrator = (Vibrator) context
//                .getSystemService(Context.VIBRATOR_SERVICE);
//        vibrator.vibrate(2000);
    }
}
