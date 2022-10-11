package com.example.finalmentormate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {


    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private TextView email, pass;
    private String e, p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        if (mAuth.getCurrentUser()!=null){
            startActivity( new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }

        email = findViewById(R.id.email);
        pass = findViewById(R.id.password);

        Button btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUserAccount();
            }
        });
    }

    private void loginUserAccount() {
        e = email.getText().toString();
        p = pass.getText().toString();

        if (TextUtils.isEmpty(e)) {
            Toast.makeText(getApplicationContext(),
                            "Please enter email!!",
                            Toast.LENGTH_LONG)
                    .show();
            return;
        }

        if (TextUtils.isEmpty(p)) {
            Toast.makeText(getApplicationContext(),
                            "Please enter password!!",
                            Toast.LENGTH_LONG)
                    .show();
            return;
        }

        mAuth.signInWithEmailAndPassword(e, p).addOnCompleteListener(
                new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(Task<AuthResult> task)
                    {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(),
                                            "Login successful!!...🥳",
                                            Toast.LENGTH_LONG)
                                    .show();

                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent intent
                                    = new Intent(getApplicationContext(),
                                    MainActivity.class);
                            startActivity(intent);
                            finish();
                        }

                        else {
                            Toast.makeText(getApplicationContext(),
                                            "Login failed!! 😟😟☹️",
                                            Toast.LENGTH_LONG)
                                    .show();
                        }
                    }
                });
        }
}