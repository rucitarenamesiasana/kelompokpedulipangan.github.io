package com.example.pedulipangan;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pedulipangan.data.model.User;
import com.example.pedulipangan.ui.login.login_Activity;

import io.realm.Realm;
import io.realm.RealmResults;

public class register_Activity extends AppCompatActivity {

    ImageView btnBack;
    TextView loginLink;
    Button btnRegister;

    EditText fullName, email, password, confirmPassword;
    Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Realm.init(this);
        realm = Realm.getDefaultInstance();

        btnBack = findViewById(R.id.btnBack);
        loginLink = findViewById(R.id.loginLink);
        btnRegister = findViewById(R.id.btnRegister);

        fullName = findViewById(R.id.edtUsername);
        email = findViewById(R.id.edtEmail);
        password = findViewById(R.id.edtPassword);
        confirmPassword = findViewById(R.id.confirmPassword);

        btnBack.setOnClickListener(view -> {
            startActivity(new Intent(this, onboarding_Activity.class));
            finish();
        });

        btnRegister.setOnClickListener(v -> {
            String nameInput = fullName.getText().toString().trim();
            String emailInput = email.getText().toString().trim();
            String passInput = password.getText().toString().trim();
            String confirmPassInput = confirmPassword.getText().toString().trim();

            if (nameInput.isEmpty() || emailInput.isEmpty() || passInput.isEmpty() || confirmPassInput.isEmpty()) {
                Toast.makeText(this, "Semua kolom wajib diisi", Toast.LENGTH_SHORT).show();
            } else if (!passInput.equals(confirmPassInput)) {
                Toast.makeText(this, "Password tidak cocok", Toast.LENGTH_SHORT).show();
            } else {
                User existing = realm.where(User.class).equalTo("username", nameInput.toLowerCase()).findFirst();
                if (existing != null) {
                    Toast.makeText(this, "Username sudah digunakan", Toast.LENGTH_SHORT).show();
                    return;
                }
                realm.executeTransaction(r -> {
                    User user = r.createObject(User.class, nameInput.toLowerCase());
                    user.setEmail(emailInput);
                    user.setPassword(passInput);
                });

                SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
                prefs.edit().putString("logged_in_username", nameInput.toLowerCase()).apply();

                Toast.makeText(this, "Registrasi berhasil", Toast.LENGTH_SHORT).show();
                toLogin();
            }
        });

        loginLink.setOnClickListener(view -> {
            startActivity(new Intent(this, login_Activity.class));
        });
    }

    public void toLogin() {
        startActivity(new Intent(this, login_Activity.class));
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (realm != null && !realm.isClosed()) realm.close();
    }
}