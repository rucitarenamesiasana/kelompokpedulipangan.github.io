package com.example.pedulipangan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pedulipangan.R;
import com.example.pedulipangan.data.model.NotificationItem;

import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder> {

    private final Context context;
    private final List<NotificationItem> notificationList;
    private final OnNotificationDiscardedListener listener;

    // Interface untuk callback ke Fragment
    public interface OnNotificationDiscardedListener {
        void onDiscarded(String itemId);
    }

    public NotificationAdapter(Context context, List<NotificationItem> notificationList, OnNotificationDiscardedListener listener) {
        this.context = context;
        this.notificationList = notificationList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_notifikasi, parent, false);
        return new NotificationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder holder, int position) {
        NotificationItem item = notificationList.get(position);
        holder.txvNotif.setText(item.getText());

        holder.btnClose.setOnClickListener(v -> {
            // Panggil callback listener ke Fragment
            listener.onDiscarded(item.getItemId());

            // Hapus dari list adapter
            notificationList.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, notificationList.size());
        });
    }

    @Override
    public int getItemCount() {
        return notificationList.size();
    }

    static class NotificationViewHolder extends RecyclerView.ViewHolder {
        TextView txvNotif;
        ImageButton btnClose;

        public NotificationViewHolder(@NonNull View itemView) {
            super(itemView);
            txvNotif = itemView.findViewById(R.id.txvNotif);
            btnClose = itemView.findViewById(R.id.btnClose);
        }
    }
}
