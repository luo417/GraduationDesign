package com.holy.common.app;

import android.content.Context;

import com.holy.factory.presenter.BaseContract;

/**
 * @author Hailin
 * @time 2018/4/27 09:02
 * @function
 */
public abstract class PresenterFragment<Presenter extends BaseContract.Presenter> extends Fragment implements BaseContract.View<Presenter> {
    protected Presenter mPresenter;

    protected abstract Presenter initPresenter();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        initPresenter();
    }

    @Override
    public void showError(int str) {
        Application.showToast(str);
    }

    @Override
    public void showLoading() {
        // TODO
    }

    @Override
    public void setPresenter(Presenter presenter) {
        mPresenter = presenter;
    }
}
