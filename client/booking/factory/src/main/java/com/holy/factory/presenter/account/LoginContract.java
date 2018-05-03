package com.holy.factory.presenter.account;

import com.holy.factory.presenter.BaseContract;

/**
 * @author Hailin
 * @time 2018/4/27 08:45
 * @function
 */
public interface LoginContract {
    interface View extends BaseContract.View<Presenter>{
        void loginSuccess();
    }

    interface Presenter extends BaseContract.Presenter{
        void login(String phone, String passwd);
    }
}
