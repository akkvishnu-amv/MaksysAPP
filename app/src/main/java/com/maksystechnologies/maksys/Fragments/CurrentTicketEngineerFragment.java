package com.maksystechnologies.maksys.Fragments;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
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
import com.maksystechnologies.maksys.Models.ServiceRequest;
import com.maksystechnologies.maksys.R;
import com.maksystechnologies.maksys.Utilities.SharedPrefManager;
import com.maksystechnologies.maksys.Utilities.URLs;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CurrentTicketEngineerFragment extends Fragment {
    private Uri imageToUploadUri;
    EditText customername,issuedetails,serviceprogrss,currentTicketID,addressEdt;
    TextView emptytext;
    Button next;

    FragmentManager fragmentManager ;
    FragmentTransaction fragmentTransaction;
    CardView cardView;
    String pictutepath="",ticket_id_assing,cstname,tktissue,tktid,tktissue1,status="";
    Bitmap bitmap;
    ProgressBar mProgressbar;
    String mobile,addres,ticketcode;
    private static final int PERMISSION_REQUEST=0;
    private static final int RESULT_LOAD_IMAGE=1;

    private ArrayList permissionsToRequest;
    private ArrayList permissionsRejected = new ArrayList();
    private ArrayList permissions = new ArrayList();

    private final static int ALL_PERMISSIONS_RESULT = 107;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_engineer_current_ticket, container, false);
        customername=rootView.findViewById(R.id.et_engineer_current_ticket_name);
//        ticketid=rootView.findViewById(R.id.et_engineer_current_ticket_ticketid);
        issuedetails=rootView.findViewById(R.id.et_engineer_current_ticket_issuedetails);
        serviceprogrss=rootView.findViewById(R.id.et_engineer_current_ticket_service_progress);
        serviceprogrss.setText(SharedPrefManager.getInstance(getActivity()).getKEY_Current_Ticket_Progress());
        cardView=rootView.findViewById(R.id.card_engineer_current_tickets);
//        emptytext=rootView.findViewById(R.id.tv_currentTicket_empty);
        next=rootView.findViewById(R.id.btn_engineer_current_ticket_next);
        currentTicketID=rootView.findViewById(R.id.et_engineer_current_ticket_id);
        addressEdt=rootView.findViewById(R.id.et_engineer_current_ticket_address);
//        mProgressbar=rootView.findViewById(R.id.progressBarCurrentRequest);
        mProgressbar.setVisibility(View.GONE);
//        cardView.setVisibility(View.GONE);

//        getCurrentService();
        Bundle bundle=this.getArguments();
        if(bundle != null){
            // handle your code here.
            ticket_id_assing=bundle.getString("id");
            cstname=bundle.getString("name");
            tktissue=bundle.getString("issue");
            tktid=bundle.getString("ticket");
            ticketcode=bundle.getString("ticketcode");
            mobile=bundle.getString("number");
            addres=bundle.getString("address");

            customername.setText(cstname);
            issuedetails.setText(tktissue);
            currentTicketID.setText(ticketcode);
            addressEdt.setText(addres);
        }


        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            if(!serviceprogrss.getText().toString().equals("")) {
                Fragment fragment = new CurrentTicketEngineerFragmentSpareDetails();

                Bundle args = new Bundle();
                args.putString("Key", ticket_id_assing);
                args.putString("Keyprogress",serviceprogrss.getText().toString());
                args.putString("Keyticket",tktid);
                fragment.setArguments(args);
                fragmentTransaction.setCustomAnimations(R.anim.enterfromright, R.anim.exittoleft);

                fragmentTransaction.replace(R.id.content_frame, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();


                SharedPrefManager.getInstance(getActivity()).setKEY_Current_Ticket_Progress(serviceprogrss.getText().toString());
                }else {
                serviceprogrss.setError("Add Service progress");
            }
//                closeRequest(tktid,ticket_id_assing,tktissue,tktissue1);
            }
        });


        return rootView;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fragmentManager = getActivity().getSupportFragmentManager();
        fragmentTransaction =  fragmentManager.beginTransaction();
        getActivity().setTitle("Current Ticket");

    }
    public CurrentTicketEngineerFragment(){

    }
