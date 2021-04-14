package com.example.devlop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AuthorizationActivity extends AppCompatActivity {
    private static final String TAG = "AuthorizationActivity";
    TextView forgotPassTxt, signUpTxt;
    EditText edLogin, edPass;
    Button signInBtn;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorization);

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged111: " + user.toString());
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                updateUI(user);
            }
        };
        initView();
      //  mAuth.signOut();
    }
    private void updateUI(FirebaseUser user) {
        if (user != null) {
            Log.d(TAG, "updateUI: user already auth " );
          //  startActivity(new Intent(getApplicationContext(), MainActivity.class));
        } else {

        }
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
                startActivity(new Intent(getApplicationContext(),RegistrationActivity.class));
            }
        });

        forgotPassTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),RestorePassActivity.class));
            }
        });
        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pass = edPass.getText().toString().trim();
                String username = edLogin.getText().toString().trim();

                if (TextUtils.isEmpty(username)) {
                    edLogin.setError("email пустой");
                    return;
                }
                if (TextUtils.isEmpty(pass)) {
                    edPass.setError("Пароль пустой");
                    return;
                }

                if (pass.length() < 6){
                    edPass.setError("Пароль должен быть больше 6-ти символов");
                }
                mAuth.signInWithEmailAndPassword(username, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Пользователь успешно вошёл", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(), "неверный email или пароль", Toast.LENGTH_LONG).show();
                        }
                    }
                });
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
    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
