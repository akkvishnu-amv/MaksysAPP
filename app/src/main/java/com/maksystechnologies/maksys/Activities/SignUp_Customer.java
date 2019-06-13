package com.maksystechnologies.maksys.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
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

public class SignUp_Customer extends AppCompatActivity {
EditText username,mobilenum,password,confirmpassword;
Button BtnSignup;
String status,id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_sign_up__customer);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        BtnSignup=findViewById(R.id.btn_signup_customer_signup);
        username=findViewById(R.id.et_signup_customer_username);
        mobilenum=findViewById(R.id.et_signup_customer_mobile);
        password=findViewById(R.id.et_signup_customer_password);
//        confirmpassword=findViewById(R.id.et_signup_customer_confirmpassword);

    }
    public void proceed(View view) {

        final String mobile = mobilenum.getText().toString();

        if(TextUtils.isEmpty(username.getText().toString())){  username.setError("Enter username");}
        if(TextUtils.isEmpty(password.getText().toString())){  password.setError("Enter Password");}
//        if(TextUtils.isEmpty(confirmpassword.getText().toString())){  confirmpassword.setError("Enter Confirm Password");}


        if (TextUtils.isEmpty(mobile)){
//            mobile_number.setError("Enter Mobile Number");
            mobilenum.setError("Enter Mobile Number");
            return;
        }
        if (!TextUtils.isDigitsOnly(mobile)){
            mobilenum.setError("Invalid Mobile Number");
            return;
        }
        if (mobile.length()!=10 || mobile.contains(" ") || mobile.contains("+")){
            mobilenum.setError("Please Enter 10 digit Mobile Number");
            return;
        } else {

           signupCustomer();



//        mobilenum.setText("");

        }
    }
    public void SignUp(View view) {
        startActivity(new Intent(this,MobileOtpVerification.class));

    }

    private void smsOTPsend(final String otp){
        final String message ="mobile verification otp is "+otp;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.SmsOtpCreate,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Volleyresponse",response.toString());
                        try {
                            JSONArray jArray;
                            JSONObject Jobject = (JSONObject) new JSONTokener(response).nextValue();
                            Log.e("volleyJson", Jobject.toString());

                            Intent intent = new Intent(SignUp_Customer.this, MobileOtpVerification.class);
                            intent.putExtra("mobile", mobilenum.getText().toString());
                            intent.putExtra("checksum", "2");
//        Toast.makeText(getApplicationContext(),response,Toast.LENGTH_SHORT).show();
                            startActivity(intent);
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
//                        Toast.makeText(SignUp_Customer.this,error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put("mobile", String.valueOf(mobilenum.getText().toString()));
                params.put("message", message);
                params.put("id", SharedPrefManager.getInstance(getApplicationContext()).getKEY_Customer_BranchID());
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

private void signupCustomer(){
        String Status1="";
    final int min = 111111;
    final int max = 999999;
    final int random = new Random().nextInt((max - min) + 1) + min;
    StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.SignupCustomer,
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
//                        Toast.makeText(getApplicationContext(),status,Toast.LENGTH_SHORT).show();
                        Log.d("status result",  status);
                        String id1="";
                        if(Jobject.getString("cust_branch_id")!=null){
                           id1 = Jobject.getString("cust_branch_id");
                        }

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

//                            if(status.equals("success")){
//                                Intent intent = new Intent(SignUp_Customer.this, MobileOtpVerification.class);
//                                intent.putExtra("mobile", mobilenum.getText().toString());
//                                intent.putExtra("checksum","2");
//                                SharedPrefManager.getInstance(getApplicationContext()).setKEY_Customer_Branch_ID(id);
//                                smsOTPsend(String.valueOf(random));
//                            }else {
//                                Toast.makeText(getApplicationContext(), "Username or Password Does Not Match!! Try again", Toast.LENGTH_LONG).show();
//
//                                }
//
                    switch(status) {
                        case "success":
//                                Toast.makeText(getApplicationContext(), "Logged in", Toast.LENGTH_SHORT).show();
//                            Engineer engineer=new Engineer(name,designation,contact_no,mail_id,profile_photo,pin_no,address,dob,department,emp_id,passwordRead,username);
                            Intent intent = new Intent(SignUp_Customer.this, MobileOtpVerification.class);
                            intent.putExtra("mobile", mobilenum.getText().toString());
                            intent.putExtra("checksum","2");
                            SharedPrefManager.getInstance(getApplicationContext()).setKEY_Customer_Branch_ID(id);
                           smsOTPsend(String.valueOf(random));
                           startActivity(intent);
                           break;

                        case "fail":
                            Toast.makeText(getApplicationContext(), "Username or Password Does Not Match!! Try again", Toast.LENGTH_LONG).show();
                            break;
                    }
                };
            },

            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
//                            pDialog.hide();
//                    Toast.makeText(SignUp_Customer.this,error.toString(), Toast.LENGTH_LONG).show();
                }
            }) {
        @Override
        protected Map<String, String> getParams() {
            Map<String, String> params = new HashMap<String, String>();
            params.put("username", String.valueOf(username.getText().toString()));
            params.put("password", String.valueOf(password.getText().toString()));
            params.put("mobile", String.valueOf(mobilenum.getText().toString()));
            params.put("otp", String.valueOf(String.valueOf(random).toString()));
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
