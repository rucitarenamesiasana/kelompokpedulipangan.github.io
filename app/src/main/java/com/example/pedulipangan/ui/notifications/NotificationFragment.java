package com.example.pedulipangan.ui.notifications;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.pedulipangan.adapter.NotificationAdapter;
import com.example.pedulipangan.data.model.NotificationItem;
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
    private Realm realm;

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState
    ) {
        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        realm = Realm.getDefaultInstance();

        // Ambil userId dari SharedPreferences
        String userId = requireContext()
                .getSharedPreferences("prefs", android.content.Context.MODE_PRIVATE)
                .getString("logged_in_username", "User");

        RealmResults<StockItem> stocks = realm.where(StockItem.class)
                .equalTo("userId", userId)
                .findAll();

        Date today = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(today);
        cal.add(Calendar.DAY_OF_MONTH, 2);
        Date twoDaysLater = cal.getTime();

        List<NotificationItem> notifList = new ArrayList<>();

        for (StockItem item : stocks) {
            if (item.isNotificationDiscarded()) continue;

            Date expiry = item.getExpiryDate();
            Date used = item.getUsedDate();

            if (expiry != null && expiry.before(today)) {
                notifList.add(new NotificationItem("🗑️ Produk " + item.getName() + " sudah kadaluarsa!", item.getId()));
            } else if (expiry != null && expiry.before(twoDaysLater) && used == null) {
                notifList.add(new NotificationItem("⚠️ Produk " + item.getName() + " akan kadaluarsa dalam 2 hari!", item.getId()));
            }

            if (used != null && expiry != null && !used.after(expiry)) {
                notifList.add(new NotificationItem("🎉 Produk " + item.getName() + " berhasil digunakan tepat waktu!", item.getId()));
            }
        }

        // Atur adapter dengan listener untuk discard
        NotificationAdapter adapter = new NotificationAdapter(requireContext(), notifList, discardedItemId -> {
            realm.executeTransaction(r -> {
                StockItem item = r.where(StockItem.class)
                        .equalTo("id", discardedItemId)
                        .findFirst();
                if (item != null) item.setNotificationDiscarded(true);
            });
        });

        binding.recyclerNotif.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.recyclerNotif.setAdapter(adapter);

        return root;
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
