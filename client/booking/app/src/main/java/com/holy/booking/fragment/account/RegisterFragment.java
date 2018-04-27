package com.holy.booking.fragment.account;


import android.content.Context;
import android.widget.Button;
import android.widget.EditText;

import com.holy.booking.R;
import com.holy.booking.activity.MainActivity;
import com.holy.common.app.PresenterFragment;
import com.holy.factory.presenter.account.RegisterContract;
import com.holy.factory.presenter.account.RegisterPresenter;

import net.qiujuer.genius.ui.widget.Loading;

import butterknife.BindView;
import butterknife.OnClick;

public class RegisterFragment extends PresenterFragment<RegisterContract.Presenter> implements RegisterContract.View{
    private AccountTrigger mAccountTrigger;

    @BindView(R.id.edit_phone)
    EditText mPhone;

    @BindView(R.id.edit_name)
    EditText mName;

    @BindView(R.id.edit_password)
    EditText mPasswd;

    @BindView(R.id.loading)
    Loading mLoading;

    @BindView(R.id.btn_submit)
    Button mSubmit;


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

    @OnClick(R.id.btn_submit)
    void onSubmitClick() {
        String phone = mPhone.getText().toString();
        String name = mName.getText().toString();
        String passwd = mPasswd.getText().toString();

        mPresenter.register(phone, name, passwd);
    }

    @OnClick(R.id.txt_go_login)
    void onShowLoginClick() {
        //切换到登录界面
        mAccountTrigger.triggerView();
    }

    @Override
    public void showError(int str) {
        super.showError(str);

        mLoading.stop();
        mPhone.setEnabled(true);
        mName.setEnabled(true);
        mPasswd.setEnabled(true);
        mSubmit.setEnabled(true);
    }

    @Override
    public void showLoding() {
        super.showLoding();
        mLoading.start();
        mPhone.setEnabled(false);
        mName.setEnabled(false);
        mPasswd.setEnabled(false);
        mSubmit.setEnabled(false);
    }

    @Override
    public void registerSuccess() {
        //注册成功跳转到MainActivity
        MainActivity.show(getContext());
        getActivity().finish();
    }
}
