package com.example.desagu;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class AddChildActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText editTextBirthCertificateNo, editTextFirstName, editTextMiddleName, editTextLastName,
            editTextDOB, editTextFatherName, editTextFatherId,  editTextMotherName, editTextMotherId,
            editTextHome, editTextVillage;
    private Button buttonAddchildren,uploadImage;
    final int CODE_GALLEY_REQUEST = 999;
    Spinner editTextGender;
    ImageView imageView;
    Bitmap bitmap;
    DatePickerDialog datePickerDialog;
String path, gender;
    private ProgressDialog progressDialog;
AlertDialog.Builder aler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_child);
        aler=new AlertDialog.Builder(this);
        uploadImage = findViewById(R.id.buttonChooseImage);
        editTextBirthCertificateNo = findViewById(R.id.editTextBirthCertificateNo);
        editTextFirstName = findViewById(R.id.editTextFirstName);
        editTextMiddleName = findViewById(R.id.editTextMiddleName);
        editTextLastName = findViewById(R.id.editTextLastName);
        editTextDOB = findViewById(R.id.editTextDOB);
        editTextGender = findViewById(R.id.editTextGender);
        editTextFatherName = findViewById(R.id.editTextFatherName);
        editTextFatherId = findViewById(R.id.editTextFatherId);
        editTextMotherName = findViewById(R.id.editTextMotherName);
        editTextMotherId = findViewById(R.id.editTextMotherId);
        editTextHome = findViewById(R.id.editTextHomePhone);
        editTextVillage = findViewById(R.id.editTextVillage);

        editTextGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i==1){
                    gender ="M";
                }
                if(i==2){
                    gender = "F";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        editTextDOB.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                int mYear =c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);


                datePickerDialog = new DatePickerDialog(AddChildActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view,int year,int monthOfYear,int dayOfMonth) {
                        editTextDOB.setText( year+ "-"+(monthOfYear+1)+"-"+dayOfMonth);
                    }
                },mYear,mMonth,mDay);
                datePickerDialog.show();
            }
        });
        buttonAddchildren = findViewById(R.id.buttonAddChild);
        uploadImage = findViewById(R.id.buttonChooseImage);
        imageView = findViewById(R.id.imageViewPreview);

        buttonAddchildren.setOnClickListener(this);
        progressDialog = new ProgressDialog(this);

        progressDialog.setMessage("Adding Child ...");
        uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCompat.requestPermissions(
                        AddChildActivity.this,
                        new String[] {
                                Manifest.permission.READ_EXTERNAL_STORAGE
                        },
                        CODE_GALLEY_REQUEST );
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent,"Select Child Photo"),999);

            }
        });

    }


 @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode ==999 && resultCode == RESULT_OK && data !=null){
            Uri filepath = data.getData();
            Toast.makeText(getApplicationContext(),"Image selected",Toast.LENGTH_LONG).show();
            try {
                //array
                String [] child={MediaStore.Images.Media.DATA};
                //cursor
                Cursor inputStream = getContentResolver().query(filepath,child,null,null,null);
              if(inputStream!=null)
              {
                  inputStream.moveToFirst();
                  int id=inputStream.getColumnIndex(child[0]);
                path=inputStream.getString(id);
              }
                //Toast.makeText(getApplicationContext(),path,Toast.LENGTH_LONG).show();
                bitmap = BitmapFactory.decodeFile(path);
                imageView.setImageBitmap(bitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    private String imageProcess(Bitmap bitmap){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);

        byte[] imageByte = byteArrayOutputStream.toByteArray();
        String encodedImage = Base64.encodeToString(imageByte, Base64.DEFAULT);
        return  encodedImage;


    }
public void addChild() {
    final String BirthCertificateNo = editTextBirthCertificateNo.getText().toString().trim();
    final String FirstName = editTextFirstName.getText().toString().trim();
    final String MiddleName = editTextMiddleName.getText().toString().trim();
    final String LastName = editTextLastName.getText().toString().trim();
    final String DOB = editTextDOB.getText().toString().trim();
    final String FatherName = editTextFatherName.getText().toString().trim();
    final String FatherId = editTextFatherId.getText().toString().trim();
    final String HomePhone = editTextHome.getText().toString().trim();
    final String MotherName = editTextMotherName.getText().toString().trim();
    final String MotherId = editTextMotherId.getText().toString().trim();
    final String Village = editTextVillage.getText().toString().trim();
    final String imageData = imageProcess(bitmap);
    final String imageUrl = BirthCertificateNo + ".jpeg";

    View w;
    if(BirthCertificateNo.length()==0){
        editTextBirthCertificateNo.setError("enter birth certificate number");
        w=editTextBirthCertificateNo;
        w.requestFocus();

    }else if(TextUtils.isEmpty(editTextLastName.getText())){
        editTextLastName.setError("Last Name Required");
    }
    else if(TextUtils.isEmpty(editTextDOB.getText())){editTextDOB.setError("D O B required");}
    else if (FirstName.length()==0){
        editTextFirstName.setError("First Name Required");
        w=editTextFirstName;
        w.requestFocus();
    }
else{  progressDialog.show();
        if (imageData != null) {
            StringRequest stringRequest = new StringRequest(Request.Method.POST,
                    Constants.ADDCHILD_URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            progressDialog.dismiss();

                            try {
                                JSONObject jsonObject = new JSONObject(response);

                                Toast.makeText(AddChildActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }
                    }, new Response.ErrorListener() {


                @Override
                public void onErrorResponse(VolleyError error) {
                    progressDialog.hide();


                    aler.setTitle("connection Error")
                            .setMessage("you are Offline")
                            .setIcon(R.drawable.ic_signal_wifi_off_black_24dp)
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            }).setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialogInterface) {

                        }
                    }).show();


                }
            }) {
                @Override

                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("birthCert", BirthCertificateNo);
                    params.put("fname", FirstName);
                    params.put("mname", MiddleName);
                    params.put("lname", LastName);
                    params.put("dob", DOB);
                    params.put("gender", gender);
                    params.put("faname", FatherName);
                    params.put("faid", FatherId);
                    params.put("moname", MotherName);
                    params.put("moid", MotherId);
                    params.put("homephone", HomePhone);
                    params.put("vil", Village);
                    params.put("image", imageData);
                    params.put("imageUrl", imageUrl);

                    return params;
                }
            };

            RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
        }
    }
}
    @Override
    public void onClick(View view) {
        if (view == buttonAddchildren)
            try{addChild();}catch (Exception p){}


    }
}