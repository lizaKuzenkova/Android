package com.example.myquizapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AuthActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        mAuth = FirebaseAuth.getInstance();
        Button singin = findViewById(R.id.signin);
        Button singup = findViewById(R.id.signup);
        singin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String email = ((EditText)findViewById(R.id.email_field)).getText().toString();
                    String password = ((EditText)findViewById(R.id.pass_field)).getText().toString();
                    mAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(AuthActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Intent intent = new Intent(AuthActivity.this, MenuActivity.class);
                                        startActivity(intent);
                                    } else {
                                        Toast.makeText(AuthActivity.this, "Authentication failed. "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }catch (Throwable throwable){
                    Toast.makeText(AuthActivity.this, "Authentication failed. "+throwable.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        singup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String email = ((EditText)findViewById(R.id.email_field)).getText().toString();
                    String password = ((EditText)findViewById(R.id.pass_field)).getText().toString();
                    mAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(AuthActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Intent intent = new Intent(AuthActivity.this, MenuActivity.class);
                                        startActivity(intent);
                                    } else {
                                        Toast.makeText(AuthActivity.this, "Authentication failed. " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }catch (Throwable throwable){
                    Toast.makeText(AuthActivity.this, "Authentication failed. " + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
