package com.kaps.valetparking.utils;

import android.content.Context;

import java.util.UUID;

public class RandomIdGenerator {


    private static RandomIdGenerator mRandomIdGenerator;

    public static RandomIdGenerator getInstance(){
        if( mRandomIdGenerator == null)
            mRandomIdGenerator = new RandomIdGenerator();

        return mRandomIdGenerator;
    }

    public static void creatUniqueId(Context context){
        SharedPreferenceUtil.getInstance(context);

        String uniqueId = getId();

        if( uniqueId == null){
            uniqueId = getUUID();

            // store data to pref
            SharedPreferenceUtil.putString(Constants.GENERATED_ID, uniqueId);
        }
    }

    private static String getUUID(){
        return UUID.randomUUID().toString();
    }

    public static String getId(){
        return SharedPreferenceUtil.getString(Constants.GENERATED_ID);
    }

}
