package com.holy.factory.presenter.account;


import android.text.TextUtils;

import com.holy.common.Common;
import com.holy.factory.R;
import com.holy.factory.data.DataSource;
import com.holy.factory.data.helper.AccountHelper;
import com.holy.factory.model.api.account.RegisterModel;
import com.holy.factory.model.db.User;
import com.holy.factory.presenter.BasePresenter;

import net.qiujuer.genius.kit.handler.Run;
import net.qiujuer.genius.kit.handler.runable.Action;

import java.util.regex.Pattern;

/**
 * @author Hailin
 * @time 2018/4/27 09:12
 * @function
 */
public class RegisterPresenter extends BasePresenter<RegisterContract.View>
        implements RegisterContract.Presenter, DataSource.Callback<User> {

    public RegisterPresenter(RegisterContract.View view) {
        super(view);
    }

    @Override
    public void register(String phone, String name, String passwd) {
        start();

        RegisterContract.View view = getView();

        if (!checkMobile(phone)) {
            view.showError(R.string.data_account_register_invalid_parameter_mobile);
        } else if (name.length() < 2) {
            view.showError(R.string.data_account_register_invalid_parameter_name);
        } else if (passwd.length() < 6) {
            view.showError(R.string.data_account_register_invalid_parameter_password);
        } else {  //发送网络请求
            RegisterModel model = new RegisterModel(phone, passwd, name);
            AccountHelper.register(model, this);
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

    @Override
    public void onDataLoaded(User user) {
        final RegisterContract.View view = getView();
        if(view == null) return;
        Run.onUiAsync(new Action() {
            @Override
            public void call() {
                view.registerSuccess();
            }
        });
    }

    @Override
    public void onDataNotAvailable(final int strRes) {
        final RegisterContract.View view = getView();
        if(view == null) return;
        Run.onUiAsync(new Action() {
            @Override
            public void call() {
                view.showError(strRes);
            }
        });
    }
}
