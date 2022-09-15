package com.example.desagu;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ReportIssueActivity extends AppCompatActivity implements View.OnClickListener{

    private ProgressDialog progressDialog;
    private Button button;
    private EditText editTextId,editTextContact,editTextTitle,editTextDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_issue);

        editTextId = findViewById(R.id.editTextIdNumber);
        editTextContact = findViewById(R.id.editTextContact);
        editTextTitle =findViewById(R.id.editTextTitle);
        editTextDescription = findViewById(R.id.editTextDescription);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Reporting Issue ...");

        button = findViewById(R.id.buttonSendIssue);

        button.setOnClickListener(this);
    }

    public  void sendIssue() {

        if (TextUtils.isEmpty(editTextTitle.getText())) {
            editTextTitle.setError("Title Required");
        } else if (TextUtils.isEmpty(editTextDescription.getText())) {
            editTextDescription.setError("Description Required");
        } else {

            final String ID_NO = editTextId.getText().toString().trim();
            final String CONTACT = editTextContact.getText().toString().trim();
            final String TITLE = editTextTitle.getText().toString().trim();
            final String DESCRIPTION = editTextDescription.getText().toString().trim();

            progressDialog.show();

            StringRequest stringRequest = new StringRequest(Request.Method.POST,
                    Constants.REPORT_ISSUE,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            progressDialog.dismiss();
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                if (!jsonObject.getBoolean("error")) {
                                    Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressDialog.hide();
                    error.printStackTrace();

                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("idnumber", ID_NO);
                    params.put("contact", CONTACT);
                    params.put("title", TITLE);
                    params.put("description", DESCRIPTION);

                    return params;
                }
            };

            RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
        }
    }
    @Override
    public void onClick(View view) {
        if (view == button){
            sendIssue();
        }
    }
}
