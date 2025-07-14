package com.example.pedulipangan.ui.notifications;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.pedulipangan.databinding.FragmentNotificationsBinding;

import io.realm.Realm;
import io.realm.RealmConfiguration;
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

        // Inisialisasi Realm
        Realm.init(requireContext());
        RealmConfiguration config = new RealmConfiguration.Builder()
                .allowWritesOnUiThread(true)
                .build();
        realm = Realm.getInstance(config);

        String userId = getCurrentUserId();

        // 🟡 Reset notifikasi setiap fragment dibuka
        resetNotificationsForUser(userId);

        // Tampilkan semua notifikasi (pastikan semua visible)
        binding.cardNotif1.setVisibility(View.VISIBLE);
        binding.cardNotif2.setVisibility(View.VISIBLE);
        binding.cardNotif3.setVisibility(View.VISIBLE);

        // Handle tombol X
        binding.btnClose1.setOnClickListener(v -> dismissNotification(userId, "notif1", binding.cardNotif1));
        binding.btnClose2.setOnClickListener(v -> dismissNotification(userId, "notif2", binding.cardNotif2));
        binding.btnClose3.setOnClickListener(v -> dismissNotification(userId, "notif3", binding.cardNotif3));

        return root;
    }

    private void checkDismissed(String userId, String notifId, View cardView) {
        NotificationStatus status = realm.where(NotificationStatus.class)
                .equalTo("id", userId + "_" + notifId)
                .findFirst();

        if (status != null && status.isDismissed()) {
            cardView.setVisibility(View.GONE);
        }
    }

    private void dismissNotification(String userId, String notifId, View cardView) {
        cardView.setVisibility(View.GONE);

        realm.executeTransaction(r -> {
            NotificationStatus status = r.where(NotificationStatus.class)
                    .equalTo("id", userId + "_" + notifId)
                    .findFirst();

            if (status == null) {
                status = r.createObject(NotificationStatus.class, userId + "_" + notifId);
                status.setUserId(userId);
                status.setNotifId(notifId);
            }
            status.setDismissed(true);
        });
    }

    private void resetNotificationsForUser(String userId) {
        realm.executeTransaction(r -> {
            RealmResults<NotificationStatus> statuses = r.where(NotificationStatus.class)
                    .equalTo("userId", userId)
                    .findAll();
            statuses.deleteAllFromRealm();
        });
    }

    private String getCurrentUserId() {
        // Ganti sesuai sistem login kamu
        return "user123"; // HARUS diganti ke ID user yang login
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        if (realm != null && !realm.isClosed()) {
            realm.close();
        }
    }
}
