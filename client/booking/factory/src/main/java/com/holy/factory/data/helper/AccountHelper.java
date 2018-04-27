package com.holy.factory.data.helper;

import com.holy.factory.R;
import com.holy.factory.data.DataSource;
import com.holy.factory.model.api.account.RegisterModel;
import com.holy.factory.model.db.User;

/**
 * @author Hailin
 * @time 2018/4/27 09:53
 * @function
 */
public class AccountHelper {


    public static void register(RegisterModel model, final DataSource.Callback<User> callback) {
        new Thread() {
            @Override
            public void run() {
                super.run();

                try {
                    sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                callback.onDataNotAvailable(R.string.data_rsp_error_parameters);
            }
        }.start();
    }
}
