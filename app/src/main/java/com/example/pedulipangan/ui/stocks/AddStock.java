package com.example.pedulipangan.ui.stocks;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import java.util.UUID;

import com.example.pedulipangan.R;
import com.example.pedulipangan.data.model.StockItem;
import com.example.pedulipangan.databinding.DialogStockBinding;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import android.content.Context;
import android.content.SharedPreferences;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import io.realm.Realm;

public class AddStock extends DialogFragment {

    private DialogStockBinding binding;
    private Realm realm;

    public interface OnStockAddedListener {
        void onStockAdded();
    }

    private OnStockAddedListener listener;

    public void setOnStockAddedListener(OnStockAddedListener listener) {
        this.listener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState
    ) {
        binding = DialogStockBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(
            @NonNull View view,
            @Nullable Bundle savedInstanceState
    ) {
        super.onViewCreated(view, savedInstanceState);
        realm = Realm.getDefaultInstance();

        setupCategorySpinner();
        setupDatePicker();
        setupButton();
    }

    private void setupCategorySpinner() {
        ArrayAdapter<CharSequence> categoryAdapter = ArrayAdapter.createFromResource(
                getContext(),
                R.array.product_categories,
                android.R.layout.simple_spinner_item
        );
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.sprCategory.setAdapter(categoryAdapter);
    }

    private void setupDatePicker() {
        binding.txvExpiry.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    getContext(),
                    (view1, year, month, dayOfMonth) -> {
                        calendar.set(year, month, dayOfMonth);
                        String formattedDate = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                                .format(calendar.getTime());
                        binding.txvExpiry.setText(formattedDate);
                    },
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
            );
            datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
            datePickerDialog.show();
        });
    }

    private void setupButton() {
        binding.btnDone.setOnClickListener(v -> {
            String category = binding.sprCategory.getSelectedItem().toString();
            String amountStr = binding.edtAmount.getText().toString();
            String expiryStr = binding.txvExpiry.getText().toString();

            if (category.isEmpty() || amountStr.isEmpty() || expiryStr.equals("dd/mm/yyyy")) {
                Toast.makeText(getContext(), "Lengkapi semua data!", Toast.LENGTH_SHORT).show();
                return;
            }

            int amount;
            try {
                amount = Integer.parseInt(amountStr);
            } catch (NumberFormatException e) {
                Toast.makeText(getContext(), "Jumlah tidak valid", Toast.LENGTH_SHORT).show();
                return;
            }

            Date expiryDate;
            try {
                expiryDate = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).parse(expiryStr);
            } catch (ParseException e) {
                Toast.makeText(getContext(), "Tanggal tidak valid", Toast.LENGTH_SHORT).show();
                return;
            }

            // Cek apakah tanggal yang dipilih sudah lewat
            Date today = new Date();
            if (expiryDate.before(today)) {
                Toast.makeText(getContext(), "Tanggal kadaluarsa sudah lewat. Barang akan ditandai sebagai wasted.", Toast.LENGTH_LONG).show();
            }

            // Ambil userId dari SharedPreferences
            SharedPreferences prefs = requireContext().getSharedPreferences("prefs", Context.MODE_PRIVATE);
            String userId = prefs.getString("logged_in_username", "User");

            // Simpan ke database Realm
            realm.executeTransaction(r -> {
                StockItem item = r.createObject(StockItem.class, UUID.randomUUID().toString());
                item.setCategory(category);
                item.setAmount(amount);
                item.setExpiryDate(expiryDate);
                item.setUserId(userId);

                // Tidak perlu set wasted sekarang, karena sudah dihitung otomatis di HomeFragment
                // Tapi kamu bisa tambahkan logika tambahan jika mau tandai manual
            });

            // Notify listener agar HomeFragment bisa refresh
            if (listener != null) {
                listener.onStockAdded();
            }

            dismiss();
        });
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
