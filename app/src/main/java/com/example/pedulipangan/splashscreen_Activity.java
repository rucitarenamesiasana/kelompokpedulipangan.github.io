package com.example.pedulipangan;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pedulipangan.R;
import com.example.pedulipangan.onboarding_Activity;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class splashscreen_Activity extends AppCompatActivity {

    private static final int SPLASH_DURATION = 5000; // 5 detik

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        ImageView logo = findViewById(R.id.logoSplash);
        Animation popUpAnim = AnimationUtils.loadAnimation(this, R.anim.pop_up);

        // Tampilkan logo dan mulai animasi
        logo.setVisibility(ImageView.VISIBLE);
        logo.startAnimation(popUpAnim);

        // Setelah 5 detik, lanjut ke onboarding
        new Handler().postDelayed(() -> {
            Intent intent = new Intent(splashscreen_Activity.this, onboarding_Activity.class);
            startActivity(intent);
            finish();
        }, SPLASH_DURATION);

        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder()
                .name("pedulipangan.realm")
                .allowWritesOnUiThread(true)
                .schemaVersion(1)
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(config);

    }
}