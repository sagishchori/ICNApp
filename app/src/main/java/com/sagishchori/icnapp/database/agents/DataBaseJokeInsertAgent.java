package com.sagishchori.icnapp.database.agents;

import android.content.Context;

import androidx.room.Room;

import com.sagishchori.icnapp.database.AppDataBase;
import com.sagishchori.icnapp.database.DbInterface;
import com.sagishchori.icnapp.models.Value;

import java.util.ArrayList;

public class DataBaseJokeInsertAgent extends DataBaseBaseAgent<Value, Void, Boolean> {

    public DataBaseJokeInsertAgent(Context context, DbInterface dbInterface) {
        super(context, dbInterface);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        db = Room.databaseBuilder(context, AppDataBase.class, "database-name").build();
    }

    @Override
    protected Boolean doInBackground(Value... values) {
        try {
            Value value = values[0];

            Value[] jokesList = db.jrDao().getAllJokes();

            ArrayList<Value> list = new ArrayList<>();
            for (Value value1: jokesList) {
                list.add(value1);
            }

            list.add(value);

            db.jrDao().insertAllJokes(list);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    protected void onPostExecute(Boolean bool) {
        super.onPostExecute(bool);

        if (dbInterface != null) {
            if (bool) {
                dbInterface.onDataInsert();
            } else {
                dbInterface.onDataFailedToInsert();
            }
        }
    }
}
