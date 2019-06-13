package com.maksystechnologies.maksys.Activities;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
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
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
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
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class CurrentTicketSpares extends AppCompatActivity {
    CheckBox used,needed,others,generatepdf;
    TextView useddetails,neededdetails,otherreason;
    Button closeTicket,back;
    CardView backCard,closeCard;
    ImageButton uploadimage;
    ImageView UploadedImage;
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
    String used_model="",used_make="",used_specification="",need_make="",need_model="",need_specification="",other_reason="";
    File pdfFile;
    final private int REQUEST_CODE_ASK_PERMISSIONS = 111;
    ProgressBar progressBar;
    FragmentManager fragmentManager ;
    FragmentTransaction fragmentTransaction;
    String addresslink;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_ticket_spares);
        setupActionBar();
       addresslink="https://www.google.com/maps/search/?api=1&query="+SharedPrefManager.getInstance(getApplicationContext()).getKEY_Latitude()+","+SharedPrefManager.getInstance(getApplicationContext()).getKEY_Longitude();

        if (getIntent().getExtras() != null) {
            ticket_asignid = getIntent().getExtras().getString("Key");
            progress= getIntent().getExtras().getString("Keyprogress");
            ticketid=getIntent().getExtras().getString("Keyticket");

        }
        useddetails=findViewById(R.id.et_engineer_current_ticket_used_spares);
        neededdetails=findViewById(R.id.et_engineer_current_ticket_need_spares);
        otherreason=findViewById(R.id.et_engineer_current_ticket_other_reason);

        used=findViewById(R.id.cb_engineer_current_ticket_user_spares);
        needed=findViewById(R.id.cb_engineer_current_ticket_need_spares);
        others=findViewById(R.id.cb_engineer_current_ticket_other_reason);
        generatepdf=findViewById(R.id.cb_engineer_current_ticket_generate_pdf);

        UploadedImage=findViewById(R.id.img_current_ticket_spare_image);

        closeTicket=findViewById(R.id.btn_engineer_current_ticket__spare_close);
        uploadimage=findViewById(R.id.btn_engineer_current_ticket_uploaddoc);
        back=findViewById(R.id.btn_engineer_current_ticket__spare_next);
        closeCard=findViewById(R.id.card_engineer_current_tickets__spare_btn_close);
        backCard=findViewById(R.id.card_engineer_current_tickets_btn_back);
        progressBar=findViewById(R.id.progress_engineer_ticket_current_ticket_spare);
        used.setChecked(false);
        needed.setChecked(false);
        useddetails.setVisibility(View.GONE);
        neededdetails.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
        closeTicket.setText(R.string.completeandclose);
        closeCard.setCardBackgroundColor(getResources().getColor(R.color.green));
        used.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked){

                    showUsedSpare(CurrentTicketSpares.this);
                    useddetails.setVisibility(View.VISIBLE);
                    useddetails.setText("");
                }else {

                    used_make="";
                    used_model="";
                    used_specification="";
                    useddetails.setVisibility(View.GONE);
                }

            }
        });

        needed.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    showNeedSpare(CurrentTicketSpares.this);
                    others.setChecked(false);
                    others.setEnabled(false);
                    other_reason="";
                    closeTicket.setText(R.string.pendingandclose);
                    closeCard.setCardBackgroundColor(getResources().getColor(R.color.orange));
                    neededdetails.setText("");
                    neededdetails.setVisibility(View.VISIBLE);

                }else {


                    need_make="";
                    need_model="";
                    need_specification="";
                    neededdetails.setText("");
                    neededdetails.setVisibility(View.GONE);
                    closeTicket.setText(R.string.completeandclose);
                    closeCard.setCardBackgroundColor(getResources().getColor(R.color.green));
                    others.setEnabled(true);

                }
            }
        });
        others.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    showOtherReason(CurrentTicketSpares.this);
                    needed.setEnabled(false);
                    needed.setChecked(false);
                    need_make="";
                    need_model="";
                    need_specification="";
                    otherreason.setVisibility(View.VISIBLE);
                    closeTicket.setText(R.string.pendingandclose);
                    otherreason.setText("");
                    closeCard.setCardBackgroundColor(getResources().getColor(R.color.orange));

                }else {

                    other_reason="";
                    otherreason.setVisibility(View.GONE);
                    needed.setEnabled(true);
                    closeTicket.setText(R.string.completeandclose);
                    closeCard.setCardBackgroundColor(getResources().getColor(R.color.green));


                }
            }
        });

        uploadimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkPermission();
