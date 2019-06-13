package com.maksystechnologies.maksys.Activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.maksystechnologies.maksys.Fragments.Change_Pin_Engineer;
import com.maksystechnologies.maksys.Fragments.EngineerScheduledTickets;
import com.maksystechnologies.maksys.Fragments.Engineer_Home_Fragment;
import com.maksystechnologies.maksys.Fragments.Profile_Engineer_Fragment;
import com.maksystechnologies.maksys.Fragments.Service_Request_Fragement_Engineer;
import com.maksystechnologies.maksys.Fragments.Ticket_Engineer_Fragment;
import com.maksystechnologies.maksys.R;
import com.maksystechnologies.maksys.Utilities.AttendanceBroadCastReceiver;
import com.maksystechnologies.maksys.Utilities.SharedPrefManager;
import com.maksystechnologies.maksys.Utilities.URLs;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class MainEngineerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, LocationListener ,Service_Request_Fragement_Engineer.OnFragmentInteractionListener {
    SplashScreen mainActivity;
    AlarmManager alarmManager;
    private PendingIntent pendingIntent;
    public Switch mySwitch;

    private AudioManager audio;
    private PendingIntent pendingIntentam;
    private PendingIntent pentdingIntentpm;


    private static MainEngineerActivity inst;
    LinearLayout nav_home;
    ToggleButton attendance;
    Boolean toogledProgrammatically = false;
    private boolean isChecked = false;
    public double latitude,longitude;
    String status, id;


    public static final int RequestPermissionCode = 1;
    Button buttonEnable, buttonGet,exit;
    TextView textViewLongitude, textViewLatitude;
    Context context;
    Intent intent1;
    Location location;
    LocationManager locationManager;
    boolean GpsStatus = false;
    Criteria criteria;
    String Holder;

    public static MainEngineerActivity instance() {
        return inst;
    }

    @Override
    public void onStart() {
        super.onStart();
        inst = this;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_engineer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            mainActivity=new SplashScreen();
//        if(!mainActivity.isNetworkConnecteted()){
//            Toast.makeText(getApplicationContext(),"Check Your Internet",Toast.LENGTH_LONG).show();
//        }

//        Drawable drawable = ContextCompat.getDrawable(getApplicationContext(),R.drawable.round_notifications_white);
//        toolbar.setOverflowIcon(drawable);
        setSupportActionBar(toolbar);
        exit=findViewById(R.id.btn_exit);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

//        AlarmManager alarm = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
//        Intent intent1 = new Intent(getBaseContext(), AttendanceBroadCastReceiver.class);
//        pendingIntent = PendingIntent.getBroadcast(getBaseContext(), 0, intent1, 0);
//

//
//        AlarmManager am = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
//
//        Calendar calendaram = Calendar.getInstance();
//        calendaram.setTimeInMillis(System.currentTimeMillis());
//        calendaram.set(Calendar.HOUR_OF_DAY, 12);
//        calendaram.set(Calendar.MINUTE, 45);
//        calendaram.set(Calendar.SECOND, 0);
//        Intent intent2 = new Intent(getApplicationContext(), AttendanceBroadCastReceiver.class);
//
//        PendingIntent sender = PendingIntent.getBroadcast(getApplicationContext(), 0, intent2, PendingIntent.FLAG_UPDATE_CURRENT);
//        am.set(AlarmManager.RTC_WAKEUP, calendaram.getTimeInMillis(), sender);

        //set time for activate toggle or switch//


        String token= FirebaseInstanceId.getInstance().getToken();
//Toast.makeText(getApplicationContext(),token,Toast.LENGTH_LONG).show();



        SharedPrefManager.getInstance(getApplicationContext()).setKEY_Engineer_Token(token);
//        insertFCMToken();

        startAlertAtParticularTime();


//
//        alarm.setRepeating(AlarmManager.RTC_WAKEUP, calendaram.getTimeInMillis(),
//                AlarmManager.INTERVAL_DAY, pendingIntent);


//        alarmManager=(AlarmManager).getSystemService(Context.ALARM_SERVICE);


        EnableRuntimePermission();

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        criteria = new Criteria();

        Holder = locationManager.getBestProvider(criteria, false);

        context = getApplicationContext();

        CheckGpsStatus();
        if (GpsStatus == false) {

            intent1 = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(intent1);
        }



//location get code

        CheckGpsStatus();

        if(GpsStatus == true) {
            if (Holder != null) {
                if (ActivityCompat.checkSelfPermission(
                        MainEngineerActivity.this,
                        Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                        &&
                        ActivityCompat.checkSelfPermission(MainEngineerActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION)
                                != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                location = locationManager.getLastKnownLocation(Holder);
                locationManager.requestLocationUpdates(Holder, 12000, 7, MainEngineerActivity.this);
            }
        }else {

            Toast.makeText(MainEngineerActivity.this, "Please Enable GPS First", Toast.LENGTH_LONG).show();

        }




        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
            @Override
            public void onComplete(@NonNull Task<InstanceIdResult> task) {
                if (!task.isSuccessful()) {
                    Log.w("Token Tag", "getInstanceId failed", task.getException());
                    return;
                }
                String token = task.getResult().getToken();

                // Log and toast

                Log.d("Token Tag", token);

                SharedPrefManager.getInstance(getApplicationContext()).setKEY_Engineer_Token(token);
                insertFCMToken();


            }
        });

        if (!SharedPrefManager.getInstance(getApplicationContext()).isEngineerSetPin()) {
            Intent intent = new Intent(getApplicationContext(), LockScreenEngineerCreate.class);
            startActivity(intent);
        }



//        checkAttendance(attendance);
        final DrawerLayout drawer = findViewById(R.id.drawer_layout1);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        View headerview = navigationView.getHeaderView(0);

        navigationView.setNavigationItemSelectedListener(this);
        nav_home = headerview.findViewById(R.id.nav_home);
        displaySelectedScreen(R.id.nav_home);

        nav_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displaySelectedScreen(R.id.nav_home);
            }
        });

