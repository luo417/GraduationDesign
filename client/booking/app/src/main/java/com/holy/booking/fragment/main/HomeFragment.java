package com.holy.booking.fragment.main;


import android.view.View;

import com.holy.booking.R;
import com.holy.common.app.Fragment;
import com.holy.common.widget.GalleyView;

import butterknife.BindView;


public class HomeFragment extends Fragment {
    @BindView(R.id.galleyView)
    GalleyView mGalleyView;


    @Override
    protected int getContentLayoutID() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initWidget(View root) {
        super.initWidget(root);

        mGalleyView.setup(getLoaderManager(), new GalleyView.SelectedChangeListener() {
            @Override
            public void onSelectedCountChanged(int count) {


            }
        });
    }
}
