package com.example.pedulipangan.ui.notifications;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.pedulipangan.databinding.FragmentNotificationsBinding;

public class NotificationFragment extends Fragment {

    private FragmentNotificationsBinding binding;

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState
    ) {
        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Tampilkan semua notifikasi saat fragment dibuka
        binding.cardNotif1.setVisibility(View.VISIBLE);
        binding.cardNotif2.setVisibility(View.VISIBLE);
        binding.cardNotif3.setVisibility(View.VISIBLE);

        // Handle tombol X → hanya menyembunyikan notifikasi
        binding.btnClose1.setOnClickListener(v -> binding.cardNotif1.setVisibility(View.GONE));
        binding.btnClose2.setOnClickListener(v -> binding.cardNotif2.setVisibility(View.GONE));
        binding.btnClose3.setOnClickListener(v -> binding.cardNotif3.setVisibility(View.GONE));

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
