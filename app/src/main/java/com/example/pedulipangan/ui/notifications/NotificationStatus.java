package com.example.pedulipangan.ui.notifications;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class NotificationStatus extends RealmObject {
    @PrimaryKey
    private String id;
    private String userId;
    private String notifId;
    private boolean dismissed;

    // Getter & Setter
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getNotifId() { return notifId; }
    public void setNotifId(String notifId) { this.notifId = notifId; }

    public boolean isDismissed() { return dismissed; }
    public void setDismissed(boolean dismissed) { this.dismissed = dismissed; }

    // Tambahan method untuk reset notifikasi user
    public static void resetAllNotificationsForUser(Realm realm, String userId) {
        realm.executeTransaction(r -> {
            r.where(NotificationStatus.class)
                    .equalTo("userId", userId)
                    .findAll()
                    .deleteAllFromRealm();
        });
    }
}
