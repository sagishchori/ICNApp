package com.sagishchori.icnapp.adapters;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import com.sagishchori.icnapp.fragments.JokeFragment;
import com.sagishchori.icnapp.models.Value;

import java.util.ArrayList;

public class JokesAdapter extends FragmentStatePagerAdapter {


    private ArrayList<Value> jokesList;

    public JokesAdapter(FragmentManager fm, ArrayList<Value> jokesList) {
        super(fm);
        this.jokesList = jokesList;
    }

    @Override
    public int getCount() {
        if (jokesList != null) {
            if (jokesList.size() == 0) {
                Value value = new Value();
                jokesList.add(value);
            }

            return jokesList.size();
        }

        return 0;
    }

    @Override
    public Fragment getItem(int i) {
        Value value = jokesList.get(i);

        Bundle bundle = new Bundle();
        bundle.putSerializable(JokeFragment.KEY_JOKE_OBJECT, value);

        return JokeFragment.newInstance(bundle);
    }

    public void updateData(ArrayList<Value> values) {

        // No data, no need to update
        if (values == null || values.size() == 0) {
            return;
        }

        if (jokesList == null) {
            jokesList = new ArrayList<>();
        }

        this.jokesList.clear();

        // This triggers to jokes loading process to continue by adding more items that need to be populate by new joke
        values.add(new Value());

        this.jokesList.addAll(values);

        notifyDataSetChanged();
    }
}
