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

public class ChangePin_Customer_Fragment extends Fragment {

    EditText currentpin,newpin,confirmpin;
    Button btnConfirm;
    String status;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_change_pin_customer, container, false);
        btnConfirm=rootView.findViewById(R.id.btn_customer_confirm_pin);
        currentpin=rootView.findViewById(R.id.et_customer_old_pin);
        newpin=rootView.findViewById(R.id.et_customer_new_pin);
        confirmpin=rootView.findViewById(R.id.et_customer_confirm_pin);

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(newpin.getText().toString().equals(confirmpin.getText().toString())){
                    change();
                    SharedPrefManager.getInstance(getActivity()).customerPinSet(confirmpin.getText().toString());


                }
                else {
                    Toast.makeText(getActivity().getApplicationContext(),"New pin and confirm pin does not match!",Toast.LENGTH_SHORT).show();
                }

            }
        });


        return rootView;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().setTitle("Change Pin");

    }
    private void change(){

            StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.ChangePinCustomer,
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
                                    Toast.makeText(getActivity(), "Pin Has Been Changed.!", Toast.LENGTH_LONG).show();
                                    SharedPrefManager.getInstance(getActivity()).customerPinSet(confirmpin.getText().toString());
                                    currentpin.setText("");
                                    newpin.setText("");
                                    confirmpin.setText("");
                                    break;
                                case "fail":
                                    Toast.makeText(getActivity(), "Current Pin Does Not Match!", Toast.LENGTH_LONG).show();
                                    currentpin.setText("");

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
                    params.put("oldpin",currentpin.getText().toString() );
                    params.put("newpin", String.valueOf(confirmpin.getText().toString()));

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
