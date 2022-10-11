package com.example.finalmentormate.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalmentormate.Modals.DoubtModel;
import com.example.finalmentormate.Modals.DoubtReplyModel;
import com.example.finalmentormate.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class DoubtReplyAdapter extends RecyclerView.Adapter<DoubtReplyAdapter.ViewHolder> {

    private Context context;
    private ArrayList<DoubtReplyModel> doubtModelArrayList;
    private DoubtReplyModel doubtModel;

    public DoubtReplyAdapter(Context context, ArrayList<DoubtReplyModel> doubtModelArrayList) {
        this.context = context;
        this.doubtModelArrayList = doubtModelArrayList;
    }

    @NonNull
    @Override
    public DoubtReplyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.doubt_reply_layout,parent,false);
        return new DoubtReplyAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull DoubtReplyAdapter.ViewHolder holder, int position) {
        doubtModel = doubtModelArrayList.get(position);
        holder.doubttext.setText(doubtModel.getReply());
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
        }
    }
}
