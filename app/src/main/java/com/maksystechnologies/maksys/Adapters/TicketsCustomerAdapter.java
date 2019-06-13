package com.maksystechnologies.maksys.Adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.maksystechnologies.maksys.Models.Tickets;
import com.maksystechnologies.maksys.R;
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

public class TicketsCustomerAdapter  extends RecyclerView.Adapter<TicketsCustomerAdapter.ViewHolder> {
    private Activity mActivity;
    private Context mContext;
    private String code="";

    private List<Tickets> ticketsItems;


    public TicketsCustomerAdapter(Activity activity, Context context, List<Tickets> item) {
        this.mActivity = activity;
        this.mContext = context;
        this.ticketsItems = item;
    }

    @Override
    public TicketsCustomerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.customer_tickets, parent, false);
        return new TicketsCustomerAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TicketsCustomerAdapter.ViewHolder holder, int position) {

        final Tickets bind=ticketsItems.get(position);
        holder.title.setText(bind.getTicketId());
        getCustAssetCode(bind.getDevice());
        Log.e("device",bind.getDevicename());
        if(bind.getStatus().equals("0")){
            holder.ticket_status.setText("Ticket Created");
            holder.ticket_status.setTextColor(Color.parseColor("#0000FF"));
            holder.ticket_status.setTypeface( holder.ticket_status.getTypeface(), Typeface.BOLD);

        }else if(bind.getStatus().equals("1")){
            holder.ticket_status.setText("Engineer Assigned");
            holder.ticket_status.setTextColor(Color.parseColor("#FFE4B392"));
            holder.ticket_status.setTypeface( holder.ticket_status.getTypeface(), Typeface.BOLD);
        }
        else if(bind.getStatus().equals("2")){
            holder.ticket_status.setText("Pending");
            holder.ticket_status.setTextColor(Color.parseColor("#D81B60"));
            holder.ticket_status.setTypeface( holder.ticket_status.getTypeface(), Typeface.BOLD);
        }
        else if(bind.getStatus().equals("3")){
            holder.ticket_status.setText("Completed");
            holder.ticket_status.setTextColor(Color.parseColor("#21E43C"));
            holder.ticket_status.setTypeface( holder.ticket_status.getTypeface(), Typeface.BOLD);
        } else if(bind.getStatus().equals("4")) {
            holder.ticket_status.setText("Completed");
            holder.ticket_status.setTextColor(Color.parseColor("#21E43C"));
            holder.ticket_status.setTypeface( holder.ticket_status.getTypeface(), Typeface.BOLD);
        }

        String strCurrentDate = bind.getCreated();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date newDate = null;
        try {
            newDate = format.parse(strCurrentDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        format = new SimpleDateFormat("dd-MM-yyyy hh:mm a");
        final String date = format.format(newDate);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowTicketDetails(bind.getTicketId(),code,bind.getDescription(),date);
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
        public TextView title, description, customer_status, ticket_status;
        public CardView cardView;

        public ViewHolder(final View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.tv_customer_ticket_title);
            cardView=itemView.findViewById(R.id.card_customer_ticket);
            ticket_status = (TextView) itemView.findViewById(R.id.tv_customer_ticket_status);
        }
    }

    private void ShowTicketDetails(String tikt,String devices,String isse,String assing){

//        setResendOTP();// otp send to registered customer number fetch from shared preference
        Button cancel;
        ImageView imageView;
        TextView ticketcode,customername,issue,device,completedon;
        ViewGroup viewGroup = mActivity.findViewById(android.R.id.content);

        //then we will inflate the custom alert dialog xml that we created
        final View dialogView = LayoutInflater.from(mContext).inflate(R.layout.customer_ticket_layout, viewGroup, false);

//        String devicelist=getCustAssetCode(dvc);
//        Toast.makeText(mContext,devicelist,Toast.LENGTH_SHORT).show();
        cancel=dialogView.findViewById(R.id.customer_ticket_details_close);

        ticketcode=dialogView.findViewById(R.id.text_customer_ticket_ticket_code);
        issue=dialogView.findViewById(R.id.text_customer_ticket_ticket_Issue);
        device=dialogView.findViewById(R.id.text_customer_ticket_ticket_device);
        completedon=dialogView.findViewById(R.id.text_customer_ticket_ticket_serviece_completed);


        ticketcode.setText(tikt);
        device.setText(devices);
        issue.setText(isse);
        completedon.setText(assing);
        //Now we need an AlertDialog.Builder object
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);

        //setting the view of the builder to our custom view that we already inflated
        builder.setView(dialogView);
        final AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(true);




        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                alertDialog.cancel();
            }
        });
        //finally creating the alert dialog and displaying it

        alertDialog.show();
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
        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        requestQueue.add(stringRequest);

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        return  code;
    }


}

