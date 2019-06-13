package com.maksystechnologies.maksys.Adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.maksystechnologies.maksys.Activities.MainEngineerActivity;
import com.maksystechnologies.maksys.Fragments.Service_Request_Fragement_Engineer;
import com.maksystechnologies.maksys.Models.ServiceRequest;
import com.maksystechnologies.maksys.R;
import com.maksystechnologies.maksys.Utilities.SharedPrefManager;
import com.maksystechnologies.maksys.Utilities.URLs;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServiceRequestAdapter extends RecyclerView.Adapter<ServiceRequestAdapter.ViewHolder> {
    private Activity mActivity;
    private Context mContext;
    private  ServiceRequestAdapter serviceRequestAdapter;
    String date_time = "";
    String time,custcode=" ";
    int mYear;
    int mMonth;
    int mDay;
    private int lastPosition = -1;
MainEngineerActivity mainEngineerActivity=new MainEngineerActivity();
    int mHour;
    int mMinute;
    Service_Request_Fragement_Engineer service_request_fragement_engineer=new Service_Request_Fragement_Engineer();

    private List<ServiceRequest> serviceRequestList;


    public ServiceRequestAdapter(Activity activity, Context context, List<ServiceRequest> item,ServiceRequestAdapter serviceRequestAdapter) {
        this.mActivity = activity;
        this.mContext = context;
        this.serviceRequestList = item;
        this.serviceRequestAdapter=serviceRequestAdapter;
    }

    @Override
    public ServiceRequestAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.service_request, parent, false);
        return new ServiceRequestAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ServiceRequestAdapter.ViewHolder holder, final int position) {

//        58.698017,-152.522067
//        final String address=holder.engineerActivity.getCompleteAddressString(Double.parseDouble(SharedPrefManager.getInstance(mContext).getKEY_Latitude()),Double.parseDouble(SharedPrefManager.getInstance(mContext).getKEY_Longitude()));
        final String address="https://www.google.com/maps/search/?api=1&query="+SharedPrefManager.getInstance(mContext).getKEY_Latitude()+","+SharedPrefManager.getInstance(mContext).getKEY_Longitude();
//        Toast.makeText(mContext,address,Toast.LENGTH_SHORT).show();
        Log.e("address",address);
        final ServiceRequest bind = serviceRequestList.get(position);

        if(bind.getUpload()!="null"){
            String imageURL="http://maksys.co.in/sms/admin/dist/img/ticket/complaint/"+bind.getUpload();
            Glide.with(mContext).load(imageURL).into(holder.uploadImage);
        }

        holder.uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String imageURL="";
                if(bind.getUpload()!="null"){
                   imageURL="http://maksys.co.in/sms/admin/dist/img/ticket/complaint/"+bind.getUpload();
                    ShowDetails(imageURL);
                }

            }
        });
        final String devicelist=getCustAssetCode(bind.getCustAssetId());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strCurrentDate = bind.getAccepted_date();
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date newDate = null;
                try {
                    newDate = format.parse(strCurrentDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                format = new SimpleDateFormat("dd-MM-yyyy hh:mm a");
                String date = format.format(newDate);
                ShowTicketDetails(bind.getTicketcode(),bind.getBranch_name()+"( "+bind.getCustomerName()+" )",custcode,bind.getIssue(),date);
//                Toast.makeText(mContext,"card clicked",Toast.LENGTH_SHORT).show();
            }
        });
        holder.description.setText("("+bind.getTktMIssue()+")");
        holder.title.setText(bind.getTicketcode());
        holder.schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePicker(bind.getTicketId(),bind.getId(),address,position,v,bind.getTicketcode(),bind.getMobile());
            }
        });







//
//        holder.message.setText(bind.getMessage());
//        holder.time.setText(bind.getTime().substring(0,bind.getTime().length() - 3));


