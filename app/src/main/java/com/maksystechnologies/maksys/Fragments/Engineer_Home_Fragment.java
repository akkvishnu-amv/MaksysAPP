package com.maksystechnologies.maksys.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

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

import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.HashMap;
import java.util.Map;


public class Engineer_Home_Fragment extends Fragment {
    FrameLayout myTickets,currentTicket,serviceRequest,profile,settings,scheduled;
    FragmentManager fragmentManager ;
    FragmentTransaction fragmentTransaction;
    TextView ticketCounts,serviceRequestCount,countsrev,countsche;
    String countservice="0",countschedule="0";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        final View rootView = inflater.inflate(R.layout.fragment_engineer_home_fragement, container, false);

        myTickets=rootView.findViewById(R.id.btn_engineer_main_ticket);
//        currentTicket=rootView.findViewById(R.id.btn_engineer_main_current_ticket);
        serviceRequest=rootView.findViewById(R.id.btn_engineer_main_service_request);
        profile=rootView.findViewById(R.id.btn_engineer_main_profile);
        settings=rootView.findViewById(R.id.btn_engineer_main_settings);
        scheduled=rootView.findViewById(R.id.btn_engineer_main_scheduled);
        countsrev=rootView.findViewById(R.id.tv_countservicerequest);
        countsche=rootView.findViewById(R.id.text_engineer_schedule_count);
        getService();

        scheduled.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new EngineerScheduledTickets();
                fragmentTransaction.setCustomAnimations(R.anim.enterfromleft, R.anim.exittoright);
                fragmentTransaction.replace(R.id.content_frame1, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        myTickets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment fragment = new Ticket_Engineer_Fragment();
                fragmentTransaction.setCustomAnimations(R.anim.enterfromleft, R.anim.exittoright);
                fragmentTransaction.replace(R.id.content_frame1, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }
        });
//        currentTicket.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Fragment fragment = new CurrentTicketEngineerFragment();
//                fragmentTransaction.setCustomAnimations(R.anim.enterfromleft, R.anim.exittoright);
//                fragmentTransaction.replace(R.id.content_frame, fragment);
//                fragmentTransaction.addToBackStack(null);
//                fragmentTransaction.commit();
//            }
//        });
        serviceRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new Service_Request_Fragement_Engineer();
                fragmentTransaction.setCustomAnimations(R.anim.enterfromleft, R.anim.exittoright);
                fragmentTransaction.replace(R.id.content_frame1, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }
        });
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new Profile_Engineer_Fragment();
                fragmentTransaction.setCustomAnimations(R.anim.enterfromleft, R.anim.exittoright);
                fragmentTransaction.replace(R.id.content_frame1, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(getActivity(),SettingsCustomerActivity.class);
                startActivity(intent);
            }
        });
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


    private void getService(){


        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.getServiceCount,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.e("Volleyresponse",response.toString());
                        try {

                            JSONObject Jobject = (JSONObject) new JSONTokener(response).nextValue();
                            Log.e("volleyJson", Jobject.toString());
                            countservice = Jobject.getString("id_status_count");
                            countschedule= Jobject.getString("id_status");

                            if(countservice.equals("0")){
                                countsrev.setVisibility(View.INVISIBLE);
                            }else {
                                countsrev.setVisibility(View.VISIBLE);

                            }
                            if(countschedule.equals("0")){
                                countsche.setVisibility(View.INVISIBLE);

                            }else {
                                countsche.setVisibility(View.VISIBLE);

                            }
                            countsrev.setText(countservice);

                            countsche.setText(countschedule);

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
                params.put("engineer_id", SharedPrefManager.getInstance(getActivity()).getKEY_Engineer_ID());

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


}
