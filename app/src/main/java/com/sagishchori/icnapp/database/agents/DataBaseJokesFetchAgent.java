package com.sagishchori.icnapp.database.agents;

import android.content.Context;

import com.sagishchori.icnapp.database.DbInterface;
import com.sagishchori.icnapp.models.Value;

public class DataBaseJokesFetchAgent extends DataBaseBaseAgent<Void, Void, Value[]> {

    public DataBaseJokesFetchAgent(Context context, DbInterface dbInterface) {
        super(context, dbInterface);
    }

    @Override
    protected Value[] doInBackground(Void... voids) {
        Value[] values = db.jrDao().getAllJokes();
        return values;
    }

    @Override
    protected void onPostExecute(Value[] values) {
        super.onPostExecute(values);

        if (dbInterface != null) {
            dbInterface.onDataFetch(values);
        }
    }
}
