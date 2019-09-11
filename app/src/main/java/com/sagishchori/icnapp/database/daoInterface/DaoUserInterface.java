package com.sagishchori.icnapp.database.daoInterface;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.sagishchori.icnapp.models.UserDetails;

@Dao
public interface DaoUserInterface {

    @Query("SELECT * FROM user_details")
    public UserDetails getUserDetails();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertUserDetails(UserDetails userDetails);

    @Query("DELETE FROM user_details")
    public void deleteUser();

}
