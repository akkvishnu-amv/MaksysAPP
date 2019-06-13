package com.maksystechnologies.maksys.Activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;

public class LockScreenEngineer extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "MYTAG";
    EditText otp1, otp2, otp3, otp4, otp5, otp6;
    private Button num1, num2, num3, num4, num5, num6, num7, num8, num9, num0,confirm;
    private ImageButton backButton;
    private ImageView dot1, dot2, dot3, dot4;
    private TextView nameTextView,fogotpin;
    private CircleImageView profilePicture;
    private Handler handler;
    private String actualPassword,password,Status;
    //Number of digits entered by the user
    private int noOfDigits;
    String passwordTry;
    // Store the digits entered by the user
    SplashScreen mainActivity;
    private ArrayList<Integer> passwordTryList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock_screen_engineer);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
mainActivity=new SplashScreen();
//        if(!mainActivity.isNetworkConnecteted()){
//            Toast.makeText(getApplicationContext(),"Check Your Internet",Toast.LENGTH_LONG).show();
//        }

        noOfDigits = 0;
        actualPassword = getResources().getString(R.string.app_name);
        handler = new Handler();
        initialiseWidgets();
        fogotpin=findViewById(R.id.forgot_pin_engineer);
        fogotpin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showForgotpassword();
            }
        });


        if(!isConnected()){
            Toast.makeText(getApplicationContext(),"No Internet Connection!",Toast.LENGTH_SHORT).show();
        }

    }

    private void initialiseWidgets() {
        //Numpad widgets

        otp1 = findViewById(R.id.et_pin_engineer_1);
        otp2 = findViewById(R.id.et_pin_engineer_2);
        otp3 = findViewById(R.id.et_pin_engineer_3);
        otp4 = findViewById(R.id.et_pin_engineer_4);

//        confirm=findViewById(R.id.btn_confirm_confirm_engineer);
        // Numpad widgets OnClickListeners



        otp1.addTextChangedListener(new LockScreenEngineer.EditTextWatcher(otp1));
        otp2.addTextChangedListener(new LockScreenEngineer.EditTextWatcher(otp2));
        otp3.addTextChangedListener(new LockScreenEngineer.EditTextWatcher(otp3));
        otp4.addTextChangedListener(new LockScreenEngineer.EditTextWatcher(otp4));

//        backButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.d("MYTAG", "Backbuttonpressed: " + noOfDigits);
//                if (noOfDigits <= 4 && noOfDigits > 0) {
//                    noOfDigits -= 1;
//                    setDotsIndicator(noOfDigits);
//                }
//            }
//        });

        //Dots widgets(imageViews)
//        dot1 = findViewById(R.id.dot_one);
//        dot2 = findViewById(R.id.dot_two);
//        dot3 = findViewById(R.id.dot_three);
//        dot4 = findViewById(R.id.dot_four);
//        //Header
//        profilePicture = findViewById(R.id.circular_image_view);
//        nameTextView = findViewById(R.id.name_textview);
    }

    @Override
    public void onClick(View v) {

        if (noOfDigits >= 0 && noOfDigits <= 3) {
            noOfDigits += 1;
            switch (v.getId()) {
                case R.id.btnNumpad1:
                    passwordTryList.add(1);
                    setDotsIndicator(noOfDigits);
                    attemptUnlock(noOfDigits);
                    break;
                case R.id.btnNumpad2:
                    passwordTryList.add(2);
                    setDotsIndicator(noOfDigits);
                    attemptUnlock(noOfDigits);
                    break;
                case R.id.btnNumpad3:
                    passwordTryList.add(3);
                    setDotsIndicator(noOfDigits);
                    attemptUnlock(noOfDigits);
                    break;
                case R.id.btnNumpad4:
                    passwordTryList.add(4);
                    setDotsIndicator(noOfDigits);
                    attemptUnlock(noOfDigits);
                    break;
                case R.id.btnNumpad5:
                    passwordTryList.add(5);
                    setDotsIndicator(noOfDigits);
                    attemptUnlock(noOfDigits);
                    break;
                case R.id.btnNumpad6:
                    passwordTryList.add(6);
                    setDotsIndicator(noOfDigits);
                    attemptUnlock(noOfDigits);
                    break;
                case R.id.btnNumpad7:
                    passwordTryList.add(7);
                    setDotsIndicator(noOfDigits);
                    attemptUnlock(noOfDigits);
                    break;
                case R.id.btnNumpad8:
                    passwordTryList.add(8);
                    setDotsIndicator(noOfDigits);
                    attemptUnlock(noOfDigits);
                    break;
                case R.id.btnNumpad9:
                    passwordTryList.add(9);
                    setDotsIndicator(noOfDigits);
                    attemptUnlock(noOfDigits);
                    break;
                case R.id.btnNumpad0:
                    passwordTryList.add(0);
                    setDotsIndicator(noOfDigits);
                    attemptUnlock(noOfDigits);
                    break;
            }
        }
    }
    private void clearOtpIndicator() {

        otp1.setText("");
        otp2.setText("");
        otp3.setText("");
        otp4.setText("");
        otp1.requestFocus();


    }

    // Sets the dots indicator from hollow to filled based on number of digits entered by user
    private void setDotsIndicator(int num) {
//        clearDotsIndicator();
        if (num == 1) {
            dot1.setImageResource(R.drawable.filled_circle_default);
        } else if (num == 2) {
            dot1.setImageResource(R.drawable.filled_circle_default);
            dot2.setImageResource(R.drawable.filled_circle_default);
        } else if (num == 3) {
            dot1.setImageResource(R.drawable.filled_circle_default);
            dot2.setImageResource(R.drawable.filled_circle_default);
            dot3.setImageResource(R.drawable.filled_circle_default);
        } else if (num >= 4) {
            dot1.setImageResource(R.drawable.filled_circle_default);
            dot2.setImageResource(R.drawable.filled_circle_default);
            dot3.setImageResource(R.drawable.filled_circle_default);
            dot4.setImageResource(R.drawable.filled_circle_default);
        }
    }

    // Checks if the numOfDigits == 4, if so then compare the digits in the ArrayList to the actual password
    private void attemptUnlock(int num) {
        if (num == 4) {
            noOfDigits = 0;

            // Convert chars in ArrayList to a string
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < passwordTryList.size(); i++) {
                sb.append(passwordTryList.get(i));
            }
            passwordTry = sb.toString();
            Log.d(TAG, "attemptUnlock: " + passwordTry);
            Log.d(TAG, "attemptUnlock: " + passwordTry.getClass());
            // Clears the arrayList storing the user's attempt
            passwordTryList.clear();

//            if (passwordTry.equals(actualPassword)) {
//                Toast.makeText(this, "Correct PIN entered", Toast.LENGTH_SHORT).show();
//                showSucceedVibration();
//            } else {
//                showErrorVibration();
//            }
        }
    }

    // Sets the dots to greeb and sets succeed animation (vertical vibration)
    // At end of animation, resets the dots to hollow
    private void showSucceedVibration() {
        Animation succeedAnimation = AnimationUtils.loadAnimation(this, R.anim.vertical_vibrate_animation);

        dot1.setImageResource(R.drawable.filled_circle_succeed);
        dot2.setImageResource(R.drawable.filled_circle_succeed);
        dot3.setImageResource(R.drawable.filled_circle_succeed);
        dot4.setImageResource(R.drawable.filled_circle_succeed);

        dot1.setAnimation(succeedAnimation);
        dot2.setAnimation(succeedAnimation);
        dot3.setAnimation(succeedAnimation);
        dot4.setAnimation(succeedAnimation);

        final ImageView[] dotsArray = new ImageView[4];
        dotsArray[0] = dot1;
        dotsArray[1] = dot2;
        dotsArray[2] = dot3;
        dotsArray[3] = dot4;

        for (int i = 0; i < 4; i++) {
            dotsArray[i].startAnimation(succeedAnimation);
        }

        succeedAnimation.setAnimationListener(new Animation.AnimationListener()
        {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        clearOtpIndicator();
                    }
                }, 150);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });

    }

    // Sets the dots to red and sets error animation (horizontal vibration)
    // At end of animation, resets the dots to hollow
    private void showErrorVibration() {
        Animation errorAnimation = AnimationUtils.loadAnimation(this, R.anim.horizontal_vibrate_animation);


        otp1.setAnimation(errorAnimation);
        otp2.setAnimation(errorAnimation);
        otp3.setAnimation(errorAnimation);
        otp4.setAnimation(errorAnimation);


        otp1.setAnimation(errorAnimation);
        otp2.setAnimation(errorAnimation);
        otp3.setAnimation(errorAnimation);
        otp4.setAnimation(errorAnimation);


        //At end of error animation, reset the dots
        errorAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        clearOtpIndicator();
                    }
                }, 150);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
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

