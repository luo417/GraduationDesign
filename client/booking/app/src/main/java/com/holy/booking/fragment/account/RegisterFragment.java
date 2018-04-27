package com.holy.booking.fragment.account;


import android.content.Context;

import com.holy.booking.R;
import com.holy.common.app.PresenterFragment;
import com.holy.factory.presenter.account.RegisterContract;
import com.holy.factory.presenter.account.RegisterPresenter;

public class RegisterFragment extends PresenterFragment<RegisterContract.Presenter> implements RegisterContract.View{
    private AccountTrigger mAccountTrigger;


    public RegisterFragment() {
    }

    @Override
    protected int getContentLayoutID() {
        return R.layout.fragment_register;
    }

    @Override
    protected RegisterContract.Presenter initPresenter() {
        return new RegisterPresenter(this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mAccountTrigger = (AccountTrigger) context;
    }


    @Override
    public void registerSuccess() {

    }
}
