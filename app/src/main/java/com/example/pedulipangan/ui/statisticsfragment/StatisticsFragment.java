package com.example.pedulipangan.ui.stocks;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.pedulipangan.databinding.FragmentStatisticsFragmentBinding;

public class StatisticsFragment extends Fragment {

    private FragmentStatisticsFragmentBinding binding;

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState
    ) {
        binding = FragmentStatisticsFragmentBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Tambahkan UI logic di sini kalau ada

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