//            invalidotp.setVisibility(View.GONE);

            String text = editable.toString();
            switch (view.getId()) {
                case R.id.et_pin_engineer_1:
                    if (text.length() == 1) {
                        otp2.requestFocus();
                    }
                    break;
                case R.id.et_pin_engineer_2:
                    if (text.length() == 1) {
                        otp3.requestFocus();
                    } else {
                        otp1.requestFocus();
                    }
                    break;
                case R.id.et_pin_engineer_3:
                    if (text.length() == 1) {
                        otp4.requestFocus();
                    } else {
                        otp2.requestFocus();
                    }
                    break;
                case R.id.et_pin_engineer_4:
                    if (text.length() == 1) {
                        otp4.requestFocus();

                        if(SharedPrefManager.getInstance(getApplicationContext()).getEngineerPin().equals(getOTP())){
                            loginHistory();
                            Intent intent=new Intent(getApplicationContext(),MainEngineerActivity.class);
                            startActivity(intent);
                        }
                        else {
                            Toast.makeText(getApplicationContext(),"PINs Does Not Match!",Toast.LENGTH_SHORT).show();
                            showErrorVibration();
                        }

                    }
                    break;

                default:
                    break;
            }

//            if (getOTP() != null && getOTP().length() == 6) {
//                confirm.setEnabled(true);
////                verify.setEnabled(true);
//////                verifyCard.setCardBackgroundColor(getResources().getColor(R.color.colorPrimary));
////                verify.performClick();
//            } else {
//                confirm.setEnabled(false);
////                verify.setEnabled(false);
////                verifyCard.setCardBackgroundColor(getResources().getColor(R.color.grey));
//            }

        }
    }

    public String getOTP() {
        String otp;
        otp = otp1.getText().toString() + otp2.getText().toString() + otp3.getText().toString() + otp4.getText().toString();
        return otp;
    }
    public void loginHistory() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.LoginHistory,
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
                            if(Status1.equals("success")){

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
                    };
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                            pDialog.hide();
//                        Toast.makeText(LockScreenEngineer.this,error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("id", SharedPrefManager.getInstance(getApplicationContext()).getKEY_Customer_BranchID());
                params.put("checksum", "4");
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
    private void showForgotpassword(){

        setResendOTP();// otp send to registered customer number fetch from shared preference
        Button cancel,changepin;
        final EditText otp,newpin;
        ViewGroup viewGroup = findViewById(android.R.id.content);

        //then we will inflate the custom alert dialog xml that we created
        final View dialogView = LayoutInflater.from(this).inflate(R.layout.fogotpinengineer, viewGroup, false);

        cancel=dialogView.findViewById(R.id.btn_forgotpassword_cancel_eng);
        changepin=dialogView.findViewById(R.id.btn_forgotpassword_change_eng);
        otp=dialogView.findViewById(R.id.et_forgotpassword_otp_eng);
        newpin=dialogView.findViewById(R.id.et_forgotpassword_newpinnumber_eng);

        //Now we need an AlertDialog.Builder object
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        //setting the view of the builder to our custom view that we already inflated
        builder.setView(dialogView);
        final AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);

        changepin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatePIN(otp.getText().toString(),newpin.getText().toString(),alertDialog);
            }
        });


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AlertDialog.Builder builder=new AlertDialog.Builder(LockScreenEngineer.this);// use you activity name
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
    public void updatePIN(final String Otp, final String Pin, final AlertDialog dialog) {


        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.changeEnginPIn,
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

                                Toast.makeText(getApplicationContext(), "Pin Number Changed.!", Toast.LENGTH_SHORT).show();

                                dialog.cancel();
                                SharedPrefManager.getInstance(getApplicationContext()).customerPinSet(Pin);
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
//                        Toast.makeText(LockScreenEngineer.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("otp", Otp);
                params.put("pin",Pin );
                params.put("id", SharedPrefManager.getInstance(getApplicationContext()).getKEY_Engineer_ID());

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


        final int min = 111111;
        final int max = 999999;
        final int random = new Random().nextInt((max - min) + 1) + min;

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.FORGOTotpEngineer,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Volleyresponse", response.toString());
                        try {
                            JSONArray jArray;
                            JSONObject Jobject = (JSONObject) new JSONTokener(response).nextValue();
                            Log.e("volleyJson", Jobject.toString());

                            Toast.makeText(getApplicationContext(),"OTP Send.",Toast.LENGTH_SHORT).show();

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
//                        Toast.makeText(LockScreenEngineer.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put("id", SharedPrefManager.getInstance(getApplicationContext()).getKEY_Customer_BranchID());

                params.put("otp", String.valueOf(random));
                params.put("mobile",  SharedPrefManager.getInstance(getApplicationContext()).getKEY_Customer_Mobile());

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
    public boolean isConnected() {
        boolean connected = false;
        try {
            ConnectivityManager cm = (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo nInfo = cm.getActiveNetworkInfo();
            connected = nInfo != null && nInfo.isAvailable() && nInfo.isConnected();
            return connected;
        } catch (Exception e) {
            Log.e("Connectivity Exception", e.getMessage());
        }
        return connected;
    }

}
