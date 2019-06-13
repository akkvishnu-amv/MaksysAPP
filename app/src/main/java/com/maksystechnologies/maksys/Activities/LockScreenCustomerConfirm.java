package com.maksystechnologies.maksys.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
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

import de.hdodenhof.circleimageview.CircleImageView;

public class LockScreenCustomerConfirm extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "MYTAG";
    EditText otp1, otp2, otp3, otp4, otp5, otp6;
    private Button num1, num2, num3, num4, num5, num6, num7, num8, num9, num0,confirm;
    private ImageButton backButton;
    private ImageView dot1, dot2, dot3, dot4;
    private TextView nameTextView;
    private CircleImageView profilePicture;
    private Handler handler;
    private String actualPassword,password,status;
    //Number of digits entered by the user
    private int noOfDigits;
    String passwordTry;
    // Store the digits entered by the user
    private ArrayList<Integer> passwordTryList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock_screen_customer_confirm);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        if (getIntent().getExtras()!=null){
            password = getIntent().getExtras().getString("password");
        }
        noOfDigits = 0;
        actualPassword = getResources().getString(R.string.app_name);
        handler = new Handler();
        initialiseWidgets();

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(password.equals(getOTP())){
                    change();

                }
                else {
                    Toast.makeText(getApplicationContext(),"PINs Does Not Match!",Toast.LENGTH_SHORT).show();
//                    showErrorVibration();
                    clearOtpIndicator();
                }

            }
        });
    }

    private void initialiseWidgets() {
        //Numpad widgets


        otp1 = findViewById(R.id.et_pin_confirm_1);
        otp2 = findViewById(R.id.et_pin_confirm_2);
        otp3 = findViewById(R.id.et_pin_confirm_3);
        otp4 = findViewById(R.id.et_pin_confirm_4);
        otp5 = findViewById(R.id.et_pin_confirm_5);
        otp6 = findViewById(R.id.et_pin_confirm_6);
        confirm=findViewById(R.id.btn_confirm_confirm);
        // Numpad widgets OnClickListeners



        otp1.addTextChangedListener(new LockScreenCustomerConfirm.EditTextWatcher(otp1));
        otp2.addTextChangedListener(new LockScreenCustomerConfirm.EditTextWatcher(otp2));
        otp3.addTextChangedListener(new LockScreenCustomerConfirm.EditTextWatcher(otp3));
        otp4.addTextChangedListener(new LockScreenCustomerConfirm.EditTextWatcher(otp4));
        otp5.addTextChangedListener(new LockScreenCustomerConfirm.EditTextWatcher(otp5));
        otp6.addTextChangedListener(new LockScreenCustomerConfirm.EditTextWatcher(otp6));
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
        otp5.setText("");
        otp6.setText("");

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
        otp5.setAnimation(errorAnimation);
        otp6.setAnimation(errorAnimation);


        otp1.setAnimation(errorAnimation);
        otp2.setAnimation(errorAnimation);
        otp3.setAnimation(errorAnimation);
        otp4.setAnimation(errorAnimation);
        otp5.setAnimation(errorAnimation);
        otp6.setAnimation(errorAnimation);
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
                case R.id.et_pin_confirm_1:
                    if (text.length() == 1) {
                        otp2.requestFocus();
                    }
                    break;
                case R.id.et_pin_confirm_2:
                    if (text.length() == 1) {
                        otp3.requestFocus();
                    } else {
                        otp1.requestFocus();
                    }
                    break;
                case R.id.et_pin_confirm_3:
                    if (text.length() == 1) {
                        otp4.requestFocus();
                    } else {
                        otp2.requestFocus();
                    }
                    break;
                case R.id.et_pin_confirm_4:
                    if (text.length() == 1) {
                        otp5.requestFocus();
                    } else {
                        otp3.requestFocus();
                    }
                    break;
                case R.id.et_pin_confirm_5:
                    if (text.length() == 1) {
                        otp6.requestFocus();
                    } else {
                        otp4.requestFocus();
                    }
                    break;
                case R.id.et_pin_confirm_6:
                    if (text.length() != 1) {
                        otp5.requestFocus();
                    }
                    break;
                default:
                    break;
            }

            if (getOTP() != null && getOTP().length() == 6) {
                confirm.setEnabled(true);
//                verify.setEnabled(true);
////                verifyCard.setCardBackgroundColor(getResources().getColor(R.color.colorPrimary));
//                verify.performClick();
            } else {
                confirm.setEnabled(false);
//                verify.setEnabled(false);
//                verifyCard.setCardBackgroundColor(getResources().getColor(R.color.grey));
            }

        }
    }

    public String getOTP() {
        String otp;
        otp = otp1.getText().toString() + otp2.getText().toString() + otp3.getText().toString() + otp4.getText().toString() + otp5.getText().toString() + otp6.getText().toString();
        return otp;
    }
    private void change(){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.CreatePinCustomer,
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


//                                Fragment fragment=new CustomerHomeFragment();
//                                if (fragment != null) {
//                                    FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
////                                ft.setCustomAnimations(R.anim.exittoleft, R.anim.enterfromright);
//                                    ft.replace(R.id.content_frame, fragment);
//                                    ft.commit();
//                                }
//
//                                DrawerLayout drawer = getActivity().findViewById(R.id.drawer_layout);
//                                drawer.closeDrawer(GravityCompat.START);

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

                        switch (status){
                            case "success":
                                Toast.makeText(getApplicationContext(), "Pin Has Been Set.!", Toast.LENGTH_LONG).show();
                                SharedPrefManager.getInstance(getApplicationContext()).customerPinSet(getOTP());
                                Intent intent=new Intent(getApplicationContext(),MainCustomerActivity.class);


                                startActivity(intent);

                                break;
                            case "fail":
                                Toast.makeText(getApplicationContext(), "Current Pin Does Not Match!", Toast.LENGTH_LONG).show();

                                break;
                        }

                    };
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                            pDialog.hide();

//                        Toast.makeText(getApplicationContext(),error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<String, String>();
                params.put("branchid", SharedPrefManager.getInstance(getApplicationContext()).getKEY_Customer_BranchID());
                params.put("newpin", String.valueOf(getOTP()));


                return params;
            }

        };

        //Adding the string request to the queue
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


    }
}
