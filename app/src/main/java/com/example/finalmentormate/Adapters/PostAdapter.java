package com.example.finalmentormate.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.finalmentormate.Modals.ImageViewModal;
import com.example.finalmentormate.R;

import java.util.ArrayList;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder>{
    private Context context;
    private ArrayList<ImageViewModal> posts;

    public PostAdapter(Context context, ArrayList<ImageViewModal> posts) {
        this.context = context;
        this.posts = posts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.post_layout,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ImageViewModal post = posts.get(position);

        Glide.with(context)
                .load(post.getImageUrl())
                .into(holder.post);
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView post;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            post = itemView.findViewById(R.id.imgPost);
        }

    }

}
