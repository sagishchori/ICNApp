package com.sagishchori.icnapp.database.agents;

import android.content.Context;

import com.sagishchori.icnapp.database.DbInterface;
import com.sagishchori.icnapp.models.UserDetails;


public class DataBaseUserFetchAgent extends DataBaseBaseAgent<Void, Void, UserDetails> {

    public DataBaseUserFetchAgent(Context context, DbInterface dbInterface) {
        super(context, dbInterface);
    }

    @Override
    protected UserDetails doInBackground(Void... voids) {
        UserDetails details = db.udDao().getUserDetails();
        return details;
    }

    @Override
    protected void onPostExecute(UserDetails details) {
        super.onPostExecute(details);

        if (dbInterface != null) {
            dbInterface.onDataFetch(details);
        }
    }
}
