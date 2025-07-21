package com.example.pedulipangan.data.model;

public class NotificationItem {
    private String text;
    private String itemId; // ← Tambahkan ini
    private boolean discarded;

    // Constructor dengan itemId
    public NotificationItem(String text, String itemId) {
        this.text = text;
        this.itemId = itemId;
        this.discarded = false;
    }

    public String getText() {
        return text;
    }

    public String getItemId() {
        return itemId;
    }

    public boolean isDiscarded() {
        return discarded;
    }

    public void setDiscarded(boolean discarded) {
        this.discarded = discarded;
    }
}
