package com.example.finalmentormate.Fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.finalmentormate.Adapters.DoubtAdapter;
import com.example.finalmentormate.Adapters.showPostAdapter;
import com.example.finalmentormate.Modals.DoubtModel;
import com.example.finalmentormate.Modals.PostModel;
import com.example.finalmentormate.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Collections;


public class doubt extends Fragment {

    private ArrayList<DoubtModel> doubtModelArrayList;
    private DoubtAdapter doubtAdapter;
    private Context context;
    private DoubtModel doubtModel;
    private RecyclerView rvDoubt;
    private DatabaseReference dbref;

    public doubt() {
        // Required empty public constructor
    }

    public void init(View v){
        rvDoubt = v.findViewById(R.id.recycledoubt);
        context = v.getContext();
        doubtModelArrayList = new ArrayList<>();
        doubtAdapter = new DoubtAdapter(doubtModelArrayList,context);
        doubtModel = new DoubtModel();
        dbref = FirebaseDatabase.getInstance().getReference("Doubts");
        rvDoubt.setLayoutManager(new LinearLayoutManager(context));
        rvDoubt.setAdapter(doubtAdapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_doubt, container, false);

        SwipeRefreshLayout swipeuprefreshlayout = v.findViewById(R.id.swipeuprefreshlayout);

        swipeuprefreshlayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getAllDoubts();
                swipeuprefreshlayout.setRefreshing(false);
            }
        });

        init(v);
        getAllDoubts();
        return v;
    }

    private void getAllDoubts() {
        doubtModelArrayList.clear();

        dbref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                DoubtModel dm = snapshot.getValue(DoubtModel.class);
                doubtModelArrayList.add(dm);
                Collections.reverse(doubtModelArrayList);
                doubtAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}