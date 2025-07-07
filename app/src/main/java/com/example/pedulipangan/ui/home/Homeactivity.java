package com.example.pedulipangan.ui.home;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.graphics.Color;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pedulipangan.R;

public class Homeactivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set status bar background to white
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.setStatusBarColor(Color.WHITE);
        }

        // Set dark icons (for light status bar background)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            );
        }
    }
}