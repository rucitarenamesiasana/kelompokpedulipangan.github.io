package com.example.pedulipangan.ui.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pedulipangan.MainActivity;
import com.example.pedulipangan.R;
import com.example.pedulipangan.data.model.User;
import com.example.pedulipangan.onboarding_Activity;
import com.example.pedulipangan.register_Activity;

import io.realm.Realm;

public class login_Activity extends AppCompatActivity {

    ImageView btnBack;
    TextView createAccount, forgotPassword;
    Button btnLogin;
    EditText email, password;

    Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Realm.init(this);
        realm = Realm.getDefaultInstance();

        btnBack = findViewById(R.id.btnBack);
        createAccount = findViewById(R.id.createAccount);
        forgotPassword = findViewById(R.id.forgotPassword);
        btnLogin = findViewById(R.id.btnLogin);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);

        btnBack.setOnClickListener(view -> {
            startActivity(new Intent(this, onboarding_Activity.class));
            finish();
        });

        btnLogin.setOnClickListener(view -> {
            String inputEmail = email.getText().toString().trim();
            String inputPassword = password.getText().toString().trim();

            if (inputEmail.isEmpty() || inputPassword.isEmpty()) {
                Toast.makeText(this, "Silakan isi email dan password terlebih dahulu", Toast.LENGTH_SHORT).show();
                return;
            }

            User user = realm.where(User.class).equalTo("email", inputEmail).findFirst();
            if (user != null && user.getPassword().equals(inputPassword)) {
                SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
                prefs.edit().putString("logged_in_username", user.getUsername()).apply();

                Toast.makeText(this, "Login berhasil", Toast.LENGTH_SHORT).show();
                toMain();
            } else {
                Toast.makeText(this, "Email atau password salah", Toast.LENGTH_SHORT).show();
            }
        });

        createAccount.setOnClickListener(view -> {
            startActivity(new Intent(this, register_Activity.class));
        });

        forgotPassword.setOnClickListener(view ->
                Toast.makeText(this, "Fitur 'Lupa Password' belum tersedia", Toast.LENGTH_SHORT).show()
        );
    }

    private void toMain() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (realm != null && !realm.isClosed()) realm.close();
    }
}