//        if (getIntent().getExtras() != null) {
//            String current = getIntent().getExtras().getString("currentticket");
//            if(current.equals("1")){
//                displaySelectedScreen(R.id.nav_engineer_Current_Ticket);
//                Log.e("currentticket",current);
//
//            }
//        }

    }


    @Override
    public void finish() {
        super.finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_engineer_right, menu);
//        MenuItem item = menu.findItem(R.id.action_attendance_engineer);
//        item.setActionView(R.layout.togglemenuitem);
//        mySwitch = item.getActionView().findViewById(R.id.nav_engineer_attendence_toggle);


//
        attendance();

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_notifications_engineer) {
           Intent intent=new Intent(getBaseContext(),NotificationEngineer.class);
           startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        displaySelectedScreen(item.getItemId());


        return true;

    }

    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout1);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (doubleBackToExitPressedOnce) {
//            super.onBackPressed();
                finish();
                return;
            }

        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Press again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

    private void displaySelectedScreen(int itemId) {

        //creating fragment object
        Fragment fragment = null;

        //initializing the fragment object which is selected
        switch (itemId) {
            case R.id.nav_engineer_Ticket:
                fragment = new Ticket_Engineer_Fragment();
                break;

            case R.id.nav_engineer_request:
                fragment = new Service_Request_Fragement_Engineer();
                break;
            case R.id.nav_engieer_profile:
                fragment = new Profile_Engineer_Fragment();
                break;
            case R.id.nav_attendance:
                attendance();
                break;
            case R.id.nav_engineer_settings:
                Intent intent1 = new Intent(getApplicationContext(), SettingsCustomerActivity.class);
                startActivity(intent1);
                break;
            case R.id.nav_home:
                fragment = new Engineer_Home_Fragment();
                break;
            case R.id.nav_engineer_change_pin:
                fragment = new Change_Pin_Engineer();
                break;
//            case R.id.nav_engineer_Current_Ticket:
//                fragment = new CurrentTicketEngineerFragment();
//                break;

            case R.id.nav_engineer_schedule:
                fragment =new EngineerScheduledTickets();
                break;
//            case R.id.nav_engineer_pending_service:
//                startActivity(new Intent(getApplicationContext(),ServiceRequestDetails.class));
//                break;
//            case R.id.nav_engineer_attendence:
//
//                break;
            case R.id.nav_engineer_logout:

                final AlertDialog.Builder builder=new AlertDialog.Builder(MainEngineerActivity.this);// use you activity name
                builder.setMessage("Are you sure you want to completely Logou? ")
                        .setPositiveButton("Yes",
                                new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        finish();
                                        SharedPrefManager.getInstance(getApplicationContext()).logoutEngineer();
                                        Intent intent = new Intent(getApplicationContext(), LoginEngineer.class);
                                        startActivity(intent);


                                    }
                                })

                        .setNegativeButton("No",new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();

                            }
                        });
                AlertDialog dialog=builder.create();
                dialog.show();
                break;

        }





        //replacing the fragment
        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame1, fragment);
            ft.commit();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout1);
        drawer.closeDrawer(GravityCompat.START);
    }



    private void setAttendance() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.EngineerAttendance,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.e("Volleyresponse", response.toString());
                        try {
                            JSONArray jArray;
                            JSONObject Jobject = (JSONObject) new JSONTokener(response).nextValue();
                            Log.e("volleyJson", Jobject.toString());

//
                            status = Jobject.getString("status");
                            Log.d("status result", status);
                            String id1 = Jobject.getString("id");
                            Log.d("id result", id1);

//
                            id = id1;


                        } catch (Exception e) {
                        }


                        switch (status) {
                            case "success":
//                                Toast.makeText(getApplicationContext(), "Logged in", Toast.LENGTH_SHORT).show();

                                SharedPrefManager.getInstance(getApplicationContext()).setKEY_Engineer_AttendanceID(id);
                                break;

                            case "fail":
                                Toast.makeText(getApplicationContext(), "Something Went Wrong!.", Toast.LENGTH_SHORT).show();
                                break;
                        }
                    }

                    ;
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                            pDialog.hide();
//                        Toast.makeText(MainEngineerActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<String, String>();
                params.put("engineer_id", SharedPrefManager.getInstance(getApplicationContext()).getKEY_Engineer_ID());

