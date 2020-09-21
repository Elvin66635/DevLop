package com.example.devlop;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class RestorePassActivity extends AppCompatActivity {
    Toolbar toolbar;
    EditText edEmail;
    Button restoreBtn;
    private FirebaseAuth mAuth;
    ProgressDialog pd;
    ProgressBar pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resetpass);
        mAuth = FirebaseAuth.getInstance();
        initView();


    }

    private void initView() {
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        toolbar = findViewById(R.id.toolBarResetPass);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        edEmail = findViewById(R.id.edEmailResPass);
        restoreBtn = findViewById(R.id.restoreBtn);
        pd = new ProgressDialog(this);
        pb = findViewById(R.id.pbResetPass);


        restoreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edEmail.getText().toString();
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(RestorePassActivity.this, "Введите электронную почту", Toast.LENGTH_SHORT).show();
                } else {

                    mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(RestorePassActivity.this, "Проверьте почту", Toast.LENGTH_LONG).show();
                                pd.setMessage("Подождите");
                           //     pd.show();
                                pb.setVisibility(View.VISIBLE);
                                startActivity(new Intent(RestorePassActivity.this, AuthorizationActivity.class));
                                finish();
                            } else {
                                String message = task.getException().getMessage();
                                Toast.makeText(RestorePassActivity.this, "Произошла ошибка " + message, Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //  finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
