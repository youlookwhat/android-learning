package com.example.jingbin.viewcollect.adapter;

import android.view.View;

/**
 * Created by yangcai on 16-4-23.
 */
public abstract class BinBaseHolder<T> implements BaseHolder<T> {

    public View itemView;

    public BinBaseHolder(View itemView) {
        this.itemView = itemView;
    }

    @Override
    public View getItemView() {
        return itemView;
    }




}
