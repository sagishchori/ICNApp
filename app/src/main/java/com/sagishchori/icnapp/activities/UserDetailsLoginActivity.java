package com.sagishchori.icnapp.activities;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Menu;
import android.view.MenuItem;

import com.sagishchori.icnapp.R;
import com.sagishchori.icnapp.databinding.ActivityMainBinding;
import com.sagishchori.icnapp.databinding.ActivityUserDetailsLoginBinding;
import com.sagishchori.icnapp.fragments.UserDetailsFragment;
import com.sagishchori.icnapp.viewmodels.UserDetailsViewModel;

public class UserDetailsLoginActivity extends AbstractActivity {

    ActivityUserDetailsLoginBinding binding;
    UserDetailsViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_user_details_login);

        setSupportActionBar(binding.toolbar);

        viewModel = ViewModelProviders.of(this).get(UserDetailsViewModel.class);

        setFragmentToView(UserDetailsFragment.TAG);
    }

    @Override
    protected Fragment getFragmentByTag(String tag) {
        if (getSupportFragmentManager().findFragmentByTag(tag) != null) {
            return getSupportFragmentManager().findFragmentByTag(tag);
        } else {
            return UserDetailsFragment.newInstance(null);
        }
    }
}
