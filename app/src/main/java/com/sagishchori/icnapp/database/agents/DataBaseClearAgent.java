package com.sagishchori.icnapp.database.agents;

import android.content.Context;

import com.sagishchori.icnapp.database.DbInterface;

public class DataBaseClearAgent extends DataBaseBaseAgent<Void, Void, Boolean> {

    public DataBaseClearAgent(Context context, DbInterface anInterface) {
        super(context, anInterface);
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        try {
            db.jrDao().clearAllJokes();
            db.udDao().deleteUser();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    protected void onPostExecute(Boolean bool) {
        super.onPostExecute(bool);

        if (dbInterface != null) {
            if (bool) {
                dbInterface.onDataDeleted();
            } else {
                dbInterface.onDataFailedToDelete();
            }
        }
    }
}
