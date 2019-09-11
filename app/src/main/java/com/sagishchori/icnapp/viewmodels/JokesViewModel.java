package com.sagishchori.icnapp.viewmodels;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;

import com.sagishchori.icnapp.logic.JokesLogic;
import com.sagishchori.icnapp.api.responses.JokeResponse;
import com.sagishchori.icnapp.models.UserDetails;
import com.sagishchori.icnapp.models.Value;

import java.util.ArrayList;
import java.util.Arrays;

public class JokesViewModel extends AbstractViewModel {

    private MutableLiveData<UserDetails> userDetails;
    private MutableLiveData<Integer> lastItemPosition;

    @Override
    public void init(Context context, LifecycleOwner owner) {
        this.context = context;
        this.owner = owner;
    }

    @Override
    public MutableLiveData<JokesLogic> getLogic() {
        if (logic == null) {
            logic = new MutableLiveData<JokesLogic>();
            logic.setValue(new JokesLogic());
        }

        return logic;
    }

    public void setLogic(JokesLogic logic) {
        getLogic().setValue(logic);
    }

    public MutableLiveData<ArrayList<Value>> getData() {
        if (data == null) {
            ArrayList<Value> jokes = new ArrayList<>();
            data = new MutableLiveData<JokeResponse>();
            data.setValue(jokes);
        }
        return data;
    }

    public MutableLiveData<Integer> getLastPositionData() {
        if (lastItemPosition == null) {
            lastItemPosition = new MutableLiveData<Integer>();
            lastItemPosition.setValue(0);
        }

        return lastItemPosition;
    }

    public MutableLiveData<UserDetails> getUserDetailsData() {
        if (userDetails == null) {
            UserDetails userDetailsObject = new UserDetails();
            userDetails = new MutableLiveData<UserDetails>();
            userDetails.setValue(userDetailsObject);
        }

        return userDetails;
    }

    public void setUserDetails(UserDetails userDetails) {
        getUserDetailsData().setValue(userDetails);
    }

    public UserDetails getUserDetails() {
        return getUserDetailsData().getValue();
    }

    public void setJokes(Value[] data) {
        ArrayList<Value> newJokesList = new ArrayList<>();
        newJokesList.addAll(Arrays.asList(data));
        getData().postValue(newJokesList);
    }

    public void setLastItemPosition(int position) {
        getLastPositionData().setValue(position);
    }

    public int getLastAdapterPosition() {
        return getLastPositionData().getValue();
    }
}
