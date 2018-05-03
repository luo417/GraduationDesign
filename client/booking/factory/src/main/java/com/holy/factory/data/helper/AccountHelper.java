package com.holy.factory.data.helper;

import com.holy.factory.Factory;
import com.holy.factory.R;
import com.holy.factory.data.DataSource;
import com.holy.factory.model.api.RspModel;
import com.holy.factory.model.api.account.AccountRspModel;
import com.holy.factory.model.api.account.RegisterModel;
import com.holy.factory.model.db.User;
import com.holy.factory.net.NetWork;
import com.holy.factory.net.RemoteService;
import com.holy.factory.persistence.Account;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author Hailin
 * @time 2018/4/27 09:53
 * @function
 */
public class AccountHelper {

    public static void register(RegisterModel model, final DataSource.Callback<User> callback) {
        RemoteService service = NetWork.getRetrofit().create(RemoteService.class);
        Call<RspModel<AccountRspModel>> call = service.accountRegister(model);
        call.enqueue(new Callback<RspModel<AccountRspModel>>() {
            @Override
            public void onResponse(Call<RspModel<AccountRspModel>> call, Response<RspModel<AccountRspModel>> response) {
                RspModel<AccountRspModel> rspModel = response.body();

                if (rspModel.success()) {
                    //拿到实体
                    AccountRspModel accountRspModel = rspModel.getResult();

                    User user = accountRspModel.getUser();

                    //数据库写入和缓存绑定
                    // 第一种，之间保存
                    user.save();
                        /*
                        // 第二种通过ModelAdapter
                        FlowManager.getModelAdapter(User.class)
                                .save(user);

                        // 第三种，事务中
                        DatabaseDefinition definition = FlowManager.getDatabase(AppDatabase.class);
                        definition.beginTransactionAsync(new ITransaction() {
                            @Override
                            public void execute(DatabaseWrapper databaseWrapper) {
                                FlowManager.getModelAdapter(User.class)
                                        .save(user);
                            }
                        }).build().execute();
                        */

                    Account.login(accountRspModel);

                    //判断是否绑定
                    if (accountRspModel.isBind()) {
                        callback.onDataLoaded(user);
                    } else {
                        //设备绑定
                        bindPush(callback);
                    }

                } else {
                    //对返回的RspModel中的code进行解析，解析到对应的String资源上
                    Factory.decodeRspCode(rspModel, callback);
                }
            }

            @Override
            public void onFailure(Call<RspModel<AccountRspModel>> call, Throwable t) {
                callback.onDataNotAvailable(R.string.data_network_error);
            }
        });

        System.out.println("register finished");
    }

    public static void bindPush(final DataSource.Callback<User> callback) {
        Account.setBind(true );
//        callback.onDataNotAvailable(R.string.app_name);
    }
}
