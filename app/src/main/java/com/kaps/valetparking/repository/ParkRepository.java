package com.kaps.valetparking.repository;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.kaps.valetparking.models.Park;
import com.kaps.valetparking.network.ServiceBuilder;
import com.kaps.valetparking.network.ValetParkService;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ParkRepository {
    private static ParkRepository sParkRepository;

    private ValetParkService mValetParkService;
    private static MutableLiveData<Boolean> mExitStatus ;

    // constructor to create Park service from retrofit
    public ParkRepository(){
        mValetParkService = ServiceBuilder.createService(ValetParkService.class);
    }

    // get instance of Parkrepository
    public static ParkRepository getInstance(){
        //check if already an instance was created
        if( sParkRepository == null ){
            sParkRepository = new ParkRepository();
        }
        return sParkRepository;
    }


    // exit the car park
    public MutableLiveData<Boolean> exitPark(String plate_number){
        mExitStatus = new MutableLiveData<>();
        new exitParkTask(mValetParkService).execute(plate_number);
        return mExitStatus;
    }

    //get single park detail
    public MutableLiveData<List<Park>> getPark(){
        final MutableLiveData<List<Park>> parkData = new MutableLiveData<>();

        mValetParkService.getParkDetail().enqueue(new Callback<List<Park>>() {
            @Override
            public void onResponse(Call<List<Park>> call, Response<List<Park>> response) {
                if( response.isSuccessful())
                    if(response.code() == 200){
                        Log.d("repo", "successful");
                        Log.d("repo", response.body().toString());
                        parkData.setValue(response.body());

                    }

            }

            @Override
            public void onFailure(Call<List<Park>> call, Throwable t) {
                parkData.setValue(Collections.<Park>emptyList());
                Log.d("home",t.toString());
            }
        });


        return parkData;
    }

    // create a park item
    public void createPark(Park park){
        new createParkTask(mValetParkService).execute(park);

    }

    public static class createParkTask extends AsyncTask<Park, Void, Void>{

        private ValetParkService mValetParkService;

        private createParkTask(ValetParkService valetParkService){ this.mValetParkService = valetParkService;}
        @Override
        protected Void doInBackground(Park... parks) {
            mValetParkService.createPark(parks[0]).enqueue(new Callback<Park>() {
                @Override
                public void onResponse(Call<Park> call, Response<Park> response) {
                    //ToDO: navigate to homepage and send new data
                    if( response.isSuccessful())
                        Log.d("Create data", "Successfully stored data");
                    else if( response.code() == 500)
                        Log.d("Create Data", "No Server connection");

                }

                @Override
                public void onFailure(Call<Park> call, Throwable t) {
                    // error message
                    //Toast.makeText(, "", Toast.LENGTH_SHORT).show();
                    Log.d("Create data", "failure");

                }
            });
            return null;
        }
    }


    private static class exitParkTask extends AsyncTask<String, Void,Boolean>{

        private ValetParkService mValetParkService;
        private exitParkTask(ValetParkService valetParkService){
            this.mValetParkService = valetParkService;
        }


        @Override
        protected Boolean doInBackground(String... strings) {
            final Boolean[] status = {false};
            mValetParkService.exitPark(strings[0]).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if(response.code() == 200 && response.body() != null) {
                        Log.d("Repo", "exit the building ");
                        //try {
                            //if(response.body().string().equals("1"))
                            //status[0] = true;
                                String memo = response.message();
                                mExitStatus.setValue(true);
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }

                    }

                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    mExitStatus.setValue(false);
                }
            });
            return status[0];
        }

//        @Override
//        protected void onPostExecute(Boolean aBoolean) {
//            super.onPostExecute(aBoolean);
//            if(aBoolean)
//                mExitStatus.setValue(true);
//        }
    }

}
