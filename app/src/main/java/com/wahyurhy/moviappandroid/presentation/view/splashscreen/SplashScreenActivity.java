package com.wahyurhy.moviappandroid.presentation.view.splashscreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.wahyurhy.moviappandroid.R;
import com.wahyurhy.moviappandroid.presentation.MainActivity;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        startSplashScreen();
    }

    private void startSplashScreen() {
        new Handler().postDelayed(() -> {
            Intent intentMainActivity = new Intent(SplashScreenActivity.this, MainActivity.class);
            intentMainActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intentMainActivity);
        }, 2000);
    }
}