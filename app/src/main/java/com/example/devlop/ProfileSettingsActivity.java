package com.example.devlop;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class ProfileSettingsActivity extends Activity {
    private static final String TAG = "ProfileSettings";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_settings);


        Intent intent = getIntent();
        String email = intent.getStringExtra("email");
        Log.d(TAG, "onCreate: " + email);


    }
}
