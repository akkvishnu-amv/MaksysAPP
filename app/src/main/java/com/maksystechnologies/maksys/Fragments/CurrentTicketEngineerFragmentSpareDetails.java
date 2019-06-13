package com.maksystechnologies.maksys.Fragments;

import android.Manifest;
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
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.CardView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
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
import com.maksystechnologies.maksys.Activities.MainEngineerActivity;
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
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.app.Activity.RESULT_OK;


public class CurrentTicketEngineerFragmentSpareDetails extends Fragment {
    CheckBox used,needed;
    EditText useddetails,neededdetails;
    Button closeTicket,back;
    CardView backCard,closeCard;
    ImageButton uploadimage;
    private static final int PERMISSION_REQUEST=0;
    private static final int RESULT_LOAD_IMAGE=1;
    String pictutepath="",ticket_asignid,progress,ticketid;
    Bitmap bitmap;
    File pdfFile;
    final private int REQUEST_CODE_ASK_PERMISSIONS = 111;

    FragmentManager fragmentManager ;
    FragmentTransaction fragmentTransaction;
    final String addresslink="https://www.google.com/maps/search/?api=1&query="+SharedPrefManager.getInstance(getActivity()).getKEY_Latitude()+","+SharedPrefManager.getInstance(getActivity()).getKEY_Longitude();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments

        View rootView=inflater.inflate(R.layout.fragment_current_ticket_engineer_fragment_spare_details, container, false);

        if(!getArguments().getString("Key").equals(null)){
            ticket_asignid = getArguments().getString("Key");
            progress= getArguments().getString("Keyprogress");
            ticketid=getArguments().getString("Keyticket");
        }

        useddetails=rootView.findViewById(R.id.et_engineer_current_ticket_used_spares);
        neededdetails=rootView.findViewById(R.id.et_engineer_current_ticket_need_spares);
        used=rootView.findViewById(R.id.cb_engineer_current_ticket_user_spares);
        needed=rootView.findViewById(R.id.cb_engineer_current_ticket_need_spares);
        closeTicket=rootView.findViewById(R.id.btn_engineer_current_ticket__spare_close);
        uploadimage=rootView.findViewById(R.id.btn_engineer_current_ticket_uploaddoc);
        back=rootView.findViewById(R.id.btn_engineer_current_ticket__spare_next);
        closeCard=rootView.findViewById(R.id.card_engineer_current_tickets__spare_btn_close);
        backCard=rootView.findViewById(R.id.card_engineer_current_tickets_btn_back);
        used.setChecked(false);
        needed.setChecked(false);
        useddetails.setVisibility(View.GONE);
        neededdetails.setVisibility(View.GONE);
        closeTicket.setText(R.string.completeandclose);
        closeCard.setCardBackgroundColor(getResources().getColor(R.color.green));
        used.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked){
                    useddetails.setVisibility(View.VISIBLE);

                }else {
                    useddetails.setVisibility(View.GONE);
                }

            }
        });

        needed.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    neededdetails.setVisibility(View.VISIBLE);
                    closeTicket.setText(R.string.pendingandclose);
                    closeCard.setCardBackgroundColor(getResources().getColor(R.color.orange));

                }else {
                    neededdetails.setVisibility(View.GONE);
                    closeTicket.setText(R.string.completeandclose);
                    closeCard.setCardBackgroundColor(getResources().getColor(R.color.green));


                }
            }
        });

        uploadimage.setOnClickListener(new View.OnClickListener() {
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

        closeTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    SharedPrefManager.getInstance(getActivity()).clearKEY_Current_Ticket_Progress();
                    createPdfWrapper();
                    closeRequest();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (DocumentException e) {
                    e.printStackTrace();
                }


            }
        });
            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Fragment fragment = new CurrentTicketEngineerFragment();
                    fragmentTransaction.setCustomAnimations(R.anim.enterfromleft, R.anim.exittoright);

                    fragmentTransaction.replace(R.id.content_frame, fragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();

                }
            });
        return rootView;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fragmentManager = getActivity().getSupportFragmentManager();
        fragmentTransaction =  fragmentManager.beginTransaction();
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Current Ticket");
    }
    public CurrentTicketEngineerFragmentSpareDetails(){

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
            case REQUEST_CODE_ASK_PERMISSIONS:
            {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted
                    try {
                        createPdfWrapper();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (DocumentException e) {
                        e.printStackTrace();
                    }
                } else {
                    // Permission Denied
                    Toast.makeText(getContext(), "WRITE_EXTERNAL Permission Denied", Toast.LENGTH_SHORT)
                            .show();
                }
                break;
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


        public void closeRequest()  {

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
                            SharedPrefManager.getInstance(getContext()).clearPendingRequest();
                            Toast.makeText(getContext(),"Your Ticket Has Been Closed!.",Toast.LENGTH_LONG).show();
                            startActivity(new Intent(getContext(),MainEngineerActivity.class));

                            Log.e("status",status);



                        } catch (Exception e) { }


//

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
                String sparedetails="";
                String needstatus="0";
                String usedstatus="";
                String uploadchecksum="0";
                String imgupload="";
                String spareneed="";
                String assign_status="3";
                if(used.isChecked()){
                    sparedetails=useddetails.getText().toString();
                    usedstatus="1";
                    needstatus="0";
                }else {
                    usedstatus="0";
                }

                if(needed.isChecked()){
                    spareneed=neededdetails.getText().toString();
                    assign_status="2";
                    needstatus="1";
                }else {
                    needstatus="0";
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
                params.put("sparedetails", sparedetails);
                params.put("upload", imgupload);
                params.put("needstatus", needstatus);
                params.put("usedstatus", usedstatus);
                params.put("uploadchechsum", uploadchecksum);
                params.put("engineer_id", SharedPrefManager.getInstance(getActivity()).getKEY_Engineer_ID());


                params.put("assign_status", assign_status);
                params.put("address", addresslink);
                params.put("spareneedDetails",spareneed);


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
    private void createPdfWrapper() throws FileNotFoundException,DocumentException {

        int hasWriteStoragePermission = ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
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

        pdfFile = new File(docsFolder.getAbsolutePath(),"HelloWorld.pdf");
        OutputStream output = new FileOutputStream(pdfFile);
        Document document = new Document();
        PdfWriter.getInstance(document, output);
        document.open();
        document.add(new Paragraph(progress));
        document.add(new Paragraph(progress));
        document.add(new Paragraph(progress));
        document.add(new Paragraph(progress));

        document.close();
//        previewPdf();

    }

    private void previewPdf() {

        PackageManager packageManager =getActivity().getPackageManager();
        Intent testIntent = new Intent(Intent.ACTION_VIEW);
        testIntent.setType("application/pdf");
        List list = packageManager.queryIntentActivities(testIntent, PackageManager.MATCH_DEFAULT_ONLY);
        if (list.size() > 0) {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            Uri uri = FileProvider.getUriForFile(getContext(), getContext().getApplicationContext().getPackageName() + ".com.maksystechnologies.maksys", pdfFile);
            intent.setDataAndType(uri, "application/pdf");
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivity(intent);
        }else{
            Toast.makeText(getActivity(),"Download a PDF Viewer to see the generated PDF",Toast.LENGTH_SHORT).show();
        }
    }
}
