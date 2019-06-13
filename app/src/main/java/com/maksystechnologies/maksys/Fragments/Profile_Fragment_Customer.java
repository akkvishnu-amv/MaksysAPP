package com.maksystechnologies.maksys.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
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

public class Profile_Fragment_Customer extends Fragment {
    ScrollView customerScroll;
    ProgressBar progressBar;
    Button updatepassword;
    EditText custname,custmobile,custemail,branchname,branchaddress,password,newpassword,confirmpassword,contactPerson;
    ImageView edit;
    LinearLayout updateLayout;
    Boolean layoutVisibility=false;
    String status;
    String Status=null ,custid=null,branchid=null ,branchcode =null,usrname=null,emailid=null,contactperson=null,PHnumber =null,custaddress=null ,pinnumber=null,branchnameString=null,passwordread=null,customername=null ;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        final View rootView =inflater.inflate(R.layout.fragment_profile_customer, container, false);

        updatepassword=rootView.findViewById(R.id.btn_customer_update_password);
        custname=rootView.findViewById(R.id.et_customer_profile_customername);
        custemail=rootView.findViewById(R.id.et_customer_profile_contactemail);
        custmobile=rootView.findViewById(R.id.et_customer_profile_contactnumber);
        branchname=rootView.findViewById(R.id.et_customer_profile_branchname);
        branchaddress=rootView.findViewById(R.id.et_customer_profile_branchaddress);
        password=rootView.findViewById(R.id.et_customer_profile_password);
        newpassword=rootView.findViewById(R.id.et_customer_profile_newpassword);
        confirmpassword=rootView.findViewById(R.id.et_customer_profile_confirmpassword);
        edit=rootView.findViewById(R.id.img_customer_profile_editpassword);
        updateLayout=rootView.findViewById(R.id.edit_customer_password_layout);
        contactPerson=rootView.findViewById(R.id.et_customer_profile_contactperson);
        customerScroll=rootView.findViewById(R.id.scroll_customer_profile);
        progressBar=rootView.findViewById(R.id.progress_customer_profile);


        progressBar.setVisibility(View.GONE);
        customerScroll.setVisibility(View.INVISIBLE);

        getCustomer();


        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (layoutVisibility) {
                    updateLayout.setVisibility(View.GONE);
                    layoutVisibility=false;
                }else {
                    updateLayout.setVisibility(View.VISIBLE);
                    layoutVisibility=true;
                }
            }
        });


        updatepassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(password.getText().toString().equals(SharedPrefManager.getInstance(getActivity()).getKEY_Customer_Password())) {
                    if (newpassword.getText().toString().equals(confirmpassword.getText().toString())) {

                        change();
                    }
                    else {
                        Toast.makeText(getActivity(),"Check Password and Confirm Password.!",Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(getActivity(),"Check Current Password.!",Toast.LENGTH_SHORT).show();

                }

            }
        });
    return rootView;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Profile");
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
                                Toast.makeText(getActivity(), "Password Has Been Updated.!", Toast.LENGTH_LONG).show();
                                SharedPrefManager.getInstance(getActivity()).setKEY_Customer_Password(confirmpassword.getText().toString());
                                password.setText(confirmpassword.getText().toString());
                                confirmpassword.setText("");
                                newpassword.setText("");
                                password.clearFocus();
                                confirmpassword.clearFocus();
                                newpassword.clearFocus();

                                break;
                            case "fail":
                                Toast.makeText(getActivity(), "Something Went Wrong!", Toast.LENGTH_LONG).show();


                                break;
                        }

                    };
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                            pDialog.hide();

                        Toast.makeText(getActivity(),error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<String, String>();
                params.put("branchid", SharedPrefManager.getInstance(getActivity()).getKEY_Customer_BranchID());
                params.put("password",confirmpassword.getText().toString() );
                params.put("checksum","0");


//                    Log.e("country code send",countrycode);
                return params;
            }

        };

        //Adding the string request to the queue
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


    }
    public void getCustomer() {
        progressBar.setVisibility(View.VISIBLE);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.CustomeDataRead,
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
                            String  cstname=Jobject.getString("customer_name");


                            Log.e(Status1, "result");

                            Status=Status1;
                            branchid=cust_branch_id;
                            branchnameString=cust_branch_name;
                            branchcode=cust_branch_code;
                            emailid=cust_mail_id;
                            contactperson=contact_person;
                            custaddress=cust_branch_address;
                            PHnumber=number;
                            pinnumber=pin;
                            custid=customer_id;
                            usrname=username;
                            passwordread=passwrd;
                            customername=cstname;

//                                }
//                                        c1[i]=cid;
//                                        c3[i]=sname;

//                                        statecodename=c3;
//                                        statecodeid=c1;
//

                        } catch (Exception e) { }

                        customerScroll.setVisibility(View.VISIBLE);

                        custname.setText(customername);
                        branchname.setText(branchnameString);
                        branchaddress.setText(custaddress);
                        custmobile.setText(PHnumber);
                        custemail.setText(emailid);
                        password.setText(passwordread);
                        contactPerson.setText(contactperson);
                        progressBar.setVisibility(View.GONE);


//

                    };
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                            pDialog.hide();

                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(getActivity(),error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("custid", SharedPrefManager.getInstance(getActivity()).getKEY_Customer_BranchID());
//                    Log.e("country code send",countrycode);
                return params;
            }

        };

        //Adding the string request to the queue
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

    }


}