//

    public void getCurrentService() {
mProgressbar.setVisibility(View.VISIBLE);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.EngineerCurrentRequest,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Volleyresponse",response.toString());
                        try {

                            JSONObject Jobject = (JSONObject) new JSONTokener(response).nextValue();
                            Log.e("volleyJson", Jobject.toString());
                            JSONArray feedArray = Jobject.getJSONArray("result");

                            Log.e("feedlength",String.valueOf(feedArray.length()));

                            for (int i = 0; i < feedArray.length(); i++) {
                                int jarraylength = feedArray.length() + 1;

                                JSONObject feedObj = (JSONObject) feedArray.get(i);
                                ServiceRequest tickets = new ServiceRequest();


                                String id = feedObj.getString("ticket_assign_id");
                                String status = feedObj.getString("ticket_assign_current_status");
                                String ticketid = feedObj.getString("ticket_id");
                                String accepted_location = feedObj.getString("start_location");
                                String addrss = feedObj.getString("cust_branch_address");
                                String completed_date = feedObj.getString("end_time");

                                String nm = feedObj.getString("cust_branch_name");
                                String mobile = feedObj.getString("ticket_mobile");
                                String device = feedObj.getString("cust_asset_id");
                                String upload = feedObj.getString("document_upload");

                                String description = feedObj.getString("ticket_complaint");

//
                                Log.e("ticket id", ticketid);
//                             String ticketids=Jobject.getString("ticket_id");
//                             String issue1=Jobject.getString("device");
//                                     String issue2=Jobject.getString("description");
//                             String ticket_id=       Jobject.getString("id");

//


                                tktid = ticketid;
                                cstname = nm;
                                tktissue = device;
                                tktissue1 = description;
                                ticket_id_assing = id;
                                SharedPrefManager.getInstance(getActivity()).setKEY_Current_Ticket_id(ticket_id_assing);
                                SharedPrefManager.getInstance(getActivity()).setKEY_Current_Ticket_ticket(tktid);
                                if(feedArray.length()==0){
                                    cardView.setVisibility(View.GONE);
                                    emptytext.setVisibility(View.VISIBLE);
                                }else {
                                    cardView.setVisibility(View.VISIBLE);
                                    emptytext.setVisibility(View.GONE);

                                }
                            }
                        } catch (Exception e) { }



                        customername.setText(cstname);
//                        ticketid.setText(tktid);
                        issuedetails.setText("Device : "+tktissue+" // "+" Issues : "+tktissue1);
//                        Log.e("ticket_assignid",ticket_id_assing);
                        mProgressbar.setVisibility(View.GONE);
//

                    };
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                            pDialog.hide();

                        mProgressbar.setVisibility(View.GONE);
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

//    public void closeRequest(final String ticket_id, final String assign_id, final String item, final String description)  {
//
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.EngineerCloseRequest,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        Log.d("Volleyresponse",response.toString());
//                        try {
//                            JSONArray jArray;
//                            JSONObject Jobject = (JSONObject) new JSONTokener(response).nextValue();
//                            Log.e("volleyJson", Jobject.toString());
//
////                                jArray = Jobject.getJSONArray("result");
////                                int jarraylength=jArray.length()+1;
//////                                String c1[] = new String[jarraylength];
//////                                String c2[] = new String[jarraylength];
//////                                String c3[] = new String[jarraylength];
//////
////                                for (int i = 0; i < jarraylength; i++) {
////                                    JSONObject json_data = jArray.getJSONObject(i);
//                            status  = Jobject.getString("status");
//                            SharedPrefManager.getInstance(getContext()).clearPendingRequest();
//                            startActivity(new Intent(getContext(),MainEngineerActivity.class));
//
//                            Log.e("status",status);
//
//
//
//                        } catch (Exception e) { }
//
//
////
//
//                    };
//                },
//
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
////                            pDialog.hide();
//
//
//                        Toast.makeText(getActivity(),error.toString(), Toast.LENGTH_LONG).show();
//                    }
//                }) {
//            @Override
//            protected Map<String, String> getParams() {
//                String sparedetails="";
//                String status="";
//                String checksum="";
//                String uploadchecksum="0";
//                String imgupload="";
//                if(!spareusd.getText().toString().equals("")){
//                   sparedetails=spareusd.getText().toString();
//                   checksum="1";
//                }
//                if(!pictutepath.equals("")){
//                    imgupload=getStringImage(bitmap);
//                    uploadchecksum="1";
//                }
//                if(ticketStatus.getSelectedItemPosition()==0){
//                   status="2";
//                }else if(ticketStatus.getSelectedItemPosition()==1){
//                    status="1";
//                }
//                Map<String, String> params = new HashMap<String, String>();
//                params.put("ticket_id", ticket_id);
//                params.put("assign_id", assign_id);
//                params.put("progress", serviceprogrss.getText().toString());
//                params.put("sparedetails", sparedetails);
//                params.put("upload", imgupload);
//                params.put("status", status);
//                params.put("checksum", checksum);
//                params.put("uploadchechsum", uploadchecksum);
//                params.put("engineer_id", SharedPrefManager.getInstance(getActivity()).getKEY_Engineer_ID());
//                params.put("item", item);
//                params.put("description", description);
//
////  Log.e("country code send",countrycode);
//                return params;
//            }
//
//        };
//
//        //Adding the string request to the queue
//        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
//        requestQueue.add(stringRequest);
//
//        stringRequest.setRetryPolicy(new DefaultRetryPolicy(10000,
//                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//
//    }


}
