package com.example.devlop;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;


public class ResetPassActivity extends AppCompatActivity {
    Toolbar mToolbar;
    EditText edEmail;
    Button restoreBtn;
    private FirebaseAuth mAuth;
    ProgressBar pb;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reset_pass_activity);
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mAuth = FirebaseAuth.getInstance();

        initView();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void initView() {


        edEmail = findViewById(R.id.edEmail);
        restoreBtn = findViewById(R.id.restoreBtn);
        pb = findViewById(R.id.resetPb);

        restoreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edEmail.getText().toString();
                if (TextUtils.isEmpty(email)){
                    Toast.makeText(ResetPassActivity.this, "Введите электронную почту", Toast.LENGTH_SHORT).show();
                }else {

                    mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                pb.setVisibility(View.VISIBLE);
                                Toast.makeText(ResetPassActivity.this, "Проверьте почту", Toast.LENGTH_LONG).show();
                                startActivity(new Intent(ResetPassActivity.this, AuthorizationActivity.class));

                            }else {
                                String message = task.getException().getMessage();
                                Toast.makeText(ResetPassActivity.this, "Произошла ошибка " + message, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
               //     pb.setVisibility(View.GONE);
                }
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
}
