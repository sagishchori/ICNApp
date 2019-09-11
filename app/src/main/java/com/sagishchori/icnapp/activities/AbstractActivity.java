package com.sagishchori.icnapp.activities;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.sagishchori.icnapp.R;

public abstract class AbstractActivity extends AppCompatActivity {

    /**
     * A method to set the requested {@link Fragment} to {@link View}.
     *
     * @param tag       The TAG of the {@link Fragment}
     */
    protected void setFragmentToView(String tag) {
        FragmentManager manager = getSupportFragmentManager();

        // First try to find the fragment in the backstack (this is used to retain state)
        Fragment fragment = manager.findFragmentByTag(tag);

        FragmentTransaction transaction = manager.beginTransaction();

        // If the requested fragment found set it to view otherwise instantiate a new one.
        transaction.addToBackStack(null).replace(R.id.container, fragment != null ? fragment : getFragmentByTag(tag), tag).commit();
    }

    /**
     * Use this to set the right {@link Fragment} to view.
     *
     * @param tag       The tag of each {@link Fragment}
     *
     * @return          The requested {@link Fragment} by its TAG field
     */
    protected abstract Fragment getFragmentByTag(String tag);

    @Override
    public void onBackPressed() {

        // If we are in the last fragment finish the Activity
        if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
            finish();
            return;
        } else if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            getSupportFragmentManager().popBackStackImmediate();
            return;
        } else {
            super.onBackPressed();
        }
    }
}
