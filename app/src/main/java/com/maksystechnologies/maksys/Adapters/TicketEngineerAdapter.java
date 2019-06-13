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
import android.widget.ImageView;
import android.widget.TextView;

import com.maksystechnologies.maksys.Models.Tickets;
import com.maksystechnologies.maksys.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class TicketEngineerAdapter  extends RecyclerView.Adapter<TicketEngineerAdapter.ViewHolder> {
    private Activity mActivity;
    private Context mContext;


    private List<Tickets> ticketsItems;


    public TicketEngineerAdapter(Activity activity, Context context, List<Tickets> item) {
        this.mActivity = activity;
        this.mContext = context;
        this.ticketsItems = item;
    }

    @Override
    public TicketEngineerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.engineertickets, parent, false);
        return new TicketEngineerAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TicketEngineerAdapter.ViewHolder holder, int position) {

        final Tickets bind = ticketsItems.get(position);
        if(bind.getStatus().equals("2")){
            holder.description.setText("Pending : ");
            holder.description.setTextColor(mContext.getResources().getColor(R.color.orange));
        }else if(bind.getStatus().equals("3")){
            holder.description.setText("Complete : ");
            holder.description.setTextColor(mContext.getResources().getColor(R.color.green));
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
        holder.title.setText(bind.getBranchname()+"-"+bind.getCustname()+" ("+bind.getId()+") ");
        holder.time.setText(date);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowTicketDetails(bind.getId(),bind.getBranchname()+"-"+bind.getCustname(),bind.getServiceprogress(),bind.getComplaints(),date);
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
        public TextView title, time,description;
        public CardView cardView;


        public ViewHolder(final View itemView) {
            super(itemView);

            description=(TextView)itemView.findViewById(R.id.tv_engineer_ticket_ticket_description);
            title = (TextView) itemView.findViewById(R.id.tv_engineer_ticket_title);
            time = (TextView) itemView.findViewById(R.id.tv_engineer_ticket_time);
            cardView=itemView.findViewById(R.id.card_engineer_ticket);



        }

    }

    private void ShowTicketDetails(String tikt,String custname,String progresss,String isse,String assing){

//        setResendOTP();// otp send to registered customer number fetch from shared preference
        Button cancel;
        ImageView imageView;
        TextView ticketcode,customername,issue,progress,completedon;
        ViewGroup viewGroup = mActivity.findViewById(android.R.id.content);

        //then we will inflate the custom alert dialog xml that we created
        final View dialogView = LayoutInflater.from(mContext).inflate(R.layout.ticket_engineer_layout, viewGroup, false);

//        String devicelist=getCustAssetCode(dvc);
//        Toast.makeText(mContext,devicelist,Toast.LENGTH_SHORT).show();
        cancel=dialogView.findViewById(R.id.engineer_ticket_details_close);
        ticketcode=dialogView.findViewById(R.id.text_engineer_ticket_ticket_code);
        customername=dialogView.findViewById(R.id.text_engineer_ticket_ticket_customer_branch);
        progress=dialogView.findViewById(R.id.text_engineer_ticket_ticket_serviece_progress);
        issue=dialogView.findViewById(R.id.text_engineer_ticket_ticket_Issue);
        completedon=dialogView.findViewById(R.id.text_engineer_ticket_ticket_serviece_completed);


        ticketcode.setText(tikt);
        customername.setText(custname);
        progress.setText(progresss);
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
}