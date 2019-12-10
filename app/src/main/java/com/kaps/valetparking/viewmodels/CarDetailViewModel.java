package com.kaps.valetparking.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.kaps.valetparking.repository.ParkRepository;

public class CarDetailViewModel extends AndroidViewModel {

    final ParkRepository mParkRepository;

    public CarDetailViewModel(@NonNull Application application) {
        super(application);

        mParkRepository = ParkRepository.getInstance();
    }

    // exit the car to the parking
    public MutableLiveData<Boolean> exitPark(String plate_number){
        return mParkRepository.exitPark(plate_number);
    }
}
