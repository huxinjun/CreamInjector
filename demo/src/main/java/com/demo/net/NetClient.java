package com.demo.net;

import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2018/9/21.
 */

public class NetClient {

    static NetClient client;

    API api;

    public static API getApi() {
        if (client == null) {
            client = new NetClient();
            init();
        }
        return client.api;
    }

    private static void init() {
        OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS);
        GsonBuilder gsonBuilder = new GsonBuilder();

        OkHttpClient okHttpClient = okHttpBuilder.build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://101.200.57.166:8080/")//不写会报错，动态域会被 @Url 替换
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gsonBuilder.create()))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())//支持rxjava
                .build();
        client.api = retrofit.create(API.class);
    }


}
