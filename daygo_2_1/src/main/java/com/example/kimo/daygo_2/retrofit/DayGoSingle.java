package com.example.kimo.daygo_2.retrofit;

/**
 * Created by Administrator on 2016/3/8 0008.
 */
public class DayGoSingle {
    private static DayGoAPI dayGoAPI = null;
    public static DayGoAPI getInstance(){
        if(dayGoAPI == null){
            synchronized (DayGoSingle.class){
                if(dayGoAPI == null){
                    dayGoAPI = new DayGoRetrofit().createService(DayGoAPI.class);
                }
            }
        }
        return dayGoAPI;
    }
}
