package com.holy.booking.fragment.main;


import android.view.View;

import com.holy.booking.R;
import com.holy.common.app.Fragment;
import com.holy.common.widget.GalleryView;

import butterknife.BindView;


public class HomeFragment extends Fragment {
    @BindView(R.id.galleyView)
    GalleryView mGalleryView;


    @Override
    protected int getContentLayoutID() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initWidget(View root) {
        super.initWidget(root);

        mGalleryView.setup(getLoaderManager(), new GalleryView.SelectedChangeListener() {
            @Override
            public void onSelectedCountChanged(int count) {


            }
        });
    }
}
