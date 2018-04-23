package com.holy.booking.activity;

import android.content.Context;
import android.content.Intent;

import com.holy.booking.R;
import com.holy.booking.fragment.account.UpdateInfoFragment;
import com.holy.common.app.Activity;
import com.holy.common.app.Fragment;

public class AccountActivity extends Activity {
    private Fragment mCurFragment;

    public static void show(Context context) {
        context.startActivity(new Intent(context, AccountActivity.class));
    }

    @Override
    protected int getContentLayoutID() {
        return R.layout.activity_account;
    }

    @Override
    protected void initWidget() {
        super.initWidget();

        mCurFragment = new UpdateInfoFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.lay_container, mCurFragment)
                .commit();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mCurFragment.onActivityResult(requestCode, resultCode, data);
    }
}
