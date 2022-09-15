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

public class AddNotificationsActivity extends AppCompatActivity implements View.OnClickListener{
    private ProgressDialog progressDialog;
    private Button addPost;
    private EditText postTitle,postDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_notifications);

        addPost = (Button) findViewById(R.id.addNotification);
        postTitle = (EditText) findViewById(R.id.noticationtitle);
        postDescription=(EditText) findViewById(R.id.noticationdescription);

        progressDialog = new ProgressDialog(this);

        progressDialog.setMessage("Adding Notification");

        addPost.setOnClickListener(this);

    }

    public void addNotification() {

        if (TextUtils.isEmpty(postTitle.getText())) {
            postTitle.setError("Title Required");
        } else if (TextUtils.isEmpty(postDescription.getText())) {
            postDescription.setError("Description Required");
        } else {
            final String POST_TITLE = postTitle.getText().toString().trim();
            final String POST_DESC = postDescription.getText().toString().trim();

            progressDialog.show();
            StringRequest stringRequest = new StringRequest(Request.Method.POST,
                    Constants.ADD_NOTIFICATION_URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            progressDialog.dismiss();
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                if (!jsonObject.getBoolean("error")) {

                                    Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressDialog.hide();
                    Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();

                    params.put("title", POST_TITLE);
                    params.put("description", POST_DESC);

                    return params;
                }
            };

            RequestHandler.getInstance(this).addToRequestQueue(stringRequest);

        }
    }

    @Override
    public void onClick(View view) {
        if(view == addPost){
            addNotification();
        }
    }
}
