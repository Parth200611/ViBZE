package com.mountreachsolution.vibez.User.Adpter;

import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.mountreachsolution.vibez.R;
import com.mountreachsolution.vibez.User.POJO.AllUser;
import com.mountreachsolution.vibez.urls;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import cz.msebera.android.httpclient.Header;
import de.hdodenhof.circleimageview.CircleImageView;

public class AdpterAllUser extends RecyclerView.Adapter<AdpterAllUser.ViewHolder> {
    List<AllUser>allUsers;
    Activity activity;
    String susername,rUsername,name,image;

    public AdpterAllUser(List<AllUser> allUsers, Activity activity) {
        this.allUsers = allUsers;
        this.activity = activity;
    }

    @NonNull
    @Override
    public AdpterAllUser.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(activity).inflate(R.layout.adduser,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdpterAllUser.ViewHolder holder, int position) {
        AllUser user = allUsers.get(position);
        holder.nameText.setText(user.getName());
        holder.usernameText.setText(user.getUsername());
        Glide.with(activity)
                .load(urls.address + "images/"+user.getImage())
                .skipMemoryCache(true)
                .error(R.drawable.baseline_person_24)// Resize the image to 800x800 pixels
                .into(holder.profileImage);
        holder.addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = activity.getSharedPreferences("UserPrefs", MODE_PRIVATE);
                susername = sharedPreferences.getString("username", "Guest");
                rUsername=user.getUsername();
                SharedPreferences sharedPreferences1 = activity.getSharedPreferences("UserData", Context.MODE_PRIVATE);
                 name = sharedPreferences1.getString("name", "");
                 image = sharedPreferences1.getString("image", "");


// Use the retrieved data as needed
                SendRequest(susername,rUsername,name,image);


            }
        });

    }

    private void SendRequest(String susername, String rUsername, String name, String image) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("name",name);
        params.put("sUsername",susername);
        params.put("rusername",rUsername);

        client.post(urls.SendREquest,params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    String status=response.getString("success");
                    if (status.equals("1")){
                        Toast.makeText(activity, "Request Send", Toast.LENGTH_SHORT).show();
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

    @Override
    public int getItemCount() {
        return allUsers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView profileImage;
        TextView nameText, usernameText;
        Button addButton;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            profileImage = itemView.findViewById(R.id.cvImage);
            nameText = itemView.findViewById(R.id.tvName);
            usernameText = itemView.findViewById(R.id.tvUsername);
            addButton = itemView.findViewById(R.id.btnAdd);
        }
    }
}
