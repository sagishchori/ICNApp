package com.sagishchori.icnapp.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.sagishchori.icnapp.database.daoInterface.DaoJokesInterface;
import com.sagishchori.icnapp.database.daoInterface.DaoUserInterface;
import com.sagishchori.icnapp.models.UserDetails;
import com.sagishchori.icnapp.models.Value;

@Database(entities = {UserDetails.class, Value.class}, version = 1, exportSchema = false)
public abstract class AppDataBase extends RoomDatabase {

    public abstract DaoUserInterface udDao();

    public abstract DaoJokesInterface jrDao();
}
