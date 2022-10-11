package com.example.finalmentormate;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.finalmentormate.Adapters.DoubtAdapter;
import com.example.finalmentormate.Adapters.DoubtReplyAdapter;
import com.example.finalmentormate.Adapters.showDoubtAdapter;
import com.example.finalmentormate.Modals.DoubtModel;
import com.example.finalmentormate.Modals.DoubtReplyModel;
import com.example.finalmentormate.databinding.ActivityMainBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Collections;

public class DoubtReply extends AppCompatActivity {

    TextView tvQuestion, username;

    private ArrayList<DoubtReplyModel> doubtModelArrayList;
    private Context context;
    private DoubtModel doubtModel;
    private RecyclerView rvDoubtShow;
    private DatabaseReference dbref;
    private DoubtReplyAdapter doubtAdapter;
    private FloatingActionButton floatingActionButton;
    private String id, question, name;

    public void init(){
        doubtModelArrayList = new ArrayList<>();
        doubtAdapter = new DoubtReplyAdapter(this, doubtModelArrayList);
        doubtModel = new DoubtModel();
        context = getApplicationContext();
        dbref = FirebaseDatabase.getInstance().getReference("DoubtReply");

        rvDoubtShow = findViewById(R.id.rvDoubtShow);
        rvDoubtShow.setLayoutManager(new LinearLayoutManager(context));
        rvDoubtShow.setAdapter(doubtAdapter);
        tvQuestion = findViewById(R.id.tvQuestion);
        username = findViewById(R.id.usernamedoubtreply);

        floatingActionButton = findViewById(R.id.doubtReply);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doubt_reply);

        getSupportActionBar().hide();
        init();
        Intent intent = getIntent();
         id = intent.getStringExtra("id");
                question = intent.getStringExtra("question");
                name = intent.getStringExtra("name");

        dbref = FirebaseDatabase.getInstance().getReference("DoubtReply").child(id);

        tvQuestion.setText(question);
        username.setText(name);

        SwipeRefreshLayout swipeuprefreshlayout = findViewById(R.id.swipeuprefreshlayout);
        getAllDoubts();
        swipeuprefreshlayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getAllDoubts();
                swipeuprefreshlayout.setRefreshing(false);
            }
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), AddNewReply.class);
                i.putExtra("id",id);
                i.putExtra("question",question);
                i.putExtra("name",name);
                startActivity(i);
            }
        });

    }

    private void getAllDoubts() {
        doubtModelArrayList.clear();

        dbref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                DoubtReplyModel dm = snapshot.getValue(DoubtReplyModel.class);
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