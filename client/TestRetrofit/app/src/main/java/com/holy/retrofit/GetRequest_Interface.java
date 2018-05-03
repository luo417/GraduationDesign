package com.holy.retrofit;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * @author Hailin
 * @time 2018/5/2 16:22
 * @function
 */
public interface GetRequest_Interface {
    @GET("test")
    Call<Translation> getCall();
    // 注解里传入 网络请求 的部分URL地址
    // Retrofit把网络请求的URL分成了两部分：一部分放在Retrofit对象里，另一部分放在网络请求接口里
    // 如果接口里的url是一个完整的网址，那么放在Retrofit对象里的URL可以忽略
    // getCall()是接受网络请求数据的方法
}
