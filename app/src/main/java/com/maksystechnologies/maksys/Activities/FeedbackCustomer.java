package com.maksystechnologies.maksys.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.maksystechnologies.maksys.R;
import com.maksystechnologies.maksys.Utilities.SharedPrefManager;
import com.maksystechnologies.maksys.Utilities.URLs;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.HashMap;
import java.util.Map;

public class FeedbackCustomer extends AppCompatActivity {
String ticketid;
    Button cancel,rating;
    EditText comment,newpin;
    RatingBar rate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback_customer);
        setupActionBar();
        if (getIntent().getExtras() != null) {
            ticketid = getIntent().getExtras().getString("ticket_id");
        }

        cancel=findViewById(R.id.btn_feedback_nothanks);
        rating=findViewById(R.id.btn_feedback_rate);
        comment=findViewById(R.id.et_feedback_comment);
        rate=findViewById(R.id.ratingBar_feedback);


        rating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String com;
                if(comment.getText().toString().equals(""))
                {
                    com="";

                }else {com=comment.getText().toString();}
                String rating="";
                if(rate.getNumStars()!=0){
                    rating="null";


                }else {
                    rating=String.valueOf(rate.getRating());
                }


                SendComment(SharedPrefManager.getInstance(getApplicationContext()).getKEY_Customer_ID(),ticketid,com,rating);

            }
        });


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String com;
                if(comment.getText().toString().equals(""))
                {
                    com="";

                }else {com=comment.getText().toString();}
                String rating="";
                if(rate.getNumStars()!=0){
                    rating="null";


                }else {
                    rating=String.valueOf(rate.getRating());
                }


                SendComment(SharedPrefManager.getInstance(getApplicationContext()).getKEY_Customer_ID(),ticketid,com,rating);


            }
        });
    }
    private void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(" ");
        if (actionBar != null) {
            // Show the Up button in the action bar.
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            startActivity(new Intent(getApplicationContext(),NotificationCustomer.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public void SendComment(final String custid,final String ticketid,final  String comment,final String rating) {


        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.SendComment,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Volleyresponse", response.toString());
                        try {
                            JSONArray jArray;
                            JSONObject Jobject = (JSONObject) new JSONTokener(response).nextValue();
                            Log.e("volleyJson", Jobject.toString());

//                                jArray = Jobject.getJSONArray("result");
//                                int jarraylength=jArray.length()+1;
////                                String c1[] = new String[jarraylength];
////                                String c2[] = new String[jarraylength];
////                                String c3[] = new String[jarraylength];
////
//                                for (int i = 0; i < jarraylength; i++) {
//                                    JSONObject json_data = jArray.getJSONObject(i);


                                Toast.makeText(getApplicationContext(),"Your Ticket Has Been Closed",Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(),NotificationCustomer.class));
//                                }
//                                        c1[i]=cid;
//                                        c3[i]=sname;

//                                        statecodename=c3;
//                                        statecodeid=c1;
//

                        } catch (Exception e) {
                        }
//

                    }

                    ;
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                            pDialog.hide();
                        Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("cust_id", custid);
                params.put("ticket_id", ticketid);
                params.put("comment", comment);
                params.put("rating", rating);
                params.put("mobile", SharedPrefManager.getInstance(getApplicationContext()).getKEY_Customer_Mobile());
                    Log.e("country code send",ticketid);
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
