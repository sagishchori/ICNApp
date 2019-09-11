package com.sagishchori.icnapp.fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sagishchori.icnapp.R;
import com.sagishchori.icnapp.adapters.JokesAdapter;
import com.sagishchori.icnapp.databinding.FragmentJokesBinding;
import com.sagishchori.icnapp.logic.JokesLogic;
import com.sagishchori.icnapp.models.UserDetails;
import com.sagishchori.icnapp.models.Value;
import com.sagishchori.icnapp.utils.SharedPreferencesUtils;
import com.sagishchori.icnapp.viewmodels.JokesViewModel;

import java.util.ArrayList;

public class JokesFragment extends Fragment implements JokesLogic.JokesLoadingInterface {

    public static final String TAG = JokesFragment.class.getCanonicalName();

    FragmentJokesBinding binding;
    JokesAdapter adapter;
    JokesViewModel viewModel;
    JokesLogic logic;

    public static JokesFragment newInstance(Bundle args) {
        JokesFragment fragment = new JokesFragment();
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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_jokes, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initAdapter(null);

        logic = viewModel.getLogic().getValue();


        if (!logic.isLogicInstantiated()) {
            initAdapter(null);

            logic = new JokesLogic();
            logic.init(getActivity(), viewModel, getActivity());

            String fName = SharedPreferencesUtils.readValueFromSP(getActivity(), UserDetails.KEY_FIRST_NAME, "");
            String lName = SharedPreferencesUtils.readValueFromSP(getActivity(), UserDetails.KEY_LAST_NAME, "");

            logic.setUserDetails(new UserDetails(fName, lName));
            logic.setLoadingInterfaceAndStartObserve(this);

            viewModel.setLogic(logic);
        } else {
            initAdapter(logic.getJokesList());
        }

        setAdapterToViewPager();

        binding.viewPager.setCurrentItem(logic.getLastPosition());

        binding.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                logic.setSelectedPage(i);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    private void initAdapter(ArrayList<Value> values) {
        adapter = new JokesAdapter(getChildFragmentManager(), values);
    }

    private void setAdapterToViewPager() {
        binding.viewPager.setAdapter(adapter);
    }

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
        if (values == null || values.size() == 0) {
            return;
        }
        adapter.updateData(values);
    }
}
