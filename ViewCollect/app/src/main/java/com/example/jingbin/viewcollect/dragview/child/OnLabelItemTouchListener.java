package com.example.jingbin.viewcollect.dragview.child;

import android.support.v7.widget.RecyclerView;

/**
 * Created by xiaguangcheng on 16/10/25.
 */
public interface OnLabelItemTouchListener<T> {
    void onItemRemoved(int position, T removedItem);

    void onLongPress();
    void onItemClick(RecyclerView.ViewHolder holder, int position);

}
