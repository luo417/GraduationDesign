package com.holy.factory.net;

import com.holy.factory.model.api.RspModel;
import com.holy.factory.model.api.account.AccountRspModel;
import com.holy.factory.model.api.account.RegisterModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * @author Hailin
 * @time 2018/4/27 23:24
 * @function
 */
public interface RemoteService {
    @POST("register")
    Call<RspModel<AccountRspModel>> accountRegister(@Body RegisterModel model);
}
