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

import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder> {

    private final Context context;
    private final List<String> notificationList;

    public NotificationAdapter(Context context, List<String> notificationList) {
        this.context = context;
        this.notificationList = notificationList;
    }

    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_notifikasi, parent, false);
        return new NotificationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder holder, int position) {
        String notifText = notificationList.get(position);
        holder.txvNotif.setText(notifText);

        holder.btnClose.setOnClickListener(v -> {
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
