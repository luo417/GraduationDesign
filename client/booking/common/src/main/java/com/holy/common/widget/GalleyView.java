package com.holy.common.widget;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.holy.common.R;
import com.holy.common.widget.recycler.RecyclerAdapter;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class GalleyView extends RecyclerView {
    private static final int  LOADER_ID = 0x0100;
    private static final int  MAX_SELECTED_IMAGE_COUNT = 3;  //最大选中图片数量
    private static final int MIN_IMAGE_FILE_SIZE = 10*1024;  //最小图片大小

    private LoaderCallback mLoaderCallback = new LoaderCallback();
    private Adapter mAdapter = new Adapter();
    private List<Image> mSelectedImages = new LinkedList<>();
    private SelectedChangeListener mListener;

    public GalleyView(Context context) {
        super(context);
        init();
    }

    public GalleyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public GalleyView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        //每行4列
        setLayoutManager(new GridLayoutManager(getContext(), 4));
        setAdapter(mAdapter);
        mAdapter.setListener(new RecyclerAdapter.AdapterListenerImpl<Image>() {
            @Override
            public void onItemClick(RecyclerAdapter.ViewHolder<Image> holder, Image image) {
                if (onItemSelectClick(image)) {
                    holder.updateData(image);
                }
            }
        });
    }

    public int setup(LoaderManager loaderManager, SelectedChangeListener listener) {
        mListener = listener;
        loaderManager.initLoader(LOADER_ID, null, mLoaderCallback);
        return LOADER_ID;
    }

    /**
     * cell点击的具体逻辑
     * @param image
     * @return true表示处理了点击，需要数据刷新
     */
    private boolean onItemSelectClick(Image image) {
        boolean notifyRefresh;  //是否需要刷新
        if (mSelectedImages.contains(image)) {
            mSelectedImages.remove(image);
            image.isSelect = false;
            notifyRefresh = true;
        } else {
            if (mSelectedImages.size() >= MAX_SELECTED_IMAGE_COUNT) {
                //Toast一个提示消息
                notifyRefresh = false;
            } else {
                mSelectedImages.add(image);
                image.isSelect = true;
                notifyRefresh = true;
            }
        }

        //通知外层，数据有更改
        if(notifyRefresh) notifySelectChanged();

        return true;
    }

    /**
     * 得到所有选中图片的path
     * @return
     */
    public String[] getSelectedPath() {
        String[] paths = new String[mSelectedImages.size()];
        int index = 0;
        for (Image image : mSelectedImages) {
            paths[index++] = image.path;
        }
        return paths;
    }

    /**
     * 清空选中的图片
     */
    public void clear() {
        for (Image image : mSelectedImages) {
            image.isSelect = false;
        }
        mSelectedImages.clear();
        mAdapter.notifyDataSetChanged();
    }

    private void notifySelectChanged() {
        SelectedChangeListener listener = mListener;
        if (listener != null) {
            listener.onSelectedCountChanged(mSelectedImages.size());
        }
    }

    /**
     * 通知Adapter数据更改
     * @param images
     */
    private void updateSource(List<Image> images) {
        mAdapter.replace(images);
    }

    /**
     * 用于实际数据加载的Loader
     */
    private class LoaderCallback implements LoaderManager.LoaderCallbacks<Cursor> {

        private final String[] IMAGE_PROJECTION = new String[]{
                MediaStore.Images.Media._ID,  //ID
                MediaStore.Images.Media.DATA,  //图片路径
                MediaStore.Images.Media.DATE_ADDED  //图片创建时间
        };

        @Override
        public Loader<Cursor> onCreateLoader(int id, Bundle args) {
            if (id == LOADER_ID) {
                return new CursorLoader(getContext(),
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        IMAGE_PROJECTION,
                        null,
                        null,
                        IMAGE_PROJECTION[2]+" DESC");  //倒序查询
            }
            return null;
        }

        /**
         * 当loader加载完成时
         * @param loader
         * @param data
         */
        @Override
        public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
            List<Image> images = new ArrayList<>();
            if (data != null) {
                int count = data.getCount();
                if (count > 0) {
                    //游标移动到开始位置
                    data.moveToFirst();

                    int indexID = data.getColumnIndexOrThrow(IMAGE_PROJECTION[0]);
                    int indexPath = data.getColumnIndexOrThrow(IMAGE_PROJECTION[1]);
                    int indexDate = data.getColumnIndexOrThrow(IMAGE_PROJECTION[2]);
                    do { //循环读取，直到没有下一条数据
                        int id = data.getInt(indexID);
                        String path = data.getString(indexPath);
                        long date = data.getLong(indexDate);

                        File file = new File(path);
                        if (!file.exists() || file.length() < MIN_IMAGE_FILE_SIZE) {
                            continue;
                        }

                        Image image = new Image();
                        image.id = id;
                        image.path = path;
                        image.date = date;
                        images.add(image);
                    } while (data.moveToNext());


                }
            }

            updateSource(images);
        }

        /**
         * loader销毁或重置
         * @param loader
         */
        @Override
        public void onLoaderReset(Loader<Cursor> loader) {
            updateSource(null);
        }
    }


    /**
     * 内部的数据结构
     */
    private static class Image{
        int id;
        String path;
        long date;
        boolean isSelect;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Image image = (Image) o;

            return path != null ? path.equals(image.path) : image.path == null;
        }

        @Override
        public int hashCode() {
            return path != null ? path.hashCode() : 0;
        }
    }

    //适配器
    private class Adapter extends RecyclerAdapter<Image>{

        @Override
        protected int getItemViewType(int position, Image image) {
            return R.layout.cell_galley;
        }

        @Override
        protected ViewHolder<Image> onCreateViewHolder(android.view.View root, int viewType) {
            return new GalleyView.ViewHolder(root);
        }
    }

    //cell对应的holder
    private class ViewHolder extends RecyclerAdapter.ViewHolder<Image>{
        private ImageView mPic;
        private View mShade;
        private CheckBox mSelected;

        public ViewHolder(View itemView) {
            super(itemView);

            mPic = findViewById(R.id.im_image);
            mShade = findViewById(R.id.view_shade);
            mSelected = findViewById(R.id.cb_select);
        }

        @Override
        protected void onBind(Image image) {
            Glide.with(getContext())
                    .load(image.path)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)  //不缓存
                    .centerCrop()  //居中剪切
                    .placeholder(R.color.grey_200)  //默认颜色
                    .into(mPic);

            mShade.setVisibility(image.isSelect ? VISIBLE : INVISIBLE);
            mSelected.setChecked(image.isSelect);
        }
    }

    public interface SelectedChangeListener{
        void onSelectedCountChanged(int count);
    }

}
