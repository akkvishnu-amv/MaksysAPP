package com.maksystechnologies.maksys.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.maksystechnologies.maksys.R;
import com.maksystechnologies.maksys.Utilities.SharedPrefManager;
import com.maksystechnologies.maksys.Utilities.URLs;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class LoginAsGuest extends AppCompatActivity {
    EditText name,mobile,item,model,description;
    ImageView logo;
    CardView logincard;
    String status,id,Status;
    Button login;
    SplashScreen mainActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_ACTION_BAR);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_login_as_guest);
//        getSupportActionBar().setTitle("Login As Guest");
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
mainActivity=new SplashScreen();
//        if(!mainActivity.isNetworkConnecteted()){
//            Toast.makeText(getApplicationContext(),"Check Your Internet",Toast.LENGTH_LONG).show();
//        }


        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        name=findViewById(R.id.name_loginguest);
        mobile=findViewById(R.id.mobile_login_guest);
        item=findViewById(R.id.item_login_guest);
//        model=findViewById(R.id.model_login_guest);
        description=findViewById(R.id.description_login_guest);
        logo=findViewById(R.id.logo_loginasGuest);
        logincard=findViewById(R.id.fullcardview_loginasGuest);

        login=findViewById(R.id.login_button_loginguest);

        //get screen heigh and width in inches

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String mobile1 = mobile.getText().toString();

                if (TextUtils.isEmpty(mobile1)){
//            mobile_number.setError("Enter Mobile Number");
                    mobile.setError("Enter Mobile Number");
                    return;
                }
                if (!TextUtils.isDigitsOnly(mobile1)){
                    mobile.setError("Invalid Mobile Number");
                    return;
                }
                if (mobile1.length()!=10 || mobile1.contains(" ") || mobile1.contains("+")){
                    mobile.setError("Please Enter 10 digit Mobile Number");
                    return;
                }

                else if(name.getText().toString().equals(null))
                {
                    name.setError("Please fill this field");

                }
                else if(item.getText().toString().equals(null))
                {
                    item.setError("Please fill this field");

                }

               else if(description.getText().toString().equals(null))
                {
                    description.setError("Please fill this field");

                }else {
                signupLOgin();

                }
            }
        });
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
            topMarginSize=200;
            topimagesize=100;

        }
        //set cardview margin
        ViewGroup.MarginLayoutParams cardViewMarginParams = (ViewGroup.MarginLayoutParams) logincard.getLayoutParams();
        cardViewMarginParams.setMargins(8, topMarginSize, 8, 8);
        logincard.requestLayout();
        //set logo margin
        ViewGroup.MarginLayoutParams logoMarginParams = (ViewGroup.MarginLayoutParams) logo.getLayoutParams();
        logoMarginParams.setMargins(0, topimagesize, 0, 0);
        logo.requestLayout();

    }



    @Override
    public void onBackPressed() {
        // do something on back.
        super.onBackPressed();

        return;
    }





    private void signupLOgin(){
        String Status1="";
        final int min = 111111;
        final int max = 999999;
        final int random = new Random().nextInt((max - min) + 1) + min;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.LoginAsGuest,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.e("Volleyresponse",response.toString());
                        try {
                            JSONArray jArray;
                            JSONObject Jobject = (JSONObject) new JSONTokener(response).nextValue();
                            Log.e("volleyJson", Jobject.toString());

//                        Toast.makeText(getApplicationContext(),response,Toast.LENGTH_SHORT).show();
//                                jArray = Jobject.getJSONArray("result");
//                                int jarraylength=jArray.length()+1;
////                                String c1[] = new String[jarraylength];
////                                String c2[] = new String[jarraylength];
////                                String c3[] = new String[jarraylength];
////
//                                for (int i = 0; i < jarraylength; i++) {
//                                    JSONObject json_data = jArray.getJSONObject(i);
//
                            status  = Jobject.getString("status");
                            Log.d("status result",  status);
                            String id1 = Jobject.getString("id");
                            Log.d("id result",  id1);

//                        String  emp_id1 = Jobject.getString("emp_id");
//                        String   mail_id1=Jobject.getString("mail_id");
//                        String profile_photo1=Jobject.getString("profile_photo");
//                        String pin_no1=Jobject.getString("pin_no");
//                        String address1=Jobject.getString("address");
//                        String  contact_no1=Jobject.getString("contact_no");
//                        String  dob1=Jobject.getString("dob");
//                        String designation1=Jobject.getString("designation");
//                        String department1=Jobject.getString("department");
//
//                        String password1=Jobject.getString("password");
//                        String username1=Jobject.getString("username");
//
//
//                        Log.e(Status1, "result");


//                        status=Status1;
                            id=id1;
//                        emp_id=emp_id1;
//                        mail_id=mail_id1;
//                        profile_photo=profile_photo1;
//                        pin_no=pin_no1;
//                        address=address1;
//                        contact_no=contact_no1;
//                        dob=dob1;
//                        designation=designation1;
//                        department=department1;
//                        passwordRead=password1;
//                        username=username1;
//                                }
//                                        c1[i]=cid;
//                                        c3[i]=sname;

//                                        statecodename=c3;
//                                        statecodeid=c1;
//

                        } catch (Exception e) { }



                        switch(status) {
                            case "success":
//                                Toast.makeText(getApplicationContext(), "Logged in", Toast.LENGTH_SHORT).show();
//                            Engineer engineer=new Engineer(name,designation,contact_no,mail_id,profile_photo,pin_no,address,dob,department,emp_id,passwordRead,username);

                                SharedPrefManager.getInstance(getApplicationContext()).setKEY_Login_AS_Guest_ID(id);
                                smsOTPsend(String.valueOf(random));
                                Intent intent = new Intent(LoginAsGuest.this, MobileOtpVerification.class);
                                intent.putExtra("mobile", mobile.getText().toString());
                                intent.putExtra("checksum","1");//1 for signup and 2 for login as guest

//        Toast.makeText(getApplicationContext(),response,Toast.LENGTH_SHORT).show();
                                startActivity(intent);
                                break;

                            case "fail":
                                Toast.makeText(getApplicationContext(), "OOPS. Try after Some time", Toast.LENGTH_SHORT).show();
                                break;
                        }
                    };
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                            pDialog.hide();
//                        Toast.makeText(LoginAsGuest.this,error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("name", String.valueOf(name.getText().toString()));
                params.put("item", String.valueOf(item.getText().toString()));
                params.put("otp", String.valueOf(String.valueOf(random).toString()));
                params.put("mobile",mobile.getText().toString());

