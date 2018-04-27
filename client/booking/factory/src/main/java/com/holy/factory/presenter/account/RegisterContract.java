package com.holy.factory.presenter.account;

import com.holy.factory.presenter.BaseContract;

/**
 * @author Hailin
 * @time 2018/4/27 08:44
 * @function
 */
public interface RegisterContract {
    interface View extends BaseContract.View<Presenter>{
        void registerSuccess();
    }

    interface Presenter extends BaseContract.Presenter{
        void register(String phone, String name, String passwd);

        //检查电话号码是否正确
        boolean checkMobile(String phone);
    }
}
