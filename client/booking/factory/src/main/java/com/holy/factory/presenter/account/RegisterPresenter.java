package com.holy.factory.presenter.account;


import android.text.TextUtils;

import com.holy.common.Common;
import com.holy.factory.data.helper.AccountHelper;
import com.holy.factory.model.api.RegisterModel;
import com.holy.factory.presenter.BasePresenter;

import java.util.regex.Pattern;

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
        start();

        if (!checkMobile(phone)) {

        } else if (name.length() < 2) {

        } else if (passwd.length() < 6) {

        } else {  //发送网络请求
            RegisterModel model = new RegisterModel(phone, passwd, name);
            AccountHelper.register(model);
        }
    }

    /**
     * 检查手机号是否合法
     * @param phone
     * @return
     */
    @Override
    public boolean checkMobile(String phone) {

        return !TextUtils.isEmpty(phone)
                && Pattern.matches(Common.Constance.REGEX_MOBILE, phone);
    }
}
