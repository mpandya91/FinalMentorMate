package com.example.finalmentormate.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalmentormate.DoubtReply;
import com.example.finalmentormate.Modals.DoubtModel;
import com.example.finalmentormate.Modals.PostModel;
import com.example.finalmentormate.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class DoubtAdapter extends RecyclerView.Adapter<DoubtAdapter.ViewHolder>{
    private ArrayList<DoubtModel> doubtModelArrayList;
    private Context context;
    private DoubtModel doubtModel;

    public DoubtAdapter(ArrayList<DoubtModel> doubtModelArrayList, Context context){
        this.doubtModelArrayList = doubtModelArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public DoubtAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.doubt_layout,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull DoubtAdapter.ViewHolder holder, int position) {
        doubtModel = doubtModelArrayList.get(position);
        holder.doubttext.setText(doubtModel.getQuestion());
        holder.usernamedoubt.setText(doubtModel.getUsername());
    }

    @Override
    public int getItemCount() {
        return doubtModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView usernamedoubt, doubttext;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            usernamedoubt = itemView.findViewById(R.id.usernamedoubt);
            doubttext = itemView.findViewById(R.id.doubttext);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Intent i = new Intent(context, DoubtReply.class);
            int position = this.getAdapterPosition();
            DoubtModel doubtModell = doubtModelArrayList.get(position);
            i.putExtra("name",doubtModell.getUsername());
            i.putExtra("question",doubtModell.getQuestion());
            i.putExtra("id", doubtModell.getId());
            i.putExtra("uid",doubtModell.getUid());
            context.startActivity(i);
        }
    }
}
