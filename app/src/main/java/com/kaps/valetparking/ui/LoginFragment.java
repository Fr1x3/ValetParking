package com.kaps.valetparking.ui;


import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
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
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.kaps.valetparking.R;
import com.kaps.valetparking.models.User;
import com.kaps.valetparking.viewmodels.LoginViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {


    private String mUser;
    private String mPassword;

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

        //view model
        final LoginViewModel viewModel = ViewModelProviders.of(this).get(LoginViewModel.class);

        // login button
        Button btnLogin = (Button) view.findViewById(R.id.btn_login);

        // EditText
        final EditText etUser = view.findViewById(R.id.et_user);
        final EditText etPassword = view.findViewById(R.id.et_password);

        // get navcontroller
        final NavController navController = Navigation.findNavController(view);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // password and username
                mUser = etUser.getText().toString().trim();
                mPassword = etPassword.getText().toString().trim();

                if(validate(mUser, mPassword)){
                    User users = new User(mUser, mPassword);
                    viewModel.authenticate(users);

                }
            }
        });

        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(),
                new OnBackPressedCallback(true) {
                    @Override
                    public void handleOnBackPressed() {
                        viewModel.refuseAuthentication();
                        navController.navigate(R.id.loginFragment);
                    }
                });

        viewModel.mAuthenticationState.observe(getViewLifecycleOwner(),
                new Observer<LoginViewModel.AuthenticationState>() {
                    @Override
                    public void onChanged(LoginViewModel.AuthenticationState authenticationState) {
                        switch( authenticationState){
                            case AUTHENTICATED:
                                navController.navigate(R.id.homeFragment);
                                break;
                            case INVALID_AUTHENTICATION:
                                Snackbar.make(getView(),
                                        "Invalid email or password",
                                        Snackbar.LENGTH_SHORT
                                        ).show();
                                break;
                        }
                    }
                });
    }

    public boolean validate(String user, String password) {

        if ( !user.isEmpty() && !password.isEmpty() )
            return true;


        // todo : check if password and user matches that from the db

        Toast.makeText(getActivity(), "Please Enter username and password", Toast.LENGTH_SHORT).show();
        return false;
    }
}
