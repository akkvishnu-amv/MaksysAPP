package com.maksystechnologies.maksys.Adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.maksystechnologies.maksys.Models.Notification;
import com.maksystechnologies.maksys.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class NotificationEngineerAdapter extends RecyclerView.Adapter<NotificationEngineerAdapter.ViewHolder> {
    private Activity mActivity;
    private Context mContext;


    private List<Notification> notificationList;


    public NotificationEngineerAdapter(Activity activity, Context context, List<Notification> item) {
        this.mActivity = activity;
        this.mContext = context;
        this.notificationList = item;
    }

    @Override
    public NotificationEngineerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.notificationitem, parent, false);
        return new NotificationEngineerAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(NotificationEngineerAdapter.ViewHolder holder, int position) {

        Notification bind = notificationList.get(position);
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



//        holder.religion.setText(bind.getReligion());

        Log.e("At ticket cstmr adapter", "fetch");
    }

    @Override
    public int getItemCount() {
        return notificationList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title, message, time, ticket_status;


        public ViewHolder(final View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.notification_title);
            message = (TextView) itemView.findViewById(R.id.notification_message);
            time = (TextView) itemView.findViewById(R.id.notification_time);



        }

    }
}