//                    Log.e("country code send",countrycode);
                return params;
            }

        };

        //Adding the string request to the queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

    public void setAttendanceUpdate() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.EngineerAttendanceUpdate,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.e("Volleyresponse", response.toString());
                        try {
                            JSONArray jArray;
                            JSONObject Jobject = (JSONObject) new JSONTokener(response).nextValue();
                            Log.e("volleyJson", Jobject.toString());

//
                            status = Jobject.getString("status");
                            Log.d("status result", status);
                            String id1 = Jobject.getString("id");
                            Log.d("id result", id1);

//
                            id = id1;


                        } catch (Exception e) {
                        }


                        switch (status) {
                            case "success":
//                                Toast.makeText(getApplicationContext(), "Logged in", Toast.LENGTH_SHORT).show();

                                break;

                            case "fail":
                                Toast.makeText(getApplicationContext(), "Something Went Wrong!.", Toast.LENGTH_SHORT).show();
                                break;
                        }
                    }

                    ;
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                            pDialog.hide();

//                        Toast.makeText(MainEngineerActivity.inst, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<String, String>();
                params.put("engineer_id", SharedPrefManager.getInstance(getApplicationContext()).getKEY_Engineer_ID());
                params.put("attendance_id", SharedPrefManager.getInstance(getApplicationContext()).getKEY_Engineer_AttendanceID());

//                    Log.e("country code send",countrycode);
                return params;
            }

        };

        //Adding the string request to the queue
        RequestQueue requestQueue = Volley.newRequestQueue(MainEngineerActivity.inst);
        requestQueue.add(stringRequest);

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }


//    private void RegisterAlarmBroadcast() {
//        mReceiver = new BroadcastReceiver() {
//            // private static final String TAG = "Alarm Example Receiver";
//            @Override
//            public void onReceive(Context context, Intent intent) {
//                Toast.makeText(context, "Alarm time has been reached", Toast.LENGTH_LONG).show();
//            }
//        };
//
//        registerReceiver(mReceiver, new IntentFilter("sample"));
//        pendingIntent = PendingIntent.getBroadcast(this, 0, new Intent("sample"), 0);
//        alarmManager = (AlarmManager)(this.getSystemService(Context.ALARM_SERVICE));
//    }
//
//    private void UnregisterAlarmBroadcast() {
//        alarmManager.cancel(pendingIntent);
//        getBaseContext().unregisterReceiver(mReceiver);
//    }

