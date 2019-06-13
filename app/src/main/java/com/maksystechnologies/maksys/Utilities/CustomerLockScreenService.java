package com.maksystechnologies.maksys.Utilities;

import android.app.KeyguardManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

public class CustomerLockScreenService  {
    private boolean isLocked=false;
    private boolean unLocked=false;

    public boolean isUnlocked(Context context)
    {

        final IntentFilter theFilter = new IntentFilter();
        /** System Defined Broadcast */
        theFilter.addAction(Intent.ACTION_SCREEN_ON);
        theFilter.addAction(Intent.ACTION_SCREEN_OFF);
        theFilter.addAction(Intent.ACTION_USER_PRESENT);

        BroadcastReceiver screenOnOffReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String strAction = intent.getAction();

                KeyguardManager myKM = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
                if(strAction.equals(Intent.ACTION_USER_PRESENT) || strAction.equals(Intent.ACTION_SCREEN_OFF) || strAction.equals(Intent.ACTION_SCREEN_ON)  )
                    if( myKM.inKeyguardRestrictedInputMode())
                    {
                        System.out.println("Screen off " + "LOCKED");
                        isLocked=true;
                        unLocked=false;
                    } else
                    {
                        isLocked=false;
                        unLocked=true;
                        System.out.println("Screen off " + "UNLOCKED");

                    }

            }
        };

        context.registerReceiver(screenOnOffReceiver, theFilter);
        return isLocked;
    }

}
