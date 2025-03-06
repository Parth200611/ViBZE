package com.mountreachsolution.vibez.User.Adpter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mountreachsolution.vibez.R;
import com.mountreachsolution.vibez.User.POJO.POJORequest;
import com.mountreachsolution.vibez.User.RequestDetails;
import com.mountreachsolution.vibez.urls;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdpterRequest extends RecyclerView.Adapter<AdpterRequest.ViewHolder> {
    List<POJORequest>pojoRequests;
    Activity activity;

    public AdpterRequest(List<POJORequest> pojoRequests, Activity activity) {
        this.pojoRequests = pojoRequests;
        this.activity = activity;
    }

    @NonNull
    @Override
    public AdpterRequest.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(activity).inflate(R.layout.userrequest,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdpterRequest.ViewHolder holder, int position) {
        POJORequest user = pojoRequests.get(position);

        holder.tvName.setText(user.getName());
        holder.tvUsername.setText(user.getSusername());
        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(activity, RequestDetails.class);
                i.putExtra("username",user.getSusername());
                i.putExtra("Rusername",user.getRusername());
                activity.startActivity(i);
            }
        });



    }

    @Override
    public int getItemCount() {
        return pojoRequests.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView cvImage;
        TextView tvName, tvUsername, tvEmail;
        Button btnAccept, btnReject;
        CardView card;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tvName);
            tvUsername = itemView.findViewById(R.id.tvusername);

            card=itemView.findViewById(R.id.card);
        }
    }
}
