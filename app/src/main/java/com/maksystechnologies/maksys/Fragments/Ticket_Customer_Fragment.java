package com.maksystechnologies.maksys.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.maksystechnologies.maksys.Adapters.TicketsCustomerAdapter;
import com.maksystechnologies.maksys.Models.Tickets;
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


public class Ticket_Customer_Fragment extends Fragment {
    ProgressBar pDialog;
    private RecyclerView recyclerView_customer_tickets;
    private TicketsCustomerAdapter ticketsCustomerAdapter;
    private List<Tickets> ticketsList;
    String code="";
    private RecyclerView.LayoutManager manager;
    private LinearLayoutManager linearLayoutManager;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        final View rootView=inflater.inflate(R.layout.fragment_ticket__customer, container, false);


        recyclerView_customer_tickets=rootView.findViewById(R.id.recycle_customer_tickets);
        ticketsList = new ArrayList<Tickets>();
        ticketsCustomerAdapter=new TicketsCustomerAdapter(getActivity(),getContext(),ticketsList);
        manager=new LinearLayoutManager(getActivity());
        linearLayoutManager=new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView_customer_tickets.setLayoutManager(linearLayoutManager);
        pDialog=rootView.findViewById(R.id.progress_customer_ticket);
        pDialog.setVisibility(View.GONE);


        getTickets();
        return rootView;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Tickets");
    }

    private void getTickets(){

pDialog.setVisibility(View.VISIBLE);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.CustomerTickets,
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
                                Tickets tickets=new Tickets();

                                 String id=feedObj.getString("ticket_id");
                                String status=feedObj.getString("ticket_current_status");
                                String ticketid=feedObj.getString("ticket_code");
                                String device=feedObj.getString("cust_asset_id");
                                String description=feedObj.getString("ticket_complaint");
                                String customer_status=feedObj.getString("ticket_issue");
                                String upload=feedObj.getString("complaint_image");
                                String createdAt=feedObj.getString("added_on");
                              getCustAssetCode(device);




                                tickets.setDevicename(code);
                                tickets.setCreated(createdAt);
                                tickets.setDescription(description);
                                tickets.setDevice(device);
                                tickets.setGivenStatus(customer_status);
                                tickets.setId(id);
                                tickets.setStatus(status);
                                tickets.setTicketId(ticketid);
                                tickets.setUpload(upload);




                                ticketsList.add(tickets);

                            }


                        } catch (Exception e) { }

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
    public String getCustAssetCode(final String id) {
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

        return  code;
    }


}
