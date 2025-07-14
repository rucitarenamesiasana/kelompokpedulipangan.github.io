package com.example.pedulipangan.ui.home;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.pedulipangan.data.model.StockItem;
import com.example.pedulipangan.databinding.FragmentHomeBinding;

import java.util.Date;

import io.realm.Realm;
import io.realm.RealmResults;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private Realm realm;

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState
    ) {
        HomeViewModel homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Inisialisasi Realm
        realm = Realm.getDefaultInstance();

        // Ambil username dari SharedPreferences
        SharedPreferences prefs = requireContext().getSharedPreferences("prefs", Context.MODE_PRIVATE);
        String username = prefs.getString("logged_in_username", "User");

        // Set greeting
        binding.txtGreeting.setText("Hi, " + capitalize(username));

        // Tampilkan ringkasan stok
        showStockSummary(username);

        return root;
    }

    private void showStockSummary(String userId) {
        Date today = new Date();
        int expiringSoon = 0;
        int usedOnTime = 0;
        int wasted = 0;

        RealmResults<StockItem> userStocks = realm.where(StockItem.class)
                .equalTo("userId", userId)
                .findAll();

        for (StockItem item : userStocks) {
            Date expiryDate = item.getExpiryDate();
            Date usedDate = item.getUsedDate();

            if (expiryDate == null) continue;

            long daysLeft = (expiryDate.getTime() - today.getTime()) / (1000 * 60 * 60 * 24);

            if (daysLeft >= 0 && daysLeft <= 2) {
                expiringSoon++;
            } else if (daysLeft < 0 && usedDate == null) {
                wasted++;
            }

            if (usedDate != null && usedDate.before(expiryDate)) {
                usedOnTime++;
            }
        }

        binding.txtExpiringSoon.setText(String.valueOf(expiringSoon));
        binding.txtUsedOnTime.setText(String.valueOf(usedOnTime));
        binding.txtWasted.setText(String.valueOf(wasted));
    }


    private String capitalize(String input) {
        if (input == null || input.isEmpty()) return "";
        return input.substring(0, 1).toUpperCase() + input.substring(1);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (realm != null && !realm.isClosed()) {
            realm.close();
        }
        binding = null;
    }
}
