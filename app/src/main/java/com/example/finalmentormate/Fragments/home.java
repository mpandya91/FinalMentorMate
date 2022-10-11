package com.example.finalmentormate.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.finalmentormate.Adapters.showPostAdapter;
import com.example.finalmentormate.Modals.PostModel;
import com.example.finalmentormate.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.Collections;
import java.util.UUID;

public class home extends Fragment {

    private Uri imageUri;
    private String id;
    private FirebaseStorage storage;
    private StorageReference storageRef;
    private FirebaseDatabase fbdb;
    private DatabaseReference dbref;
    private FirebaseAuth fa;
    private Context context;
    private RecyclerView recyclerView;
    private ArrayList<PostModel> postModelArrayList;
    private showPostAdapter postAdapter;
    private String key;
    private String pid;
    private ProgressDialog progressDialog;
    private PostModel postModel;

    public home() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void init(){
        fbdb = FirebaseDatabase.getInstance();
        fa = FirebaseAuth.getInstance();
        dbref = fbdb.getReference("Posts");
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();
        postModelArrayList = new ArrayList<PostModel>();
        postAdapter = new showPostAdapter(postModelArrayList,context);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(postAdapter);
        postModel = new PostModel();

        progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage("Uploading..");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setTitle("Uploading");

        getAllPosts();
        pid = dbref.push().getKey();

        return;
    }

    private void getAllPosts() {
        postModelArrayList.clear();
        dbref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                PostModel pm = snapshot.getValue(PostModel.class);
                postModelArrayList.add(pm);
                Collections.reverse(postModelArrayList);
                postAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                PostModel pm = snapshot.getValue(PostModel.class);
                postModelArrayList.add(pm);
                Collections.reverse(postModelArrayList);
                postAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                PostModel pm = snapshot.getValue(PostModel.class);
                postModelArrayList.add(pm);
                Collections.reverse(postModelArrayList);
                postAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                PostModel pm = snapshot.getValue(PostModel.class);
                postModelArrayList.add(pm);
                Collections.reverse(postModelArrayList);
                postAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        final View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        context = rootView.getContext();
        recyclerView = rootView.findViewById(R.id.recycle);
        SwipeRefreshLayout swipeuprefreshlayout = rootView.findViewById(R.id.swipeuprefreshlayout);

        swipeuprefreshlayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getAllPosts();
                swipeuprefreshlayout.setRefreshing(false);
            }
        });

        init();

        FloatingActionButton add = rootView.findViewById(R.id.floatingActionButton),
            addDoubt = rootView.findViewById(R.id.doubtBtn),
            addPost = rootView.findViewById(R.id.postBtn);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (addDoubt.getVisibility() != View.VISIBLE){
                    addDoubt.setVisibility(View.VISIBLE);
                    addPost.setVisibility(View.VISIBLE);
                }
                else{
                    addDoubt.setVisibility(View.GONE);
                    addPost.setVisibility(View.GONE);
                }
            }
        });

        addPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddPost(rootView);
            }
        });

        addDoubt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context, com.example.finalmentormate.askdoubt.class));
            }
        });

        return rootView;
    }

    public void AddPost(View v) {
        id = fa.getUid();
        imageUpload();
    }

    private void imageUpload() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,100);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==100 && data!=null && data.getData()!=null){
            imageUri = data.getData();
            uploadPicture();
        }
    }

    private void uploadPicture() {
        ProgressBar pb = new ProgressBar(getActivity().getApplicationContext());
        key = UUID.randomUUID().toString();
        String img_loaction = ("images/"+id)+"/"+key;
        storageRef = FirebaseStorage.getInstance().getReference(img_loaction);
        progressDialog.show();
        storageRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>(){
            @Override
            public void onSuccess(UploadTask.TaskSnapshot snapshot) {
                storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        dbref.child(pid).child("image").setValue(uri.toString());
                        postModel.setImage(uri.toString());
                        AddData();
                        Toast.makeText(context, "Task Completed Successfully...", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, "Failed...😔", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }

    private void AddData() {
        postModel.setUsername(fa.getCurrentUser().getEmail());
        postModel.setId(id);
        dbref.child(pid).setValue(postModel);
    }

}