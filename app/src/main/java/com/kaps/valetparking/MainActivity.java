package com.kaps.valetparking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.kaps.valetparking.utils.RandomIdGenerator;
import com.kaps.valetparking.utils.SharedPreferenceUtil;
import com.kaps.valetparking.viewmodels.LoginViewModel;

public class MainActivity extends AppCompatActivity {
    private LoginViewModel mLoginViewModel;

//    private MainViewModel mViewModel;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // count listener
        //mViewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        //connect to socket
        ///mViewModel.connect();

        SharedPreferenceUtil.getInstance(getApplicationContext());

        // check if the app is tied to an id if not generate  one
        RandomIdGenerator.getInstance();
        String id = RandomIdGenerator.getId();

        if( id == null)
            RandomIdGenerator.creatUniqueId(this);

        final NavController navController = Navigation.findNavController(this, R.id.navHostFragment);
//
        mLoginViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);

        // navigate between homepage and login
        mLoginViewModel.mAuthenticationState.observe(this,
                new Observer<LoginViewModel.AuthenticationState>() {
                    @Override
                    public void onChanged(LoginViewModel.AuthenticationState authenticationState) {
                        switch(authenticationState){
                            case UNAUTHENTICATED:
                                navController.navigate(R.id.loginFragment);
                                break;
                            case AUTHENTICATED:
                                navController.navigate(R.id.homeFragment);
                                Toast.makeText(getApplication(),
                                        "Welcome Back",
                                        Toast.LENGTH_SHORT);
                                break;
                        }
                    }
                });


//
//        if( SharedPreferenceUtil.getString("email") == null)
//            navController.navigate(R.id.loginFragment);
//        else
//            navController.navigate(R.id.homeFragment);





    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
       // mViewModel.disconnect();

        mLoginViewModel.logOut("");


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.logoff, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch(item.getItemId()){
            case R.id.action_log_off:
                // call method to log off
                mLoginViewModel.logOut(SharedPreferenceUtil.getString("email"));
                return true;


        }
        return super.onOptionsItemSelected(item);
    }
}
