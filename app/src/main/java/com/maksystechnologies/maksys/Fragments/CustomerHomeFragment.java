package com.maksystechnologies.maksys.Fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.maksystechnologies.maksys.Activities.SettingsCustomerActivity;
import com.maksystechnologies.maksys.R;
import com.maksystechnologies.maksys.Utilities.SharedPrefManager;
import com.maksystechnologies.maksys.Utilities.URLs;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.HashMap;
import java.util.Map;


public class CustomerHomeFragment extends Fragment implements View.OnClickListener {
    FragmentManager fragmentManager ;
    FragmentTransaction fragmentTransaction;
    FrameLayout tickets,sale_request,profile,settings,create_ticket,create_comment;
    String strTicket_count,strSalesCount;
    TextView ticketCounts,salesRequestCount;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        final View rootView = inflater.inflate(R.layout.fragment_customer_home, container, false);


        tickets=rootView.findViewById(R.id.btn_customer_main_ticket);
        sale_request=rootView.findViewById(R.id.btn_customer_main_sale_request);
        profile=rootView.findViewById(R.id.btn_customer_main_profile);
        settings=rootView.findViewById(R.id.btn_customer_main_settings);
        create_ticket=rootView.findViewById(R.id.btn_customer_main_create_ticket);
        ticketCounts=rootView.findViewById(R.id.text_customer_ticket_count);
        salesRequestCount=rootView.findViewById(R.id.text_customer_sales_count);
        create_comment=rootView.findViewById(R.id.btn_customer_main_comment);

        settings.setOnClickListener(this);
        profile.setOnClickListener(this);
        sale_request.setOnClickListener(this);
        tickets.setOnClickListener(this);
        create_ticket.setOnClickListener(this);
        create_comment.setOnClickListener(this);


        getTicketSaleCount();

        return rootView;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
       fragmentManager = getActivity().getSupportFragmentManager();
       fragmentTransaction =  fragmentManager.beginTransaction();
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Home");
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.btn_customer_main_ticket:
                Fragment fragment = new Ticket_Customer_Fragment();
                fragmentTransaction.setCustomAnimations(R.anim.enterfromleft, R.anim.exittoright);

                fragmentTransaction.replace(R.id.content_frame, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

                break;
            case R.id.btn_customer_main_sale_request:
                Fragment fragment1 = new Sale_Request_Fragment();
                fragmentTransaction.setCustomAnimations(R.anim.enterfromleft, R.anim.exittoright);

                fragmentTransaction.replace(R.id.content_frame, fragment1);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

                break;

            case R.id.btn_customer_main_profile:
                Fragment fragment2 = new Profile_Fragment_Customer();
                fragmentTransaction.setCustomAnimations(R.anim.enterfromleft, R.anim.exittoright);

                fragmentTransaction.replace(R.id.content_frame, fragment2);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                break;
            case R.id.btn_customer_main_create_ticket:
                Fragment fragment3 = new Create_Ticket_Fragment();
                fragmentTransaction.setCustomAnimations(R.anim.enterfromleft, R.anim.exittoright);

                fragmentTransaction.replace(R.id.content_frame, fragment3);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

                break;
            case R.id.btn_customer_main_comment:
//              showComment(v);

                Fragment fragment7 = new Comment_Customer_Fragment();
                fragmentTransaction.setCustomAnimations(R.anim.enterfromleft, R.anim.exittoright);

                fragmentTransaction.replace(R.id.content_frame, fragment7);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();



                break;
            case R.id.btn_customer_main_settings:
                Intent intent =new Intent(getActivity(),SettingsCustomerActivity.class);
                startActivity(intent);
                break;
        }

    }
    private void getTicketSaleCount(){


        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.CustomerTicketSalesCount,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.e("Volleyresponse",response.toString());
                        try {

                            JSONObject Jobject = (JSONObject) new JSONTokener(response).nextValue();
                            Log.e("volleyJson", Jobject.toString());
                            strTicket_count = Jobject.getString("TicketCount");
                            strSalesCount= Jobject.getString("SalesCount");

                                ticketCounts.setText(strTicket_count +" Tickets");
                            salesRequestCount.setText(strSalesCount +" Requests");

//                            for (int i = 0; i < feedArray.length(); i++) {
//                                int jarraylength=feedArray.length()+1;
//                                String listDevice[] = new String[jarraylength];
//                                String list="",listid="";
//                                JSONObject feedObj = (JSONObject) feedArray.get(i);
//
//                                list=feedObj.getString("serial_no");
//                                listid=feedObj.getString("asset_id");
//
//
//                                autocompletedatasDevice.add(list);
//                                autocompletedatDeviceId.add(listid);
//                            }


                        } catch (Exception e) { }


                    };
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                            pDialog.hide();

