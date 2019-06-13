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
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
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
import java.util.HashMap;
import java.util.Map;

import static android.app.Activity.RESULT_OK;


public class Sale_Request_Fragment extends Fragment {
    private static final int PERMISSION_REQUEST=0;
    private static final int RESULT_LOAD_IMAGE=1;
    String status;
    String pictutepath="";
    ImageButton browse;
    EditText item,description,imagepath;
    Button request;
    Bitmap bitmap;
    ProgressBar progressBar;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        final View rootView = inflater.inflate(R.layout.fragment_sale__request, container, false);

        imagepath=rootView.findViewById(R.id.et_customer_add_asset_image_path);
        description=rootView.findViewById(R.id.et_customer_add_asset_description);

        item=rootView.findViewById(R.id.et_customer_add_asset_item);
        browse=rootView.findViewById(R.id.btn_customer_add_asset_image);
        request=rootView.findViewById(R.id.btn_customer_add_asset_request);
        progressBar=rootView.findViewById(R.id.progress_customer_sale_rquest);

        request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                saleRequest();
            }
        });

        browse.setOnClickListener(new View.OnClickListener() {
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
        return rootView;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Sale Request");
    }

    private void saleRequest(){
            progressBar.setVisibility(View.VISIBLE);
            item.setEnabled(false);

            description.setEnabled(false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.SendRequest,
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
                            Toast.makeText(getActivity(), "Request Has Been Recieved. Our Executive will contact you.", Toast.LENGTH_LONG).show();

                                progressBar.setVisibility(View.GONE);
                            Fragment fragment=new CustomerHomeFragment();
                            if (fragment != null) {
                                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
//                                ft.setCustomAnimations(R.anim.exittoleft, R.anim.enterfromright);
                                ft.replace(R.id.content_frame, fragment);
                                ft.commit();
                            }

                            DrawerLayout drawer = getActivity().findViewById(R.id.drawer_layout);
                            drawer.closeDrawer(GravityCompat.START);

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



                    };
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                            pDialog.hide();
                        progressBar.setVisibility(View.GONE);

                        Fragment fragment=new CustomerHomeFragment();
                        if (fragment != null) {
                            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
//                                ft.setCustomAnimations(R.anim.exittoleft, R.anim.enterfromright);
                            ft.replace(R.id.content_frame, fragment);
                            ft.commit();
                        }

                        DrawerLayout drawer = getActivity().findViewById(R.id.drawer_layout);
                        drawer.closeDrawer(GravityCompat.START);
                        Toast.makeText(getActivity(),error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                String image="";
                String checksum="0";
                if (imagepath.getText().toString().equals("")){
                    image="";
                    checksum="0";
                }else {
                    image=getStringImage(bitmap);
                    checksum="1";
                }
                Map<String, String> params = new HashMap<String, String>();
                params.put("customerid", SharedPrefManager.getInstance(getActivity()).getKEY_Customer_ID());
                params.put("branch",SharedPrefManager.getInstance(getActivity()).getKEY_Customer_BranchID() );
                params.put("description", String.valueOf(description.getText().toString()));
                params.put("image", String.valueOf(image));
                params.put("checksum", checksum);
                params.put("item", String.valueOf(item.getText().toString()));
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
    private void browseImage(){

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M && getActivity().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},PERMISSION_REQUEST);

        }


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
        Log.i("Image result",""+bitmap);
        ByteArrayOutputStream baos=new  ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100, baos);
        byte [] b=baos.toByteArray();
        String temp=Base64.encodeToString(b, Base64.DEFAULT);


        return temp;
    }


}
