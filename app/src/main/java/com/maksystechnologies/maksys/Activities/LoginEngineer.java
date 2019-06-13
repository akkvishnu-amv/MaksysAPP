package com.maksystechnologies.maksys.Activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
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
import com.maksystechnologies.maksys.Models.Engineer;
import com.maksystechnologies.maksys.R;
import com.maksystechnologies.maksys.Utilities.SharedPrefManager;
import com.maksystechnologies.maksys.Utilities.URLs;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.HashMap;
import java.util.Map;

public class LoginEngineer extends AppCompatActivity {
    ImageView top_curve,logo;

    EditText email,password;
    TextView email_text, password_text, login_title;
    private TextView forgotpassword;
    LinearLayout new_user_layout;
    CardView login_card,fullviewCard;

    String Status=null ,name=null,emp_id=null ,mail_id =null,passwordRead=null,username=null,profile_photo =null,pin_no=null ,address=null ,contact_no=null,dob=null,designation=null,department=null;

    JSONObject Jobject;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_login_engineer);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        top_curve = findViewById(R.id.top_curve);
        email = findViewById(R.id.email_engineer_login);

        email_text = findViewById(R.id.email_text_engineer);
        password = findViewById(R.id.password_engineer_login);
        password_text = findViewById(R.id.password_text_engineer);
        logo = findViewById(R.id.logo_engineer);
        login_title = findViewById(R.id.login_text_engineer);
//        new_user_layout = findViewById(R.id.new_user_text);
        login_card = findViewById(R.id.login_card_engineer);
        fullviewCard=findViewById(R.id.fullcardview_engineer_login);

        forgotpassword=findViewById(R.id.forgotpassword_engineer);
        Animation top_curve_anim = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.top_down);
        top_curve.startAnimation(top_curve_anim);

        Animation editText_anim = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.edittext_anim);
        email.startAnimation(editText_anim);
        password.startAnimation(editText_anim);

        Animation field_name_anim = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.field_name_anim);
        email_text.startAnimation(field_name_anim);
        password_text.startAnimation(field_name_anim);
        logo.startAnimation(field_name_anim);
        login_title.startAnimation(field_name_anim);

        Animation center_reveal_anim = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.center_reveal_anim);
        login_card.startAnimation(center_reveal_anim);

        //get screen heigh and width in inches

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        double x = Math.pow(dm.widthPixels/dm.xdpi,2);
        double y = Math.pow(dm.heightPixels/dm.ydpi,2);
        double screenInches = Math.sqrt(x+y);
        Log.d("debug","Screen inches : " + screenInches);

        int topMarginSize=0,topimagesize=0;
        if (screenInches<5.0&&screenInches>3.0){
            topMarginSize=10;
            topimagesize=50;
        }
        if (screenInches<5.6&&screenInches>5.0){
            topMarginSize=170;
            topimagesize=80;
        }
        if (screenInches<6.5&&screenInches>5.6){
            topMarginSize=250;
            topimagesize=100;

        }

        //set cardview margin
        ViewGroup.MarginLayoutParams cardViewMarginParams = (ViewGroup.MarginLayoutParams) fullviewCard.getLayoutParams();
        cardViewMarginParams.setMargins(8, topMarginSize, 8, 8);
        fullviewCard.requestLayout();

        ViewGroup.MarginLayoutParams logoMarginParams = (ViewGroup.MarginLayoutParams) logo.getLayoutParams();
        logoMarginParams.setMargins(0, topimagesize, 0, 0);
        logo.requestLayout();
//        Animation new_user_anim = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.down_top);
//        new_user_layout.startAnimation(new_user_anim);
        email.clearFocus();


        forgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showForgotpassword();
            }
        });


    }

    //customer signup activity
    public void register(View view) {
        startActivity(new Intent(this,SignUp_Customer.class));
    }
    public void customerLogin(View view) {
        startActivity(new Intent(this,LoginCustomer.class));
    }

    public void forgotPassword(View view) {
//        startActivity(new Intent(this,Registration.class));
    }

    public void loginButton(View view) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.EngineerLogin,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Volleyresponse",response.toString());
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


                                    String Status1 = Jobject.getString("status");

                                    if (Status1.equals("success")){
                                        String name1 = Jobject.getString("name");
                                        String  emp_id1 = Jobject.getString("emp_id");
                                        String   mail_id1=Jobject.getString("mail_id");
                                        String profile_photo1=Jobject.getString("profile_photo");
                                        String pin_no1=Jobject.getString("pin_no");
                                        String address1=Jobject.getString("address");
                                        String  contact_no1=Jobject.getString("contact_no");
                                        String  dob1=Jobject.getString("dob");
                                        String designation1=Jobject.getString("designation");
                                        String department1=Jobject.getString("department");

                                        String password1=Jobject.getString("password");
                                        String username1=Jobject.getString("username");


                                        Log.e(Status1, "result");

                                        Status=Status1;
                                        name=name1;
                                        emp_id=emp_id1;
                                        mail_id=mail_id1;
                                        profile_photo=profile_photo1;
                                        pin_no=pin_no1;
                                        address=address1;
                                        contact_no=contact_no1;
                                        dob=dob1;
                                        designation=designation1;
                                        department=department1;
                                        passwordRead=password1;
                                        username=username1;
                                    }else{
                                        Status1=Jobject.getString("status");
                                        Status=Status1;
                                    }

