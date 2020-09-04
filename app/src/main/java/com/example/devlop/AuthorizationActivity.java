package com.example.devlop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class AuthorizationActivity extends AppCompatActivity {
    TextView forgotPassTxt, signUpTxt;
    EditText edLogin, edPass;
    Button signInBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorization);

        initView();
    }

    private void initView() {
        forgotPassTxt = findViewById(R.id.forgotPasstxt);
        signUpTxt = findViewById(R.id.signUp);
        edLogin = findViewById(R.id.edLogin);
        edPass = findViewById(R.id.edPass);
        signInBtn = findViewById(R.id.logInBtn);

        signUpTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),RegistrationActivity.class);
                startActivity(i);
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.
                INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        return true;
    }
}
