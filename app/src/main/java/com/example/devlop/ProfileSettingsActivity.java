package com.example.devlop;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Matrix;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileSettingsActivity extends AppCompatActivity {
    private static final String TAG = "ProfileSettings";
    Toolbar toolbar;
    TextView nameTxt, birthTxt;
    TextInputLayout emailEd, passEd;
    DatePicker picker;
    Button btnDone;
    CircleImageView imageView;
    Uri imageUri;
    private static final int PICK_IMAGE = 100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_settings);

        initView();

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageSelect();
            }
        });


        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String mImageUri = preferences.getString("image", null);

        if (mImageUri != null) {
           // Glide.with(this).load(mImageUri).into(imageView);
            imageView.setImageURI(Uri.parse(mImageUri));
            Glide.with(this).load(mImageUri).into(imageView);
            Log.d(TAG, "onCreate: " + mImageUri);
        } else {
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
        }


        getSharedSettings();

        SharedPreferences sharedPreferences = getSharedPreferences("myPrefs", MODE_PRIVATE);
        String login = sharedPreferences.getString("loginSettings", "");
        String email = sharedPreferences.getString("emailSettings", "");
        String pass = sharedPreferences.getString("passSettings", "");
        nameTxt.setText(login);
        emailEd.getEditText().setText(email);
        passEd.getEditText().setText(pass);

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String date = picker.getDayOfMonth() + "/" + (picker.getMonth() + 1) + "/" + picker.getYear();
                SharedPreferences sharedPreferences = getSharedPreferences("myPrefsSettings", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("date", date);
                editor.putString("emailSettings", emailEd.getEditText().toString());
                editor.putString("passSettings", passEd.getEditText().toString());
                birthTxt.setText(date);
                editor.apply();

            }
        });

    }

    public void getSharedSettings() {
        SharedPreferences sharedGet = getSharedPreferences("myPrefsSettings", MODE_PRIVATE);
        String emailGet = sharedGet.getString("emailSettings", "");
        String passGet = sharedGet.getString("passSettings", "");
        String date = sharedGet.getString("date", "");
        emailEd.getEditText().setText(emailGet);
        passEd.getEditText().setText(passGet);
        birthTxt.setText(date);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && null != data) {

                imageUri = data.getData();
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("image", String.valueOf(imageUri));
                editor.commit();
                Glide.with(this).load(imageUri).into(imageView);

            } else {
                Toast.makeText(this, "No.", Toast.LENGTH_LONG).show();
            }

        } catch (Exception e) {
            Toast.makeText(this, "Oops! Sorry", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }
    public void imageSelect() {
        permissionsCheck();
        Intent intent;
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
            intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
        }else{
            intent = new Intent(Intent.ACTION_GET_CONTENT);
        }
        intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);

    }

    public void permissionsCheck() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
            return;
        }
    }
    private void initView() {
        toolbar = findViewById(R.id.toolBarSettings);
        nameTxt = findViewById(R.id.txtNameSettings);
        emailEd = findViewById(R.id.textInputLayoutEmail);
        passEd = findViewById(R.id.textInputLayoutPass);
        birthTxt = findViewById(R.id.birthTxt);
        picker = findViewById(R.id.datePicker);
        imageView = findViewById(R.id.imgProfilePhoto);

        btnDone = findViewById(R.id.doneBtn);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        final Drawable upArrow = getResources().getDrawable(R.drawable.ic_back);
        upArrow.setColorFilter(getResources().getColor(android.R.color.darker_gray), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        getSupportActionBar().setTitle("Настройки аккаунта");

    }
}
