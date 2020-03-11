package com.kaps.valetparking.repository;

import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.kaps.valetparking.models.Devices;
import com.kaps.valetparking.models.Park;
import com.kaps.valetparking.network.ServiceBuilder;
import com.kaps.valetparking.network.ValetParkService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
    public MutableLiveData<Park> getPark(String lift, String username){
        final MutableLiveData<Park> parkData = new MutableLiveData<>();

        mValetParkService.getParkDetail(lift, username).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if( response.isSuccessful())
                    if(response.code() == 200){
                        Log.d("repo", "successful");
                        Log.d("repo", response.body().toString());


                        try {
                            JSONObject object = new JSONObject(response.body().string());
                            JSONObject data = object.getJSONObject("data");
                            Park mpark = new Park(data.getString("plate_number"), data.getString("lift"), data.getString("floor"), data.getInt("slot"));
                           parkData.setValue(mpark);

                        } catch (IOException | JSONException e) {
                            e.printStackTrace();
                        }
                        // parkData.setValue(response.body());

                    }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                parkData.setValue(null);
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

            mValetParkService.exitPark(strings[0]).enqueue(new Callback<Devices>() {
                @Override
                public void onResponse(Call<Devices> call, Response<Devices> response) {
                    if(response.code() == 200 && response.body() != null) {
                        Log.d("Repo", "exit the building ");

                        Log.d("Exit park", response.message());
                        if( Integer.parseInt(response.body().getStatus()) == 0 )
                            mExitStatus.setValue(true);
                        else
                            mExitStatus.setValue(false);


                    }

                }

                @Override
                public void onFailure(Call<Devices> call, Throwable t) {
                    Log.d("Exit park", t.getMessage());
                    t.printStackTrace();
                    mExitStatus.setValue(false);
                }
            });

            return null;
        }

//        @Override
//        protected void onPostExecute(Boolean aBoolean) {
//            super.onPostExecute(aBoolean);
//            if(aBoolean)
//                mExitStatus.setValue(true);
//        }
    }

}
