package com.maksystechnologies.maksys.Fragments;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Base64;
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
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.maksystechnologies.maksys.R;
import com.maksystechnologies.maksys.Utilities.SharedPrefManager;
import com.maksystechnologies.maksys.Utilities.URLs;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;


public class Profile_Engineer_Fragment extends Fragment {
    ScrollView customerScroll;
    ProgressBar progressBar;
    Button updatepassword;
    EditText password,newpassword,confirmpassword,contactPerson;
    TextView custname,custmobile,custemail,branchname,brachid,branchaddress;
    ImageView edit;
    ImageView profilepicEdit;
    CircleImageView ProfilePic;
    LinearLayout updateLayout;
    Boolean layoutVisibility=false;
    String status;


    public  static final int PERMISSIONS_MULTIPLE_REQUEST = 123;


    private ImageView imageview;
    private Button btnSelectImage;
    private File destination = null;
    private InputStream inputStreamImg;
    private String imgPath = null;
    private final int PICK_IMAGE_CAMERA = 1, PICK_IMAGE_GALLERY = 2;


    private static final int PERMISSION_REQUEST=0;
    private static final int RESULT_LOAD_IMAGE=1;
    String pictutepath="",ticket_asignid,progress,ticketid;
    Bitmap bitmap;
    String Status=null ,custid=null,branchid=null ,branchcode =null,usrname=null,emailid=null,contactperson=null,PHnumber =null,custaddress=null ,pinnumber=null,branchnameString=null,passwordread=null,customername=null ;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        final View rootView =  inflater.inflate(R.layout.fragment_profile__enginee, container, false);

        updatepassword=rootView.findViewById(R.id.btn_engineer_update_password);

        custname=rootView.findViewById(R.id.et_engineer_profile_engineername);
        custemail=rootView.findViewById(R.id.et_engineer_profile_contactemail);
        custmobile=rootView.findViewById(R.id.et_engineer_profile_contactnumber);
        brachid=rootView.findViewById(R.id.et_engineer_profile_branchid);
        branchaddress=rootView.findViewById(R.id.et_engineer_profile_branchaddress);

        password=rootView.findViewById(R.id.et_engineer_profile_password);
        newpassword=rootView.findViewById(R.id.et_engineer_profile_newpassword);
        confirmpassword=rootView.findViewById(R.id.et_engineer_profile_confirmpassword);
        edit=rootView.findViewById(R.id.img_engineer_profile_editpassword);
        updateLayout=rootView.findViewById(R.id.edit_engineer_password_layout);

        customerScroll=rootView.findViewById(R.id.scroll_engineer_profile);
        progressBar=rootView.findViewById(R.id.progress_engineer_profile);

        custname.setText("sample");

        profilepicEdit=rootView.findViewById(R.id.profile_pic_engineer_edit);
        ProfilePic=rootView.findViewById(R.id.profile_pic_engineer_profile);


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
                if(password.getText().toString().equals(SharedPrefManager.getInstance(getActivity()).getKEY_Engineer_Password())) {
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

        profilepicEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermission();
//                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M && getContext().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED){
//                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},PERMISSION_REQUEST);
//
//                }else {
//                    Intent i=new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                    startActivityForResult(i,RESULT_LOAD_IMAGE);
//                }
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
                params.put("branchid", SharedPrefManager.getInstance(getActivity()).getKEY_Engineer_ID());
                params.put("password",confirmpassword.getText().toString() );
                params.put("checksum","1");


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
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.ReadEngineerData,
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
                            String cust_id = Jobject.getString("empid");
                            String  cust_branch_name = Jobject.getString("cust_branch_name");
                            String   cust_branch_code=Jobject.getString("cust_branch_code");
                            String cust_mail_id=Jobject.getString("cust_mail_id");

                            String cust_branch_address=Jobject.getString("cust_branch_address");
                            String  number=Jobject.getString("number");
                            String  pin=Jobject.getString("pin");
                            String  username=Jobject.getString("username");
                            String  passwrd=Jobject.getString("password");

                            Log.e(number, "number");
                            custmobile.setText(number);
                            custname.setText(cust_branch_name);
                            branchaddress.setText(cust_branch_address);
                            custmobile.setText(number);
                            custemail.setText(cust_mail_id);
                            password.setText(passwrd);
                            brachid.setText(cust_branch_code);
                            String  profilepic=Jobject.getString("emp_image");

                            String imageURL="http://maksys.co.in/sms/admin/dist/img/emp/"+profilepic;
                            Glide.with(getContext()).load(imageURL).into(ProfilePic);
                            Log.e("image url", imageURL);


                            Status=Status1;
                            branchid=cust_id;
                            branchnameString=cust_branch_name;
                            branchcode=cust_branch_code;
                            emailid=cust_mail_id;

                            custaddress=cust_branch_address;
                            PHnumber=number;
                            pinnumber=pin;

                            usrname=username;
                            passwordread=passwrd;
                            customername=cust_branch_name;


//                            Log.e(Status, "Status");
//                            Log.e(branchcode, "branchcode");
//                            Log.e(branchnameString, "branchnameString");
//                            Log.e(emailid, "emailid");
//                            Log.e(profilepic, "profilepic");
//                            Log.e(PHnumber, "PHnumber");
//                            Log.e(custaddress, "custaddress");
//                                }

//                                        c1[i]=cid;
//                                        c3[i]=sname;

//                                        statecodename=c3;
//                                        statecodeid=c1;
//

                        } catch (Exception e) { }

