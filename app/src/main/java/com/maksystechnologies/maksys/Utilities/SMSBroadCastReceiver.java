package com.maksystechnologies.maksys.Utilities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class SMSBroadCastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

//        Bundle bundle = intent.getExtras();
//        SmsMessage[] smsm = null;
//        String sms_str ="";
//
//        if (bundle != null)
//        {
//            // Get the SMS message
//            Object[] pdus = (Object[]) bundle.get("pdus");
//            smsm = new SmsMessage[pdus.length];
//            for (int i=0; i<smsm.length; i++){
//                smsm[i] = SmsMessage.createFromPdu((byte[])pdus[i]);
//
//                sms_str += "\r\nMessage: ";
//                sms_str += smsm[i].getMessageBody().toString();
//                sms_str+= "\r\n";
//
//                String Sender = smsm[i].getOriginatingAddress();
//                //Check here sender is yours
//                Intent smsIntent = new Intent("otp");
//                smsIntent.putExtra("message",sms_str);
//
//                LocalBroadcastManager.getInstance(context).sendBroadcast(smsIntent);
//
//            }
//        }

    }
}
