package com.example.jingbin.viewcollect.dragview.child;

import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiaguangcheng on 16/10/25.
 */

public abstract class BaseLabelAdapter<T, VH extends RecyclerView.ViewHolder>
        extends RecyclerView.Adapter<VH> implements OnLabelItemChangeListener<T> {

    protected List<T> mDatas = new ArrayList<>();

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public void setDatas(List<T> datas) {
        mDatas.clear();
        mDatas.addAll(datas);
        notifyDataSetChanged();
    }

    public List<T> getDatas() {
        return mDatas;
    }
}
