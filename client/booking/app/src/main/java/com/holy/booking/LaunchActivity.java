package com.holy.booking;

import com.holy.booking.activity.MainActivity;
import com.holy.booking.fragment.assist.PermissionsFragment;
import com.holy.common.app.Activity;

public class LaunchActivity extends Activity {

    @Override
    protected int getContentLayoutID() {
        return R.layout.activity_launch;
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (PermissionsFragment.haveAll(this, getSupportFragmentManager())) {
            MainActivity.show(this);
            finish();
        }
    }
}
