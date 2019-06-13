package com.maksystechnologies.maksys.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.maksystechnologies.maksys.Adapters.ServiceRequestAdapter;
import com.maksystechnologies.maksys.Models.ServiceRequest;
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


public class Service_Request_Fragement_Engineer extends Fragment {
    ProgressBar pDialog;
    private RecyclerView recyclerView_engineer_service;
    public ServiceRequestAdapter serviceRequestAdapter;
    private List<ServiceRequest> serviceRequestList;
    private RecyclerView.LayoutManager manager;
    private LinearLayoutManager linearLayoutManager;
    private TextView noitem;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    String custcode;
    private OnFragmentInteractionListener mListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        final View rootView =  inflater.inflate(R.layout.fragment_service_request_fragement_engineer, container, false);


        recyclerView_engineer_service=rootView.findViewById(R.id.recycle_engineer_service_request);
        serviceRequestList = new ArrayList<ServiceRequest>();
        serviceRequestAdapter=new ServiceRequestAdapter(getActivity(),getContext(),serviceRequestList,serviceRequestAdapter);
        manager=new LinearLayoutManager(getActivity());
        linearLayoutManager=new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView_engineer_service.setLayoutManager(linearLayoutManager);
        pDialog=rootView.findViewById(R.id.progress_engineer_service_request);
        noitem=rootView.findViewById(R.id.tv_engineer_service_request);
        pDialog.setVisibility(View.GONE);
        noitem.setVisibility(View.GONE);

        getTickets();
        fragmentManager=getChildFragmentManager();
        return rootView;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        fragmentManager = getActivity().getSupportFragmentManager();
//        fragmentTransaction =  fragmentManager.beginTransaction();
        //you can set the title for your toolbar here for different fragments different titles
        fragmentManager = getActivity().getSupportFragmentManager();
        fragmentTransaction =  fragmentManager.beginTransaction();
        getActivity().setTitle("Service Requests");
    }

    private void getTickets(){

        pDialog.setVisibility(View.VISIBLE);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.EngineerServiceRequests,
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
                                String started_time=feedObj.getString("start_time");
                                String cust_branch_address=feedObj.getString("cust_branch_address");
                                String completed_date=feedObj.getString("end_time");
                                String name=feedObj.getString("cust_branch_name");
                                String mobile=feedObj.getString("ticket_mobile");
                                String device=feedObj.getString("cust_asset_id");
                                String upload=feedObj.getString("document_upload");
                                String proceed_status=feedObj.getString("schedule_status");
                                String description=feedObj.getString("ticket_complaint");
                                String ticketcode=feedObj.getString("ticket_code");
//                                if(feedObj.getString("ticket_issue_brief").equals(""))
                                String ticketissu=feedObj.getString("ticket_issue_brief");
                                String assing=feedObj.getString("assigned_on");

                                String custname=feedObj.getString("cust_name");

                                Log.e("cust_asset_id",device);
                                Log.e("ticketissu",ticketissu);
//                              String device1=  getCustAssetCode(device);


                                tickets.setCustomerName(custname);
                                tickets.setTktMIssue(ticketissu);
                                tickets.setId(id);
                                tickets.setTicketcode(ticketcode);
                                tickets.setProcced_status(proceed_status);
                                tickets.setTicketId(ticketid);
                                tickets.setCustAssetId(device);
                                tickets.setBranch_address(cust_branch_address);
                                tickets.setBranch_name(name);
                                tickets.setCompleted_date(completed_date);
                                tickets.setStarted_time(started_time);
                                tickets.setStatus(status);
                                tickets.setMobile(mobile);
                                tickets.setUpload(upload);
                                tickets.setIssue(description);
                                tickets.setAccepted_date(assing);
                                serviceRequestList.add(tickets);

                            }


                        } catch (Exception e) { }

                        serviceRequestAdapter.notifyDataSetChanged();
                        recyclerView_engineer_service.setAdapter(serviceRequestAdapter);
                        if(recyclerView_engineer_service.getAdapter().getItemCount()<1){
                            noitem.setVisibility(View.VISIBLE);
                        }else {
                            noitem.setVisibility(View.GONE);
                        }

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
    public  void reFresh(){

        Fragment fragment = new Service_Request_Fragement_Engineer();
//        fragmentTransaction.setCustomAnimations(R.anim.enterfromleft, R.anim.exittoright);
//        fragmentTransaction.replace(R.id.content_frame1, fragment);
//        fragmentTransaction.addToBackStack(null);
//        fragmentTransaction.commit();
//        serviceRequestAdapter.notifyDataSetChanged();
         }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Service_Request_Fragement_Engineer.OnFragmentInteractionListener) {
            mListener = (Service_Request_Fragement_Engineer.OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


    public String getCustAssetCode(final String id) {

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
                            custcode = Jobject.getString("status");


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
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        return custcode;

    }



}
