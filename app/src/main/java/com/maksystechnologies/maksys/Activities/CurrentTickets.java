package com.maksystechnologies.maksys.Activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.maksystechnologies.maksys.Fragments.CurrentTicketEngineerFragmentSpareDetails;
import com.maksystechnologies.maksys.R;
import com.maksystechnologies.maksys.Utilities.SharedPrefManager;

public class CurrentTickets extends AppCompatActivity {
    private Uri imageToUploadUri;
    EditText issuedetails,serviceprogrss,addressEdt;
    TextView emptytext,customername,currentTicketID;
    Button next;

    FragmentManager fragmentManager ;
    FragmentTransaction fragmentTransaction;
    CardView cardView;
    String pictutepath="",ticket_id_assing,cstname,tktissue,tktid,tktissue1,status="";
    Bitmap bitmap;
    ProgressBar mProgressbar;
    String mobile,addres,ticketcode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_tickets);
        setupActionBar();

        customername=findViewById(R.id.et_engineer_current_ticket_name);
//        ticketid=rootView.findViewById(R.id.et_engineer_current_ticket_ticketid);
        issuedetails=findViewById(R.id.et_engineer_current_ticket_issuedetails);
        serviceprogrss=findViewById(R.id.et_engineer_current_ticket_service_progress);
        serviceprogrss.setText(SharedPrefManager.getInstance(getApplicationContext()).getKEY_Current_Ticket_Progress());
        cardView=findViewById(R.id.card_engineer_current_tickets);
//        emptytext=findViewById(R.id.tv_currentTicket_empty);
        next=findViewById(R.id.btn_engineer_current_ticket_next);
        currentTicketID=findViewById(R.id.et_engineer_current_ticket_id);
        addressEdt=findViewById(R.id.et_engineer_current_ticket_address);


        if (getIntent().getExtras() != null) {
            ticket_id_assing=getIntent().getExtras().getString("id");
            cstname=getIntent().getExtras().getString("name");
            tktissue=getIntent().getExtras().getString("issue");
            tktid=getIntent().getExtras().getString("ticket");
            ticketcode=getIntent().getExtras().getString("ticketcode");
            mobile=getIntent().getExtras().getString("number");
            addres=getIntent().getExtras().getString("address");


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
                    Intent intent=new Intent(getApplicationContext(),CurrentTicketSpares.class);
                    intent.putExtra("Key", ticket_id_assing);
                    intent.putExtra("Keyprogress",serviceprogrss.getText().toString());
                    intent.putExtra("Keyticket",tktid);
                    startActivity(intent);
                    SharedPrefManager.getInstance(getApplicationContext()).setKEY_Current_Ticket_Progress(serviceprogrss.getText().toString());
                }else {
                    serviceprogrss.setError("Add Service progress");
                }
//                closeRequest(tktid,ticket_id_assing,tktissue,tktissue1);
            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
          onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Current Ticket Details");
        if (actionBar != null) {
            // Show the Up button in the action bar.
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

}
