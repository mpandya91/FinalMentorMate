package com.example.finalmentormate.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalmentormate.Modals.DoubtModel;
import com.example.finalmentormate.R;

import java.util.ArrayList;

public class showDoubtAdapter extends RecyclerView.Adapter<showDoubtAdapter.ViewHolder>{

    private Context context;
    private ArrayList<DoubtModel> doubtModelArrayList;
    private DoubtModel doubtModel;

    public showDoubtAdapter(Context context, ArrayList<DoubtModel> doubtModelArrayList) {
        this.context = context;
        this.doubtModelArrayList = doubtModelArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.doubt_reply_layout,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        doubtModel = doubtModelArrayList.get(position);
        holder.doubttext.setText(doubtModel.getQuestion());
        holder.usernamedoubt.setText(doubtModel.getUsername());

    }

    @Override
    public int getItemCount() {
        return doubtModelArrayList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView usernamedoubt, doubttext;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            usernamedoubt = itemView.findViewById(R.id.usernamedoubt);
            doubttext = itemView.findViewById(R.id.doubttext);

//            itemView.findViewById(R.id.doubtBtn).setVisibility(View.GONE);
        }
    }
}
