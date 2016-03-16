package com.example.kimo.daygo_2.retrofit;

import com.example.kimo.daygo_2.model.bean.DataResult;
import com.example.kimo.daygo_2.model.bean.DayResult;

import retrofit.http.GET;
import retrofit.http.Path;
import rx.Observable;

/**
 * Created by Administrator on 2016/3/8 0008.
 */
public interface DayGoAPI {
    @GET("data/{type}/{number}/{page}")
    Observable<DataResult> getDataResults(
            @Path("type") String type,
            @Path("number") int number,
            @Path("page") int page
    );

    @GET("day/{year}/{month}/{day}")
    Observable<DayResult> getDayResults(
            @Path("year") int year,
            @Path("month") int month,
            @Path("day") int day
    );
}
