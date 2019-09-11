package com.sagishchori.icnapp.database.daoInterface;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.sagishchori.icnapp.models.Value;

import java.util.List;

@Dao
public interface DaoJokesInterface {

    @Query("SELECT * FROM joke_value")
    public Value[] getAllJokes();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertAllJokes(List<Value> movies);

    @Query("DELETE FROM joke_value")
    public void clearAllJokes();
}