                        customerScroll.setVisibility(View.VISIBLE);

//                        setText(branchnameString,branchcode,custaddress,PHnumber,emailid,passwordread);

//                        branchname.setText(branchnameString);

//                        contactPerson.setText(contactperson);
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
                params.put("empid", SharedPrefManager.getInstance(getActivity()).getKEY_Engineer_ID());
                    Log.e("emp id",SharedPrefManager.getInstance(getActivity()).getKEY_Engineer_ID());
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

    private void setText(String cust_branch_name,String cust_branch_code, String cust_branch_address,String number,String cust_mail_id,String passwrd){

        custname.setText(cust_branch_name);
        brachid.setText(cust_branch_code);
        branchaddress.setText(cust_branch_address);
        custmobile.setText(number);
        custemail.setText(cust_mail_id);
        password.setText(passwrd);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case PERMISSION_REQUEST:
                if(grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(getContext().getApplicationContext(),"Permission Granded",Toast.LENGTH_SHORT).show();

                    Intent i=new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(i,RESULT_LOAD_IMAGE);
                }else {
                    Toast.makeText(getContext().getApplicationContext(),"Permission not Granded!!",Toast.LENGTH_SHORT).show();

                }

        }
    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        switch (requestCode){
//            case RESULT_LOAD_IMAGE:
//                if(resultCode==RESULT_OK){
//                    Uri selectImage=data.getData();
//                    String[] filepathcolumn={MediaStore.Images.Media.DATA};
//                    Cursor cursor=getContext().getContentResolver().query(selectImage,filepathcolumn,null,null,null);
//                    cursor.moveToFirst();
//                    int colimnindex=cursor.getColumnIndex(filepathcolumn[0]);
//                    pictutepath=cursor.getString(colimnindex);
////                    Toast.makeText(getActivity().getApplicationContext(),pictutepath,Toast.LENGTH_SHORT).show();
//
//                    try {
//                        bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), selectImage);
//                        ProfilePic.setImageBitmap(bitmap);
//                        UploadProfilePic();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//
//                    cursor.close();
//
//                }
//        }
//    }
    public String getStringImage(Bitmap bitmap){
        Log.i("Image result",""+bitmap);
        ByteArrayOutputStream baos=new  ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100, baos);
        byte [] b=baos.toByteArray();
        String temp=Base64.encodeToString(b, Base64.DEFAULT);


        return temp;
    }

    private void UploadProfilePic() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.UploadprofilePic,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Volleyresponse", response.toString());
                        try {
                            JSONArray jArray;
                            JSONObject Jobject = (JSONObject) new JSONTokener(response).nextValue();
                            Log.e("volleyJson", Jobject.toString());



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

                params.put("id", SharedPrefManager.getInstance(getContext()).getKEY_Engineer_ID());
                params.put("Image",getStringImage(bitmap));

//                    Log.e("country code send",countrycode);
                return params;
            }

        };

        //Adding the string request to the queue
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

    private void selectImage() {
        try {

            final CharSequence[] options = {"Take Photo", "Choose From Gallery","Cancel"};
            android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(getActivity());
            builder.setTitle("Select Option");
            builder.setItems(options, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int item) {
                    if (options[item].equals("Take Photo")) {
                        dialog.dismiss();
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(intent, PICK_IMAGE_CAMERA);
                    } else if (options[item].equals("Choose From Gallery")) {
                        dialog.dismiss();
                        Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(pickPhoto, PICK_IMAGE_GALLERY);
                    } else if (options[item].equals("Cancel")) {
                        dialog.dismiss();
                    }
                }
            });
            builder.show();
        } catch (Exception e) {
            Toast.makeText(getActivity(), "Camera Permission error", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        inputStreamImg = null;
        if (requestCode == PICK_IMAGE_CAMERA) {
            try {
                Uri selectedImage = data.getData();
                bitmap = (Bitmap) data.getExtras().get("data");
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 50, bytes);

                Log.e("Activity", "Pick from Camera::>>> ");

                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
                destination = new File(Environment.getExternalStorageDirectory() + "/" +
                        getString(R.string.app_name), "IMG_" + timeStamp + ".jpg");
                FileOutputStream fo;

                try {
                    destination.createNewFile();
                    fo = new FileOutputStream(destination);
                    fo.write(bytes.toByteArray());
                    fo.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                pictutepath = destination.getAbsolutePath();
                ProfilePic.setImageBitmap(bitmap);
                UploadProfilePic();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (requestCode == PICK_IMAGE_GALLERY) {
            Uri selectedImage = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), selectedImage);
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 50, bytes);
                Log.e("Activity", "Pick from Gallery::>>> ");

                pictutepath = getRealPathFromURI(selectedImage);
                destination = new File(pictutepath.toString());

                ProfilePic.setImageBitmap(bitmap);

                UploadProfilePic();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public String getRealPathFromURI(Uri contentUri) {
        String[] proj = {MediaStore.Audio.Media.DATA};
        Cursor cursor = getActivity().managedQuery(contentUri, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    private void checkPermission() {
        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.READ_EXTERNAL_STORAGE) + ContextCompat
                .checkSelfPermission(getContext(),
                        Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale
                    (getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) ||
                    ActivityCompat.shouldShowRequestPermissionRationale
                            (getActivity(), Manifest.permission.CAMERA)) {

                Snackbar.make(getActivity().findViewById(android.R.id.content),
                        "Please Grant Permissions to upload profile photo",
                        Snackbar.LENGTH_INDEFINITE).setAction("ENABLE",
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                    requestPermissions(
                                            new String[]{Manifest.permission
                                                    .READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA},
                                            PERMISSIONS_MULTIPLE_REQUEST);
                                }
                            }
                        }).show();
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(
                            new String[]{Manifest.permission
                                    .READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA},
                            PERMISSIONS_MULTIPLE_REQUEST);
                }
            }
        } else {

            selectImage();
            // write your logic code if permission already granted
        }
    }

}