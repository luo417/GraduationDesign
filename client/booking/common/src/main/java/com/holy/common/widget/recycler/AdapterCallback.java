package com.holy.common.widget.recycler;

/**
 * @author Hailin
 * @time 2018/4/17 12:53
 * @function
 */

public interface AdapterCallback<Data> {
    void updata(Data data, RecyclerAdapter.ViewHolder<Data> holder);
}