//                params.put("model", String.valueOf(model.getText().toString()));
                params.put("description", String.valueOf(description.getText().toString()));
                    Log.e("name",name.getText().toString());
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
    private void smsOTPsend(final String otp){
        final String message ="mobile verification otp is"+otp;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.SmsOtpCreate,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Volleyresponse",response.toString());
                        try {
                            JSONArray jArray;
                            JSONObject Jobject = (JSONObject) new JSONTokener(response).nextValue();
                            Log.e("volleyJson", Jobject.toString());

//                            Intent intent = new Intent(LoginAsGuest.this, MobileOtpVerification.class);
//                            intent.putExtra("mobile", mobile.getText().toString());
//                            intent.putExtra("checksum","2");//1 for signup and 2 for login as guest
//
////        Toast.makeText(getApplicationContext(),response,Toast.LENGTH_SHORT).show();
//                            startActivity(intent);
                        } catch (Exception e) { }


//
//                    switch(Status) {
//                        case "success":
////                                Toast.makeText(getApplicationContext(), "Logged in", Toast.LENGTH_SHORT).show();
//                            Engineer engineer=new Engineer(name,designation,contact_no,mail_id,profile_photo,pin_no,address,dob,department,emp_id,passwordRead,username);
//                            Intent i = new Intent(getApplicationContext(), LockScreenEngineerCreate.class);
//                            SharedPrefManager.getInstance(getApplicationContext()).EngineerLogin(engineer);
//                            startActivity(i);
//
//                        case "fail":
//                            Toast.makeText(getApplicationContext(), "Username or Password is incorrect!!", Toast.LENGTH_SHORT).show();
//                    }
                    };
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                            pDialog.hide();
//                        Toast.makeText(LoginAsGuest.this,error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put("mobile", String.valueOf(mobile.getText().toString()));
                params.put("message", message);
                params.put("id", id);

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
