package com.example.pedulipangan.ui.stocks;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.pedulipangan.databinding.FragmentStocksBinding;

public class StocksFragment extends Fragment {

    private FragmentStocksBinding binding;

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState
    ) {
        binding = FragmentStocksBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Bisa tambahkan event ke button kalau mau:
        // binding.btnPlus.setOnClickListener(v -> { ... });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
