package com.example.pedulipangan.ui.home;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.pedulipangan.R;
import com.example.pedulipangan.data.model.StockItem;
import com.example.pedulipangan.databinding.FragmentHomeBinding;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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

        realm = Realm.getDefaultInstance();

        SharedPreferences prefs = requireContext().getSharedPreferences("prefs", Context.MODE_PRIVATE);
        String username = prefs.getString("logged_in_username", "User");

        binding.txtGreeting.setText("Hi, " + capitalize(username));

        showStockSummary(username);
        showNotificationsPreview(root, username);

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        SharedPreferences prefs = requireContext().getSharedPreferences("prefs", Context.MODE_PRIVATE);
        String username = prefs.getString("logged_in_username", "User");

        showStockSummary(username);
        showNotificationsPreview(binding.getRoot(), username);
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

            if (daysLeft >= 0 && daysLeft <= 2 && usedDate == null && !item.isNotificationDiscarded()) {
                expiringSoon++;
            } else if (daysLeft < 0 && usedDate == null && !item.isNotificationDiscarded()) {
                wasted++;
            }

            if (usedDate != null && expiryDate != null && !usedDate.after(expiryDate) && !item.isNotificationDiscarded()) {
                usedOnTime++;
            }
        }

        binding.txtExpiringSoon.setText(String.valueOf(expiringSoon));
        binding.txtUsedOnTime.setText(String.valueOf(usedOnTime));
        binding.txtWasted.setText(String.valueOf(wasted));
    }

    private void showNotificationsPreview(View rootView, String userId) {
        RealmResults<StockItem> stocks = realm.where(StockItem.class)
                .equalTo("userId", userId)
                .findAll();

        Date today = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(today);
        cal.add(Calendar.DAY_OF_MONTH, 2);
        Date twoDaysLater = cal.getTime();

        List<String> notifList = new ArrayList<>();

        for (StockItem item : stocks) {
            if (item.isNotificationDiscarded()) continue;

            Date expiry = item.getExpiryDate();
            Date used = item.getUsedDate();

            if (expiry != null && expiry.before(today) && used == null) {
                notifList.add("🗑️ Produk " + item.getName() + " sudah kadaluarsa.");
            } else if (expiry != null && expiry.before(twoDaysLater) && used == null) {
                notifList.add("⚠️ Produk " + item.getName() + " akan kadaluarsa dalam 2 hari.");
            }

            if (used != null && expiry != null && !used.after(expiry)) {
                notifList.add("🎉 Produk " + item.getName() + " berhasil digunakan tepat waktu.");
            }
        }

        List<String> previewList = notifList.subList(0, Math.min(3, notifList.size()));

        LinearLayout notifContainer = rootView.findViewById(R.id.notif_preview_container);
        notifContainer.removeAllViews();

        LayoutInflater inflater = LayoutInflater.from(requireContext());

        for (String notif : previewList) {
            View notifView = inflater.inflate(R.layout.item_notification_preview, notifContainer, false);
            TextView text = notifView.findViewById(R.id.textNotifPreview);
            text.setText(notif);
            notifContainer.addView(notifView);
        }
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
