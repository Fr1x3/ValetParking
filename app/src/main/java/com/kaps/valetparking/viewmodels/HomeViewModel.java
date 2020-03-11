package com.kaps.valetparking.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.kaps.valetparking.models.Park;
import com.kaps.valetparking.repository.ParkRepository;
import com.kaps.valetparking.utils.Constants;
import com.kaps.valetparking.utils.SharedPreferenceUtil;

import java.util.List;

public class HomeViewModel extends AndroidViewModel {

    private final ParkRepository mParkRepository;

    public HomeViewModel(@NonNull Application application) {
        super(application);

        mParkRepository = ParkRepository.getInstance();
    }

    //get details of the car to be collected
    public MutableLiveData<Park> getCar(){
        String lift = SharedPreferenceUtil.getString(Constants.ZONE);
        String username = SharedPreferenceUtil.getString(Constants.EMAIL);
        return  mParkRepository.getPark(lift, username);
    }
}
