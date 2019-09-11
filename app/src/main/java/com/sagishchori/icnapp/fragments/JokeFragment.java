package com.sagishchori.icnapp.fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sagishchori.icnapp.R;
import com.sagishchori.icnapp.databinding.FragmentJokeItemBinding;
import com.sagishchori.icnapp.logic.JokesLogic;
import com.sagishchori.icnapp.models.Value;
import com.sagishchori.icnapp.utils.StringUtils;
import com.sagishchori.icnapp.viewmodels.JokesViewModel;

import java.util.ArrayList;

public class JokeFragment extends Fragment {

    public static final String TAG = JokeFragment.class.getCanonicalName();

    public static final String KEY_JOKE_OBJECT = "keyJokeObject";

    JokesViewModel viewModel;
    FragmentJokeItemBinding binding;
    JokesLogic logic;
    Value value;

    public static JokeFragment newInstance(Bundle args) {
        JokeFragment fragment = new JokeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = ViewModelProviders.of(getActivity()).get(JokesViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_joke_item, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if ((logic = viewModel.getLogic().getValue()) == null) {
            logic = new JokesLogic();
            logic.init(getActivity(), viewModel, getActivity());
        }

        if (getArguments() != null) {
            value = (Value) getArguments().getSerializable(KEY_JOKE_OBJECT);

            if (value != null && !StringUtils.isEmpty(value.getJoke())) {
                binding.joke.setText(value.getJoke());
                binding.jokeCardView.setVisibility(View.VISIBLE);
                binding.progressBar.setVisibility(View.GONE);
            } else {
                logic.tryToLoadJokesFromWeb(new JokesLogic.JokesLoadingInterface() {
                    @Override
                    public void onJokesLoadedFromCache() {

                    }

                    @Override
                    public void onJokeLoadedFromWeb(final Value joke) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                binding.joke.setText(joke.getJoke());
                                binding.jokeCardView.setVisibility(View.VISIBLE);
                                binding.progressBar.setVisibility(View.GONE);
                            }
                        });
                    }

                    @Override
                    public void onFailedToLoad() {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                binding.joke.setText(getActivity().getString(R.string.joke_loading_error));
                                binding.jokeCardView.setVisibility(View.VISIBLE);
                                binding.progressBar.setVisibility(View.GONE);
                            }
                        });
                    }

                    @Override
                    public void onJokesDataChanged(ArrayList<Value> values) {

                    }
                });
            }
        }
    }
}
