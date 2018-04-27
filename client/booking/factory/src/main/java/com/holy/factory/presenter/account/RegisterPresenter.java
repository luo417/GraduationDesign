package com.holy.factory.presenter.account;


import com.holy.factory.presenter.BasePresenter;

/**
 * @author Hailin
 * @time 2018/4/27 09:12
 * @function
 */
public class RegisterPresenter extends BasePresenter<RegisterContract.View> implements RegisterContract.Presenter {

    public RegisterPresenter(RegisterContract.View view) {
        super(view);
    }

    @Override
    public void register(String phone, String name, String passwd) {

    }

    @Override
    public boolean checkMobile(String phone) {
        return false;
    }
}
