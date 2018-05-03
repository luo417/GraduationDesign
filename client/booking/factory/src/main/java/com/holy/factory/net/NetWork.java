package com.holy.factory.net;

import com.holy.common.Common;
import com.holy.factory.Factory;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author Hailin
 * @time 2018/4/27 21:26
 * @function
 */
public class NetWork {
    public static Retrofit getRetrofit() {
        OkHttpClient client = new OkHttpClient.Builder().build();
        Retrofit.Builder builder = new Retrofit.Builder();
        return builder.baseUrl(Common.Constance.API_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(Factory.getGson()))
                .build();
    }
}
