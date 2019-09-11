package com.sagishchori.icnapp.activities;

import android.arch.lifecycle.ViewModelProviders;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.view.Menu;
import android.view.MenuItem;

import com.sagishchori.icnapp.R;
import com.sagishchori.icnapp.database.DbInterface;
import com.sagishchori.icnapp.database.agents.DataBaseClearAgent;
import com.sagishchori.icnapp.databinding.ActivityMainBinding;
import com.sagishchori.icnapp.fragments.JokesFragment;
import com.sagishchori.icnapp.receivers.NetworkStateChangeReceiver;
import com.sagishchori.icnapp.utils.SharedPreferencesUtils;
import com.sagishchori.icnapp.utils.UserUtils;
import com.sagishchori.icnapp.viewmodels.JokesViewModel;

import static com.sagishchori.icnapp.receivers.NetworkStateChangeReceiver.IS_NETWORK_AVAILABLE;

public class MainActivity extends AbstractActivity {

    ActivityMainBinding binding;
    JokesViewModel viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // First check if user logged in
        if (!UserUtils.isUserLoggedIn(this)) {

            // In case the user is not logged in go to login activity
            startLoginActivity();
            return;
        }

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        viewModel = ViewModelProviders.of(this).get(JokesViewModel.class);

        setSupportActionBar(binding.toolbar);

        setFragmentToView(JokesFragment.TAG);
    }

    @Override
    protected void onStart() {
        super.onStart();

        IntentFilter intentFilter = new IntentFilter(NetworkStateChangeReceiver.NETWORK_AVAILABLE_ACTION);
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver, intentFilter);
    }

    @Override
    protected void onStop() {
        super.onStop();

        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver);
    }

    private void startLoginActivity() {
        Intent intent = new Intent(this, UserDetailsLoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            SharedPreferencesUtils.clearSharedPreferences(this);
            clearAllDB();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void clearAllDB() {
        DataBaseClearAgent agent = new DataBaseClearAgent(this, new DbInterface() {
            @Override
            public void onDataInsert() {

            }

            @Override
            public void onDataFailedToInsert() {

            }

            @Override
            public void onDataDeleted() {
                startLoginActivity();
            }

            @Override
            public void onDataFailedToDelete() {

            }

            @Override
            public void onDataFetch(Object data) {

            }
        });

        agent.execute();
    }

    @Override
    protected Fragment getFragmentByTag(String tag) {
        if (getSupportFragmentManager().findFragmentByTag(tag) != null) {
            return getSupportFragmentManager().findFragmentByTag(tag);
        } else {
            return JokesFragment.newInstance(null);
        }
    }

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            boolean isNetworkAvailable = intent.getBooleanExtra(IS_NETWORK_AVAILABLE, false);
            String networkStatus = isNetworkAvailable ? getResources().getString(R.string.connected) : getResources().getString(R.string.disconnected);

            Snackbar.make(binding.getRoot(), "Network Status: " + networkStatus, Snackbar.LENGTH_LONG).show();
        }
    };
}
