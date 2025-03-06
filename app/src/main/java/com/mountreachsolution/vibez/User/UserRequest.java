package com.mountreachsolution.vibez.User;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.mountreachsolution.vibez.R;
import com.mountreachsolution.vibez.User.Adpter.AdpterAllUser;
import com.mountreachsolution.vibez.User.POJO.AllUser;
import com.mountreachsolution.vibez.urls;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;


public class UserRequest extends Fragment {
    RecyclerView rvList;
    TextView tvNoRequest;
    String username;
    List<AllUser> allUsers,filteredUsers;
    String id,name,mobile,email,gender,username1,password,userType,image;
    SearchView searchView;
    AdpterAllUser adpterAllUser;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_user_request, container, false);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("UserPrefs", MODE_PRIVATE);
        username = sharedPreferences.getString("username", "Guest");

        rvList = view.findViewById(R.id.rvLsit);
        tvNoRequest = view.findViewById(R.id.tvNoRequest);
        rvList.setLayoutManager(new LinearLayoutManager(getActivity()));
        allUsers=new ArrayList<>();
        getData();

        return view;
    }

    private void getData() {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("username",username);
        client.post(urls.getAllUsereRequestData,params ,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    JSONArray jsonArray =response.getJSONArray("getAllUser");
                    if (jsonArray.length()==0){
                        rvList.setVisibility(View.GONE);
                        tvNoRequest.setVisibility(View.VISIBLE);
                    }
                    for (int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        id = jsonObject.getString("id");
                        name = jsonObject.getString("name");
                        mobile = jsonObject.getString("mobile");
                        email = jsonObject.getString("email");
                        gender = jsonObject.getString("gender");
                        username1 = jsonObject.getString("username");
                        password = jsonObject.getString("password");
                        userType = jsonObject.getString("usertype");
                        image = jsonObject.getString("image");
                        allUsers.add(new AllUser(id,name,mobile,email,gender,username1,password,userType,image));

                    }
                     adpterAllUser =new AdpterAllUser(allUsers,getActivity());
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
    private void filterUsers(String query) {
        filteredUsers.clear();
        if (query.isEmpty()) {
            filteredUsers.addAll(allUsers);
        } else {
            for (AllUser user : allUsers) {
                if (user.getName().toLowerCase().contains(query.toLowerCase()) ||
                        user.getUsername().toLowerCase().contains(query.toLowerCase())) {
                    filteredUsers.add(user);
                }
            }
        }
        adpterAllUser.notifyDataSetChanged();
    }
}