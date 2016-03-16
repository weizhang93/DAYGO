package com.example.kimo.daygo_2.retrofit;

import com.example.kimo.daygo_2.DayGoKey;
import com.squareup.okhttp.OkHttpClient;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;

/**
 * Created by Administrator on 2016/3/8 0008.
 */
public class DayGoRetrofit {
    private static OkHttpClient client = new OkHttpClient();

    private static Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl(DayGoKey.API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create());

    public <S>S createService(Class<S> serviceClass){
        Retrofit retrofit = builder.client(client).build();
        return retrofit.create(serviceClass);
    }
}
