package com.sagishchori.icnapp.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity (tableName = "user_details")
public class UserDetails {

    public static final String KEY_USER_DETAILS_OBJECT = "keyUserDetailsObject";
    public static final String KEY_FIRST_NAME = "fName";
    public static final String KEY_LAST_NAME = "lName";

    @PrimaryKey (autoGenerate = true)
    public int id;

    @ColumnInfo
    String firstName;

    @ColumnInfo
    String lastName;

    public UserDetails() {
    }

    public UserDetails(String fName, String lName) {
        this.firstName = fName;
        this.lastName = lName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public static UserDetails getDefaultUser() {
        UserDetails details = new UserDetails();
        details.setFirstName("Thomas");
        details.setLastName("Anderson");
        return details;
    }
}
