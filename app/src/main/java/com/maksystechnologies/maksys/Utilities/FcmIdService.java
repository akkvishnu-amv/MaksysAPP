package com.maksystechnologies.maksys.Utilities;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.maksystechnologies.maksys.Activities.NotificationCustomer;
import com.maksystechnologies.maksys.Activities.NotificationEngineer;
import com.maksystechnologies.maksys.R;

import static com.maksystechnologies.maksys.Utilities.AppController.TAG;

public class FcmIdService extends FirebaseMessagingService {

    @Override
    public void onNewToken(String s) {
        Log.d(TAG, "Refreshed token: " + s);

//        SharedPrefManager.getInstance(getApplicationContext()).setKEY_Engineer_Token(s);

        Log.e("NEW_TOKEN",s);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
     SharedPreferences preferences=   PreferenceManager.getDefaultSharedPreferences(this);
        preferences.getAll();
        String msg=remoteMessage.getNotification().getBody();
        CharSequence messagebody =msg;
        Intent intent;
        if(SharedPrefManager.getInstance(getApplicationContext()).isCustomerLoggedIn()){
            intent = new Intent(this,NotificationCustomer.class);
        }else {
            intent = new Intent(this, NotificationEngineer.class);
        }

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);
        PendingIntent pendingIntent2 = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        String channelId = "Default Channel";
        String strRingtonePreference = preferences.getString("notifications_new_message_ringtone", "DEFAULT_SOUND");
        boolean vibrate = preferences.getBoolean("notifications_new_message_vibrate", true);

        Log.e("soundpreference",strRingtonePreference);
        String path = Environment.getRootDirectory().getAbsolutePath(); //Will return "/system"
        path = path+Uri.parse(strRingtonePreference.substring(9));
        Log.e("soundpreference",path);
        Uri defaultSoundUri = Uri.parse(path);
        Uri alarmSound = RingtoneManager
                .getDefaultUri(RingtoneManager.URI_COLUMN_INDEX);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, channelId)
                        .setSmallIcon(R.drawable.maksys_logo)
                        .setContentTitle(remoteMessage.getNotification().getTitle())
                        .setContentText(remoteMessage.getNotification().getBody())
                        .setContentInfo(remoteMessage.getNotification().getTag())
                        .setAutoCancel(false)
                        .setSound(alarmSound)
                        .addAction(R.drawable.maksys_logo,"Accept",pendingIntent)
                        .setPriority(NotificationCompat.PRIORITY_MAX)
                        .setStyle(new NotificationCompat.BigTextStyle().setSummaryText(messagebody))
                        .setContentIntent(pendingIntent);






        RingtoneManager.getRingtone(this, Uri.parse(strRingtonePreference)).play();


        if (vibrate == true ){


            notificationBuilder.setDefaults(
                    Notification.DEFAULT_LIGHTS
                            | Notification.DEFAULT_VIBRATE | Notification.PRIORITY_HIGH)
                    .setContentIntent(pendingIntent);


        } else {
            notificationBuilder.setDefaults(
                    Notification.DEFAULT_LIGHTS
                            | Notification.PRIORITY_HIGH)
                    .setContentIntent(pendingIntent);

        }
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());

    }
}
