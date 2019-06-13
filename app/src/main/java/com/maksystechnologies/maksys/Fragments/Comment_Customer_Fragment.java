package com.maksystechnologies.maksys.Fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import com.maksystechnologies.maksys.Adapters.CommentAdapter;
import com.maksystechnologies.maksys.Models.Comments;
import com.maksystechnologies.maksys.R;
import com.maksystechnologies.maksys.Utilities.SharedPrefManager;
import com.maksystechnologies.maksys.Utilities.URLs;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class  Comment_Customer_Fragment extends Fragment {
    ProgressBar pDialog;
    TextView empty;
    private List<String> arrayList;
    private RecyclerView recyclerView_customer_tickets;
    private CommentAdapter ticketsCustomerAdapter;
    private List<Comments> ticketsList;
    private RecyclerView.LayoutManager manager;
    private LinearLayoutManager linearLayoutManager;
    private  String custcode;
    public boolean isStartedService=false;
    public int position=-1;
    Button  createComent;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragment
         View rootView=inflater.inflate(R.layout.fragment_comment_customer, container, false);
        recyclerView_customer_tickets=rootView.findViewById(R.id.recycle_cusomer_tickets_comments);
        ticketsList = new ArrayList<Comments>();
        arrayList=new ArrayList<String>();
        ticketsCustomerAdapter=new CommentAdapter(getActivity(),getContext(),ticketsList);
        manager=new LinearLayoutManager(getActivity());
        linearLayoutManager=new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView_customer_tickets.setLayoutManager(linearLayoutManager);
        pDialog=rootView.findViewById(R.id.progress_customer_ticket_comments);
        empty=rootView.findViewById(R.id.tv_customer_comment_empty);
        createComent=rootView.findViewById(R.id.btn_customer_comment);
        empty.setVisibility(View.GONE);
        pDialog.setVisibility(View.GONE);
        getTickets();


        createComent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showComment(v);
            }
        });

        return rootView;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Comments");
    }


    private void getTickets(){

        pDialog.setVisibility(View.VISIBLE);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.GetComments,
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
                                Comments tickets=new Comments();

                                String id=feedObj.getString("comment_id");
                                String ticket_id=feedObj.getString("comments");
                                String schedule_date_time=feedObj.getString("added_on");





//                            String device=getCustAssetCode(cust_asset_id);

//                                Log.e("rest",device);
                                tickets.setId(id);
                                tickets.setComment(ticket_id);
                                tickets.setCreated(schedule_date_time);
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
                params.put("customer_id", SharedPrefManager.getInstance(getActivity()).getKEY_Customer_BranchID());

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

                    Comments ticket=new Comments();

                    ticket.setComment(comment.getText().toString());
                    Calendar c = Calendar.getInstance();
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String formattedDate = df.format(c.getTime());
                    ticket.setCreated(formattedDate.toString());

                    addItem(ticket);
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
    private void addItem(Comments item) {
        ticketsList.add(item);
        ticketsCustomerAdapter.notifyDataSetChanged();
    }
}
