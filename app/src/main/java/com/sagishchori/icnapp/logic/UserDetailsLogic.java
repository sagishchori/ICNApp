package com.sagishchori.icnapp.logic;

import android.app.Activity;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.sagishchori.icnapp.activities.MainActivity;
import com.sagishchori.icnapp.database.DbInterface;
import com.sagishchori.icnapp.database.agents.DataBaseUserInsertAgent;
import com.sagishchori.icnapp.models.UserDetails;
import com.sagishchori.icnapp.viewmodels.UserDetailsViewModel;

import java.util.regex.Pattern;

import static com.sagishchori.icnapp.utils.StringUtils.LETTERS_ONLY_REGEX;

public class UserDetailsLogic extends BaseLogic {

    public UserDetailsLogic() {
    }

    @Override
    public void init(Context context, ViewModel viewModel, LifecycleOwner owner) {
        super.init(context, viewModel, owner);

        getViewModel().getData().observe(owner, new Observer<UserDetails>() {
            @Override
            public void onChanged(@Nullable UserDetails userDetails) {

            }
        });
    }

    public void setUserName(String fName, String lName) {
        getViewModel().setFirstName(fName);
        getViewModel().setLastName(lName);
    }

    public void saveUserToDB(DbInterface dbInterface) {
        UserDetails userDetails = new UserDetails();
        userDetails.setFirstName(getViewModel().getData().getValue().getFirstName());
        userDetails.setLastName(getViewModel().getData().getValue().getLastName());

        DataBaseUserInsertAgent agent = new DataBaseUserInsertAgent(context, dbInterface);
        agent.execute(userDetails);
    }

    public void setFirstName(String fName) {
        getViewModel().setFirstName(fName);
    }

    public void setLastName(String lName) {
        getViewModel().setLastName(lName);
    }

    @Override
    protected UserDetailsViewModel getViewModel() {
        return (UserDetailsViewModel) viewModel;
    }

    @Override
    public boolean isLogicInstantiated() {
        return owner != null;
    }

    public void onUserSavedToDB(Activity activity) {
        Intent intent = new Intent(context, MainActivity.class);
        activity.startActivity(intent);
        activity.finish();
    }

    /**
     * A method to validate the user's first and last names. The validation is checking the characters to hold only letters
     * using the LETTERS_ONLY_REGEX.
     *
     * @return      true - If name contains only letters, false - Otherwise
     */
    public static boolean isUserNameValid(String stringToValidate) {
        Pattern pattern = Pattern.compile(LETTERS_ONLY_REGEX);
        String str = stringToValidate.replace(" ", "");

        if (pattern.matcher(str).matches()) {
            return true;
        } else {
            return false;
        }
    }

    public void validateUserName(String fName, String lName, UserNameValidationInterface anInterface) {
        if (isUserNameValid(fName)) {
            if (isUserNameValid(lName)) {
                setUserName(fName, lName);
                anInterface.onFullNameValidate();
            } else {
                anInterface.onLastNameError();
            }
        } else {
            anInterface.onFirstNameError();
        }
    }

    public interface UserNameValidationInterface {
        void onFirstNameError();

        void onLastNameError();

        void onFullNameValidate();
    }
}