//                selectImage();
//                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M && getApplicationContext().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED){
//                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},PERMISSION_REQUEST);
//
//                }else {
//                    Intent i=new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                    startActivityForResult(i,RESULT_LOAD_IMAGE);
//                }
            }
        });

        closeTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                AlertDialog.Builder builder1 = new AlertDialog.Builder(CurrentTicketSpares.this);
                builder1.setMessage("Are you sure to close ticket?");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
//                                SharedPrefManager.getInstance(getApplicationContext()).clearKEY_Current_Ticket_Progress();
//                    createPdfWrapper();
                                closeRequest();
                            }
                        });

                builder1.setNegativeButton(
                        "No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.show();




            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();

            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            startActivity(new Intent(getBaseContext(), CurrentTickets.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Current Ticket Details");
        if (actionBar != null) {
            // Show the Up button in the action bar.
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {
            case PERMISSIONS_MULTIPLE_REQUEST:
                if (grantResults.length > 0) {
                    boolean cameraPermission = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    boolean readExternalFile = grantResults[0] == PackageManager.PERMISSION_GRANTED;

                    if(cameraPermission && readExternalFile)
                    {
                        // write your logic here
                    } else {
                        Snackbar.make(CurrentTicketSpares.this.findViewById(android.R.id.content),
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
                    }
                }
                break;
        }
    }//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        switch (requestCode){
//            case RESULT_LOAD_IMAGE:
//                if(resultCode==RESULT_OK){
//                    Uri selectImage=data.getData();
//                    String[] filepathcolumn={MediaStore.Images.Media.DATA};
//                    Cursor cursor=getApplicationContext().getContentResolver().query(selectImage,filepathcolumn,null,null,null);
//                    cursor.moveToFirst();
//                    int colimnindex=cursor.getColumnIndex(filepathcolumn[0]);
//                    pictutepath=cursor.getString(colimnindex);
////                    Toast.makeText(getActivity().getApplicationContext(),pictutepath,Toast.LENGTH_SHORT).show();
//
//                    try {
//                        bitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), selectImage);
//                        UploadedImage.setImageBitmap(bitmap);
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


    public void closeRequest()  {
progressBar.setVisibility(View.VISIBLE);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.EngineerCloseRequest,
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
                            String  status  = Jobject.getString("status");
                            SharedPrefManager.getInstance(getApplicationContext()).clearPendingRequest();
                            Toast.makeText(getApplicationContext(),"Your Ticket Has Been Closed!.",Toast.LENGTH_LONG).show();
                            startActivity(new Intent(getApplicationContext(),MainEngineerActivity.class));

                            progressBar.setVisibility(View.GONE);
                            Log.e("status",status);



                        } catch (Exception e) { }


//

                    };
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                            pDialog.hide();

                            progressBar.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(),error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                String sparedetails="";
                String needstatus="0";
                String usedstatus="";
                String uploadchecksum="0";
                String imgupload="";

                String spareneedMake="";
                String spareneedModel="";
                String spareneedSpecification="";

                String assign_status="3";


                String reasonstatus="0";
                String reasonData="";

                String usedMake="";
                String usedModel="";
                String usedspecification="";


                if(used.isChecked()){
                    sparedetails=useddetails.getText().toString();
                    usedstatus="1";
                    needstatus="0";
                    usedMake=used_make;
                    usedModel=used_model;
                    usedspecification=used_specification;

                }else {
                    usedstatus="0";
                }

                if(needed.isChecked()){
                    spareneedMake=need_make;
                    spareneedModel=need_model;
                    spareneedSpecification=need_specification;
                    assign_status="2";
                    needstatus="1";
                    reasonstatus="0";
                    reasonData="";

                }else if(others.isChecked()){
                    needstatus="0";
                    reasonstatus="1";
                    reasonData=other_reason;
                    assign_status="2";
                }else {
                    needstatus="0";
                    reasonstatus="0";
                    assign_status="3";
                }

                if(!pictutepath.equals("")){
                    imgupload=getStringImage(bitmap);
                    uploadchecksum="1";
                }

                Map<String, String> params = new HashMap<String, String>();
                params.put("ticket_id", ticketid);
                params.put("assign_id", ticket_asignid);
                params.put("progress", progress);



                params.put("upload", imgupload);


                params.put("needstatus", needstatus);
                params.put("needmake", spareneedMake);
                params.put("needmodel", spareneedModel);
                params.put("needspecification", spareneedSpecification);


                params.put("usedstatus", usedstatus);
                params.put("usedmake", usedMake);
                params.put("usedmodel", usedModel);
                params.put("usedspecification", usedspecification);


                params.put("uploadchechsum", uploadchecksum);
                params.put("engineer_id", SharedPrefManager.getInstance(getApplicationContext()).getKEY_Engineer_ID());


                params.put("assign_status", assign_status);
                params.put("address", addresslink);

                params.put("reasonstatus",reasonstatus);
                params.put("reasondata",reasonData);

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
    private void createPdfWrapper() throws FileNotFoundException,DocumentException {

        int hasWriteStoragePermission = ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (hasWriteStoragePermission != PackageManager.PERMISSION_GRANTED) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (!shouldShowRequestPermissionRationale(Manifest.permission.WRITE_CONTACTS)) {

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                REQUEST_CODE_ASK_PERMISSIONS);
                    }


                    return;
                }

                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        REQUEST_CODE_ASK_PERMISSIONS);
            }
            return;
        }else {
            createPdf();
        }
    }




    private void createPdf() throws FileNotFoundException, DocumentException {

        File docsFolder = new File(Environment.getExternalStorageDirectory() + "/Documents");
        if (!docsFolder.exists()) {
            docsFolder.mkdir();
            Log.i("pdf create", "Created a new directory for PDF");
        }

        pdfFile = new File(docsFolder.getAbsolutePath(),"makysEngineerTicket.pdf");
        OutputStream output = new FileOutputStream(pdfFile);
        Document document = new Document();
        PdfWriter.getInstance(document, output);
        document.open();
        document.add(new Paragraph(ticketid));
        document.add(new Paragraph(progress));


        document.close();
//        previewPdf();

    }

    private void previewPdf() {

        PackageManager packageManager =getApplicationContext().getPackageManager();
        Intent testIntent = new Intent(Intent.ACTION_VIEW);
        testIntent.setType("application/pdf");
        List list = packageManager.queryIntentActivities(testIntent, PackageManager.MATCH_DEFAULT_ONLY);
        if (list.size() > 0) {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            Uri uri = FileProvider.getUriForFile(getApplicationContext(), getApplicationContext().getApplicationContext().getPackageName() + ".com.maksystechnologies.maksys", pdfFile);
            intent.setDataAndType(uri, "application/pdf");
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivity(intent);
        }else{
            Toast.makeText(getApplicationContext(),"Download a PDF Viewer to see the generated PDF",Toast.LENGTH_SHORT).show();
        }
    }

    private void showUsedSpare(Context context){

//        setResendOTP();// otp send to registered customer number fetch from shared preference
        Button cancel,ok;
        final EditText make,model,specification;
        ViewGroup viewGroup = findViewById(android.R.id.content);

        //then we will inflate the custom alert dialog xml that we created
        final View dialogView = LayoutInflater.from(context).inflate(R.layout.used_spare_layout, viewGroup, false);

        cancel=dialogView.findViewById(R.id.btn_currentticket_spare_used_spare_cancel);
        ok=dialogView.findViewById(R.id.btn_currentticket_spare_used_spare_ok);
        make=dialogView.findViewById(R.id.et_currentticket_spare_used_spare_make);
        model=dialogView.findViewById(R.id.et_currentticket_spare_used_spare_model);
        specification=dialogView.findViewById(R.id.et_currentticket_spare_used_spare_specification);
        //Now we need an AlertDialog.Builder object
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        //setting the view of the builder to our custom view that we already inflated
        builder.setView(dialogView);
        final AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);


        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!make.getText().toString().equals("")&&!model.getText().toString().equals("")&&!specification.getText().toString().equals("")){

                    used_make=make.getText().toString();
                    used_model=model.getText().toString();
                    used_specification=specification.getText().toString();
                    useddetails.setText("Make :"+used_make+", "+"Model :"+used_model+", "+"Specification :"+used_specification);

                    alertDialog.cancel();


                }else {
                    Toast.makeText(getApplicationContext(),"Complete All Fields.",Toast.LENGTH_SHORT).show();
                }

            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                useddetails.setVisibility(View.GONE);
                alertDialog.cancel();
                used.setChecked(false);
            }
        });
        //finally creating the alert dialog and displaying it

        alertDialog.show();


    }
    private void showNeedSpare(Context context){

//        setResendOTP();// otp send to registered customer number fetch from shared preference
        Button cancel,ok;
        final EditText make,model,specification;
        ViewGroup viewGroup = findViewById(android.R.id.content);

        //then we will inflate the custom alert dialog xml that we created
        final View dialogView = LayoutInflater.from(context).inflate(R.layout.needed_spare_layout, viewGroup, false);

        cancel=dialogView.findViewById(R.id.btn_currentticket_spare_need_spare_cancel);
        ok=dialogView.findViewById(R.id.btn_currentticket_spare_need_spare_ok);
        make=dialogView.findViewById(R.id.et_currentticket_spare_need_spare_make);
        model=dialogView.findViewById(R.id.et_currentticket_spare_need_spare_model);
        specification=dialogView.findViewById(R.id.et_currentticket_spare_need_spare_specification);
        //Now we need an AlertDialog.Builder object
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        //setting the view of the builder to our custom view that we already inflated
        builder.setView(dialogView);
        final AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);


        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!make.getText().toString().equals("")&&!model.getText().toString().equals("")&&!specification.getText().toString().equals("")){

                    need_make=make.getText().toString();
                    need_model=model.getText().toString();
                    need_specification=specification.getText().toString();
                    neededdetails.setText("Make :"+need_make+", "+"Model :"+need_model+", "+"Specification :"+need_specification);

                    alertDialog.cancel();

                }else {
                    Toast.makeText(getApplicationContext(),"Complete All Fields.",Toast.LENGTH_SHORT).show();
                }

            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                neededdetails.setVisibility(View.GONE);
                alertDialog.cancel();
                needed.setChecked(false);
            }
        });
        //finally creating the alert dialog and displaying it

        alertDialog.show();
    }
    private void showOtherReason(Context context){

//        setResendOTP();// otp send to registered customer number fetch from shared preference
        Button cancel,ok;
        final EditText make,model,specification;
        ViewGroup viewGroup = findViewById(android.R.id.content);

        //then we will inflate the custom alert dialog xml that we created
        final View dialogView = LayoutInflater.from(context).inflate(R.layout.other_reason_layout, viewGroup, false);

        cancel=dialogView.findViewById(R.id.btn_currentticket_spare_other_cancel);
        ok=dialogView.findViewById(R.id.btn_currentticket_spare_other_ok);
        make=dialogView.findViewById(R.id.et_currentticket_spare_other_reason);

        //Now we need an AlertDialog.Builder object
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        //setting the view of the builder to our custom view that we already inflated
        builder.setView(dialogView);
        final AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);


        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!make.getText().toString().equals("")){
                    other_reason=make.getText().toString();
                    otherreason.setText("Reason : "+other_reason);

                    alertDialog.cancel();
                }else {
                    Toast.makeText(getApplicationContext(),"Complete All Fields.",Toast.LENGTH_SHORT).show();
                }

            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                otherreason.setVisibility(View.GONE);
                others.setChecked(false);
                alertDialog.cancel();
            }
        });
        //finally creating the alert dialog and displaying it

        alertDialog.show();
    }



    private void selectImage() {
        try {

                final CharSequence[] options = {"Take Photo", "Choose From Gallery","Cancel"};
                android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(CurrentTicketSpares.this);
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
            Toast.makeText(this, "Camera Permission error", Toast.LENGTH_SHORT).show();
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
                UploadedImage.setImageBitmap(bitmap);

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (requestCode == PICK_IMAGE_GALLERY) {
            Uri selectedImage = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 50, bytes);
                Log.e("Activity", "Pick from Gallery::>>> ");

                pictutepath = getRealPathFromURI(selectedImage);
                destination = new File(pictutepath.toString());
                UploadedImage.setImageBitmap(bitmap);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public String getRealPathFromURI(Uri contentUri) {
        String[] proj = {MediaStore.Audio.Media.DATA};
        Cursor cursor = managedQuery(contentUri, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    private void checkPermission() {
        if (ContextCompat.checkSelfPermission(CurrentTicketSpares.this,
                Manifest.permission.READ_EXTERNAL_STORAGE) + ContextCompat
                .checkSelfPermission(getApplicationContext(),
                        Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale
                    (CurrentTicketSpares.this, Manifest.permission.READ_EXTERNAL_STORAGE) ||
                    ActivityCompat.shouldShowRequestPermissionRationale
                            (CurrentTicketSpares.this, Manifest.permission.CAMERA)) {

                Snackbar.make(CurrentTicketSpares.this.findViewById(android.R.id.content),
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
