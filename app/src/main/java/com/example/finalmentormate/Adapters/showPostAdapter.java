package com.example.finalmentormate.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.finalmentormate.Modals.PostModel;
import com.example.finalmentormate.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class showPostAdapter extends RecyclerView.Adapter<showPostAdapter.ViewHolder> {

    private ArrayList<PostModel> postModelArrayList;
    private Context context;
    private InterfacePostClick click;

    public showPostAdapter(ArrayList<PostModel> postModelArrayList, Context context){
        this.postModelArrayList = postModelArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.post_items_list,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PostModel postModel = postModelArrayList.get(position);
        holder.postuser.setText(postModel.getUsername());
        Glide.with(context)
                .load(postModel.getImage())
                .into(holder.imagepost);
    }

    @Override
    public int getItemCount() {
        return postModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView postuser;
        ImageView imagepost, like;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            postuser = itemView.findViewById(R.id.postuser);
            imagepost = itemView.findViewById(R.id.imagepost);
            like = itemView.findViewById(R.id.likes);
        }
    }

    public interface InterfacePostClick {
        void onClick(int p);
    }
}