//                        Toast.makeText(getActivity(),error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<String, String>();
                params.put("branch_id", SharedPrefManager.getInstance(getActivity()).getKEY_Customer_BranchID());

//                    Log.e("country code send",countrycode);
                return params;
            }

        };

        //Adding the string request to the queue
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


    }

    private void showComment(final View view){

//        setResendOTP();// otp send to registered customer number fetch from shared preference
        Button cancel,changepin;
        final EditText comment,newpin;
        ViewGroup viewGroup = view.findViewById(android.R.id.content);

        //then we will inflate the custom alert dialog xml that we created
        final View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.commnetlayout, viewGroup, false);

        cancel=dialogView.findViewById(R.id.btn_comment_cancel);
        changepin=dialogView.findViewById(R.id.btn_comment);
        comment=dialogView.findViewById(R.id.et_comment);

        //Now we need an AlertDialog.Builder object
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        //setting the view of the builder to our custom view that we already inflated
        builder.setView(dialogView);
        final AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);

        changepin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!comment.getText().toString().equals("")){
//                    Toast.makeText(getContext(),"Thank you For your Comment",Toast.LENGTH_SHORT);
                    showSnackbar(view,"Thank you For your Comment",3000);

                    sendComment(comment.getText().toString(),alertDialog);
//                    alertDialog.cancel();

                }else {
                    comment.setError("Please enter Comment");
                }
            }
        });


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());// use you activity name
                builder.setMessage("Are you sure you want to cancel? ")
                        .setPositiveButton("Yes",
                                new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        alertDialog.cancel();


                                    }
                                })

                        .setNegativeButton("No",new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {


                            }
                        });
                AlertDialog dialog=builder.create();
                dialog.show();

            }
        });
        //finally creating the alert dialog and displaying it

        alertDialog.show();
    }
    public void sendComment(final String email, final AlertDialog dialog) {


        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.CreateComment,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Volleyresponse", response.toString());
                        try {
                            JSONArray jArray;
                            JSONObject Jobject = (JSONObject) new JSONTokener(response).nextValue();
                            Log.e("volleyJson", Jobject.toString());



                            String status=Jobject.getString("status");
//                                jArray = Jobject.getJSONArray("result");
//                                int jarraylength=jArray.length()+1;
////                                String c1[] = new String[jarraylength];
////                                String c2[] = new String[jarraylength];
////                                String c3[] = new String[jarraylength];
////
//                                for (int i = 0; i < jarraylength; i++) {
//                                    JSONObject json_data = jArray.getJSONObject(i);


if(status.equals("success")){
    Toast.makeText(getActivity(),"Thank you For your Comment",Toast.LENGTH_SHORT);

}

//                                }
//                                        c1[i]=cid;
//                                        c3[i]=sname;

//                                        statecodename=c3;
//                                        statecodeid=c1;

                            dialog.cancel();
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
//                        Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("comment", email);

                params.put("id", SharedPrefManager.getInstance(getActivity()).getKEY_Customer_BranchID());
//                    Log.e("country code send",countrycode);
                return params;
            }

        };

        //Adding the string request to the queue
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

    public void showSnackbar(View view, String message, int duration)
    {
        Snackbar.make(view, message, duration).show();
    }

}
