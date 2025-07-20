package com.example.pedulipangan.data.model;

import java.util.Date;
import java.util.UUID;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class StockItem extends RealmObject {

    @PrimaryKey
    private String id;

    private String category;
    private int amount;
    private Date expiryDate;
    private Date usedDate;        // Tanggal dipakai (jika ada)
    private String userId;        // Pemilik stok (user login)

    // Status saat ini: "available", "used", "expired", "wasted", "deleted"
    private String status;

    private Date createdAt;       // Tanggal dibuat
    private Date deletedAt;       // Tanggal dihapus (jika status == "deleted")

    // Constructor: set ID, tanggal dibuat, dan status default "available"
    public StockItem() {
        this.id = UUID.randomUUID().toString();
        this.createdAt = new Date();
        this.status = "available";
    }

    // === Getter & Setter ===

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

    public Date getUsedDate() {
        return usedDate;
    }

    public void setUsedDate(Date usedDate) {
        this.usedDate = usedDate;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Date deletedAt) {
        this.deletedAt = deletedAt;
    }
}
