package com.example.pedulipangan.data.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

import java.util.Date;

public class ProductItem extends RealmObject {

    @PrimaryKey
    private String id;

    private String category;
    private int amount;
    private Date expiryDate;

    public String getId() {
        return id;
    }




}
