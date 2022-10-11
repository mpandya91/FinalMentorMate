package com.example.finalmentormate;

import static com.google.firebase.auth.FirebaseAuth.getInstance;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finalmentormate.Modals.DoubtModel;
import com.example.finalmentormate.Modals.DoubtReplyModel;
import com.example.finalmentormate.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

public class askdoubt extends AppCompatActivity {


    FirebaseAuth auth = getInstance();
    FirebaseUser user = auth.getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_askdoubt);
        getSupportActionBar().hide();
        Button btn = findViewById(R.id.btnPostaskdoubt);
        EditText textBox = findViewById(R.id.txtquestion);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Doubts");
                String key = UUID.randomUUID().toString();
                String did = databaseReference.push().getKey();
                String uid = user.getUid();
                DoubtModel doubtModel = new DoubtModel(key, uid, textBox.getText().toString(), user.getEmail());
                databaseReference.child(did).setValue(doubtModel);
                Toast.makeText(askdoubt.this, "Doubt Added successfully...", Toast.LENGTH_SHORT).show();
                onBackPressed();
            }
        });
    }
}