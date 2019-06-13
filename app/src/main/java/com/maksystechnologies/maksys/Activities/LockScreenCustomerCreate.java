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

import com.maksystechnologies.maksys.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class LockScreenCustomerCreate extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MYTAG";
    private Button num1, num2, num3, num4, num5, num6, num7, num8, num9, num0,next;
    private ImageButton backButton;
    private ImageView dot1, dot2, dot3, dot4;
    private TextView nameTextView;
    private CircleImageView profilePicture;
    private Handler handler;

    // Actual password
    private String actualPassword;
    String passwordTry,pwd,Status;
    //Number of digits entered by the user
    private int noOfDigits;
    // Store the digits entered by the user
    private ArrayList<Integer> passwordTryList = new ArrayList<>();
    EditText otp1, otp2, otp3, otp4, otp5, otp6;
    Button verify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock_screen_customer_create);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();


        initialiseWidgets();

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),LockScreenCustomerConfirm.class);
                intent.putExtra("password",getOTP());
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
            }
        });

    }



    private void initialiseWidgets() {
        //Numpad widgets


        otp1 = findViewById(R.id.et_pin_create_1);
        otp2 = findViewById(R.id.et_pin_create_2);
        otp3 = findViewById(R.id.et_pin_create_3);
        otp4 = findViewById(R.id.et_pin_create_4);
        otp5 = findViewById(R.id.et_pin_create_5);
        otp6 = findViewById(R.id.et_pin_create_6);
        next=findViewById(R.id.btn_confirm_create);
        next.setEnabled(false);
        // Numpad widgets OnClickListeners



        otp1.addTextChangedListener(new LockScreenCustomerCreate.EditTextWatcher(otp1));
        otp2.addTextChangedListener(new LockScreenCustomerCreate.EditTextWatcher(otp2));
        otp3.addTextChangedListener(new LockScreenCustomerCreate.EditTextWatcher(otp3));
        otp4.addTextChangedListener(new LockScreenCustomerCreate.EditTextWatcher(otp4));
        otp5.addTextChangedListener(new LockScreenCustomerCreate.EditTextWatcher(otp5));
        otp6.addTextChangedListener(new LockScreenCustomerCreate.EditTextWatcher(otp6));
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

    // Back button's listener is in initialiseWidgets(), not here
    @Override
    public void onClick(View v) {
        // Check if the number of digits already entered by the user is 0 to 3;
        // If so, register the user's click, else do nothing
        // Calls attemptUnlock() and passes noOfDigits after every listener trigger


    }

    // Clears ALL the dots indicators to initial state - hollow circle
    private void clearDotsIndicator() {
        dot1.setImageResource(R.drawable.hollow_circle);
        dot2.setImageResource(R.drawable.hollow_circle);
        dot3.setImageResource(R.drawable.hollow_circle);
        dot4.setImageResource(R.drawable.hollow_circle);
    }

    // Sets the dots indicator from hollow to filled based on number of digits entered by user
    private void setDotsIndicator(int num) {
        clearDotsIndicator();
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

            if (passwordTry.equals(actualPassword)) {
                Toast.makeText(this, "Correct PIN entered", Toast.LENGTH_SHORT).show();
                showSucceedVibration();
            } else {
                showErrorVibration();
            }
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
                        clearDotsIndicator();
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

        dot1.setImageResource(R.drawable.filled_circle_error);
        dot2.setImageResource(R.drawable.filled_circle_error);
        dot3.setImageResource(R.drawable.filled_circle_error);
        dot4.setImageResource(R.drawable.filled_circle_error);

        dot1.setAnimation(errorAnimation);
        dot2.setAnimation(errorAnimation);
        dot3.setAnimation(errorAnimation);
        dot4.setAnimation(errorAnimation);

        dot1.startAnimation(errorAnimation);
        dot2.startAnimation(errorAnimation);
        dot3.startAnimation(errorAnimation);
        dot4.startAnimation(errorAnimation);

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
                        clearDotsIndicator();
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
                case R.id.et_pin_create_1:
                    if (text.length() == 1) {
                        otp2.requestFocus();
                    }
                    break;
                case R.id.et_pin_create_2:
                    if (text.length() == 1) {
                        otp3.requestFocus();
                    } else {
                        otp1.requestFocus();
                    }
                    break;
                case R.id.et_pin_create_3:
                    if (text.length() == 1) {
                        otp4.requestFocus();
                    } else {
                        otp2.requestFocus();
                    }
                    break;
                case R.id.et_pin_create_4:
                    if (text.length() == 1) {
                        otp5.requestFocus();
                    } else {
                        otp3.requestFocus();
                    }
                    break;
                case R.id.et_pin_create_5:
                    if (text.length() == 1) {
                        otp6.requestFocus();
                    } else {
                        otp4.requestFocus();
                    }
                    break;
                case R.id.et_pin_create_6:
                    if (text.length() != 1) {
                        otp5.requestFocus();
                    }
                    break;
                default:
                    break;
            }

            if (getOTP() != null && getOTP().length() == 6) {
//                verify.setEnabled(true);
//                verifyCard.setCardBackgroundColor(getResources().getColor(R.color.colorPrimary));
//                verify.performClick();
                next.setEnabled(true);
            } else {
//                verify.setEnabled(false);
                next.setEnabled(false);
//                verifyCard.setCardBackgroundColor(getResources().getColor(R.color.grey));
            }

        }
    }

    public String getOTP() {
        String otp;
        otp = otp1.getText().toString() + otp2.getText().toString() + otp3.getText().toString() + otp4.getText().toString() + otp5.getText().toString() + otp6.getText().toString();
        return otp;
    }


}
