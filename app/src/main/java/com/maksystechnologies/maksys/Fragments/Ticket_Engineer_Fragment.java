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
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.maksystechnologies.maksys.Adapters.TicketEngineerAdapter;
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

public class Ticket_Engineer_Fragment  extends Fragment {
    ProgressBar pDialog;
    private RecyclerView recyclerView_customer_tickets;
    private TicketEngineerAdapter ticketsCustomerAdapter;
    private List<Tickets> ticketsList;
    TextView empty;
    private RecyclerView.LayoutManager manager;
    private LinearLayoutManager linearLayoutManager;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        final View rootView = inflater.inflate(R.layout.fragment_ticket_engineer, container, false);
        recyclerView_customer_tickets=rootView.findViewById(R.id.recycle_engineer_tickets);
        ticketsList = new ArrayList<Tickets>();
        ticketsCustomerAdapter=new TicketEngineerAdapter(getActivity(),getContext(),ticketsList);
        manager=new LinearLayoutManager(getActivity());
        linearLayoutManager=new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView_customer_tickets.setLayoutManager(linearLayoutManager);
        pDialog=rootView.findViewById(R.id.progress_engineer_ticket);
        empty=rootView.findViewById(R.id.empty_engineer_ticket);
        pDialog.setVisibility(View.GONE);

        getTickets();
        return rootView;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("My Tickets");
    }


    private void getTickets(){

        pDialog.setVisibility(View.VISIBLE);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.GetEngineerTickets,
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

                                String id=feedObj.getString("ticket_code");

                                String createdAt=feedObj.getString("end_time");
                                String name=feedObj.getString("cust_branch_name");
                                String status=feedObj.getString("ticket_assign_current_status");
                                String service_progress=feedObj.getString("service_progress");
                                String assigned_on=feedObj.getString("assigned_on");
                                String ticket_complaint=feedObj.getString("ticket_complaint");
                                String cust_name=feedObj.getString("cust_name");


                                tickets.setServiceprogress(service_progress);
                                tickets.setAssignedon(assigned_on);
                                tickets.setComplaints(ticket_complaint);
                                tickets.setCustname(cust_name);
                               tickets.setId(id);
                               tickets.setStatus(status);
                               tickets.setBranchname(name);
                               tickets.setCreated(createdAt);

                                ticketsList.add(tickets);

                            }


                        } catch (Exception e) { }

                        if(ticketsCustomerAdapter.getItemCount()==0){
                            empty.setVisibility(View.VISIBLE);
                        }else empty.setVisibility(View.GONE);
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
}
