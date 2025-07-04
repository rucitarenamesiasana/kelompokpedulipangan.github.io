package com.example.pedulipangan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pedulipangan.ui.home.HomeFragment;
import com.example.pedulipangan.ui.login.login_Activity;

public class register_Activity extends AppCompatActivity {

    ImageView btnBack;
    TextView loginLink;

    Button btnRegister;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        btnBack = findViewById(R.id.btnBack);
        loginLink = findViewById(R.id.loginLink);
        btnRegister = findViewById(R.id.btnRegister);

        // kembali ke onboarding saat tekan panah
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(register_Activity.this, onboarding_Activity.class);
                startActivity(intent);
                finish();
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toLogin();
            }
        });
        // navigasi ke login
        loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(register_Activity.this, login_Activity.class);
                startActivity(intent);
            }
        });
    }
    public void toLogin(){
        Intent intent = new Intent(register_Activity.this, login_Activity.class);
        startActivity(intent);
        finish();
    }
}
