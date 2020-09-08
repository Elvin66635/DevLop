package com.example.devlop;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegistrationActivity extends AppCompatActivity {
    private static final String TAG = "RegistrationActivity";
    EditText edLoginReg, edPassReg, edEmailReg;
    Button signUpBtn;
    ProgressBar progressBar;
    private FirebaseAuth mAuth;
    boolean isOpened = false;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        mAuth = FirebaseAuth.getInstance();

        initView();
    //    setListenerToRootView();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void initView() {


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
                String username = edLoginReg.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    edEmailReg.setError("Email пустой");
                    return;
                }
                if (TextUtils.isEmpty(pass)) {
                    edPassReg.setError("Пароль пустой");
                    return;
                }
                if (pass.length() < 6){
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
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        } else {
                            Toast.makeText(RegistrationActivity.this, "Ошибка " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }


        });

    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //    updateUI(currentUser);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.
                INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        return true;
    }

    public void setListenerToRootView() {
        final View activityRootView = getWindow().getDecorView().findViewById(android.R.id.content);
        activityRootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {

                int heightDiff = activityRootView.getRootView().getHeight() - activityRootView.getHeight();
                if (heightDiff > 100) { // 99% of the time the height diff will be due to a keyboard.
                    Toast.makeText(getApplicationContext(), "Gotcha!!! softKeyboardup", Toast.LENGTH_LONG).show();

                    if (isOpened == false) {
                        //Do two things, make the view top visible and the editText smaller
                    }
                    isOpened = true;
                } else if (isOpened == true) {
                    Toast.makeText(getApplicationContext(), "softkeyborad Down!!!", Toast.LENGTH_LONG).show();
                    isOpened = false;
                }
            }
        });
    }
}