//        holder.religion.setText(bind.getReligion());

        Log.e("At ticket cstmr adapter", "fetch");
    }

    @Override
    public int getItemCount() {
        return serviceRequestList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title,description;
        public Button schedule;
        public ImageView uploadImage;
        public CardView cardView;
        public MainEngineerActivity engineerActivity=new MainEngineerActivity();
        public ViewHolder(final View itemView) {
            super(itemView);

            description=(TextView)itemView.findViewById(R.id.service_request_description);
            title = (TextView) itemView.findViewById(R.id.service_request_title);
            schedule = (Button) itemView.findViewById(R.id.btn_service_request_schedule);
            uploadImage=itemView.findViewById(R.id.service_request_image);
            cardView=itemView.findViewById(R.id.card_service_request_list);



        }

    }

    private void  setTicketAccept(final String ticketid, final String assignid, final String address, final String schedule, final View v,final String ticketcode,final  String mob){


        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.EngineerAcceptServiceRequest,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.e("Volleyresponse",response.toString());
                        try {

                            JSONObject Jobject = (JSONObject) new JSONTokener(response).nextValue();
                            Log.e("volleyJson", Jobject.toString());
//                            setAnimation
                            showSnackbar(v,"Ticket Scheduled",3000);

                            Intent intent=new Intent(mContext,MainEngineerActivity.class);

                            mContext.startActivity(intent);
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
                params.put("schedule_time",schedule);
                params.put("ticket_code",ticketcode);
                params.put("engname",SharedPrefManager.getInstance(mContext).getEngineerName());
                params.put("mobile",SharedPrefManager.getInstance(mContext).getEngineerMobile());
                params.put("custmobile",mob);
                params.put("engineer_id",SharedPrefManager.getInstance(mContext).getKEY_Engineer_ID());
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


    }
    private void  setTicketDecline(final String ticketid, final String assginid, final String address,final String scheduletime){


        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.EngineerDeclineServiceRequest,
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
                params.put("assign_id",assginid );
                params.put("engineer_id",SharedPrefManager.getInstance(mContext).getKEY_Engineer_ID() );
                params.put("address",address );
                params.put("schedule_time",scheduletime);
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


    }
    private void showDecline(final String ticket_id, final String assignid, final String address){

        // otp send to registered customer number fetch from shared preference
        Button cancel,decline;
        final EditText reason;
        ViewGroup viewGroup = mActivity.findViewById(android.R.id.content);

        //then we will inflate the custom alert dialog xml that we created
        final View dialogView = LayoutInflater.from(mContext).inflate(R.layout.decline_service_request, viewGroup, false);

        cancel=dialogView.findViewById(R.id.btn_cancel_service_request);
        decline=dialogView.findViewById(R.id.btn_decline_service_request_decline);
        reason=dialogView.findViewById(R.id.et_decline_service_request_reason);

        //Now we need an AlertDialog.Builder object
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);

        //setting the view of the builder to our custom view that we already inflated
        builder.setView(dialogView);
        final AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.show();
       decline.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if(!reason.getText().toString().equals("")){


               AlertDialog.Builder builder=new AlertDialog.Builder(mContext);// use you activity name
               builder.setMessage("Are you sure you want to cancel? ")
                       .setPositiveButton("Yes",
                               new DialogInterface.OnClickListener() {

                                   @Override
                                   public void onClick(DialogInterface dialog, int which) {
                                           setTicketDecline(ticket_id,assignid,reason.getText().toString(),address);
                                           alertDialog.cancel();

                                       mContext.startActivity(new Intent(mContext,MainEngineerActivity.class));


                                   }
                               })

                       .setNegativeButton("No",new DialogInterface.OnClickListener() {

                           @Override
                           public void onClick(DialogInterface dialog, int which) {

                               alertDialog.cancel();
                           }
                       });
               AlertDialog dialog=builder.create();
               dialog.show();

           }else {
               reason.setError("Please fill the field");
           }


       }
       });
        //finally creating the alert dialog and displaying it




        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AlertDialog.Builder builder=new AlertDialog.Builder(mContext);// use you activity name
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

    private void datePicker(final String ticketid, final String assginid, final String address, final int position, final View v,final String ticketcode,final String mob){

        // Get Current Date
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(mContext,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        date_time = year+"-"+(monthOfYear + 1)+ "-"+dayOfMonth;
                        //*************Call Time Picker Here ********************
                        timePicker( ticketid,assginid, address,position,v,ticketcode,mob);
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }
    private void timePicker(final String ticketid, final String assginid, final String address, final int position, final View v,final String ticketcode,final String Mob){
        // Get Current Time
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(mContext,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        mHour = hourOfDay;
                        mMinute = minute;

                        time=date_time+" "+hourOfDay + ":" + minute+":00";

                        setTicketAccept(ticketid,assginid,address,time,v,ticketcode,Mob);

                        setAnimation(v,position);

//                        serviceRequestAdapter. notifyDataSetChanged();

                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();
    }
    public void remove(int position) {
//        if (position >= 0 && serviceRequestList.size() < position && serviceRequestList.get(position) != null) {

//        serviceRequestList.remove(position);
//        service_request_fragement_engineer.reFresh();
//            service_request_fragement_engineer.serviceRequestAdapter.notifyDataSetChanged();
//        }


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
        RequestQueue requestQueue = Volley.newRequestQueue(mActivity);
        requestQueue.add(stringRequest);

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
return custcode;

    }
    private static String removeLastChar(String str) {
        return str.substring(0, str.length() - 1);
    }
    public void showSnackbar(View view, String message, int duration)
    {
        Snackbar.make(view, message, duration).show();
    }
    private void setAnimation(final View viewToAnimate, final int position)
    {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition)
        {


            AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
            alertDialog.setTitle("Ticket Scheduled");

            alertDialog.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                    serviceRequestList.remove(position);
                    notifyDataSetChanged();
                    Animation animation = AnimationUtils.loadAnimation(mContext, android.R.anim.slide_in_left);
                    viewToAnimate.startAnimation(animation);
                    lastPosition = position;
                }
            });

            AlertDialog dialog = alertDialog.create();
            dialog.show();