//    public void startAlert(){
//
//        int i = Integer.parseInt();
//        Intent intent = new Intent(this, AttendanceBroadCastReceiver.class);
//        PendingIntent pendingIntent = PendingIntent.getBroadcast(
//                this.getApplicationContext(), 234324243, intent, 0);
//        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
//        alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()
//                + (i * 1000), pendingIntent);
//        Toast.makeText(this, "Alarm set in " + i + " seconds",Toast.LENGTH_LONG).show();
//    }


    public class myscheduleactivity extends Activity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            audio.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
        }

    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equalsIgnoreCase("sw")) {

                Boolean yourBool = intent.getBooleanExtra("switch", false);
                if (yourBool) {
                    mySwitch.setEnabled(yourBool);
                }


            }
        }
    };

    @Override
    public void onResume() {
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, new IntentFilter("sw"));
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
    }


    public void insertFCMToken() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.EngineerInsertFCMToken,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.e("Volleyresponse", response.toString());
                        try {
                            JSONArray jArray;
                            JSONObject Jobject = (JSONObject) new JSONTokener(response).nextValue();
                            Log.e("volleyJson", Jobject.toString());

//
                            status = Jobject.getString("status");
                            Log.d("status result", status);
                            String id1 = Jobject.getString("id");
                            Log.d("id result", id1);

//
                            id = id1;


                        } catch (Exception e) {
                        }


//                        switch (status) {
//                            case "success":
////                                Toast.makeText(getApplicationContext(), "Logged in", Toast.LENGTH_SHORT).show();
//
//                                break;
//
//                            case "fail":
////                                Toast.makeText(getApplicationContext(), "Something Went Wrong!.", Toast.LENGTH_SHORT).show();
//                                break;
//                        }
                    }

                    ;
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                            pDialog.hide();
//                        Toast.makeText(MainEngineerActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                //user type 1 for engineer 0 for customer
                Map<String, String> params = new HashMap<String, String>();
                params.put("id", SharedPrefManager.getInstance(getApplicationContext()).getKEY_Engineer_ID());
                params.put("fcm_token", SharedPrefManager.getInstance(getApplicationContext()).getKEY_Engineer_Token());
                params.put("usertype","2");
