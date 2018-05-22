package com.example.jingbin.viewcollect.dragview.child;

/**
 * Created by xiaguangcheng on 16/10/25.
 */
public interface OnLabelItemChangeListener<T> {

    boolean onItemMove(int fromPosition, int toPosition);

    void onItemRemoved(int position);

    void onItemInsert(int position, T data);
}
