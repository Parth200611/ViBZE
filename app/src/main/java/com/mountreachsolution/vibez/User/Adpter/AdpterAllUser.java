package com.mountreachsolution.vibez.User.Adpter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mountreachsolution.vibez.R;
import com.mountreachsolution.vibez.User.POJO.AllUser;
import com.mountreachsolution.vibez.urls;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdpterAllUser extends RecyclerView.Adapter<AdpterAllUser.ViewHolder> {
    List<AllUser>allUsers;
    Activity activity;

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
