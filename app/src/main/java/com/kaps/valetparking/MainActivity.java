package com.kaps.valetparking;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

//    private MainViewModel mViewModel;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // count listener
        //mViewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        //connect to socket
        ///mViewModel.connect();



        NavController navController = Navigation.findNavController(this, R.id.navHostFragment);

        // todo : navigate between homepage and login
        navController.navigate(R.id.homeFragment);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
       // mViewModel.disconnect();

    }
}
