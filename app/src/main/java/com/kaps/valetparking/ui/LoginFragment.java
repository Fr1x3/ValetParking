package com.kaps.valetparking.ui;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.kaps.valetparking.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {


    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // login button
        Button btnLogin = (Button) view.findViewById(R.id.btn_login);

        // password and username
        final String user = view.findViewById(R.id.et_user).toString().trim();
        final String password = view.findViewById(R.id.et_password).toString().trim();

        // get navcontroller
        final NavController navController = Navigation.findNavController(view);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validate(user, password)){
                    //navigate to homepage
                    navController.navigate(R.id.homeFragment);
                }
            }
        });
    }

    public boolean validate(String user, String password){

        if( user == null & password == null){
            Toast.makeText(getActivity(), "Please Enter username and password", Toast.LENGTH_SHORT).show();
            return false;
        }
        // todo : check if password and user matches that from the db

        return true;
    }
}
