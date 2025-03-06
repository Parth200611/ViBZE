package com.mountreachsolution.vibez.User.Adpter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mountreachsolution.vibez.R;
import com.mountreachsolution.vibez.User.POJO.POJORequest;
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
        holder.tvEmail.setText(user.getEmail());
        Glide.with(activity)
                .load(urls.address + "images/"+user.getImage())
                .skipMemoryCache(true)
                .error(R.drawable.baseline_person_24)// Resize the image to 800x800 pixels
                .into(holder.cvImage);

    }

    @Override
    public int getItemCount() {
        return pojoRequests.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView cvImage;
        TextView tvName, tvUsername, tvEmail;
        Button btnAccept, btnReject;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cvImage = itemView.findViewById(R.id.cvImage);
            tvName = itemView.findViewById(R.id.tvName);
            tvUsername = itemView.findViewById(R.id.tvusername);
            tvEmail = itemView.findViewById(R.id.tvEmail);
            btnAccept = itemView.findViewById(R.id.btnAccept);
            btnReject = itemView.findViewById(R.id.btnReject);
        }
    }
}
