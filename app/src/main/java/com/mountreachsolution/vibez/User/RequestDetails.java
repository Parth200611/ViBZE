package com.mountreachsolution.vibez.User;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.mountreachsolution.vibez.R;
import com.mountreachsolution.vibez.urls;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class RequestDetails extends AppCompatActivity {
    ImageView ivImage;
     TextView tvName, tvUsername, tvEmail;
     Button btnAccept;
     String susername,Rusername;
     String name,email,image,status;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_request_details);
        getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.lavender));
        getWindow().setNavigationBarColor(ContextCompat.getColor(this,R.color.white));
        susername=getIntent().getStringExtra("username");
        Rusername=getIntent().getStringExtra("Rusername");


        ivImage = findViewById(R.id.ivImage);
        tvName = findViewById(R.id.tvName);
        tvUsername = findViewById(R.id.tvusername);
        tvEmail = findViewById(R.id.tvEmail);
        btnAccept = findViewById(R.id.btnAccept);
        getData(susername);
        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                status="Accepted";
                Accepetdetrails();
            }
        });

    }

    private void Accepetdetrails() {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("name",name);
        params.put("sUsername",susername);
        params.put("rusername",Rusername);
        params.put("image",image);
        params.put("email",email);
        params.put("status",status);
        client.post(urls.sendAccepetRequest,params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    String status1=response.getString("success");
                    if (status1.equals("1")){
                        Toast.makeText(RequestDetails.this, "Request Accepteed", Toast.LENGTH_SHORT).show();
                        removeREquest(Rusername);
                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });
    }

    private void removeREquest(String rusername) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("username",rusername);
        client.post(urls.removerequest,params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    String status=response.getString("status");
                    if (status.equals("success")){
                        Intent i = new Intent(RequestDetails.this,RequestPage.class);
                        startActivity(i);
                        finish();
                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });
    }

    private void getData(String susername) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        params.put("username",susername);
        client.post(urls.Profildata,params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    JSONArray jsonArray = response.getJSONArray("getProfildata");
                    for (int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                         name=jsonObject.getString("name");
                        String mobileno=jsonObject.getString("mobile");
                        String gender=jsonObject.getString("gender");
                         email=jsonObject.getString("email");
                         image=jsonObject.getString("image");
                        String usertype=jsonObject.getString("usertype");

                        tvName.setText(name);
                        tvEmail.setText(email);
                        tvUsername.setText(susername);
                        Glide.with(RequestDetails.this)
                                .load(urls.address + "images/"+image)
                                .skipMemoryCache(true)
                                .error(R.drawable.baseline_person_24)// Resize the image to 800x800 pixels
                                .into(ivImage);
                    }

                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });
    }
}