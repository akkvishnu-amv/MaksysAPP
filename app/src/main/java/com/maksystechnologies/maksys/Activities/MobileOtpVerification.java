package com.maksystechnologies.maksys.Activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.maksystechnologies.maksys.Models.Customer;
import com.maksystechnologies.maksys.R;
import com.maksystechnologies.maksys.Utilities.SharedPrefManager;
import com.maksystechnologies.maksys.Utilities.URLs;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class MobileOtpVerification extends AppCompatActivity implements View.OnClickListener {
    EditText otp1, otp2, otp3, otp4, otp5, otp6;
    Button verify;
    ImageButton edit;
    TextView mobile;
    TextView invalidotp;
    String idVerification="",checksum="";
    String sMob, Status = null,checksumvalue="";
    CardView verifyCard;
    TextView resendOTP;
    String custid=null,branchid=null ,branchcode =null,usrname=null,emailid=null,contactperson=null,PHnumber =null,custaddress=null ,pinnumber=null,branchname=null,passwordread=null ;

    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile_otp_verification);

        if (getIntent().getExtras() != null) {
            sMob = getIntent().getExtras().getString("mobile");
            checksumvalue = getIntent().getExtras().getString("checksum");//1 for signup and 2 for login as guest
        }
        mobile = findViewById(R.id.txt_otp_mobile);
        verify = findViewById(R.id.btn_verify_otp);
        verify.setEnabled(false);
        resendOTP = findViewById(R.id.resend_otp);

        otp1 = findViewById(R.id.et_otp_pin_1);
        otp2 = findViewById(R.id.et_otp_pin_2);
        otp3 = findViewById(R.id.et_otp_pin_3);
        otp4 = findViewById(R.id.et_otp_pin_4);
        otp5 = findViewById(R.id.et_otp_pin_5);
        otp6 = findViewById(R.id.et_otp_pin_6);

        edit = findViewById(R.id.edit_mobile);
        invalidotp = findViewById(R.id.invalid_otp);
        verifyCard = findViewById(R.id.verify_card);

        mobile.setText(sMob);


        otp1.addTextChangedListener(new EditTextWatcher(otp1));
        otp2.addTextChangedListener(new EditTextWatcher(otp2));
        otp3.addTextChangedListener(new EditTextWatcher(otp3));
        otp4.addTextChangedListener(new EditTextWatcher(otp4));
        otp5.addTextChangedListener(new EditTextWatcher(otp5));
        otp6.addTextChangedListener(new EditTextWatcher(otp6));


//        String response= sendCampaign("E21EH9RD5B2ZE2Z266EXA6ED917PITOP","FGP9DDF4AWQ4ZGR7","prod",sMob,"mobile otp is986734","bellfr");
//Toast.makeText(getApplicationContext(),response,Toast.LENGTH_SHORT).show();


        verify.setOnClickListener(this);
        edit.setOnClickListener(this);
        resendOTP.setOnClickListener(this);


//        if (checkAndRequestPermissions()) {
//            // carry on the normal flow, as the case of  permissions  granted.
//        }


    }

//    private BroadcastReceiver receiver = new BroadcastReceiver() {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            if (intent.getAction().equalsIgnoreCase("otp")) {
//                final String message = intent.getStringExtra("message");
//
//                String otp = message.substring(message.lastIndexOf("is") + 2); // change interger as the sub string chatector count
//
//                Log.d("otp number ", otp.trim());
//
//                int otpnumber = Integer.parseInt(otp.trim());
//                List<Integer> digits = new ArrayList<Integer>();
//                while (otpnumber > 0) {
//                    digits.add(otpnumber % 10);
//                    otpnumber /= 10;
//                }
//
//                otp1.setText(digits.get(5).toString());
//                otp2.setText(digits.get(4).toString());
//                otp3.setText(digits.get(3).toString());
//                otp4.setText(digits.get(2).toString());
//                otp5.setText(digits.get(1).toString());
//                otp6.setText(digits.get(0).toString());
//
////                TextView tv = (TextView) findViewById(R.id.txtview);
////                tv.setText(message);
//            }
//        }
//    };

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.edit_mobile:

                break;
            case R.id.btn_verify_otp:

                if(checksumvalue.equals("2")){
                    VerifyOTPSignUp();
                }else if (checksumvalue.equals("1"))
                {
                    VerifyOTPLoginAsGuest();
                }
//                Toast.makeText(getApplicationContext(), "Verify Clicked", Toast.LENGTH_SHORT).show();
                break;
            case R.id.resend_otp:
                setResendOTP();
