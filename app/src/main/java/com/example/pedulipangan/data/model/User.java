package com.example.pedulipangan.data.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class User extends RealmObject {

    @PrimaryKey
    private String username;

    private String password;
    private String email;
    private String gender;

    private String birthdate;
    private String city;

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username.toLowerCase();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    // Getter dan Setter untuk city
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
