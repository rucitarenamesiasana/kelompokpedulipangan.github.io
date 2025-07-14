package com.example.pedulipangan.data.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

import java.util.Date;
import java.util.UUID;

public class StockItem extends RealmObject {

    @PrimaryKey
    private String id = UUID.randomUUID().toString();
    private String category;
    private int amount;
    private Date expiryDate;
    private Date usedDate;
    private String userId; // ✅ Tambahkan ini

    // Getter & Setter untuk userId
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getUsedDate() {
        return usedDate;
    }

    public void setUsedDate(Date usedDate) {
        this.usedDate = usedDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }
}
