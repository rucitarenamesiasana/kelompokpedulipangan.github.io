package com.example.pedulipangan.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pedulipangan.R;
import com.example.pedulipangan.onboarding_Activity;
import com.example.pedulipangan.register_Activity;

public class login_Activity extends AppCompatActivity {

    ImageView btnBack;
    TextView createAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnBack = findViewById(R.id.btnBack);
        createAccount = findViewById(R.id.createAccount);

        // kembali ke onboarding saat tekan panah
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(login_Activity.this, onboarding_Activity.class);
                startActivity(intent);
                finish();
            }
        });

        // navigasi ke register
        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(login_Activity.this, register_Activity.class);
                startActivity(intent);
            }
        });
    }
}
