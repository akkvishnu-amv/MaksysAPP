package com.maksystechnologies.maksys.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.maksystechnologies.maksys.Activities.CurrentTickets;
import com.maksystechnologies.maksys.Activities.MainEngineerActivity;
import com.maksystechnologies.maksys.Fragments.EngineerScheduledTickets;
import com.maksystechnologies.maksys.Models.CurrentTicket;
import com.maksystechnologies.maksys.R;
import com.maksystechnologies.maksys.Utilities.SharedPrefManager;
import com.maksystechnologies.maksys.Utilities.URLs;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TicketScheduledEngineersAdapter extends RecyclerView.Adapter<TicketScheduledEngineersAdapter.ViewHolder> {
   MainEngineerActivity mainEngineerActivity=new MainEngineerActivity();
    private Activity mActivity;
    private Context mContext;
    boolean isClicked=false;
    List<String> arrayList;
    String code=" ";
    boolean isAvailable=false;
    private  TicketScheduledEngineersAdapter ticketScheduledEngineersAdapter;
        EngineerScheduledTickets engineerScheduledTickets=new EngineerScheduledTickets();

    private List<CurrentTicket> ticketsItems;
    String addresslink="";

    public TicketScheduledEngineersAdapter(Activity activity, Context context, List<CurrentTicket> item,List<String> arrayList) {
        this.mActivity = activity;
        this.mContext = context;
        this.ticketsItems = item;
        this.arrayList=arrayList;
        addresslink="https://www.google.com/maps/search/?api=1&query="+SharedPrefManager.getInstance(mActivity).getKEY_Latitude()+","+SharedPrefManager.getInstance(mActivity).getKEY_Longitude();


    }

    @Override
    public TicketScheduledEngineersAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ticketscheduled, parent, false);
        return new TicketScheduledEngineersAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final TicketScheduledEngineersAdapter.ViewHolder holder, int position) {

        final CurrentTicket bind = ticketsItems.get(position);

//        if(engineerScheduledTickets.isStartedService && engineerScheduledTickets.getPosition()!=position){
//            holder.CardListItem.setEnabled(true);
//        }else {
//            holder.CardListItem.setEnabled(true);
//        }

//        if ( containsSubString(arra, "pens")) {
//            int listIndex = getItemPos("pens");
//        }




        holder.branch.setText("("+bind.getAddress()+" - "+bind.getCustname()+")");
        String strCurrentDate = bind.getScheduledtime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date newDate = null;
        try {
            newDate = format.parse(strCurrentDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        format = new SimpleDateFormat("dd-MM-yyyy hh:mm a");
        String date = format.format(newDate);
        holder.time.setText(date);
        holder.ticketcode.setText(bind.getTicketcode());
//        holder.devicedetails.setText(bind.getDevice());
        if(bind.getStatus().equals("4")){
            holder.startService.setText(mActivity.getResources().getText(R.string.startwork));
            holder.startService.setBackgroundColor(mActivity.getResources().getColor(R.color.colorGreen));
        }
//        getCustAssetCode(bind.getCustssetid());
        holder.startService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(holder.startService.getText().equals(mActivity. getResources().getText(R.string.startwork))){
                    if(SharedPrefManager.getInstance(mContext).isEngineerAttendedToday()) {
//                    setTicketStart(ticket,ids,addresslink);
                        SharedPrefManager.getInstance(mContext).setKEY_Pending_Requeststatus(mActivity.getResources().getString(R.string.sharedPendingStatusStart));

                        Intent intent = new Intent(mContext, CurrentTickets.class);
                        intent.putExtra("name", bind.getAddress()+"-"+bind.getCustname());
                        intent.putExtra("address", bind.getBranchAddress());
                        intent.putExtra("issue", "Device : " + removeLastChar(bind.getDevicename()) +"\n\nIssue : "+ bind.getDevice());
                        intent.putExtra("number", bind.getTicketMobile());
                        intent.putExtra("id", bind.getTicketAssignID());
                        intent.putExtra("ticket", bind.getTicketid());
                        intent.putExtra("ticketcode", bind.getTicketcode());

                        mActivity.startActivity(intent);
                    }else {
                        Toast.makeText(mContext,"Check Your Attendance.!",Toast.LENGTH_SHORT).show();

                    }

                }
//                Toast.makeText(mContext,"status "+bind.getStatus(),Toast.LENGTH_SHORT).show();
                if(bind.getStatus().equals("4")){
                    holder.startService.setText(mActivity.getResources().getText(R.string.startwork));
                    holder.startService.setBackgroundColor(mActivity.getResources().getColor(R.color.colorGreen));
                }else{
                    if(SharedPrefManager.getInstance(mContext).isEngineerAttendedToday()) {
//                    setTicketStart(ticket,ids,addresslink);
                        SharedPrefManager.getInstance(mContext).setKEY_Pending_Requeststatus(mActivity.getResources().getString(R.string.sharedPendingStatusStart));

                        setTicketStart(bind.getTicketid(), bind.getTicketAssignID(), addresslink, bind.getSchedule_id());
                        holder.startService.setText(mActivity.getResources().getText(R.string.processing));
                    }else {

                        Toast.makeText(mContext,"Check Your Attendance.!",Toast.LENGTH_SHORT).show();

                    }

                }
                Handler mHandler = new Handler();
                mHandler.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        holder.startService.setText(mActivity.getResources().getText(R.string.startwork));
                        holder.startService.setBackgroundColor(mActivity.getResources().getColor(R.color.colorGreen));
                        //start your activity here
                    }

                }, 1000L);






