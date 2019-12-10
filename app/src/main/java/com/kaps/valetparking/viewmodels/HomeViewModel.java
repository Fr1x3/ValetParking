package com.kaps.valetparking.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.kaps.valetparking.models.Park;
import com.kaps.valetparking.repository.ParkRepository;

import java.util.List;

public class HomeViewModel extends AndroidViewModel {

    private final ParkRepository mParkRepository;

    public HomeViewModel(@NonNull Application application) {
        super(application);

        mParkRepository = ParkRepository.getInstance();
    }

    //get details of the car to be collected
    public MutableLiveData<List<Park>> getCar(){

        return  mParkRepository.getPark();
    }
}
