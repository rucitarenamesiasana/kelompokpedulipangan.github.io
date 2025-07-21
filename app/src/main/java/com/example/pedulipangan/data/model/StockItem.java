package com.example.pedulipangan.data.model;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class StockItem extends RealmObject {

    @PrimaryKey
    private String id;
    private String name;
    private String userId;
    private Date expiryDate;
    private Date usedDate;

    private int amount;

    private String category;
    private boolean notificationDiscarded;

    // Getter dan Setter
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setUsedDate(Date usedDate) {
        this.usedDate = usedDate;
    }

    public boolean isNotificationDiscarded() {
        return notificationDiscarded;
    }

    public void setNotificationDiscarded(boolean notificationDiscarded) {
        this.notificationDiscarded = notificationDiscarded;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

}
