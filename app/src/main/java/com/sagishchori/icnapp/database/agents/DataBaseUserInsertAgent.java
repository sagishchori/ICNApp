package com.sagishchori.icnapp.database.agents;

import android.content.Context;

import com.sagishchori.icnapp.database.DbInterface;
import com.sagishchori.icnapp.models.UserDetails;

public class DataBaseUserInsertAgent extends DataBaseBaseAgent<UserDetails, Void, Boolean> {

    public DataBaseUserInsertAgent(Context context, DbInterface dbInterface) {
        super(context, dbInterface);
    }

    @Override
    protected Boolean doInBackground(UserDetails... userDetails) {

        try {
            db.udDao().insertUserDetails(userDetails[0]);
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
