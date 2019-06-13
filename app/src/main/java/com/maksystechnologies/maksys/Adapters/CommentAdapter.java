package com.maksystechnologies.maksys.Adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.maksystechnologies.maksys.Models.Comments;
import com.maksystechnologies.maksys.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {
    private Activity mActivity;
    private Context mContext;


    private List<Comments> notificationList;


    public CommentAdapter(Activity activity, Context context, List<Comments> item) {
        this.mActivity = activity;
        this.mContext = context;
        this.notificationList = item;
    }

    @Override
    public CommentAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.coment_list_layout, parent, false);
        return new CommentAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CommentAdapter.ViewHolder holder, int position) {

        Comments bind = notificationList.get(position);


        holder.message.setText(bind.getComment());
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
        holder.time.setText(date);



//        holder.religion.setText(bind.getReligion());

        Log.e("At ticket cstmr adapter", "fetch");
    }

    @Override
    public int getItemCount() {
        return notificationList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView  message, time, ticket_status;


        public ViewHolder(final View itemView) {
            super(itemView);


            message = (TextView) itemView.findViewById(R.id.comment_message);
            time = (TextView) itemView.findViewById(R.id.commentn_time);



        }

    }
}