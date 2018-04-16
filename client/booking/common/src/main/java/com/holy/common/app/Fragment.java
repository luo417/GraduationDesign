package com.holy.common.app;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author Hailin
 * @time 2018/4/15 16:45
 * @function
 */

public abstract class Fragment extends android.support.v4.app.Fragment {
    protected View mRoot;
    protected Unbinder mRootUnBinder;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        //初始化参数
        initArgs(getArguments());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (mRoot == null) {
            int layoutID = getContentLayoutID();
            //初始化当前根布局，但不在创建时就添加到container
            View root = inflater.inflate(layoutID, container, false);
            initWidget(root);

            mRoot = root;
        } else {
            if (mRoot.getParent() != null) {
                ((ViewGroup)mRoot.getParent()).removeView(mRoot);
            }
        }

        return mRoot;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
    }

    protected void initArgs(Bundle bundle){

    }

    protected abstract int getContentLayoutID();

    protected void initWidget(View root){
        mRootUnBinder = ButterKnife.bind(this, root);
    }

    protected void initData(){

    }

    /**
     * 返回按键触发是调用
     * @return 返回true代表我已处理返回逻辑，Activity不用自己处理
     */
    public boolean onBackPressed(){
        return false;
    }
}