//                Toast.makeText(getApplicationContext(), "Resend Codes", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }


    public class EditTextWatcher implements TextWatcher {

        View view;

        public EditTextWatcher(View view) {
            this.view = view;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {

            invalidotp.setVisibility(View.GONE);

            String text = editable.toString();
            switch (view.getId()) {
                case R.id.et_otp_pin_1:
                    if (text.length() == 1) {
                        otp2.requestFocus();
                    }
                    break;
                case R.id.et_otp_pin_2:
                    if (text.length() == 1) {
                        otp3.requestFocus();
                    } else {
                        otp1.requestFocus();
                    }
                    break;
                case R.id.et_otp_pin_3:
                    if (text.length() == 1) {
                        otp4.requestFocus();
                    } else {
                        otp2.requestFocus();
                    }
                    break;
                case R.id.et_otp_pin_4:
                    if (text.length() == 1) {
                        otp5.requestFocus();
                    } else {
                        otp3.requestFocus();
                    }
                    break;
                case R.id.et_otp_pin_5:
                    if (text.length() == 1) {
                        otp6.requestFocus();
                    } else {
                        otp4.requestFocus();
                    }
                    break;
                case R.id.et_otp_pin_6:
                    if (text.length() != 1) {
                        otp5.requestFocus();
                    }
                    break;
                default:
                    break;
            }

            if (getOTP() != null && getOTP().length() == 6) {
                verify.setEnabled(true);
                verifyCard.setCardBackgroundColor(getResources().getColor(R.color.blueMaksys));
//                verify.performClick();
            } else {
                verify.setEnabled(false);
                verifyCard.setCardBackgroundColor(getResources().getColor(R.color.grey));
            }

        }
    }

    public String getOTP() {
        String otp;
        otp = otp1.getText().toString() + otp2.getText().toString() + otp3.getText().toString() + otp4.getText().toString() + otp5.getText().toString() + otp6.getText().toString();
        return otp;
    }


    private boolean checkAndRequestPermissions() {
        int permissionSendMessage = ContextCompat.checkSelfPermission(this,
                Manifest.permission.SEND_SMS);

        int receiveSMS = ContextCompat.checkSelfPermission(this,
                Manifest.permission.RECEIVE_SMS);

        int readSMS = ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_SMS);
        List<String> listPermissionsNeeded = new ArrayList<>();

        if (receiveSMS != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.RECEIVE_MMS);
        }
        if (readSMS != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_SMS);
        }
        if (permissionSendMessage != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.SEND_SMS);
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this,
                    listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]),
                    REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }

