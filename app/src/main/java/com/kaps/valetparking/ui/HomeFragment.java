package com.kaps.valetparking.ui;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.kaps.valetparking.R;
import com.kaps.valetparking.models.Park;
import com.kaps.valetparking.viewmodels.HomeViewModel;

import com.kaps.valetparking.utils.Constants;



import java.net.URISyntaxException;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private Socket socket;
    private TextView mTvCountCar;




    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        // count text view
        mTvCountCar = (TextView) view.findViewById(R.id.tv_car_count);

        // viewmodel
        final HomeViewModel viewModel = ViewModelProviders.of(this).get(HomeViewModel.class);

        //navController
        final NavController navController = Navigation.findNavController(view);

        // button
        Button btnParkCar = (Button) view.findViewById(R.id.btn_park_car);
        Button btngetCar = (Button) view.findViewById(R.id.btn_get_car);

        // navigate to process park car
        btnParkCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_homeFragment_to_parkCarFragment);
            }
        });

        // navigate to get car from parking
        btngetCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //check if there is any car to be picked


                viewModel.getCar().observe(getViewLifecycleOwner(), new Observer<List<Park>>() {
                    @Override
                    public void onChanged(List<Park> parks) {

                        if(parks.size() > 0 ) {


                            //create bundle
                             Bundle carBundle = new Bundle();
                             carBundle.putParcelable(Constants.PARK_DATA, parks.get(0));

                            // navigate to the next fragment
                            navController.navigate(R.id.action_homeFragment_to_carDetailFragment, carBundle);
                        }else
                            Toast.makeText(getActivity(),"No car to be collected", Toast.LENGTH_LONG).show();
                    }

                });


            }
        });
    }

    private Emitter.Listener incomingMessages = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            if( getActivity() != null) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (args[0] != null) {
                            int data = (int) args[0];

                            addMessage(String.valueOf(data));


                        }
                    }

                });
            }
        }
    };

    private void addMessage(String message) {
        if( mTvCountCar != null)
            mTvCountCar.setText(message);

    }


    @Override
    public void onResume() {
        super.onResume();
        try{
            socket = IO.socket(Constants.URL + Constants.PORT );
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        // SOCKETS
        socket.connect();
        socket.on("count-pending", incomingMessages );
    }


    @Override
    public void onPause() {
        super.onPause();
        socket.disconnect();
        socket.on("count-pending", incomingMessages );
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}
