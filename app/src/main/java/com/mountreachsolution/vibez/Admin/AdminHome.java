package com.mountreachsolution.vibez.Admin;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.mountreachsolution.vibez.R;
import com.mountreachsolution.vibez.User.RequestPage;
import com.mountreachsolution.vibez.urls;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;


public class AdminHome extends Fragment {
    RecyclerView rvList;
    TextView tvNoRequest;
    List<POJOALLUSER>pojoallusers;
    AdpterAllUser adpterAllUser;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_admin_home, container, false);
        rvList = view.findViewById(R.id.rvLsit);
        tvNoRequest = view.findViewById(R.id.tvNoRequest);
        rvList.setLayoutManager(new LinearLayoutManager(getActivity()));
        pojoallusers =new ArrayList<>();
        getData();
        return view;
    }
    private void getData() {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();


        client.post(urls.getAllUser,params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    JSONArray jsonArray = response.getJSONArray("getAllUser");
                    if (jsonArray.length()==0){
                        tvNoRequest.setVisibility(View.VISIBLE);
                        rvList.setVisibility(View.GONE);
                    }
                    for (int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String name=jsonObject.getString("name");
                        String id=jsonObject.getString("id");
                        String mobileno=jsonObject.getString("mobile");
                        String gender=jsonObject.getString("gender");
                        String email=jsonObject.getString("email");
                        String image=jsonObject.getString("image");
                        String usertype=jsonObject.getString("usertype");
                        String username=jsonObject.getString("username");
                        pojoallusers.add(new POJOALLUSER(id,name,mobileno,email,gender,usertype,username,image));

                    }
                    adpterAllUser=new AdpterAllUser(pojoallusers,getActivity());
                    rvList.setAdapter(adpterAllUser);


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