package com.maksystechnologies.maksys.Activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.maksystechnologies.maksys.Models.ServiceRequest;
import com.maksystechnologies.maksys.R;
import com.maksystechnologies.maksys.Utilities.SharedPrefManager;
import com.maksystechnologies.maksys.Utilities.URLs;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.HashMap;
import java.util.Map;

public class ServiceRequestDetails extends AppCompatActivity {
    EditText customername,address,issue,ticketID;

    Button startService;
    String name,addrs,issu,num,ids,ticket;
    CardView cardView,cardStartengineerRequest;
    TextView noresult,contactnumber;
    ProgressBar pDialog;
    String statuscheck="",ID,tktassinsts,ticketcode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_request_details);
        setupActionBar();
        customername=findViewById(R.id.et_engineer_service_request_branchname);
        address=findViewById(R.id.et_engineer_service_request_branchaddress);
        issue=findViewById(R.id.et_engineer_service_request_issuedetails);
        contactnumber=findViewById(R.id.et_engineer_service_request_contactnumber);
        startService=findViewById(R.id.btn_engineer_service_request_start);
        cardView=findViewById(R.id.card_engineer_pending_requests);
        noresult=findViewById(R.id.tv_engineer_service_request_details);
        cardStartengineerRequest=findViewById(R.id.card_engineer_service_start);
        pDialog=findViewById(R.id.progressBarServiceRequest);
        ticketID=findViewById(R.id.et_engineer_service_request_ticketid);
            pDialog.setVisibility(View.GONE);
        noresult.setVisibility(View.INVISIBLE);
        cardView.setVisibility(View.VISIBLE);

        final String addresslink="https://www.google.com/maps/search/?api=1&query="+SharedPrefManager.getInstance(getApplicationContext()).getKEY_Latitude()+","+SharedPrefManager.getInstance(getApplicationContext()).getKEY_Longitude();

