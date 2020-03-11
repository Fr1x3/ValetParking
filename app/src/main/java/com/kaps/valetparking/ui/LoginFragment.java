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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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
    private String mZone;

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
        Button btnLogin =  view.findViewById(R.id.btn_login);

        // EditText
        final EditText etUser = view.findViewById(R.id.et_user);
        final EditText etPassword = view.findViewById(R.id.et_password);
        final Spinner spinnerZone = view.findViewById(R.id.spinner_login_zone);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(view.getContext(),
                R.array.zone_arrays, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerZone.setAdapter(adapter);

        spinnerZone.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mZone = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // get navcontroller
        final NavController navController = Navigation.findNavController(view);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // password and username
                mUser = etUser.getText().toString().trim();
                mPassword = etPassword.getText().toString().trim();


                if(validate(mUser, mPassword, mZone)){
                    User users = new User(mUser, mPassword);
                    users.setZone(mZone);
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

    public boolean validate(String user, String password, String zone) {

        if ( !user.isEmpty() && !password.isEmpty() && !zone.isEmpty())
            return true;


        Toast.makeText(getActivity(), "Please Enter username, password and zone", Toast.LENGTH_SHORT).show();
        return false;
    }
}
