package com.kaps.valetparking.viewmodels;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.kaps.valetparking.models.User;
import com.kaps.valetparking.repository.LoginRepository;
import com.kaps.valetparking.utils.Constants;
import com.kaps.valetparking.utils.SharedPreferenceUtil;

public class LoginViewModel extends AndroidViewModel {

    private final LoginRepository mLoginRepository;
    private SharedPreferenceUtil mPref;

    public enum AuthenticationState{
        UNAUTHENTICATED,                // initial state
        AUTHENTICATED,                  // successful authentication
        INVALID_AUTHENTICATION          // authentication failed
    }

    public final MutableLiveData<AuthenticationState> mAuthenticationState =
            new MutableLiveData<>();

    public LoginViewModel(@NonNull Application application) {
        super(application);

        mLoginRepository = new LoginRepository();
        SharedPreferenceUtil.getInstance(application);

        //if( mAuthenticationState == null)
            mAuthenticationState.setValue(AuthenticationState.UNAUTHENTICATED);
    }



    public void authenticate(User user){
        if( login(user) ) {
            mAuthenticationState.setValue(AuthenticationState.AUTHENTICATED);
            SharedPreferenceUtil.putString(Constants.EMAIL, user.getEmail());
        }
        else
            mAuthenticationState.setValue(AuthenticationState.INVALID_AUTHENTICATION);

    }

    public boolean login(User user){

        String id = SharedPreferenceUtil.getString(Constants.GENERATED_ID);
        if( id == null){

        }
        user.setId(id);
        return mLoginRepository.login(user);
    }

    public void refuseAuthentication(){
        mAuthenticationState.setValue(AuthenticationState.UNAUTHENTICATED);

    }


    public void logOut(String email){
        if( mLoginRepository.logout(email) ) {
            refuseAuthentication();
            //SharedPreferenceUtil.removeAll();

        }


    }


}