//        getServiceRequest();


        contactnumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isPermissionGranted()){

                }
            }
        });


        if (getIntent().getExtras() != null) {
            num = getIntent().getExtras().getString("number");
            name = getIntent().getExtras().getString("name");
            addrs = getIntent().getExtras().getString("address");
            issu = getIntent().getExtras().getString("issue");
            ids = getIntent().getExtras().getString("id");
            ticket = getIntent().getExtras().getString("ticket");
            ticketcode = getIntent().getExtras().getString("ticketcode");
            Log.e("ticket code service",ticketcode);
            customername.setText(name);
            address.setText(addrs);
            issue.setText(issu);
            contactnumber.setText(num);
            ticketID.setText(ticketcode);
            SharedPrefManager.getInstance(getApplicationContext()).setPendingRequest(ids,name,issu,addrs,num,ticket);
        }else if(SharedPrefManager.getInstance(getApplicationContext()).isKEYPendingRequest()) {
//            customername.setText(SharedPrefManager.getInstance(getApplicationContext()).getKEY_Pending_RequestName());
//            address.setText(SharedPrefManager.getInstance(getApplicationContext()).getKEY_Pending_RequestAddress());
//            issue.setText(SharedPrefManager.getInstance(getApplicationContext()).getKEY_Pending_RequestIssue());
//            contactnumber.setText(SharedPrefManager.getInstance(getApplicationContext()).getKEY_Pending_RequestContact());
            customername.setText(name);
            address.setText(addrs);
            issue.setText(issu);
            contactnumber.setText(num);
            ticketID.setText(ticketcode);
        }







        startService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(SharedPrefManager.getInstance(getApplicationContext()).isEngineerAttendedToday()){
//                    setTicketStart(ticket,ids,addresslink);
                    SharedPrefManager.getInstance(getApplicationContext()).setKEY_Pending_Requeststatus(getResources().getString(R.string.sharedPendingStatusStart));

                    Intent intent=new Intent(getApplicationContext(),CurrentTickets.class);
                    intent.putExtra("name",name);
                    intent.putExtra("address",addrs);
                    intent.putExtra("issue",issu);
                    intent.putExtra("number",num);
                    intent.putExtra("id",ids);
                    intent.putExtra("ticket",ticket);
                    intent.putExtra("ticketcode",ticketcode);
//                    Toast.makeText(getApplicationContext(),ticketcode,Toast.LENGTH_SHORT).show();
                    startActivity(intent);

                }else {
                    Toast.makeText(getApplicationContext(),"Check Your Attendance.!",Toast.LENGTH_SHORT).show();
                }

               }
        });


    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            startActivity(new Intent(getBaseContext(), MainEngineerActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Service Request Details");
        if (actionBar != null) {
            // Show the Up button in the action bar.
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void  setTicketStart(final String ticketid, final String assignid,final String address){

//        Log.e("ticketid",assignid);
//        Log.e("ticketcode",ticketid);
//        Log.e("address",address);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.EngineerStartServiceRequest,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.e("Volleyresponse",response.toString());
                        try {

                            JSONObject Jobject = (JSONObject) new JSONTokener(response).nextValue();
                            Log.e("volleyJson", Jobject.toString());


                        } catch (Exception e) { }


                    };
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                            pDialog.hide();

                    }
                }) {
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<String, String>();
                params.put("ticket_id",ticketid );
                params.put("assign_id",assignid );
                params.put("address",address );
Log.e("address",address);
                Log.e("assign_id",assignid);
                Log.e("ticket_id",ticketid);
                return params;
            }

        };

        //Adding the string request to the queue
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


    }

    private void getServiceRequest(){

        pDialog.setVisibility(View.VISIBLE);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.EngineerServiceRequestGet,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.e("Volleyresponse",response.toString());
                        try {

                            JSONObject Jobject = (JSONObject) new JSONTokener(response).nextValue();
                            Log.e("volleyJson", Jobject.toString());
                            JSONArray feedArray = Jobject.getJSONArray("result");

                            Log.e("feedlength",String.valueOf(feedArray.length()));

                            for (int i = 0; i < feedArray.length(); i++) {
                                int jarraylength=feedArray.length()+1;

                                JSONObject feedObj = (JSONObject) feedArray.get(i);
                                ServiceRequest tickets=new ServiceRequest();


                                String id=feedObj.getString("ticket_assign_id");
                                String status=feedObj.getString("ticket_assign_current_status");
                                String ticketid=feedObj.getString("ticket_id");

                                String accepted_location=feedObj.getString("start_location");
                                String started_time=feedObj.getString("start_time");
                                String addrss=feedObj.getString("cust_branch_address");
                                String completed_date=feedObj.getString("end_time");
                                String nm=feedObj.getString("cust_branch_name");
                                String mobile=feedObj.getString("ticket_mobile");
                                String device=feedObj.getString("cust_asset_id");
                                String upload=feedObj.getString("document_upload");
                                String proceed_status=feedObj.getString("schedule_status");
                                String description=feedObj.getString("ticket_complaint");

                                addrs=addrss;
                                name=nm;
                                issu="Device : "+device+" //"+ " Issue :"+description;
                                num=mobile;ids=ticketid;
                                ticket=ticketid;
                                statuscheck=proceed_status;
                                ID=id;
                                tktassinsts=status;
//                                Toast.makeText(getApplicationContext(),started_time,Toast.LENGTH_LONG).show();

                                if(!statuscheck.equals("1")){
                                    cardView.setVisibility(View.GONE);
                                    noresult.setVisibility(View.VISIBLE);
                                }
                                if(!tktassinsts.equals("1")){
                                    cardView.setVisibility(View.GONE);
                                    noresult.setVisibility(View.VISIBLE);
                                }

                                if(statuscheck.equals("1")&&!started_time.equals("null")){
                                    cardStartengineerRequest.setVisibility(View.INVISIBLE);

                                }

                            }


                        } catch (Exception e) { }


                        customername.setText(name);
                        address.setText(addrs);
                        issue.setText(issu);
                        contactnumber.setText(num);
                        ticketID.setText(ticket);
                        pDialog.setVisibility(View.GONE);
                    };
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                            pDialog.hide();
                        pDialog.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(),error.toString(), Toast.LENGTH_LONG).show();
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
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


    }

    public  boolean isPermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.CALL_PHONE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v("TAG","Permission is granted");
                return true;
            } else {

                Log.v("TAG","Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 1);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v("TAG","Permission is granted");
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {

            case 1: {

                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    Toast.makeText(getApplicationContext(), "Permission granted", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Intent.ACTION_CALL,Uri.parse("tel:"+contactnumber.getText().toString())));

                } else {
                    Toast.makeText(getApplicationContext(), "Permission denied", Toast.LENGTH_SHORT).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
}
