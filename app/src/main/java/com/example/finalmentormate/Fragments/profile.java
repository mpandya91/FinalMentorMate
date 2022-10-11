package com.example.finalmentormate.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finalmentormate.Adapters.DoubtAdapter;
import com.example.finalmentormate.Adapters.PostAdapter;
import com.example.finalmentormate.Login;
import com.example.finalmentormate.Modals.DoubtModel;
import com.example.finalmentormate.Modals.ImageViewModal;
import com.example.finalmentormate.Modals.PostModel;
import com.example.finalmentormate.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class profile extends Fragment {

    private FirebaseUser firebaseUser;
    private LinearLayout post, doubt;
    private TextView username, posts, doubts;
    private String userId;
    private Context context;
    private RecyclerView RecyclerviewPost;
    private RecyclerView RecyclerviewDoubt;
    private ArrayList<ImageViewModal> postImages;
    private ArrayList<DoubtModel> doubtlist;
    private PostAdapter postAdapter;
    private DoubtAdapter doubtAdapter;
    private LinearLayoutManager DoubtLayoutManager;

    public profile() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        init(rootView);

        FloatingActionButton logout = rootView.findViewById(R.id.btnLogout);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(rootView.getContext(), Login.class));
                getActivity().finish();
            }
        });

        posting(rootView);

        RecyclerviewDoubt.setLayoutManager(DoubtLayoutManager);
        RecyclerviewDoubt.setAdapter(doubtAdapter);

        SwipeRefreshLayout swipeuprefreshlayout = rootView.findViewById(R.id.swipeuprefreshlayout);
        swipeuprefreshlayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                postImages.clear();
                userPosts(rootView);

                swipeuprefreshlayout.setRefreshing(false);
            }
        });

        posts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doubts.setTypeface(null, Typeface.NORMAL);
                posts.setTypeface(null, Typeface.BOLD);
                RecyclerviewPost.setVisibility(rootView.VISIBLE);
                RecyclerviewDoubt.setVisibility(rootView.GONE);
                userPosts(rootView);
            }
        });

        doubts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doubting(rootView);
                RecyclerviewDoubt.setVisibility(rootView.VISIBLE);
                RecyclerviewPost.setVisibility(rootView.GONE);
                posts.setTypeface(null, Typeface.NORMAL);
                doubts.setTypeface(null, Typeface.BOLD);
                usersDoubts(rootView);
            }
        });

        return rootView;
    }

    private void doubting(View rootView){
        DoubtLayoutManager = new LinearLayoutManager(context);
        RecyclerviewDoubt.setLayoutManager(DoubtLayoutManager);
        doubtlist = new ArrayList<>();
        doubtAdapter = new DoubtAdapter(doubtlist,context);
        RecyclerviewDoubt.setAdapter(doubtAdapter);
    }

    private void posting(View rootView) {
        post = rootView.findViewById(R.id.LinearPost);
        username = rootView.findViewById(R.id.username);
        LinearLayoutManager linearLayoutManager = new GridLayoutManager(getContext(), 3);
        RecyclerviewPost.setLayoutManager(linearLayoutManager);
        postImages = new ArrayList<>();
        postAdapter = new PostAdapter(getContext(),postImages);
        RecyclerviewPost.setAdapter(postAdapter);
        userPosts(rootView);
    }

    private void usersDoubts(View rootView) {
        doubtlist.clear();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Doubts");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot snap : snapshot.getChildren()) {
                    DoubtModel dm = snap.getValue(DoubtModel.class);
                    if (dm.getUid().equals(FirebaseAuth.getInstance().getUid())) {
                        doubtlist.add(dm);
                    }
                }

                Collections.reverse(doubtlist);
                doubtAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void userPosts(View v) {
        postImages.clear();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Posts");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot snap : snapshot.getChildren()){
                    PostModel postModel = snap.getValue(PostModel.class);
                    ImageViewModal ivm;

                    if (postModel.getId().equals(FirebaseAuth.getInstance().getUid())){
                        ivm = new ImageViewModal(postModel.getImage());
                        postImages.add(ivm);
                    }
                }
                username.setText(firebaseUser.getEmail());
                postAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void init(View v) {
        context = v.getContext();
        doubtlist = new ArrayList<>();
        doubtAdapter= new DoubtAdapter(doubtlist,v.getContext());
        doubts = v.findViewById(R.id.doubts);
        posts = v.findViewById(R.id.posts);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        userId = firebaseUser.getUid();
        RecyclerviewPost = v.findViewById(R.id.RecyclerviewPost);
        RecyclerviewDoubt = v.findViewById(R.id.RecyclerviewDoubt);
        DoubtLayoutManager = new LinearLayoutManager(getContext());
    }

}