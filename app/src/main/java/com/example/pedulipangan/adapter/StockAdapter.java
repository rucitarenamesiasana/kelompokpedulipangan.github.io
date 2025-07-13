package com.example.pedulipangan.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pedulipangan.data.model.StockItem;
import com.example.pedulipangan.databinding.LayoutStockItemBinding;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

public class StockAdapter extends RecyclerView.Adapter<StockAdapter.StockViewHolder> {

    private RealmResults<StockItem> stockList;
    private Realm realm;
    private RealmChangeListener<RealmResults<StockItem>> listener;

    public StockAdapter(RealmResults<StockItem> stockList) {
        this.stockList = stockList;
        this.realm = Realm.getDefaultInstance();

        listener = results -> notifyDataSetChanged();
        this.stockList.addChangeListener(listener);
    }

    @NonNull
    @Override
    public StockViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutStockItemBinding binding = LayoutStockItemBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new StockViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull StockViewHolder holder, int position) {
        StockItem item = stockList.get(position);
        if (item == null) return;

        holder.binding.txvCategory.setText(item.getCategory());
        holder.binding.txvAmount.setText(item.getAmount() + " pcs");

        String formattedDate = new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
                .format(item.getExpiryDate());
        holder.binding.txvExpiry.setText("Expires: " + formattedDate);

        holder.binding.btnEdit.setOnClickListener(v -> {
            Toast.makeText(v.getContext(), "Edit belum di-implementasi", Toast.LENGTH_SHORT).show();
        });

        holder.binding.btnDiscard.setOnClickListener(v -> {
            Realm realm = Realm.getDefaultInstance();
            realm.executeTransaction(r -> {
                item.deleteFromRealm();
            });
            realm.close();
            Toast.makeText(v.getContext(), "Stok dihapus", Toast.LENGTH_SHORT).show();
            notifyDataSetChanged();
        });
    }

    @Override
    public int getItemCount() {
        return stockList != null ? stockList.size() : 0;
    }

    public static class StockViewHolder extends RecyclerView.ViewHolder {
        LayoutStockItemBinding binding;

        public StockViewHolder(@NonNull LayoutStockItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public void updateData(RealmResults<StockItem> newList) {
        this.stockList = newList;
        notifyDataSetChanged();
    }

    public void close() {
        if (stockList != null && listener != null) {
            stockList.removeChangeListener(listener);
        }
        if (realm != null && !realm.isClosed()) {
            realm.close();
        }
    }

}