package com.kaps.valetparking.ui;


import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.kaps.valetparking.R;
import com.kaps.valetparking.models.Park;
import com.kaps.valetparking.viewmodels.ParkDetailsViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class ParkCarFragment extends Fragment {


    private String mCarNumberPlate;
    private String mLift;
    private String mFloor;
    private String mParkSlot;
    private ParkDetailsViewModel mViewModel;
    private EditText mEtFloor;
    private EditText mEtParkSlot;

    public  static ParkCarFragment newInstance() {
        return new ParkCarFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //custom onback pressed
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                Toast.makeText(getActivity(), "Enter the car details first to exit.", Toast.LENGTH_LONG).show();

            }
        };

        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_park_car, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mViewModel = ViewModelProviders.of(this).get(ParkDetailsViewModel.class);


        //button
        Button btnSave =  view.findViewById(R.id.btn_save);

        // navController
        final NavController navController = Navigation.findNavController(view);

        // edit text
        final EditText etCarPlate = view.findViewById(R.id.et_car_plate);
        final EditText etLift = view.findViewById(R.id.et_lift);
        mEtFloor = view.findViewById(R.id.et_floor);
        mEtParkSlot = view.findViewById(R.id.et_park_slot);

        // click button action
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // data collected
                mCarNumberPlate = etCarPlate.getText().toString().trim();
                mLift = etLift.getText().toString().trim();
                mFloor =  mEtFloor.getText().toString().trim();
                mParkSlot =  mEtParkSlot.getText().toString().trim();

                // Todo: validate and confirmation dialog box
                if(validate() ) {
                    // alert to confirm if the data is one to be sent
                    new AlertDialog.Builder(getActivity())
                            .setTitle("Confirm Car Details are correct")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    // add data to the db
                                    addData();
                                    //navigate to homepage
                                    navController.navigate(R.id.action_parkCarFragment_to_homeFragment);
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            })
                            .show();

                }
                else
                    Toast.makeText(getActivity(), "The fields cannot be empty", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addData() {

        // convert to integer
        int floor = Integer.parseInt(mFloor);
        int slot = Integer.parseInt(mParkSlot);
        //create a new park object
        // set data to the park object
        //todo observables
        Park park = new Park(mCarNumberPlate, mLift, floor, slot);
        mViewModel.createPark(park);
    }

    private boolean validate() {
        if( !mCarNumberPlate.isEmpty() &
            !mLift.isEmpty() &
            !mFloor.isEmpty() &
            !mParkSlot.isEmpty()) {

            convertCarNumberPlate();
            return true;
        }

        return false;
    }

    private void convertCarNumberPlate() {
        //to caps
        mCarNumberPlate = mCarNumberPlate.toUpperCase();

        // remove all white spaces
        mCarNumberPlate = mCarNumberPlate.replaceAll("\\s","");

    }


}
