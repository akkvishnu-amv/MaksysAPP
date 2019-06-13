package com.maksystechnologies.maksys.Activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.maksystechnologies.maksys.Fragments.CurrentTicketEngineerFragment;
import com.maksystechnologies.maksys.R;

public class RedirectActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_redirect);

        if (getIntent().getExtras() != null) {
            Bundle bundle=new Bundle();
            bundle.putString("name",getIntent().getExtras().getString("name"));
            bundle.putString("address",getIntent().getExtras().getString("address"));
            bundle.putString("issue",getIntent().getExtras().getString("issue"));
            bundle.putString("number",getIntent().getExtras().getString("number"));
            bundle.putString("id",getIntent().getExtras().getString("id"));
            bundle.putString("ticket",getIntent().getExtras().getString("ticket"));
            bundle.putString("ticketcode",getIntent().getExtras().getString("ticketcode"));
            Fragment fragment =new CurrentTicketEngineerFragment();
            FragmentManager fragmentManager=getSupportFragmentManager();
            FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.content_frame, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

        }
    }
}
