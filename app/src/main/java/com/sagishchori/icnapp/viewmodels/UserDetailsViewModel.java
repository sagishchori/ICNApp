package com.sagishchori.icnapp.viewmodels;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.support.annotation.Nullable;

import com.sagishchori.icnapp.logic.UserDetailsLogic;
import com.sagishchori.icnapp.models.UserDetails;

public class UserDetailsViewModel extends AbstractViewModel {

    @Override
    public void init(Context context, LifecycleOwner owner) {

    }

    @Override
    public MutableLiveData<UserDetailsLogic> getLogic() {
        if (logic == null) {
            logic = new MutableLiveData<UserDetailsLogic>();
            logic.setValue(new UserDetailsLogic());
        }

        return logic;
    }

    public MutableLiveData<UserDetails> getData() {
        if (data == null) {
            data = new MutableLiveData<UserDetails>();
            UserDetails details = new UserDetails().getDefaultUser();
            data.setValue(details);
        }

        return data;
    }

    public void setFirstName(String fName) {
        getData().getValue().setFirstName(fName);
    }

    public void setLastName(String lName) {
        getData().getValue().setLastName(lName);
    }

    public void setLogic(UserDetailsLogic logic) {
        getLogic().setValue(logic);
    }

    public String getFirstName() {
        return getData().getValue().getFirstName();
    }

    public String getLastName() {
        return getData().getValue().getLastName();
    }
}
