package com.kaps.valetparking.viewmodels;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.kaps.valetparking.models.Devices;
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
    Context mContext;

    public LoginViewModel(@NonNull Application application) {
        super(application);

        mLoginRepository = new LoginRepository();
        SharedPreferenceUtil.getInstance(application);

        // checkif already logged in
        if( SharedPreferenceUtil.getString(Constants.EMAIL).isEmpty())
            mAuthenticationState.setValue(AuthenticationState.UNAUTHENTICATED);
    }



    public void authenticate(final User user){
        // check if login is already authenticated
        if(mAuthenticationState.getValue().compareTo(AuthenticationState.UNAUTHENTICATED) == 0) {
            // listen to change in login status
            login(user).observe((LifecycleOwner) getApplication(), new Observer<Devices>() {
                @Override
                public void onChanged(Devices devices) {
                    // check if login is successfull

                    if (devices.getStatus().compareToIgnoreCase( "0") == 0) {
                        mAuthenticationState.setValue(AuthenticationState.AUTHENTICATED);
                        SharedPreferenceUtil.putString(Constants.EMAIL, user.getEmail());
                        SharedPreferenceUtil.putString(Constants.ZONE, devices.getZone());
                    } else
                        mAuthenticationState.setValue(AuthenticationState.INVALID_AUTHENTICATION);
                }
            });
        }


    }

    public MutableLiveData<Devices> login(User user){

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
            SharedPreferenceUtil.removeAll();

        }


    }


}
