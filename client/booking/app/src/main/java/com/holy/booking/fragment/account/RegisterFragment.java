package com.holy.booking.fragment.account;


import android.content.Context;

import com.holy.booking.R;
import com.holy.common.app.Fragment;

public class RegisterFragment extends Fragment {
    private AccountTrigger mAccountTrigger;


    public RegisterFragment() {
    }

    @Override
    protected int getContentLayoutID() {
        return R.layout.fragment_register;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mAccountTrigger = (AccountTrigger) context;
    }

}
