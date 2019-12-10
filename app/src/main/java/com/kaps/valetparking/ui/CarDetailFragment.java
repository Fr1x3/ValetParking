package com.kaps.valetparking.ui;


import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.kaps.valetparking.R;
import com.kaps.valetparking.models.Park;
import com.kaps.valetparking.utils.Constants;
import com.kaps.valetparking.viewmodels.CarDetailViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class CarDetailFragment extends Fragment {


    private NavController mNavController;
    private TextView mTvCarPlate;
    private CarDetailViewModel mViewModel;

    public CarDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //custom onback pressed
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                Toast.makeText(getActivity(), "Complete the task first", Toast.LENGTH_LONG).show();
                //saveData();
            }
        };

        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        return inflater.inflate(R.layout.fragment_car_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // call viewmodel
        mViewModel = ViewModelProviders.of(this).get(CarDetailViewModel.class);

        // text views
        mTvCarPlate = view.findViewById(R.id.tv_car_plate);
        TextView tvLift = view.findViewById(R.id.tv_lift);
        TextView tvFloor = view.findViewById(R.id.tv_floor);
        TextView tvParkSlot = view.findViewById(R.id.tv_park_slot);

        // populate the textview fields with data of the car
        Bundle bundle = getArguments();
        if ( bundle != null){
            Park park = bundle.getParcelable(Constants.PARK_DATA);
            mTvCarPlate.setText(park.getVehicleNo());
            tvLift.setText(park.getLift());
            tvFloor.setText(String.valueOf(park.getFloor()));
            tvParkSlot.setText(String.valueOf(park.getParkSlot()));
        }


        // buton
        Button btnCarDeliver = (Button) view.findViewById(R.id.btn_car_deliver);

        // navcontrollwe
        mNavController = Navigation.findNavController(view);

        // button action
        btnCarDeliver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // todo validate and confirm
                saveData();


                //todo: ensure successfull send of data

                //mNavController.navigate(R.id.action_carDetailFragment_to_homeFragment);
            }
        });
    }

    // save data to remote
    private void saveData(){
        mViewModel.exitPark(mTvCarPlate.getText().toString().trim())
                .observe(getViewLifecycleOwner(), new Observer<Boolean>() {
                    @Override
                    public void onChanged(@NonNull Boolean aBoolean) {

                        if(aBoolean)
                            // navigate to homepage
                            mNavController.navigate(R.id.action_carDetailFragment_to_homeFragment);
                        else
                            Toast.makeText(getActivity(), "Something went wrong! Check internet", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    // confirm that the car is given to the owner
    public boolean confirmCarGiven(){
        return false;
    }



}
