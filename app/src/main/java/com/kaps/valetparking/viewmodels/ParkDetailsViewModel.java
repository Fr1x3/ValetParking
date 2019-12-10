package com.kaps.valetparking.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import com.kaps.valetparking.models.Park;
import com.kaps.valetparking.repository.ParkRepository;

public class ParkDetailsViewModel extends AndroidViewModel {


    private final ParkRepository mParkRepository;

    public ParkDetailsViewModel(@NonNull Application application) {
        super(application);

        mParkRepository = ParkRepository.getInstance();

    }


    // create a parking details
    public void createPark(Park park){
        mParkRepository.createPark(park);
    }

}