//                    Log.e("country code send",countrycode);
                return params;
            }

        };

        //Adding the string request to the queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }















    @Override
    public void onLocationChanged(Location location) {

//    Toast.makeText(getApplicationContext(),"Longitude:" + location.getLongitude(),Toast.LENGTH_LONG).show();
//        Toast.makeText(getApplicationContext(),"Latitude:" + location.getLatitude(),Toast.LENGTH_SHORT).show();
        latitude=location.getLatitude();
        longitude=location.getLongitude();
        SharedPrefManager.getInstance(getApplicationContext()).setKEY_Latitude(String.valueOf(location.getLatitude()));
        SharedPrefManager.getInstance(getApplicationContext()).setKEY_Longitudee(String.valueOf(location.getLongitude()));
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    public void CheckGpsStatus(){

        locationManager = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);

        GpsStatus = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

    }

    public void EnableRuntimePermission(){

        if (ActivityCompat.shouldShowRequestPermissionRationale(MainEngineerActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION))
        {

//            Toast.makeText(MainEngineerActivity.this,"ACCESS_FINE_LOCATION permission allows us to Access GPS in app", Toast.LENGTH_LONG).show();

        } else {

            ActivityCompat.requestPermissions(MainEngineerActivity.this,new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION}, RequestPermissionCode);

        }
    }

    @Override
    public void onRequestPermissionsResult(int RC, String per[], int[] PResult) {

        switch (RC) {

            case RequestPermissionCode:

                if (PResult.length > 0 && PResult[0] == PackageManager.PERMISSION_GRANTED) {

//                    Toast.makeText(MainEngineerActivity.this,"Permission Granted.", Toast.LENGTH_LONG).show();


                } else {

//                    Toast.makeText(MainEngineerActivity.this,"Permission Canceled.", Toast.LENGTH_LONG).show();

                }
                break;
        }
    }

    @SuppressLint("LongLogTag")
    public String getCompleteAddressString(double LATITUDE, double LONGITUDE) {
        String strAdd = "";
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
            if (addresses != null) {
                Address returnedAddress = addresses.get(0);
                StringBuilder strReturnedAddress = new StringBuilder("");

                for (int i = 0; i <= returnedAddress.getMaxAddressLineIndex(); i++) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
                }
                strAdd = strReturnedAddress.toString();
                Log.w("My Current loction address", strReturnedAddress.toString());
            } else {
                Log.w("My Current loction address", "No Address returned!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.w("My Current loction address", "Canont get Address!");
        }
        return strAdd;
    }
    public void setSwichStatus(){
        try {

            mySwitch.setEnabled(true);
        }catch (Exception e){

        }
    }
    public void startAlertAtParticularTime() {

        // alarm first vibrate at 14 hrs and 40 min and repeat itself at ONE_HOUR interval

        Intent intent = new Intent(this, AttendanceBroadCastReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(
                this.getApplicationContext(), 280192, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 9);
        calendar.set(Calendar.MINUTE, 00);

        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY, pendingIntent);

//        Toast.makeText(this, "Alarm will vibrate at time specified",
//                Toast.LENGTH_SHORT).show();

    }
    @Override
    protected void onStop() {
        super.onStop();
        if (alarmManager != null) {
            alarmManager.cancel(pendingIntent);
        }


    }
    private  void  attendance(){


        mySwitch = findViewById(R.id.nav_engineer_attendence_toggle);
//
//        item.setActionView(R.layout.togglemenuitem);
//
//
//        mySwitch = item.getActionView().findViewById(R.id.nav_attendance);
//
//


        if (!SharedPrefManager.getInstance(getApplicationContext()).isEngineerAttendedToday()) {

            mySwitch.setChecked(false);
        } else if (SharedPrefManager.getInstance(getApplicationContext()).isEngineerAttendedToday() && SharedPrefManager.getInstance(getApplicationContext()).getKEY_Engineer_AttendanceToday().equals("1")) {
            mySwitch.setChecked(true);
        }

        if (SharedPrefManager.getInstance(getApplicationContext()).isEngineerAttendedTodayCount() && SharedPrefManager.getInstance(getApplicationContext()).getKEY_Engineer_AttendanceTodayCount().equals("1")) {
            mySwitch.setEnabled(true);

        } else if (!SharedPrefManager.getInstance(getApplicationContext()).isEngineerAttendedTodayCount()) {
            mySwitch.setEnabled(true);
        } else if (SharedPrefManager.getInstance(getApplicationContext()).isEngineerAttendedTodayCount() && SharedPrefManager.getInstance(getApplicationContext()).getKEY_Engineer_AttendanceTodayCount().equals("2")) {
            mySwitch.setEnabled(false);
        }



        mySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                // Start or stop your Service


                if (toogledProgrammatically) {
                    toogledProgrammatically = false;
                } else {
                    if (isChecked) {

                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                                MainEngineerActivity.this);

                        alertDialogBuilder
                                .setMessage(
                                        "Are You Sure To Tick Attendance In?. ")
                                .setCancelable(true)
                                .setPositiveButton(
                                        "YES",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog,
                                                                int which) {

                                                setAttendance();
                                                SharedPrefManager.getInstance(getApplicationContext()).setKEY_Engineer_AttendanceToday("1");
                                                SharedPrefManager.getInstance(getApplicationContext()).setKEY_Engineer_AttendanceTodayCount("1");
                                            }
                                        })

                                .setNegativeButton(
                                        "NO",
                                        new DialogInterface.OnClickListener() {

                                            @Override
                                            public void onClick(DialogInterface dialog,
                                                                int which) {

                                                dialog.cancel();
                                                toogledProgrammatically = true;
                                                mySwitch.toggle();
                                            }
                                        });

                        AlertDialog alertDialog = alertDialogBuilder.create();
                        alertDialog.show();
                    } else {
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                                MainEngineerActivity.this);
                        alertDialogBuilder
                                .setMessage(
                                        "Are You Sure To Tick Attendance Out?. ")
                                .setCancelable(true)
                                .setPositiveButton(
                                        "YES",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog,
                                                                int which) {
                                                setAttendanceUpdate();
                                                SharedPrefManager.getInstance(getApplicationContext()).setKEY_Engineer_AttendanceTodayCount("2");
                                                SharedPrefManager.getInstance(getApplicationContext()).clearEngineerAttendedToday();
                                                mySwitch.setEnabled(false);

                                            }
                                        })

                                .setNegativeButton(
                                        "NO",
                                        new DialogInterface.OnClickListener() {

                                            @Override
                                            public void onClick(DialogInterface dialog,
                                                                int which) {

                                                dialog.cancel();
                                                toogledProgrammatically = true;
                                                mySwitch.toggle();
                                            }
                                        });

                        AlertDialog alertDialog = alertDialogBuilder.create();
                        alertDialog.show();
                    }
                }
            }


        });
    }
    public void showSnackbar(View view, String message, int duration)
    {
        Snackbar.make(view, message, duration).show();
    }


}