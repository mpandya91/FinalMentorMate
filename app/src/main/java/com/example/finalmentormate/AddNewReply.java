package com.example.finalmentormate;

import static com.google.firebase.auth.FirebaseAuth.getInstance;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.finalmentormate.Modals.DoubtModel;
import com.example.finalmentormate.Modals.DoubtReplyModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

public class AddNewReply extends AppCompatActivity {

    FirebaseAuth auth = getInstance();
    FirebaseUser user = auth.getCurrentUser();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_reply);
        getSupportActionBar().hide();
        Button btn = findViewById(R.id.btnPostaskdoubtreply);
        EditText textBox = findViewById(R.id.txtquestionreply);

        Intent intent = getIntent();

        String id = intent.getStringExtra("id");
        String username = intent.getStringExtra("name");
        String question = intent.getStringExtra("question");
        String rid = UUID.randomUUID().toString();

        DatabaseReference dbref = FirebaseDatabase.getInstance().getReference("DoubtReply");

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String reply = textBox.getText().toString();
                DoubtReplyModel dmr = new DoubtReplyModel(id, auth.getUid(), reply, user.getEmail());
                dbref.child(id).child(rid).setValue(dmr);
                Toast.makeText(getApplicationContext(), "done", Toast.LENGTH_SHORT).show();
                onBackPressed();
            }
        });
    }
}