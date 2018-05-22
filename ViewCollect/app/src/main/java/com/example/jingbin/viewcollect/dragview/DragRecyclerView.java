package com.example.jingbin.viewcollect.dragview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.AttributeSet;

import com.example.jingbin.viewcollect.dragview.child.BaseLabelAdapter;
import com.example.jingbin.viewcollect.dragview.child.DragGridLayoutManager;
import com.example.jingbin.viewcollect.dragview.child.DragItemStartListener;
import com.example.jingbin.viewcollect.dragview.child.LabelSelecterAdapter;
import com.example.jingbin.viewcollect.dragview.child.MyItemTouchHelper;
import com.example.jingbin.viewcollect.dragview.child.OnLabelItemTouchListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiaguangcheng on 16/10/25.
 */

public class DragRecyclerView extends RecyclerView implements DragItemStartListener {

    private LabelSelecterAdapter mAdapter;
    private DragGridLayoutManager mManager;
    private List mDatas;
    private ItemTouchHelper mItemTouchHelper;
    private OnLabelItemTouchListener mOnItemTouchListener;
    private int mKeepItemCount = 1;

    public DragRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthSpec, expandSpec);
    }

    public DragRecyclerView datas(List datas) {
        if (null == mDatas) mDatas = new ArrayList();
        mDatas.clear();
        mDatas.addAll(datas);
        return this;
    }



    public DragRecyclerView onItemTouch(OnLabelItemTouchListener onItemTouchListener) {
        mOnItemTouchListener = onItemTouchListener;
        return this;
    }



    public DragRecyclerView keepItemCount(int keepItemCount) {
        mKeepItemCount = keepItemCount;
        return this;
    }

    public void build() {
        if (null == mAdapter) {
            mAdapter = new LabelSelecterAdapter(this);
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
            mAdapter.setOnLongPressListener(mOnItemTouchListener);
        }

        mAdapter.setKeepItemCount(mKeepItemCount);

        MyItemTouchHelper callBack = new MyItemTouchHelper(mAdapter, mKeepItemCount);
        mItemTouchHelper = new ItemTouchHelper(callBack);
        mItemTouchHelper.attachToRecyclerView(this);
    }

    @Override
    public void onDragStart(ViewHolder viewHolder) {
        mItemTouchHelper.startDrag(viewHolder);
    }

    public void quitLongPressMode() {
        if (null != mAdapter) {
            mAdapter.quitLongPressMode();
        }
    }

    public boolean getLongPressMode() {
        Adapter adapter = getAdapter();
        if (adapter instanceof LabelSelecterAdapter) {
            return ((LabelSelecterAdapter) adapter).getLongPressMode();
        }
        return false;
    }

    public void addItem(String data) {
        mAdapter.onItemInsert(0, data);
    }

    /**
     * @return transformed datas
     */
    public List getDatas() {
        if (getAdapter() instanceof BaseLabelAdapter) {
            return ((BaseLabelAdapter) getAdapter()).getDatas();
        }
        return mDatas;
    }
}
