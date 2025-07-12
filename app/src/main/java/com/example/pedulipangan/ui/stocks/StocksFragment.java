package com.example.pedulipangan.ui.stocks;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.pedulipangan.adapter.StockAdapter;
import com.example.pedulipangan.data.model.StockItem;
import com.example.pedulipangan.databinding.FragmentStocksBinding;

import io.realm.Realm;
import io.realm.RealmResults;

public class StocksFragment extends Fragment {

    private FragmentStocksBinding binding;
    private StockAdapter adapter;
    private Realm realm;

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState
    ) {
        binding = FragmentStocksBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        realm = Realm.getDefaultInstance();
        RealmResults<StockItem> stocks = realm.where(StockItem.class).findAll();

        binding.rvStocks.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new StockAdapter(stocks);
        binding.rvStocks.setAdapter(adapter);

        binding.btnAdd.setOnClickListener(v -> {
            AddStock dialog = new AddStock();
            dialog.setOnStockAddedListener(() -> {
                RealmResults<StockItem> updatedStocks = realm.where(StockItem.class).findAll();
                adapter.updateData(updatedStocks);
            });
            dialog.show(getParentFragmentManager(), "AddStockDialog");
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (realm != null) {
            realm.close();
        }
        binding = null;
    }
}