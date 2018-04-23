package com.holy.common.widget.recycler;

import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.holy.common.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author Hailin
 * @time 2018/4/17 12:54
 * @function
 */

public abstract class RecyclerAdapter<Data>
        extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder<Data>>
        implements View.OnLongClickListener, View.OnClickListener, AdapterCallback<Data> {
    private final List<Data> mDataList;
    private AdapterListener<Data> mListener;

    public RecyclerAdapter() {
        this(null);
    }

    public RecyclerAdapter(AdapterListener<Data> mListener) {
        this(new ArrayList<Data>(), mListener);
    }

    public RecyclerAdapter(List<Data> dataList, AdapterListener<Data> mListener) {
        this.mDataList = dataList;
        this.mListener = mListener;
    }

    @Override
    public int getItemViewType(int position) {
        return getItemViewType(position, mDataList.get(position));
    }

    /**
     * 得到布局的类型
     * @param position
     * @param data
     * @return XML文件的ID，用于创建ViewHolder
     */
    @LayoutRes
    protected abstract int getItemViewType(int position, Data data);

    /**
     * 创建一个ViewHolder
     * @param parent RecyclerView
     * @param viewType 界面类型，约定为XML布局的ID
     * @return ViewHolder
     */
    @Override
    public ViewHolder<Data> onCreateViewHolder(ViewGroup parent, int viewType) {
        //LayoutInflater用于将XML初始化为View
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View root = inflater.inflate(viewType, parent, false);
        ViewHolder<Data> holder = onCreateViewHolder(root, viewType);

        //设置View的tag为ViewHolder，进行双向绑定
        root.setTag(R.id.tag_recycler_holder, holder);

        //设置点击事件
        root.setOnClickListener(this);
        root.setOnLongClickListener(this);


        //进行界面注解绑定
        holder.unbinder = ButterKnife.bind(holder, root);

        //绑定callback
        holder.callback = this;

        return null;
    }

    /**
     * 得到一个新的ViewHolder
     * @param root 根布局
     * @param viewType 界面类型，约定为XML布局的ID
     * @return ViewHolder
     */
    protected abstract ViewHolder<Data> onCreateViewHolder(View root, int viewType);

    /**
     * 绑定数据到一个Holder上
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(ViewHolder<Data> holder, int position) {
        //得到需要绑定的数据
        Data data = mDataList.get(position);
        //绑定数据
        holder.bind(data);
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    /**
     * 插入一条数据，并通知插入
     * @param data
     */
    public void add(Data data) {
        mDataList.add(data);
        notifyItemInserted(mDataList.size()-1);
    }

    /**
     * 插入多条数据，并通知插入
     * @param dataList
     */
    public void add(Data... dataList){
        if(dataList != null && dataList.length > 0){
            int startPos = mDataList.size();
            Collections.addAll(mDataList, dataList);
            notifyItemRangeInserted(startPos, dataList.length);
        }
    }

    /**
     * 插入多条数据，并通知插入
     * @param dataList
     */
    public void add(Collection<Data> dataList){
        if(dataList != null && dataList.size() > 0){
            int startPos = mDataList.size();
            mDataList.addAll(dataList);
            notifyItemRangeInserted(startPos, dataList.size());
        }
    }

    /**
     * 清空
     */
    public void clear(){
        mDataList.clear();
        notifyDataSetChanged();
    }

    /**
     * 将数据替换为一个新的集合
     * @param dataList 替换后的数据
     */
    public void replace(Collection<Data> dataList) {
        mDataList.clear();
        if (dataList == null || dataList.size() == 0)
            return;
        mDataList.addAll(dataList);
        notifyDataSetChanged();
    }

    @Override
    public void updata(Data data, ViewHolder<Data> holder) {
        int pos = holder.getAdapterPosition();
        if (pos >= 0) {
            mDataList.remove(pos);
            mDataList.add(pos, data);
            notifyItemChanged(pos);
        }
    }

    @Override
    public void onClick(View view) {
        ViewHolder holder = (ViewHolder) view.getTag(R.id.tag_recycler_holder);
        if (this.mListener != null) {
            int pos = holder.getAdapterPosition();
            this.mListener.onItemClick(holder, mDataList.get(pos));
        }
    }

    @Override
    public boolean onLongClick(View view) {
        ViewHolder holder = (ViewHolder) view.getTag(R.id.tag_recycler_holder);
        if (this.mListener != null) {
            int pos = holder.getAdapterPosition();
            this.mListener.onItemLongClick(holder, mDataList.get(pos));
            return true;
        }
        return false;
    }

    /**
     * 设置适配器的监听
     * @param listener
     */
    public void setListener(AdapterListener<Data> listener) {
        this.mListener = listener;
    }

    /**
     * 自定义监听器
     * @param <Data>
     */
    public interface AdapterListener<Data> {
        void onItemClick(RecyclerAdapter.ViewHolder<Data> holder, Data data);
        void onItemLongClick(RecyclerAdapter.ViewHolder<Data> holder, Data data);
    }

    /**
     * 自定义ViewHolder
     * @param <Data>
     */
    public static abstract class ViewHolder<Data> extends RecyclerView.ViewHolder{
        private Unbinder unbinder;
        protected Data mData;
        private AdapterCallback<Data> callback;

        public ViewHolder(View itemView) {
            super(itemView);
        }

        /**
         * 绑定数据
         * @param data
         */
        void bind(Data data) {
            this.mData = data;
        }

        /**
         * 绑定数据时的回调
         * @param data
         */
        protected abstract void onBind(Data data);

        //更新当前ViewHolder
        public void updateData(Data data){
            if (this.callback != null) {
                callback.updata(data, this);
            }
        }
    }

    public static abstract class AdapterListenerImpl<Data> implements AdapterListener<Data>{

        @Override
        public void onItemClick(ViewHolder<Data> holder, Data data) {

        }

        @Override
        public void onItemLongClick(ViewHolder<Data> holder, Data data) {

        }
    }
}
