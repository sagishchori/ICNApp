package com.sagishchori.icnapp.logic;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.sagishchori.icnapp.R;
import com.sagishchori.icnapp.api.ApiClient;
import com.sagishchori.icnapp.database.agents.DataBaseJokeInsertAgent;
import com.sagishchori.icnapp.database.agents.DataBaseJokesFetchAgent;
import com.sagishchori.icnapp.database.DbInterface;
import com.sagishchori.icnapp.api.responses.JokeResponse;
import com.sagishchori.icnapp.models.UserDetails;
import com.sagishchori.icnapp.models.Value;
import com.sagishchori.icnapp.utils.NetworkUtils;
import com.sagishchori.icnapp.viewmodels.JokesViewModel;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JokesLogic extends BaseLogic{

    private JokesLoadingInterface listner;

    private HashMap<Integer, Value> savedJokesHashMap;

    public JokesLogic() {
    }

    @Override
    public void init(Context context, ViewModel viewModel, LifecycleOwner owner) {
        super.init(context, viewModel, owner);
        savedJokesHashMap = new HashMap<>();
    }

    private void loadJokes() {
        tryToLoadJokesFromCache(new JokesLoadingInterface(){

            @Override
            public void onJokesLoadedFromCache() {
                if (listner != null) {
                    listner.onJokesDataChanged(getViewModel().getData().getValue());
                }

                ArrayList<Value> values = getViewModel().getData().getValue();

                if (values != null && values.size() > 0) {
                    for (Value value: values) {
                        savedJokesHashMap.put(value.getId(), value);
                    }
                }
            }

            @Override
            public void onJokeLoadedFromWeb(Value joke) {

            }

            @Override
            public void onFailedToLoad() {
                tryToLoadJokesFromWeb(new JokesLoadingInterface(){

                    @Override
                    public void onJokesLoadedFromCache() {

                    }

                    @Override
                    public void onJokeLoadedFromWeb(Value joke) {

                    }

                    @Override
                    public void onFailedToLoad() {

                    }

                    @Override
                    public void onJokesDataChanged(ArrayList<Value> values) {

                    }
                });
            }

            @Override
            public void onJokesDataChanged(ArrayList<Value> values) {

            }
        });
    }

    public void tryToLoadJokesFromWeb(final JokesLoadingInterface anInterface) {
        UserDetails userDetails = getViewModel().getUserDetails();

        // Check if the user is connected to internet
        if (NetworkUtils.isConnected(context)) {
            ApiClient.getInstance().getRandomJoke(userDetails, new Callback<JokeResponse>() {
                @Override
                public void onResponse(Call<JokeResponse> call, Response<JokeResponse> response) {
                    Value value = response.body().getValue();

                    if (isJokeSaved(value.getId())) {
                        tryToLoadJokesFromWeb(anInterface);
                    } else {
                        ArrayList<Value> jokesList = getViewModel().getData().getValue();

                        if (jokesList == null) {
                            return;
                        }

                        if (jokesList.size() > 0) {
                            // This is due to the adding of an empty item
                            jokesList.remove(jokesList.size() - 1);
                        }
                        jokesList.add(value);

                        getViewModel().getData().postValue(jokesList);

                        saveJoke(value);
                    }

                    anInterface.onJokeLoadedFromWeb(value);
                }

                @Override
                public void onFailure(Call<JokeResponse> call, Throwable t) {
                    // Show to the user the error message to indicate that an error occurred while trying to download
                    // the joke
                    Toast.makeText(context, R.string.joke_loading_error, Toast.LENGTH_LONG).show();
                }
            });
        } else {

            // Show to the user the connection error message
            Toast.makeText(context, R.string.internet_connection_error, Toast.LENGTH_LONG).show();
        }
    }

    private void saveJoke(final Value value) {
        DataBaseJokeInsertAgent agent = new DataBaseJokeInsertAgent(context, new DbInterface() {
            @Override
            public void onDataInsert() {
                savedJokesHashMap.put(value.getId(), value);
            }

            @Override
            public void onDataFailedToInsert() {
                Toast.makeText(context, R.string.data_failed_to_insert_error_text, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onDataDeleted() {

            }

            @Override
            public void onDataFailedToDelete() {

            }

            @Override
            public void onDataFetch(Object data) {

            }
        });

        agent.execute(value);
    }

    private void tryToLoadJokesFromCache(final JokesLoadingInterface anInterface) {
        DataBaseJokesFetchAgent agent = new DataBaseJokesFetchAgent(context, new DbInterface<Value[]>() {
            @Override
            public void onDataInsert() {

            }

            @Override
            public void onDataFailedToInsert() {

            }

            @Override
            public void onDataDeleted() {

            }

            @Override
            public void onDataFailedToDelete() {

            }

            @Override
            public void onDataFetch(Value[] data) {
                getViewModel().setJokes(data);

                if (data == null || data.length == 0) {
                    anInterface.onFailedToLoad();
                    return;
                }

                anInterface.onJokesLoadedFromCache();
            }
        });
        agent.execute();
    }

    private boolean isJokeSaved(int jokeId) {
        return false;
    }

    @Override
    protected JokesViewModel getViewModel() {
        return (JokesViewModel) viewModel;
    }

    public void setLoadingInterfaceAndStartObserve(JokesLoadingInterface anInterface) {
        this.listner = anInterface;

        getViewModel().getData().observe(owner, new Observer<ArrayList<Value>>() {
            @Override
            public void onChanged(@Nullable ArrayList<Value> values) {
                if (listner != null) {
                    listner.onJokesDataChanged(values);
                }
            }
        });

        loadJokes();
    }

    public void setUserDetails(UserDetails details) {
        getViewModel().setUserDetails(details);
    }

    public void setSelectedPage(int position) {
        getViewModel().setLastItemPosition(position);
    }

    public ArrayList<Value> getJokesList() {
        return getViewModel().getData().getValue();
    }

    public int getLastPosition() {
        return getViewModel().getLastAdapterPosition();
    }

    /**
     * The savedJokesHashMap field is instantiated when init() is called so in case it is null
     * the logic init() method was never called.
     *
     * @return      true - if logic was instantiated, false - otherwise
     */
    public boolean isLogicInstantiated() {
        return savedJokesHashMap != null;
    }

    /**
     * An interface for loading jokes events.
     */
    public interface JokesLoadingInterface {

        /**
         * Indicates that the jokes has loaded. This will be used to indicate the jokes list loading.
         */
        void onJokesLoadedFromCache();

        /**
         * Indicates that a single joke has loaded. This will be used to indicate the joke from web loading.
         * @param joke
         */
        void onJokeLoadedFromWeb(Value joke);

        /**
         * Indicate that the loading of joke/s failed.
         */
        void onFailedToLoad();

        /**
         * Indicates that the Jokes data has been changed.
         *
         * @param values        The JokesData that has been changed
         */
        void onJokesDataChanged(ArrayList<Value> values);
    }
}
