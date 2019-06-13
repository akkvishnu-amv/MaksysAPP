package com.maksystechnologies.maksys.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.maksystechnologies.maksys.Adapters.TicketScheduledEngineersAdapter;
import com.maksystechnologies.maksys.Models.CurrentTicket;
import com.maksystechnologies.maksys.R;
import com.maksystechnologies.maksys.Utilities.SharedPrefManager;
import com.maksystechnologies.maksys.Utilities.URLs;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class EngineerScheduledTickets extends Fragment {
    ProgressBar pDialog;
    TextView empty;
    private List<String> arrayList;
    private RecyclerView recyclerView_customer_tickets;
    private TicketScheduledEngineersAdapter ticketsCustomerAdapter;
    private List<CurrentTicket> ticketsList;
    private RecyclerView.LayoutManager manager;
    private LinearLayoutManager linearLayoutManager;
    private  String custcode;
    public boolean isStartedService=false;
    public int position=-1;
    String code="";
     FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        View rootView=inflater.inflate(R.layout.fragment_engineer_scheduled_tickets, container, false);
        recyclerView_customer_tickets=rootView.findViewById(R.id.recycle_engineer_tickets_scheduled);
        ticketsList = new ArrayList<CurrentTicket>();
        arrayList=new ArrayList<String>();
        ticketsCustomerAdapter=new TicketScheduledEngineersAdapter(getActivity(),getContext(),ticketsList,arrayList);
        manager=new LinearLayoutManager(getActivity());
        linearLayoutManager=new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView_customer_tickets.setLayoutManager(linearLayoutManager);
        pDialog=rootView.findViewById(R.id.progress_engineer_ticket_scheduled);
        empty=rootView.findViewById(R.id.tv_engineer_scheduled_empty);
        empty.setVisibility(View.GONE);
        pDialog.setVisibility(View.GONE);
        getTickets();
        return rootView;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        fragmentManager=getActivity().getSupportFragmentManager();
        fragmentTransaction=fragmentManager.beginTransaction();
        getActivity().setTitle("Scheduled Tickets");
    }
    private void getTickets(){

        pDialog.setVisibility(View.VISIBLE);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.getEngineerScheduledTcikets,
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
                                CurrentTicket tickets=new CurrentTicket();

                                String ticket_code=feedObj.getString("ticket_code");
                                String ticket_id=feedObj.getString("ticket_id");
                                String schedule_date_time=feedObj.getString("schedule_date_time");
                                String branch=feedObj.getString("cust_branch_name");
                                String cust_asset_id=feedObj.getString("cust_asset_id");
                                String ticket_complaint=feedObj.getString("ticket_complaint");
                                String address=feedObj.getString("cust_branch_address");
                                String mobile=feedObj.getString("ticket_mobile");
                                String schedul_id=feedObj.getString("schedule_id");
                                String sts=feedObj.getString("schedule_status");
                                String assign=feedObj.getString("ticket_assign_id");
                                String custname=feedObj.getString("cust_name");
                                String devicedata=feedObj.getString("device_data");

                                Log.e("ticket_code id",ticket_code);

                                if(sts.equals("4")){
                                    isStartedService=true;
                                    position=i;
                                }

//                                getCustAssetCode(cust_asset_id);
                                Log.e("device",code);
//                            String device=getCustAssetCode(cust_asset_id);

                                arrayList.add(sts);
//                                Log.e("rest",device);

                                tickets.setDevicename(devicedata);
                                tickets.setCustssetid(cust_asset_id);
                                tickets.setBranchAddress(address);
                                tickets.setAddress(branch);
                                tickets.setScheduledtime(schedule_date_time);
                                tickets.setTicketid(ticket_id);
                                tickets.setTicketcode(ticket_code);
                                tickets.setDevice(ticket_complaint);
                                tickets.setTicketMobile(mobile);
                                tickets.setTicketAssignID(assign);
                                tickets.setStatus(sts);
                                tickets.setSchedule_id(schedul_id);
                                tickets.setCustname(custname);

                                ticketsList.add(tickets);

                            }


                        } catch (Exception e) { }

                        if(ticketsCustomerAdapter.getItemCount()==0){
                            empty.setVisibility(View.VISIBLE);
                        }

                        ticketsCustomerAdapter.notifyDataSetChanged();
                        recyclerView_customer_tickets.setAdapter(ticketsCustomerAdapter);

                        pDialog.setVisibility(View.GONE);
                    };
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                            pDialog.hide();
                        pDialog.setVisibility(View.GONE);
                        Toast.makeText(getActivity(),error.toString(), Toast.LENGTH_LONG).show();
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
    public void getCustAssetCode(final String id) {
        final String[] reslt = {""};
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.getCustAssetCode,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.e("Volleyresponse", response.toString());
                        try {
                            JSONArray jArray;
                            JSONObject Jobject = (JSONObject) new JSONTokener(response).nextValue();
                            Log.e("volleyJson", Jobject.toString());

//
                            code = Jobject.getString("status");


                            Log.e("result", code);
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
                params.put("id",id);

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
    public void showSnackbar(View view, String message, int duration)
    {
        Snackbar.make(view, message, duration).show();
    }
    public boolean isStartedService(){
        return isStartedService;
    }

    public int getPosition() {
        return position;
    }

}
