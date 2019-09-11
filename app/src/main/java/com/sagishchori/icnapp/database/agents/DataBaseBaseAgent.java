package com.sagishchori.icnapp.database.agents;

import android.content.Context;
import android.os.AsyncTask;

import androidx.room.Room;

import com.sagishchori.icnapp.database.AppDataBase;
import com.sagishchori.icnapp.database.DbInterface;

public class DataBaseBaseAgent<T, V, E> extends AsyncTask<T, V, E> {

    protected Context context;
    protected AppDataBase db;
    protected DbInterface dbInterface;

    public DataBaseBaseAgent(Context context, DbInterface anInterface) {
        this.context = context;
        this.dbInterface = anInterface;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        db = Room.databaseBuilder(context, AppDataBase.class, "database-name").build();
    }

    @Override
    protected E doInBackground(T... ts) {
        return null;
    }
}
