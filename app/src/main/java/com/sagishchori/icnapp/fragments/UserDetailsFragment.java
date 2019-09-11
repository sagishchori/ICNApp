package com.sagishchori.icnapp.fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.sagishchori.icnapp.R;
import com.sagishchori.icnapp.database.DbInterface;
import com.sagishchori.icnapp.databinding.FragmentUserDetailsBinding;
import com.sagishchori.icnapp.logic.UserDetailsLogic;
import com.sagishchori.icnapp.models.UserDetails;
import com.sagishchori.icnapp.utils.SharedPreferencesUtils;
import com.sagishchori.icnapp.viewmodels.UserDetailsViewModel;

public class UserDetailsFragment extends Fragment {

    public static final String TAG = UserDetailsFragment.class.getCanonicalName();

    FragmentUserDetailsBinding binding;
    UserDetailsViewModel viewModel;
    UserDetailsLogic logic;
    private boolean isFirstNameFull = false;
    private boolean isLastNameFull = false;

    public static UserDetailsFragment newInstance(Bundle args) {

        UserDetailsFragment fragment = new UserDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = ViewModelProviders.of(getActivity()).get(UserDetailsViewModel.class);

        logic = viewModel.getLogic().getValue();

        if (!logic.isLogicInstantiated()) {
            logic = new UserDetailsLogic();
            logic.init(getActivity(), viewModel, getActivity());
            viewModel.setLogic(logic);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_user_details, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setTextChangeListeners();

        setOnClickListeners();

        enableSubmitButton();
    }

    private void setOnClickListeners() {
        binding.submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String fName = binding.firstNameEditText.getText().toString();
                final String lName = binding.lastNameEditText.getText().toString();

                logic.validateUserName(fName, lName, new UserDetailsLogic.UserNameValidationInterface() {

                    @Override
                    public void onFirstNameError() {
                        binding.firstNameInputLayout.setError(getString(R.string.name_validation_error_text));
                        binding.firstNameInputLayout.setErrorEnabled(true);
                    }

                    @Override
                    public void onLastNameError() {
                        binding.lastNameInputLayout.setError(getString(R.string.name_validation_error_text));
                        binding.lastNameInputLayout.setErrorEnabled(true);
                    }

                    @Override
                    public void onFullNameValidate() {
                        SharedPreferencesUtils.writeValueToSP(getActivity(), UserDetails.KEY_FIRST_NAME, fName);
                        SharedPreferencesUtils.writeValueToSP(getActivity(), UserDetails.KEY_LAST_NAME, lName);

                        logic.saveUserToDB(new DbInterface() {
                            @Override
                            public void onDataInsert() {
                                logic.onUserSavedToDB(getActivity());
                            }

                            @Override
                            public void onDataFailedToInsert() {
                                Toast.makeText(getActivity(), R.string.data_failed_to_insert_error_text, Toast.LENGTH_LONG).show();
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
                    }
                });
            }
        });
    }

    private void setTextChangeListeners() {
        binding.firstNameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() > 0) {
                    isFirstNameFull = true;
                } else {
                    isFirstNameFull = false;
                }

                if (logic != null) {
                    logic.setFirstName(editable.toString());
                }

                enableSubmitButton();
            }
        });

        binding.lastNameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() > 0) {
                    isLastNameFull = true;
                } else {
                    isLastNameFull = false;
                }

                if (logic != null) {
                    logic.setFirstName(editable.toString());
                }

                enableSubmitButton();
            }
        });
    }

    /**
     * A method to enable/disable the submitButton according to text fields containing content.
     */
    private void enableSubmitButton() {

        // If all text fields are holding at least one character enable the button
        if (isFirstNameFull && isLastNameFull) {
            binding.submitButton.setEnabled(true);
        }

        // If, at least one text field is empty the button is disabled
        else {
            binding.submitButton.setEnabled(false);
        }
    }
}