//            Toast.makeText(mContext,"Ticket Scheduled",Toast.LENGTH_LONG).show();


//            mActivity.startActivity(new Intent(mContext,MainEngineerActivity.class));
//           mActivity. overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        }
    }

    private void ShowDetails(String URL){

//        setResendOTP();// otp send to registered customer number fetch from shared preference
        Button cancel;
        ImageView imageView;
        ViewGroup viewGroup = mActivity.findViewById(android.R.id.content);

        //then we will inflate the custom alert dialog xml that we created
        final View dialogView = LayoutInflater.from(mContext).inflate(R.layout.view_image_from_service_request_list, viewGroup, false);

        cancel=dialogView.findViewById(R.id.service_request_image_view_close);
        imageView=dialogView.findViewById(R.id.img_service_request_list_upload);
        Glide.with(mContext).load(URL).into(imageView);
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

    private void ShowTicketDetails(String tikt,String custname,String dvc,String isse,String assing){

//        setResendOTP();// otp send to registered customer number fetch from shared preference
        Button cancel;
        ImageView imageView;
        TextView ticketcode,customername,device,issue,assigned;
        ViewGroup viewGroup = mActivity.findViewById(android.R.id.content);

        //then we will inflate the custom alert dialog xml that we created
        final View dialogView = LayoutInflater.from(mContext).inflate(R.layout.service_request_details, viewGroup, false);

//        String devicelist=getCustAssetCode(dvc);
//        Toast.makeText(mContext,devicelist,Toast.LENGTH_SHORT).show();
        cancel=dialogView.findViewById(R.id.service_request_details_close);
        ticketcode=dialogView.findViewById(R.id.text_service_request_ticket_code);
        customername=dialogView.findViewById(R.id.text_service_request_ticket_customer_branch);
        device=dialogView.findViewById(R.id.text_service_request_ticket_device);
        issue=dialogView.findViewById(R.id.text_service_request_ticket_Issue);
        assigned=dialogView.findViewById(R.id.text_service_request_ticket_assigned);


        ticketcode.setText(tikt);
        customername.setText(custname);
        device.setText(dvc);
        issue.setText(isse);
        assigned.setText(assing);
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


}
