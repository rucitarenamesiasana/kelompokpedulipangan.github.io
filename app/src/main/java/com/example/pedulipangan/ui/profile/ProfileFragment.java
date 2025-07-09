package com.example.pedulipangan.ui.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.pedulipangan.databinding.FragmentProfileFragmentBinding;
import com.example.pedulipangan.ui.home.HomeViewModel;

public class ProfileFragment extends Fragment {

    private FragmentProfileFragmentBinding binding;

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState
    ) {
        HomeViewModel homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentProfileFragmentBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Jika ingin mengaktifkan observer ViewModel, uncomment bagian ini:
        /*
        final TextView textView = binding.textHome;
        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        */

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}

