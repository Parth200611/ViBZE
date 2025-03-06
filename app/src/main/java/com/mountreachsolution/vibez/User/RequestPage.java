package com.mountreachsolution.vibez.User;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.mountreachsolution.vibez.R;
import com.mountreachsolution.vibez.User.Adpter.AdpterRequest;
import com.mountreachsolution.vibez.User.POJO.POJORequest;
import com.mountreachsolution.vibez.urls;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class RequestPage extends AppCompatActivity {
    RecyclerView rvList;
    TextView tvNoRequest;
    String username;
    String image,email,susername,gender;
    List<POJORequest>pojoRequests;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_request_page);
        getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.lavender));
        getWindow().setNavigationBarColor(ContextCompat.getColor(this,R.color.white));
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        username = sharedPreferences.getString("username", "Guest");

        rvList = findViewById(R.id.rvLsit);
        tvNoRequest = findViewById(R.id.tvNoRequest);
        rvList.setLayoutManager(new LinearLayoutManager(RequestPage.this));
        pojoRequests=new ArrayList<>();
        getData();
        getData2();
      
    }
    private void getData2() {
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
                        String name=jsonObject.getString("name");
                        String mobileno=jsonObject.getString("mobile");
                        gender=jsonObject.getString("gender");
                        email=jsonObject.getString("email");
                        image=jsonObject.getString("image");
                        String usertype=jsonObject.getString("usertype");


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

    private void getData() {
        AsyncHttpClient client =new AsyncHttpClient();
        RequestParams params =new RequestParams();
        params.put("username",username);
        client.post(urls.getRequest,params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    JSONArray jsonArray=response.getJSONArray("getRequest");
                    if (jsonArray.length()==0){
                        tvNoRequest.setVisibility(View.VISIBLE);
                        rvList.setVisibility(View.GONE);
                    }for (int i=0;i<jsonArray.length();i++)
                    {
                        JSONObject jsonObject =jsonArray.getJSONObject(i);
                        String id=jsonObject.getString("id");
                        String rusername=jsonObject.getString("reciverusername");
                        susername=jsonObject.getString("Susername");
                        String name=jsonObject.getString("name");
                        pojoRequests.add(new POJORequest(susername,rusername,name,image,email));
                    }
                    AdpterRequest adpterRequest =new AdpterRequest(pojoRequests,RequestPage.this);
                    rvList.setAdapter(adpterRequest);
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