package com.holy.factory.presenter;

import android.support.annotation.StringRes;

/**
 * MVP模式，公共契约
 *
 * @author Hailin
 * @time 2018/4/27 08:53
 * @function
 */
public interface BaseContract {
    interface View<T extends Presenter> {

        void showError(@StringRes int str);

        void showLoading();

        void setPresenter(T presenter);
    }

    interface Presenter {

        void start();

        void destory();

    }
}
