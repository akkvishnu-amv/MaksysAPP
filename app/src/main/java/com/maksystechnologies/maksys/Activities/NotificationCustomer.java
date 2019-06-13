package com.maksystechnologies.maksys.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.maksystechnologies.maksys.Adapters.NotificationCustomerAdapter;
import com.maksystechnologies.maksys.Models.Notification;
import com.maksystechnologies.maksys.R;
import com.maksystechnologies.maksys.Utilities.SharedPrefManager;
import com.maksystechnologies.maksys.Utilities.URLs;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NotificationCustomer extends AppCompatActivity {

    ProgressBar pDialog;
    private RecyclerView recyclerView_engineer_notification;
    private TextView noNotification;
    private NotificationCustomerAdapter notificationEngineerAdapter;
    private List<Notification> notificationList;
    private RecyclerView.LayoutManager manager;
    private LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_engineer);
        setupActionBar();
        recyclerView_engineer_notification = findViewById(R.id.recycle_engineer_notification);
        notificationList = new ArrayList<Notification>();
        notificationEngineerAdapter = new NotificationCustomerAdapter(this, getApplicationContext(), notificationList);
        manager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        noNotification = findViewById(R.id.tv_engineer_notifications);
        noNotification.setVisibility(View.GONE);
        recyclerView_engineer_notification.setLayoutManager(linearLayoutManager);
        pDialog = findViewById(R.id.progress_engineer_notifications);
        pDialog.setVisibility(View.GONE);
        getNotification();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            if (SharedPrefManager.getInstance(getApplicationContext()).isCustomerLoggedIn()) {
                startActivity(new Intent(getBaseContext(), MainCustomerActivity.class));
            } else {
                startActivity(new Intent(getBaseContext(), MainEngineerActivity.class));
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Notifications");
        if (actionBar != null) {
            // Show the Up button in the action bar.
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void getNotification() {

        pDialog.setVisibility(View.VISIBLE);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.EngineerNotification,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.e("Volleyresponse", response.toString());
                        try {

                            JSONObject Jobject = (JSONObject) new JSONTokener(response).nextValue();
                            Log.e("volleyJson", Jobject.toString());
                            JSONArray feedArray = Jobject.getJSONArray("result");

                            Log.e("feedlength", String.valueOf(feedArray.length()));

                            for (int i = 0; i < feedArray.length(); i++) {
                                int jarraylength = feedArray.length() + 1;

                                JSONObject feedObj = (JSONObject) feedArray.get(i);
                                Notification tickets = new Notification();

                                String id = feedObj.getString("notification_id");
                                String title = feedObj.getString("notification_title");
                                String message = feedObj.getString("notification_message");
                                String user_id = feedObj.getString("user_id");
                                String user_type = feedObj.getString("user_type");

                                String ticket_id = feedObj.getString("ticket_id");
                                String time = feedObj.getString("sent_on");


                                tickets.setTicket_id(ticket_id);
                                tickets.setId(id);
                                tickets.setTitle(title);
                                tickets.setMessage(message);
                                tickets.setTime(time);


                                notificationList.add(tickets);

                            }


                        } catch (Exception e) {
                        }


                        notificationEngineerAdapter.notifyDataSetChanged();
                        recyclerView_engineer_notification.setAdapter(notificationEngineerAdapter);
                        if (notificationEngineerAdapter.getItemCount() == 0) {
                            noNotification.setVisibility(View.VISIBLE);
                        } else {
                            noNotification.setVisibility(View.GONE);
                        }
                        pDialog.setVisibility(View.GONE);
                    }

                    ;
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                            pDialog.hide();
                        pDialog.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                String checksum = "", id = "";
                Map<String, String> params = new HashMap<String, String>();
                if (SharedPrefManager.getInstance(getApplicationContext()).isCustomerLoggedIn()) {
                    checksum = "1";
                    id = SharedPrefManager.getInstance(getApplicationContext()).getKEY_Customer_BranchID();
                } else {
                    checksum = "2";
                    id = SharedPrefManager.getInstance(getApplicationContext()).getKEY_Engineer_ID();


                }
                params.put("checksum", checksum);

                params.put("engineer_id", id);

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

    public static String getDateFromatedString(String inputDate) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS");

        Date date = null;
        try {
            date = simpleDateFormat.parse(inputDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (date == null) {
            return "";
        }

        SimpleDateFormat convetDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");

        return convetDateFormat.format(date);
    }
}