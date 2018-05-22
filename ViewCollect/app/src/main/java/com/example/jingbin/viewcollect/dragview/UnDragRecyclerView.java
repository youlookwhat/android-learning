package com.example.jingbin.viewcollect.dragview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.example.jingbin.viewcollect.dragview.child.BaseLabelAdapter;
import com.example.jingbin.viewcollect.dragview.child.DragGridLayoutManager;
import com.example.jingbin.viewcollect.dragview.child.LabelUnSelectedAdapter;
import com.example.jingbin.viewcollect.dragview.child.OnLabelItemTouchListener;

import java.util.List;

/**
 * Created by xiaguangcheng on 16/10/25.
 */

public class UnDragRecyclerView extends RecyclerView {

    private LabelUnSelectedAdapter mAdapter;
    private DragGridLayoutManager mManager;
    private List mDatas;


    public UnDragRecyclerView(Context context) {
        super(context);
    }

    public UnDragRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public UnDragRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    public UnDragRecyclerView datas(List datas) {
        mDatas = datas;
        return this;
    }

    OnLabelItemTouchListener mOnItemTouchListener;
    public UnDragRecyclerView onItemTouch(OnLabelItemTouchListener onItemTouchListener) {
        mOnItemTouchListener = onItemTouchListener;
        return this;
    }


    public void build() {
        if (null == mAdapter) {
            mAdapter = new LabelUnSelectedAdapter();
        }
        this.setAdapter(mAdapter);

        if (null == mManager) {
            mManager = new DragGridLayoutManager(getContext(), 3);
        }
        this.setLayoutManager(mManager);

        if (null != mDatas) {
            mAdapter.setDatas(mDatas);
        }

        if (null != mOnItemTouchListener) {
            mAdapter.setmOnItemTouchListener(mOnItemTouchListener);
        }
    }

    public void addItem(String data) {
        if (null != mAdapter) {
            mAdapter.addItem(data);
        }
    }

    public void addItem(int insertPosition, String data) {
        mAdapter.addItem(insertPosition, data);
    }

    public List getDatas() {
        if (getAdapter() instanceof BaseLabelAdapter) {
            return ((BaseLabelAdapter) getAdapter()).getDatas();
        }
        return mDatas;
    }
}
