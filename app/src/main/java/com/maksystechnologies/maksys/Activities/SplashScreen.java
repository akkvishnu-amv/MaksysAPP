package com.maksystechnologies.maksys.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.maksystechnologies.maksys.R;
import com.maksystechnologies.maksys.Utilities.SharedPrefManager;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash_screen);


        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        Thread splash=new Thread(){
            public void run(){

                try{
                    Intent splash_intent=new Intent();
                    sleep(4*1000);
                    if(SharedPrefManager.getInstance(getApplicationContext()).isCustomerLoggedIn()){

                        if(SharedPrefManager.getInstance(getApplicationContext()).isCustomerSetPin()){

                            splash_intent=new Intent(getApplicationContext(),LockScreenCustomer.class);

                        }else {
                            splash_intent=new Intent(getApplicationContext(),MainCustomerActivity.class);
                        }
                    }else  if (SharedPrefManager.getInstance(getApplicationContext()).isEngineerLoggedIn()){


                            if (SharedPrefManager.getInstance(getApplicationContext()).isEngineerSetPin()){
                                splash_intent=new Intent(getApplicationContext(),LockScreenEngineer.class);

                            }else{
                                splash_intent=new Intent(getApplicationContext(),MainEngineerActivity.class);
                            }

                    }else {
                        splash_intent=new Intent(getApplicationContext(),LoginCustomer.class);
                    }
                    startActivity(splash_intent);
                    finish();
                }catch (Exception e){

                }
            }


        };
        splash.start();
    }

    @Override
    public void onBackPressed() {
        // Simply Do noting!
    }
//    public boolean isNetworkConnecteted(){
//        ConnectivityManager cm=(ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
//        return cm.getActiveNetworkInfo()!=null;
//    }
}
