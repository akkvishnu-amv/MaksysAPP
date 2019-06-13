package com.maksystechnologies.maksys.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
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

public class ChangePassword extends AppCompatActivity {
    EditText newpassword,cconfirmpassword;
    Button changepassword;
    String status;
    SplashScreen mainActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        newpassword=findViewById(R.id.et_changepassword_newpassword);
        cconfirmpassword=findViewById(R.id.et_changepassword_confirmpassword);
        changepassword=findViewById(R.id.btn_changepassword_change);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        mainActivity=new SplashScreen();

//        if(!mainActivity.isNetworkConnecteted()){
//            Toast.makeText(getApplicationContext(),"Check Your Internet",Toast.LENGTH_LONG).show();
//        }
        changepassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(newpassword.getText().toString().equals(cconfirmpassword.getText().toString())){
                    change();
                }
            }
        });
    }
    private void change(){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.CustomeChangePassword,
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

                                if(SharedPrefManager.getInstance(getApplicationContext()).isCustomerLoggedIn()){
                                    SharedPrefManager.getInstance(getApplicationContext()).setKEY_Customer_Password(cconfirmpassword.getText().toString());

                                    Intent i = new Intent(getApplicationContext(), LockScreenCustomerCreate.class);
                                    startActivity(i);

                                }else if(SharedPrefManager.getInstance(getApplicationContext()).isEngineerLoggedIn()){
                                    SharedPrefManager.getInstance(getApplicationContext()).setKEY_Engineer_Password(cconfirmpassword.getText().toString());

                                    Intent i = new Intent(getApplicationContext(), LockScreenEngineerCreate .class);
                                    startActivity(i);
                                }

                                break;
                            case "fail":
                                Toast.makeText(getApplicationContext(), "Something Went Wrong!", Toast.LENGTH_LONG).show();


                                break;
                        }

                    };
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                            pDialog.hide();

                        Toast.makeText(getApplicationContext(),error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                String id="",checksum="";

                if(SharedPrefManager.getInstance(getApplicationContext()).isCustomerLoggedIn()){
                   id= SharedPrefManager.getInstance(getApplicationContext()).getKEY_Customer_BranchID();
                    checksum="0";
                }else if(SharedPrefManager.getInstance(getApplicationContext()).isEngineerLoggedIn()){
                   id= SharedPrefManager.getInstance(getApplicationContext()).getKEY_Engineer_ID();
                    checksum="1";

                }

                Map<String, String> params = new HashMap<String, String>();
                params.put("branchid", id);
                params.put("checksum", checksum);
                params.put("password",cconfirmpassword.getText().toString() );


//                    Log.e("country code send",countrycode);
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