//                getCustAssetCode(bind.getCustssetid());
//                Bundle bundle = new Bundle();
//                Intent intent=new Intent(mContext,ServiceRequestDetails.class);
//                intent.putExtra("name",bind.getAddress());
//                intent.putExtra("address",bind.getBranchAddress());
//                intent.putExtra("issue","Device : "+removeLastChar(code) +bind.getDevice());
//                intent.putExtra("number",bind.getTicketMobile());
//                intent.putExtra("id",bind.getTicketAssignID());
//                intent.putExtra("ticket",bind.getTicketid());
//                intent.putExtra("ticketcode",bind.getTicketcode());
//
//                mActivity.startActivity(intent);
//               // mainEngineerActivity.showSnackbar("snackbar message",5000);
//
//              //  mActivity.startActivity(new Intent(mActivity,MainEngineerActivity.class));
            }
        });

        holder.dropdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                getCustAssetCode(bind.getCustssetid());
                if(isClicked){

                    holder.deviceLayout.setVisibility(View.VISIBLE);

                    holder.devicedetails.setText(removeLastChar(bind.getDevicename() ));
                    holder.issuedetails.setText(bind.getDevice());
                    holder.dropdown.setImageResource(R.drawable.round_expand_less_black_24dp);
                    isClicked=false;
                }else {
                    holder.deviceLayout.setVisibility(View.GONE);
                    holder.dropdown.setImageResource(R.drawable.round_expand_more_black_24dp);
                    isClicked=true;
                }

            }
        });
//        holder.religion.setText(bind.getReligion());

        Log.e("At ticket cstmr adapter", "fetch");
    }

    @Override
    public int getItemCount() {
        return ticketsItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView ticketcode,branch, time;
        public Button startService;
        public ImageView dropdown;
        public TextView devicedetails,issuedetails;
        public LinearLayout deviceLayout;
        public CardView CardListItem;


        public ViewHolder(final View itemView) {
            super(itemView);

            ticketcode = (TextView) itemView.findViewById(R.id.tv_engineer_ticket_scheduled_title);
            time = (TextView) itemView.findViewById(R.id.tv_engineer_ticket_scheduled_time);
            branch = (TextView) itemView.findViewById(R.id.tv_engineer_ticket_scheduled_branch);
            startService=itemView.findViewById(R.id.btn_service_request_schedule);
            devicedetails=itemView.findViewById(R.id.edit_engineer_schedule_device);
            deviceLayout=itemView.findViewById(R.id.engineer_scheduled_device_layout);
            CardListItem=itemView.findViewById(R.id.card_engineer_ticket);
            issuedetails=itemView.findViewById(R.id.edit_engineer_schedule_device_issue);

            dropdown=itemView.findViewById(R.id.tv_engineer_ticket_scheduled_device);

        }

    }




    private int getItemPos(String item) {
        return arrayList.indexOf(item);
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
        RequestQueue requestQueue = Volley.newRequestQueue(mActivity);
        requestQueue.add(stringRequest);

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }
    private static String removeLastChar(String str) {
        return str.substring(0, str.length() - 1);
    }
    public void showSnackbar(View view, String message, int duration)
    {
        Snackbar.make(view, message, duration).show();
    }

    private void  setTicketStart(final String ticketid, final String assignid,final String address,final String schedule_id){

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
                params.put("schedule_id",schedule_id );
                Log.e("address",address);
                Log.e("assign_id",assignid);
                Log.e("ticket_id",ticketid);
                return params;
            }

        };

        //Adding the string request to the queue
        RequestQueue requestQueue = Volley.newRequestQueue(mActivity);
        requestQueue.add(stringRequest);

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


    }
//    public void swap(List list){
//        if (mFeedsList != null) {
//            mFeedsList.clear();
//            mFeedsList.addAll(list);
//        }
//        else {
//            mFeedsList = list;
//        }
//        notifyDataSetChanged();
//    }


}