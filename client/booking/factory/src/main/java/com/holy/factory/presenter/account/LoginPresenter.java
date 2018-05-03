package com.holy.factory.presenter.account;

import com.holy.factory.presenter.BasePresenter;

/**
 * @author Hailin
 * @time 2018/5/3 14:56
 * @function
 */
public class LoginPresenter extends BasePresenter<LoginContract.View>
implements LoginContract.Presenter{
    public LoginPresenter(LoginContract.View view) {
        super(view);
    }

    @Override
    public void login(String phone, String passwd) {

    }
}
