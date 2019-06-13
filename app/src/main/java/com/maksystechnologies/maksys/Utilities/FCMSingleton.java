package com.maksystechnologies.maksys.Utilities;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class FCMSingleton {
    private static FCMSingleton fcmSingleton;
    private static Context mContext;
    private RequestQueue requestQueue;


    private RequestQueue getRequestQueue(){
        if(requestQueue==null){

            requestQueue=Volley.newRequestQueue(mContext.getApplicationContext());
        }
        return requestQueue;

    }
    private FCMSingleton(Context context){
        mContext=context;
        requestQueue=getRequestQueue();

    }
    public static synchronized  FCMSingleton getFcmSingleton(Context context){
        if (mContext==null){
            fcmSingleton=new FCMSingleton(context);
        }
        return fcmSingleton;
    }

    public<T> void addToRequestQue(Request<T> request){
        getRequestQueue().add(request);
    }
}
