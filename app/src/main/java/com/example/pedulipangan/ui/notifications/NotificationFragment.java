package com.example.pedulipangan.ui.notifications;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.pedulipangan.adapter.NotificationAdapter;
import com.example.pedulipangan.data.model.StockItem;
import com.example.pedulipangan.databinding.FragmentNotificationsBinding;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

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

        // Ambil userId dari SharedPreferences
        String userId = requireContext()
                .getSharedPreferences("prefs", Context.MODE_PRIVATE)
                .getString("logged_in_username", "User");

        Realm realm = Realm.getDefaultInstance();

        RealmResults<StockItem> stocks = realm.where(StockItem.class)
                .equalTo("userId", userId)
                .findAll();

        int soonToExpire = 0;
        int wasted = 0;
        int onTime = 0;

        Date today = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(today);
        cal.add(Calendar.DAY_OF_MONTH, 2);
        Date twoDaysLater = cal.getTime();

        for (StockItem item : stocks) {
            Date expiry = item.getExpiryDate();
            Date used = item.getUsedDate();

            if (expiry != null && expiry.before(today)) {
                wasted++;
            } else if (expiry != null && expiry.before(twoDaysLater)) {
                soonToExpire++;
            }

            if (used != null && expiry != null && !used.after(expiry)) {
                onTime++;
            }
        }

        // Buat list notifikasi
        List<String> notifList = new ArrayList<>();

        if (soonToExpire > 0) {
            notifList.add("⚠️ Ada " + soonToExpire + " barang yang akan kadaluarsa dalam 2 hari!");
        }

        if (onTime > 0) {
            notifList.add("🎉 Kamu berhasil menggunakan " + onTime + " barang tepat waktu. Lanjutkan ya!");
        }

        if (wasted > 0) {
            notifList.add("🗑️ Ada " + wasted + " barang yang sudah kadaluarsa. Coba lebih diperhatikan lagi, ya!");
        }

        // Atur RecyclerView
        NotificationAdapter adapter = new NotificationAdapter(requireContext(), notifList);
        binding.recyclerNotif.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.recyclerNotif.setAdapter(adapter);



        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
