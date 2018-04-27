package com.holy.factory.data;

import android.support.annotation.StringRes;

/**
 * @author Hailin
 * @time 2018/4/27 16:21
 * @function
 */
public interface DataSource {
    interface Callback<T> extends SuccessCallback<T>, FailedCallback {

    }

    interface SuccessCallback<T> {
        void onDataLoaded(T t);
    }

    interface FailedCallback {
        void onDataNotAvailable(@StringRes int strRes);
    }
}
