package com.maksystechnologies.maksys.Activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
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
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.iid.FirebaseInstanceId;
import com.maksystechnologies.maksys.Fragments.ChangePin_Customer_Fragment;
import com.maksystechnologies.maksys.Fragments.Comment_Customer_Fragment;
import com.maksystechnologies.maksys.Fragments.Create_Ticket_Fragment;
import com.maksystechnologies.maksys.Fragments.CustomerHomeFragment;
import com.maksystechnologies.maksys.Fragments.Profile_Fragment_Customer;
import com.maksystechnologies.maksys.Fragments.Sale_Request_Fragment;
import com.maksystechnologies.maksys.Fragments.Ticket_Customer_Fragment;
import com.maksystechnologies.maksys.R;
import com.maksystechnologies.maksys.Utilities.SharedPrefManager;
import com.maksystechnologies.maksys.Utilities.URLs;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.HashMap;
import java.util.Map;

public class MainCustomerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
LinearLayout nav_home;
String status,id;
Button exit;
    SplashScreen mainActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_customer_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        Drawable drawable = ContextCompat.getDrawable(getApplicationContext(),R.drawable.round_notifications_white);
//        toolbar.setOverflowIcon(drawable);
        setSupportActionBar(toolbar);
mainActivity=new SplashScreen();
//        if(!mainActivity.isNetworkConnecteted()){
//            Toast.makeText(getApplicationContext(),"Check Your Internet",Toast.LENGTH_LONG).show();
//        }


       String token= FirebaseInstanceId.getInstance().getToken();
//Toast.makeText(getApplicationContext(),token,Toast.LENGTH_LONG).show();



                SharedPrefManager.getInstance(getApplicationContext()).setKEY_Customer_Token(token);
                insertFCMToken();




        if (!SharedPrefManager.getInstance(getApplicationContext()).isCustomerSetPin()) {
            Intent intent = new Intent(getApplicationContext(), LockScreenCustomerCreate.class);
            startActivity(intent);
        }





        final DrawerLayout drawer =  findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView =  findViewById(R.id.nav_view);
        View headerview = navigationView.getHeaderView(0);

        navigationView.setNavigationItemSelectedListener(this);
        nav_home=headerview.findViewById(R.id.nav_home);
        exit=findViewById(R.id.btn_exit);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        displaySelectedScreen(R.id.nav_home);
        nav_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displaySelectedScreen(R.id.nav_home);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_customer, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_notifications) {
            Intent intent=new Intent(getBaseContext(),NotificationCustomer.class);
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
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
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
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }
    private void displaySelectedScreen(int itemId) {

        //creating fragment object
        Fragment fragment = null;

        //initializing the fragment object which is selected
        switch (itemId) {
            case R.id.nav_Ticket:
                fragment = new Ticket_Customer_Fragment();
                break;
            case R.id.nav_Ticket_create:
                fragment = new Create_Ticket_Fragment();
                break;
            case R.id.nav_request:
                fragment = new Sale_Request_Fragment();
                break;
            case R.id.nav_profile:
                fragment = new Profile_Fragment_Customer();
                break;
//            case R.id.nav_about:
//                fragment = new About_App_Fragment();
//                break;
            case R.id.nav_settings:
              Intent intent1=new Intent(getApplicationContext(),SettingsCustomerActivity.class);
              startActivity(intent1);
                break;
            case R.id.nav_home:
                fragment = new CustomerHomeFragment();
                break;
            case R.id.nav_change_pin:
                fragment = new ChangePin_Customer_Fragment();
                break;
            case R.id.nav_comments:
                fragment = new Comment_Customer_Fragment();
                break;
            case R.id.nav_logout:
                final AlertDialog.Builder builder=new AlertDialog.Builder(MainCustomerActivity.this);// use you activity name
                builder.setMessage("Are you sure you want to completely Logou? ")
                        .setPositiveButton("Yes",
                                new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        finish();
                                        SharedPrefManager.getInstance(getApplicationContext()).logoutCustomer();
                                        Intent intent=new Intent(getApplicationContext(),LoginCustomer.class);
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
            ft.replace(R.id.content_frame, fragment);
            ft.commit();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
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
//                        Toast.makeText(MainCustomerActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                //user type 1 for engineer 0 for customer
                Map<String, String> params = new HashMap<String, String>();
                params.put("id", SharedPrefManager.getInstance(getApplicationContext()).getKEY_Customer_BranchID());
                params.put("fcm_token", SharedPrefManager.getInstance(getApplicationContext()).getKEY_Customet_Token());
                params.put("usertype","1");
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

}
