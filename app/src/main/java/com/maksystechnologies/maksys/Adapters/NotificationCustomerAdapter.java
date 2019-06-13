package com.maksystechnologies.maksys.Adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.maksystechnologies.maksys.Models.Notification;
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

public class NotificationCustomerAdapter  extends RecyclerView.Adapter<NotificationCustomerAdapter.ViewHolder> {
    private Activity mActivity;
    private Context mContext;


    private List<Notification> notificationList;


    public NotificationCustomerAdapter(Activity activity, Context context, List<Notification> item) {
        this.mActivity = activity;
        this.mContext = context;
        this.notificationList = item;
    }

    @Override
    public NotificationCustomerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.notificationitem, parent, false);
        return new NotificationCustomerAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(NotificationCustomerAdapter.ViewHolder holder, int position) {

        final Notification bind = notificationList.get(position);
        holder.title.setText(bind.getTitle());

        holder.message.setText(bind.getMessage());

        String strCurrentDate = bind.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date newDate = null;
        try {
            newDate = format.parse(strCurrentDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        format = new SimpleDateFormat("dd-MM-yyyy hh:mm a");
        final String date = format.format(newDate);
        holder.time.setText(date);

        holder.notify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//            Toast.makeText(mContext,"Feedback will Show here!",Toast.LENGTH_LONG).show();
//                Intent intent =new Intent(mContext,FeedbackCustomer.class);
//                intent.putExtra("ticket_id",bind.getTicket_id());
//                mActivity.startActivity(intent);
//                showForgotpassword(bind.getTicket_id());
            }
        });

//        holder.religion.setText(bind.getReligion());

        Log.e("At ticket cstmr adapter", "fetch");
    }

    @Override
    public int getItemCount() {
        return notificationList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title, message, time, ticket_status;
        public CardView notify;


        public ViewHolder(final View itemView) {
            super(itemView);

            notify=itemView.findViewById(R.id.cardView_notification);
            title = (TextView) itemView.findViewById(R.id.notification_title);
            message = (TextView) itemView.findViewById(R.id.notification_message);
            time = (TextView) itemView.findViewById(R.id.notification_time);


        }

    }
    private void showForgotpassword(final  String ticketid){

//        setResendOTP();// otp sendi to registered customer number fetch from shared preference
        Button cancel,changepin;
        final EditText comment,newpin;
        final RatingBar rate;
        ViewGroup viewGroup = mActivity.findViewById(android.R.id.content);

        //then we will inflate the custom alert dialog xml that we created
        final View dialogView = LayoutInflater.from(mActivity).inflate(R.layout.feedbacklayout, viewGroup, false);

        cancel=dialogView.findViewById(R.id.btn_feedback_nothanks);
        changepin=dialogView.findViewById(R.id.btn_feedback_rate);
        comment=dialogView.findViewById(R.id.et_feedback_comment);
        rate=dialogView.findViewById(R.id.ratingBar_feedback);

        //Now we need an AlertDialog.Builder object
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);

        //setting the view of the builder to our custom view that we already inflated
        builder.setView(dialogView);
        final AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);

        changepin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String com;
                if(comment.getText().toString().equals(""))
                {
                    com="";

                }else {com=comment.getText().toString();}
                String rating="";
                if(rate.getNumStars()!=0){
                    rating="null";


                }else {
                    rating=String.valueOf(rate.getNumStars());
                }


                SendComment(SharedPrefManager.getInstance(mContext).getKEY_Customer_ID(),ticketid,com,rating);

            }
        });


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });
        //finally creating the alert dialog and displaying it

      alertDialog.show();
    }
    public void SendComment(final String custid,final String ticketid,final  String comment,final String rating) {


        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.SendComment,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Volleyresponse", response.toString());
                        try {
                            JSONArray jArray;
                            JSONObject Jobject = (JSONObject) new JSONTokener(response).nextValue();
                            Log.e("volleyJson", Jobject.toString());

//                                jArray = Jobject.getJSONArray("result");
//                                int jarraylength=jArray.length()+1;
////                                String c1[] = new String[jarraylength];
////                                String c2[] = new String[jarraylength];
////                                String c3[] = new String[jarraylength];
////
//                                for (int i = 0; i < jarraylength; i++) {
//                                    JSONObject json_data = jArray.getJSONObject(i);



//                                }
//                                        c1[i]=cid;
//                                        c3[i]=sname;

//                                        statecodename=c3;
//                                        statecodeid=c1;
//

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
                        Toast.makeText(mActivity, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("cust_id", custid);
                params.put("ticket_id", ticketid);
                params.put("comment", comment);
                params.put("rating", rating);
                params.put("mobile", SharedPrefManager.getInstance(mContext).getKEY_Customer_Mobile());
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

}
