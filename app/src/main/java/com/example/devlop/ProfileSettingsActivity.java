package com.example.devlop;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Locale;

public class ProfileSettingsActivity extends AppCompatActivity {
    private static final String TAG = "ProfileSettings";
    Toolbar toolbar;
    TextView nameTxt, birthTxt;
    TextInputLayout emailEd, passEd;
    DatePicker picker;
    Button btnDone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_settings);

        toolbar = findViewById(R.id.toolBarSettings);
        nameTxt = findViewById(R.id.txtNameSettings);
        emailEd = findViewById(R.id.textInputLayoutEmail);
        passEd = findViewById(R.id.textInputLayoutPass);
        birthTxt = findViewById(R.id.birthTxt);
        picker = findViewById(R.id.datePicker);

        btnDone = findViewById(R.id.doneBtn);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        final Drawable upArrow = getResources().getDrawable(R.drawable.ic_back);
        upArrow.setColorFilter(getResources().getColor(android.R.color.darker_gray), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        getSupportActionBar().setTitle("Настройки аккаунта");

        getSharedSettings();

        SharedPreferences sharedPreferences = getSharedPreferences("myPrefs",MODE_PRIVATE);
        String login = sharedPreferences.getString("loginSettings","");
        String email = sharedPreferences.getString("emailSettings","");
        String pass = sharedPreferences.getString("passSettings","");
        nameTxt.setText(login);
        emailEd.getEditText().setText(email);
        passEd.getEditText().setText(pass);

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String date = picker.getDayOfMonth() + "/"+ (picker.getMonth() + 1)+"/"+picker.getYear();
                SharedPreferences sharedPreferences = getSharedPreferences("myPrefsSettings",MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("date",date);
                editor.putString("emailSettings",emailEd.getEditText().toString());
                editor.putString("passSettings",passEd.getEditText().toString());
                birthTxt.setText(date);
                editor.apply();
            }
        });

      //  getSharedSettings();

    }
        public void getSharedSettings(){
            SharedPreferences sharedGet = getSharedPreferences("myPrefsSettings",MODE_PRIVATE);
            String emailGet = sharedGet.getString("emailSettings","");
            String passGet = sharedGet.getString("passSettings","");
            String date = sharedGet.getString("date","");
            emailEd.getEditText().setText(emailGet);
            passEd.getEditText().setText(passGet);
            birthTxt.setText(date);
        }
}
