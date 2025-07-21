package com.example.pedulipangan;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

import io.realm.Realm;
import io.realm.RealmConfiguration;

import com.example.pedulipangan.data.model.User;
import com.example.pedulipangan.ui.login.login_Activity;

public class register_Activity extends AppCompatActivity {

    private EditText fullName, email, password, confirmPassword;
    private Button btnRegister;
    private ImageView btnBack;
    private TextView loginLink;

    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Realm

        Realm.init(this); // ✅ pastikan ada ini dulu

        RealmConfiguration config = new RealmConfiguration.Builder()
                .allowWritesOnUiThread(true)
                .deleteRealmIfMigrationNeeded() // optional: buat dev biar gampang
                .build();

        Realm.setDefaultConfiguration(config);

        Realm.init(getApplicationContext());
        realm = Realm.getDefaultInstance();

        loginLink = findViewById(R.id.loginLink);
        btnRegister = findViewById(R.id.btnRegister);
        fullName = findViewById(R.id.edtUsername);
        email = findViewById(R.id.edtEmail);
        password = findViewById(R.id.edtPassword);
        confirmPassword = findViewById(R.id.confirmPassword);
        btnBack = findViewById(R.id.btnBack);

        // Ganti deprecated onBackPressed
        btnBack.setOnClickListener(v -> {
            finish(); // kembali ke login
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
                realm.executeTransaction(r -> {
                    User newUser = realm.createObject(User.class, nameInput.toLowerCase());
                    newUser.setEmail(emailInput);
                    newUser.setPassword(passInput);
                });

                Toast.makeText(this, "Registrasi berhasil!", Toast.LENGTH_SHORT).show();

                // Pindah ke login
                startActivity(new Intent(this, login_Activity.class));
                finish();
            }
        });

        loginLink.setOnClickListener(v -> {
            startActivity(new Intent(this, login_Activity.class));
            finish();
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (realm != null) realm.close();
    }
}
