package com.maksystechnologies.maksys.Fragments;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.MultiAutoCompleteTextView;
import android.widget.ProgressBar;
import android.widget.Spinner;
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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static android.app.Activity.RESULT_OK;


public class Create_Ticket_Fragment extends Fragment {
    private String tag_json_obj = "jobj_req", tag_json_arry = "jarray_req";
    private String tag_string_req = "string_req";
    MultiAutoCompleteTextView autoDevice;
    Spinner deviceStatus;
    EditText description,imagepath,mobile;
    Button createTicket;
    String status,devicelisttemp="",autodevicelist="";
    ImageButton selectImage;
    private static final int PERMISSION_REQUEST=0;
    private static final int RESULT_LOAD_IMAGE=1;
    String pictutepath="";
    Bitmap bitmap;
    String deviceiD="";
    ProgressBar progressBar;
    List<String> devicelist ;
    ArrayList<String> autocompletedatasDevice = new ArrayList<String>();
    ArrayList<String> autocompletedatDeviceId = new ArrayList<String>();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        final    View rootView = inflater.inflate(R.layout.fragment_create__ticket, container, false);


        getDeviceList();


        devicelist = new ArrayList<>();
        autoDevice=rootView.findViewById(R.id.autocomplete_customer_create_ticket_device_name);
        deviceStatus=rootView.findViewById(R.id.sp_customer_create_ticket_device_status);
        description=rootView.findViewById(R.id.et_customer_create_ticket_device_description);
        createTicket=rootView.findViewById(R.id.btn_customer_create_ticket_create);
        imagepath=rootView.findViewById(R.id.et_customer_create_ticket_imagepath);
        selectImage=rootView.findViewById(R.id.btn_customer_create_ticket_upload);
        progressBar=rootView.findViewById(R.id.progress_customer_create_ticket);
        mobile=rootView.findViewById(R.id.et_customer_create_ticket_device_number);
        progressBar.setVisibility(View.GONE);


autoDevice.addTextChangedListener(new TextWatcher() {
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {


        if(autocompletedatDeviceId.size()!=0) {
            devicelist.add(autocompletedatDeviceId.get(start));
            deviceiD = TextUtils.join(",", devicelist);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {


    }
});
        autoDevice.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                try {


                }catch (Exception e){

                }
            }
        });


        selectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M && getActivity().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED){
                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},PERMISSION_REQUEST);

                }else {
                    Intent i=new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(i,RESULT_LOAD_IMAGE);
                }
            }
        });

        createTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!autoDevice.getText().toString().equals("")&& !description.getText().toString().equals("")&&!mobile.getText().toString().equals("")){
//                    try{
//                        List<String> myList = new ArrayList<String>(Arrays.asList(autoDevice.getText().toString().split(",")));
//                        for (String device : myList) {
//                            devicelisttemp = devicelisttemp + (autocompletedatasDevice.indexOf(device));
//                            //                        Toast.makeText(getActivity(),devicelisttemp,Toast.LENGTH_SHORT) .show();
//                        }
//
//                    List<String> myList1 = new ArrayList<String>(Arrays.asList(devicelisttemp.split("-")));
//
//                    for (String device: myList1){
//                        autodevicelist=autodevicelist+(autocompletedatDeviceId.get(Integer.parseInt(device)));
//                        //clear autodevicelist after every create ticket .
//                        Toast.makeText(getActivity(),autodevicelist+"   device id",Toast.LENGTH_SHORT) .show();
//                    }
//                    }
//                    catch (Exception e){
//
//                    }


                    setCreateTicket();
                }
                else {
                    Toast.makeText(getActivity(),"Complete All Fields!.",Toast.LENGTH_SHORT) .show();
                }

            }
        });


        return rootView;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Create Ticket");
    }


    private void setCreateTicket(){
    progressBar.setVisibility(View.VISIBLE);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.CustomerCreateTicket,
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

                            if(Jobject.getString("status").equals("success")){

                            }
                            status  = Jobject.getString("status");
                            Log.d("status result",  status);
//                            String id1 = Jobject.getString("id");
//                            Log.d("id result",  id1);

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
//                            id=id1;
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
                                progressBar.setVisibility(View.GONE);
                                clearTask();
                                Toast.makeText(getActivity(), "Successfully Created Ticket!!", Toast.LENGTH_LONG).show();
                                Fragment fragment=new CustomerHomeFragment();
                                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                                ft.replace(R.id.content_frame, fragment);
                                ft.commit();

//                            Engineer engineer=new Engineer(name,designation,contact_no,mail_id,profile_photo,pin_no,address,dob,department,emp_id,passwordRead,username);


                                break;

                            case "fail":
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(getActivity(), "Something Went Wrong.!", Toast.LENGTH_SHORT).show();
                                break;
                        }
                    };
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                            pDialog.hide();
                        progressBar.setVisibility(View.GONE);
                        clearTask();
                        Toast.makeText(getActivity(),error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            protected Map<String, String> getParams() {


                String image="";
                String checksum="0";
                String deviestatus="";
                if (imagepath.getText().toString().equals("")){
                    image="";
                    checksum="0";
                }else {
                    image=getStringImage(bitmap);
                    checksum="1";
                }

                String devices=deDup(removeLastChar(deviceiD));
                String devices1 = deDup(deviceiD);
                Map<String, String> params = new HashMap<String, String>();
                params.put("device",devices1);
                params.put("description", String.valueOf(description.getText().toString()));
                params.put("upload", image);
                Log.e("imagebitmap",image);
                params.put("branch", SharedPrefManager.getInstance(getActivity()).getKEY_Customer_BranchID());
                params.put("checksum", checksum);
                params.put("mobile", mobile.getText().toString());

                params.put("issue",String.valueOf(deviceStatus.getSelectedItemPosition()+1));
                    Log.e("country code send",devices1);


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
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case PERMISSION_REQUEST:
                if(grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(getActivity().getApplicationContext(),"Permission Granded",Toast.LENGTH_SHORT).show();

                    Intent i=new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(i,RESULT_LOAD_IMAGE);
                }else {
                    Toast.makeText(getActivity().getApplicationContext(),"Permission not Granded!!",Toast.LENGTH_SHORT).show();

                }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case RESULT_LOAD_IMAGE:
                if(resultCode==RESULT_OK){
                    Uri selectImage=data.getData();
                    String[] filepathcolumn={MediaStore.Images.Media.DATA};
                    Cursor cursor=getActivity().getContentResolver().query(selectImage,filepathcolumn,null,null,null);
                    cursor.moveToFirst();
                    int colimnindex=cursor.getColumnIndex(filepathcolumn[0]);
                    pictutepath=cursor.getString(colimnindex);
//                    Toast.makeText(getActivity().getApplicationContext(),pictutepath,Toast.LENGTH_SHORT).show();
                    imagepath.setText(pictutepath);
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectImage);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    cursor.close();

                }
        }
    }

    public String getStringImage(Bitmap bitmap){
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);

        return Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT);

    }
    private void clearTask(){
        autoDevice.setText("");
        description.setText("");
        bitmap=null;
        imagepath.setText("");
        deviceStatus.setSelection(0);
        autoDevice.setSelected(true);

    }
    private void getDeviceList(){


        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.CustomeGetDeviceList,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.e("Volleyresponse",response.toString());
                        try {

                            JSONObject Jobject = (JSONObject) new JSONTokener(response).nextValue();
                            Log.e("volleyJson", Jobject.toString());
                            JSONArray feedArray = Jobject.getJSONArray("result");

                            Log.e("feedlength",String.valueOf(feedArray.length()));

                            for (int i = 0; i < feedArray.length(); i++) {
                                int jarraylength=feedArray.length()+1;
                                String listDevice[] = new String[jarraylength];
                                String list="",listid="";
                                JSONObject feedObj = (JSONObject) feedArray.get(i);

                                list=feedObj.getString("cust_asset_code");
                                listid=feedObj.getString("cust_asset_id");


                                autocompletedatasDevice.add(list);
                                autocompletedatDeviceId.add(listid);
                            }


                        } catch (Exception e) { }

                        ArrayAdapter<String> tagArray = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, autocompletedatasDevice);
                        autoDevice.setAdapter(tagArray);
                        autoDevice.setThreshold(2);
                        autoDevice.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());

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
                params.put("branch_id", SharedPrefManager.getInstance(getActivity()).getKEY_Customer_BranchID());

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
    @RequiresApi(api = Build.VERSION_CODES.N)
    public String deDup(String s) {
        return Arrays.stream(s.split(",")).distinct().collect(Collectors.joining(","));
    }

    private static String removeLastChar(String str) {
        return str.substring(0, str.length() - 1);
    }

}
