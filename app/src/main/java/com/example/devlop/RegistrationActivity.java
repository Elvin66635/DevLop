package com.example.devlop;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.jaeger.library.StatusBarUtil;

public class RegistrationActivity extends AppCompatActivity {
    private static final String TAG = "RegistrationActivity";
    EditText edLoginReg, edPassReg, edEmailReg;
    Button signUpBtn;
    ProgressBar progressBar;
    private FirebaseAuth mAuth;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        mAuth = FirebaseAuth.getInstance();

        initView();

    }


    private void initView() {
            StatusBarUtil.setTransparent(this);

        toolbar = findViewById(R.id.toolBarReg);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        edLoginReg = findViewById(R.id.edLoginReg);
        edPassReg = findViewById(R.id.edPassReg);
        edEmailReg = findViewById(R.id.edEmailReg);
        signUpBtn = findViewById(R.id.signUpBtn);
        progressBar = findViewById(R.id.pbReg);
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edEmailReg.getText().toString().trim();
                String pass = edPassReg.getText().toString().trim();
                final String username = edLoginReg.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    edEmailReg.setError("Email пустой");
                    return;
                }
                if (TextUtils.isEmpty(pass)) {
                    edPassReg.setError("Пароль пустой");
                    return;
                }
                if (pass.length() < 6) {
                    edPassReg.setError("Пароль должен быть больше 6-ти символов");
                }
                if (TextUtils.isEmpty(username)) {
                    edLoginReg.setError("Имя пользователя пустое");
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(RegistrationActivity.this, "Пользователь создан", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class)
                                    .putExtra("email", edLoginReg.getText().toString()));
                            Log.d(TAG, "onComplete: " + edLoginReg.getText().toString() + " " + username);

                           // finish();
                        } else {
                            Toast.makeText(RegistrationActivity.this, "Ошибка " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                        progressBar.setVisibility(View.GONE);
                    }
                });
            }


        });

    }



    @Override
    public boolean onTouchEvent(MotionEvent event) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.
                INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        return true;

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null){
            startActivity(new Intent(this,MainActivity.class));
        }
    }
}