//                                }
//                                        c1[i]=cid;
//                                        c3[i]=sname;

//                                        statecodename=c3;
//                                        statecodeid=c1;
//

                        } catch (Exception e) { }
//
                        switch(Status) {
                            case "success":
//                                Toast.makeText(getApplicationContext(), "Logged in", Toast.LENGTH_SHORT).show();
                            { Engineer engineer=new Engineer(name,designation,contact_no,mail_id,profile_photo,pin_no,address,dob,department,emp_id,passwordRead,username);
                                SharedPrefManager.getInstance(getApplicationContext()).EngineerLogin(engineer);
                                Intent i = new Intent(getApplicationContext(), LockScreenEngineerCreate.class);
                                startActivity(i);
                                break;
                            }

                            case "fail":
                            {
                                Toast.makeText(getApplicationContext(), "Username or Password is incorrect!!", Toast.LENGTH_SHORT).show();
                                break;
                            }
                        }
                    };
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                            pDialog.hide();
//                        Toast.makeText(LoginEngineer.this,error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("username", String.valueOf(email.getText().toString()));
                params.put("password", String.valueOf(password.getText().toString()));
//                    Log.e("country code send",countrycode);
                return params;
            }

        };

        //Adding the string request to the queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }
//        Toast.makeText(this,"Login Clicked",Toast.LENGTH_SHORT).show();

    public void loginasguest(View view) {
        startActivity(new Intent(this,LoginAsGuest.class));
    }

    private void showForgotpassword(){

//        setResendOTP();// otp send to registered customer number fetch from shared preference
        Button cancel,changepin;
        final EditText email,newpin;
        ViewGroup viewGroup = findViewById(android.R.id.content);

        //then we will inflate the custom alert dialog xml that we created
        final View dialogView = LayoutInflater.from(this).inflate(R.layout.forgotpassword, viewGroup, false);

        cancel=dialogView.findViewById(R.id.btn_forgotpassword__forgot_cancel);
        changepin=dialogView.findViewById(R.id.btn_forgotpassword__forgot_change);
        email=dialogView.findViewById(R.id.et_forgotpassword_email);

        //Now we need an AlertDialog.Builder object
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        //setting the view of the builder to our custom view that we already inflated
        builder.setView(dialogView);
        final AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);

        changepin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!email.getText().toString().equals("")){
                    Log.e("email",email.getText().toString());
                    resetEmail(email.getText().toString(),alertDialog);
                }else {
                    email.setError("Please enter email");
                }
            }
        });


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AlertDialog.Builder builder=new AlertDialog.Builder(LoginEngineer.this);// use you activity name
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
    public void resetEmail(final String email, final AlertDialog dialog) {


        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.EngineerForgotEmail,
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

                            Status = Jobject.getString("status");


//                                }
//                                        c1[i]=cid;
//                                        c3[i]=sname;

//                                        statecodename=c3;
//                                        statecodeid=c1;
//

                        } catch (Exception e) {
                        }
//
                        switch (Status) {
                            case "success":

                                Toast.makeText(getApplicationContext(), "New Password Has Been Sent Your Registered Email.!", Toast.LENGTH_SHORT).show();

                                dialog.cancel();

//                                Engineer engineer=new Engineer(name,designation,contact_no,mail_id,profile_photo,pin_no,address,dob,department,emp_id,passwordRead,username);
//                                Intent i = new Intent(getApplicationContext(), LockScreenEngineerCreate.class);
//                                SharedPrefManager.getInstance(getApplicationContext()).EngineerLogin(engineer);
//                                startActivity(i);
                                break;
                            case "fail":
                                Toast.makeText(getApplicationContext(), "invalid email!!", Toast.LENGTH_SHORT).show();
                                break;
                        }
                    }

                    ;
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                            pDialog.hide();
//                        Toast.makeText(LoginEngineer.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("email", email);

//                    Log.e("country code send",countrycode);
                return params;
            }

        };

        //Adding the string request to the queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }
}
