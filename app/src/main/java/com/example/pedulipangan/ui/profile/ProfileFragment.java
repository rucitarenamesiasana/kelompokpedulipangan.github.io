package com.example.pedulipangan.ui.profile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.pedulipangan.R;
import com.example.pedulipangan.databinding.FragmentProfileFragmentBinding;
import com.example.pedulipangan.onboarding_Activity;
import com.example.pedulipangan.data.model.User;

import io.realm.Realm;

public class ProfileFragment extends Fragment {

    private FragmentProfileFragmentBinding binding;
    private Realm realm;
    private User currentUser;

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState
    ) {
        binding = FragmentProfileFragmentBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Inisialisasi Realm
        realm = Realm.getDefaultInstance();

        // Ambil username dari SharedPreferences
        SharedPreferences prefs = requireActivity().getSharedPreferences("prefs", Context.MODE_PRIVATE);
        String username = prefs.getString("logged_in_username", null); // pastikan ini benar

        if (username != null) {
            currentUser = realm.where(User.class)
                    .equalTo("username", username.toLowerCase())
                    .findFirst();

            if (currentUser != null) {
                // Menampilkan "Hi, Username"
                binding.txvNamaUser.setText("Hi, " + currentUser.getUsername());

                // Menampilkan email
                binding.email.setText(currentUser.getEmail());

                // Setup spinner gender
                ArrayAdapter<CharSequence> genderAdapter = ArrayAdapter.createFromResource(
                        requireContext(),
                        R.array.gender_options,
                        android.R.layout.simple_spinner_item
                );
                genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                binding.spinnerGender.setAdapter(genderAdapter);

                // Set pilihan gender yang sudah disimpan
                String savedGender = currentUser.getGender();
                if (savedGender != null) {
                    int position = genderAdapter.getPosition(savedGender);
                    binding.spinnerGender.setSelection(position);
                }
            }
        }


        // Tombol Logout
        binding.btnLogout.setOnClickListener(v -> {
            saveGenderToRealm(); // Simpan gender yang dipilih

            // Hapus sesi login
            prefs.edit().remove("logged_in_username").apply();

            // Navigasi ke onboarding
            Intent intent = new Intent(requireContext(), onboarding_Activity.class);
            startActivity(intent);
            requireActivity().finish();
        });

        return root;
    }

    private void saveGenderToRealm() {
        if (currentUser != null) {
            String selectedGender = binding.spinnerGender.getSelectedItem().toString();
            realm.executeTransaction(r -> currentUser.setGender(selectedGender));
        }
    }

    @Override
    public void onDestroyView() {
        saveGenderToRealm(); // Simpan saat fragment dihancurkan
        if (realm != null) realm.close();
        binding = null;
        super.onDestroyView();
    }
}
