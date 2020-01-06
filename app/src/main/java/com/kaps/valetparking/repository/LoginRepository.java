package com.kaps.valetparking.repository;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.kaps.valetparking.models.Devices;
import com.kaps.valetparking.models.User;
import com.kaps.valetparking.network.LoginService;
import com.kaps.valetparking.network.ServiceBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginRepository {

    private LoginService mLoginService;
    private Boolean mStatus = false;
    private MutableLiveData<Devices> mDevices;

    public LoginRepository(){

        mLoginService = ServiceBuilder.createService(LoginService.class);
    }


    public MutableLiveData<Devices> login(User user){
        mDevices = new MutableLiveData<>();

        mLoginService.login(user).enqueue(new Callback<Devices>() {
            @Override
            public void onResponse(Call<Devices> call, Response<Devices> response) {
                if( response.body() !=  null && response.code() == 200){
                    // confirm that the login is successfull
                    // obtain  the response

                    mDevices.setValue(response.body());

                }
            }

            @Override
            public void onFailure(Call<Devices> call, Throwable t) {
                Log.d("login", t.getMessage());
                t.getStackTrace();
                mDevices.setValue(null);

            }
        });

        return mDevices;
    }

    public boolean logout(String email){
        final Boolean[] outStatus = {false};
        mLoginService.logout(email).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if( response.body() != null && response.isSuccessful() ){
                    // obtain the jsonobject response
                    try {
                        JSONObject data = new JSONObject(response.body().string());
                        int status = data.getInt("status");
                        if( status == 0)
                            outStatus[0] = true;
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("logout", t.getMessage());
                t.getStackTrace();

                outStatus[0] = false;

            }
        });

        return outStatus[0];
    }
}
