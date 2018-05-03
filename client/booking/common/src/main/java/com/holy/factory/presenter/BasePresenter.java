package com.holy.factory.presenter;

/**
 * @author Hailin
 * @time 2018/4/27 09:16
 * @function
 */
public class BasePresenter<T extends BaseContract.View>  implements BaseContract.Presenter{
    protected T mView;

    public BasePresenter(T view) {
        setView(view);
    }

    @SuppressWarnings("unchecked")
    protected void setView(T view) {
        this.mView = view;
        this.mView.setPresenter(this);
    }

    protected T getView() {
        return mView;
    }

    @Override
    public void start() {
        T view = mView;
        if (view != null) {
            view.showLoading();
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public void destory() {
        T view = mView;
        mView = null;
        if (view != null) {
            view.setPresenter(null);
        }
    }
}