//    @Override
//    public void onResume() {
//        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, new IntentFilter("otp"));
//        super.onResume();
//    }
//
//    @Override
//    public void onPause() {
//        super.onPause();
//        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
//    }


    //executing the async task


    public void VerifyOTPSignUp() {

        if(SharedPrefManager.getInstance(getApplicationContext()).isLoginAsGuestSetPin()){
            idVerification=SharedPrefManager.getInstance(getApplicationContext()).getKEY_Login_AS_Guest_ID();
            checksum="0";
        }else  if(SharedPrefManager.getInstance(getApplicationContext()).isSignUpSetPin()){
            idVerification=SharedPrefManager.getInstance(getApplicationContext()).getKEY_Sign_Up_ID();
            checksum="1";
        }
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.SignupotpVerification,
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

                            String Status1 = Jobject.getString("status");
                            if(Status1.equals("success")){
                                String cust_branch_id = Jobject.getString("cust_branch_id");
                                String  cust_branch_name = Jobject.getString("cust_branch_name");
                                String   cust_branch_code=Jobject.getString("cust_branch_code");
                                String cust_mail_id=Jobject.getString("cust_mail_id");
                                String contact_person=Jobject.getString("contact_person");
                                String cust_branch_address=Jobject.getString("cust_branch_address");
                                String  number=Jobject.getString("number");
                                String  pin=Jobject.getString("pin");
                                String  customer_id=Jobject.getString("customer_id");
                                String  username=Jobject.getString("username");
                                String  passwrd=Jobject.getString("password");


                                Log.e(Status1, "result");

                                Status=Status1;
                                Log.e("statusresult",Status);
                                branchid=cust_branch_id;
                                branchname=cust_branch_name;
                                branchcode=cust_branch_code;
                                emailid=cust_mail_id;
                                contactperson=contact_person;
                                custaddress=cust_branch_address;
                                PHnumber=number;
                                pinnumber=pin;
                                custid=customer_id;
                                usrname=username;
                                passwordread=passwrd;
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

                        } catch (Exception e) {
                        }
//
                        switch (Status) {
                            case "success":

                                Toast.makeText(getApplicationContext(), "Mobile Number verified.", Toast.LENGTH_SHORT).show();
                                Customer customer=new Customer(branchid,branchname,branchid,usrname,emailid,pinnumber,custid,contactperson,custaddress,passwordread,PHnumber);
                                SharedPrefManager.getInstance(getApplicationContext()).customerLogin(customer);

                                    if(SharedPrefManager.getInstance(getApplicationContext()).isCustomerLoggedIn()){
                                        Intent i = new Intent(getApplicationContext(), ChangePassword.class);
                                        startActivity(i);
                                    }else if(SharedPrefManager.getInstance(getApplicationContext()).isEngineerLoggedIn()){
                                        Intent i = new Intent(getApplicationContext(), ChangePassword.class);
                                        startActivity(i);
                                    }


//                                Engineer engineer=new Engineer(name,designation,contact_no,mail_id,profile_photo,pin_no,address,dob,department,emp_id,passwordRead,username);
//                                Intent i = new Intent(getApplicationContext(), LockScreenEngineerCreate.class);
//                                SharedPrefManager.getInstance(getApplicationContext()).EngineerLogin(engineer);
//                                startActivity(i);
                                break;
                            case "fail":
                                Toast.makeText(getApplicationContext(), "invalid otp!!", Toast.LENGTH_SHORT).show();
                                break;
                        }
                    }

                    ;
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                            pDialog.hide();
//                        Toast.makeText(MobileOtpVerification.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("otp", String.valueOf(getOTP()));
                params.put("mobile", String.valueOf(sMob));
                params.put("id", idVerification);
                params.put("checksum", checksum);
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
    public void VerifyOTPLoginAsGuest() {


        if(SharedPrefManager.getInstance(getApplicationContext()).isLoginAsGuestSetPin()){
            idVerification=SharedPrefManager.getInstance(getApplicationContext()).getKEY_Login_AS_Guest_ID();
            checksum="0";
        }else  if(SharedPrefManager.getInstance(getApplicationContext()).isCustomerLoggedIn()){
            idVerification=SharedPrefManager.getInstance(getApplicationContext()).getKEY_Customer_BranchID();
            checksum="1";
        }
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.SignupotpVerification,
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
                                if(checksum.equals("0")){
                                    showToast();
//                                    Toast.makeText(getApplicationContext(), "Thank you. Our Representive will Contact you Soon.", Toast.LENGTH_SHORT).show();
                                    SharedPrefManager.getInstance(getApplicationContext()).clearLoginAsGuestKey();
                                    startActivity(new Intent(getApplicationContext(),LoginCustomer.class));
                                }else {
                                    Toast.makeText(getApplicationContext(), "Mobile Number verified.", Toast.LENGTH_SHORT).show();

                                }
//                                Engineer engineer=new Engineer(name,designation,contact_no,mail_id,profile_photo,pin_no,address,dob,department,emp_id,passwordRead,username);
//                                Intent i = new Intent(getApplicationContext(), LockScreenEngineerCreate.class);
//                                SharedPrefManager.getInstance(getApplicationContext()).EngineerLogin(engineer);
//                                startActivity(i);
                                break;
                            case "fail":
                                Toast.makeText(getApplicationContext(), "invalid otp!!", Toast.LENGTH_SHORT).show();
                                break;
                        }
                    }

                    ;
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                            pDialog.hide();
//                        Toast.makeText(MobileOtpVerification.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("otp", String.valueOf(getOTP()));
                params.put("mobile", String.valueOf(sMob));
                params.put("id", idVerification);
                params.put("checksum", checksum);
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


    private void setResendOTP() {
        if(SharedPrefManager.getInstance(getApplicationContext()).isLoginAsGuestSetPin()){
            idVerification=SharedPrefManager.getInstance(getApplicationContext()).getKEY_Login_AS_Guest_ID();
            checksum="0";
        }else  if(SharedPrefManager.getInstance(getApplicationContext()).isSignUpSetPin()){
            idVerification=SharedPrefManager.getInstance(getApplicationContext()).getKEY_Customer_BranchID();
            checksum="1";
        }

        final int min = 111111;
        final int max = 999999;
        final int random = new Random().nextInt((max - min) + 1) + min;
        final String message = "mobile verification otp is" + random;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.SignupotpResend,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Volleyresponse", response.toString());
                        try {
                            JSONArray jArray;
                            JSONObject Jobject = (JSONObject) new JSONTokener(response).nextValue();
                            Log.e("volleyJson", Jobject.toString());

        Toast.makeText(getApplicationContext(),"OTP Resend  .",Toast.LENGTH_SHORT).show();

                        } catch (Exception e) {
                        }


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
                    }

                    ;
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                            pDialog.hide();
//                        Toast.makeText(MobileOtpVerification.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put("mobile", sMob);
                params.put("message", message);
                params.put("otp", String.valueOf(random));
                params.put("id", idVerification);
                params.put("checksum", checksum);
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
    public void showToast() {
        // Set the toast and duration
        final Toast mToastToShow;
        int toastDurationInMilliSeconds = 15000;
        mToastToShow = Toast.makeText(this, "Thank you. Our Representive will Contact you Soon.", Toast.LENGTH_LONG);

        // Set the countdown to display the toast
        CountDownTimer toastCountDown;
        toastCountDown = new CountDownTimer(toastDurationInMilliSeconds, 1000 /*Tick duration*/) {
            public void onTick(long millisUntilFinished) {
                mToastToShow.show();
            }
            public void onFinish() {
                mToastToShow.cancel();
            }
        };

        // Show the toast and starts the countdown
        mToastToShow.show();
        toastCountDown.start();
    }
}




