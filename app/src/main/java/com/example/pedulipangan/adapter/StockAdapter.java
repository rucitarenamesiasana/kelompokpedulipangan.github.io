package com.example.pedulipangan.adapter;

import android.app.DatePickerDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pedulipangan.R;
import com.example.pedulipangan.data.model.StockItem;
import com.example.pedulipangan.databinding.LayoutStockItemBinding;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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
            View dialogView = LayoutInflater.from(v.getContext()).inflate(R.layout.dialog_stock, null);

            Spinner sprCategory = dialogView.findViewById(R.id.sprCategory);
            EditText edtAmount = dialogView.findViewById(R.id.edtAmount);
            TextView txvExpiry = dialogView.findViewById(R.id.txvExpiry);
            Button btnDone = dialogView.findViewById(R.id.btnDone);

            // Isi data lama
            edtAmount.setText(String.valueOf(item.getAmount()));

            String[] categories = v.getResources().getStringArray(R.array.product_categories);
            for (int i = 0; i < categories.length; i++) {
                if (categories[i].equals(item.getCategory())) {
                    sprCategory.setSelection(i);
                    break;
                }
            }

            // Set tanggal kedaluwarsa
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(item.getExpiryDate());
            txvExpiry.setText(sdf.format(item.getExpiryDate()));

            txvExpiry.setOnClickListener(view -> {
                new DatePickerDialog(
                        v.getContext(),
                        (picker, year, month, dayOfMonth) -> {
                            calendar.set(year, month, dayOfMonth);
                            txvExpiry.setText(sdf.format(calendar.getTime()));
                        },
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)
                ).show();
            });

            AlertDialog dialog = new AlertDialog.Builder(v.getContext())
                    .setView(dialogView)
                    .setTitle("Edit Stock")
                    .create();

            btnDone.setOnClickListener(view -> {
                String updatedCategory = sprCategory.getSelectedItem().toString();
                String amountStr = edtAmount.getText().toString().trim();
                if (amountStr.isEmpty()) {
                    Toast.makeText(v.getContext(), "Jumlah tidak boleh kosong", Toast.LENGTH_SHORT).show();
                    return;
                }

                int updatedAmount = Integer.parseInt(amountStr);
                Date updatedExpiry = calendar.getTime();

                Realm realm = Realm.getDefaultInstance();
                realm.executeTransaction(r -> {
                    item.setCategory(updatedCategory);
                    item.setAmount(updatedAmount);
                    item.setExpiryDate(updatedExpiry);
                });
                realm.close();

                notifyDataSetChanged();
                Toast.makeText(v.getContext(), "Stok diperbarui", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            });

            dialog.show();
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

        holder.binding.imvCheck.setOnClickListener(v -> {
            Realm realm = Realm.getDefaultInstance();
            realm.executeTransaction(r -> {
                item.setUsedDate(new Date());
            });
            realm.close();

            Toast.makeText(v.getContext(), "Stok ditandai sebagai digunakan tepat waktu", Toast.LENGTH_SHORT).show();
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
