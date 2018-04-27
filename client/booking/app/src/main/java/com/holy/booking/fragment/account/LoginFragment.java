package com.holy.booking.fragment.account;


import android.content.Context;

import com.holy.booking.R;
import com.holy.common.app.Fragment;

public class LoginFragment extends Fragment {
    private AccountTrigger mAccountTrigger;

    public LoginFragment() {
    }

    @Override
    protected int getContentLayoutID() {
        return R.layout.fragment_login;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mAccountTrigger = (AccountTrigger) context;
    }

    @Override
    public void onResume() {
        super.onResume();
        //切换到注册界面
        mAccountTrigger.triggerView();
    }
}
