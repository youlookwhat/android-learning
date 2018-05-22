package com.example.jingbin.viewcollect.dragview.child;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jingbin.viewcollect.R;


/**
 * Created by xiaguangcheng on 16/10/25.
 */

public class LabelUnSelectedAdapter extends BaseLabelAdapter<String, DragViewHolder> {

    //    private OnItemRemovedListener mOnItemRemovedListener;
    private OnLabelItemTouchListener<String> mOnItemTouchListener;

    @Override
    public void onBindViewHolder(final DragViewHolder holder, int position) {
        onViewHolderBind(holder, position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDatas != null && holder.getAdapterPosition() > -1 && holder.getAdapterPosition() < mDatas.size()) {
                    if (null != mOnItemTouchListener) {
                        mOnItemTouchListener.onItemRemoved(holder.getAdapterPosition(), mDatas.get(holder.getAdapterPosition()));
                    }
                    onItemRemoved(holder.getAdapterPosition());
                }
            }
        });
    }

    protected void onViewHolderBind(DragViewHolder holder, int position) {
        holder.onNormalMode();
        holder.mTextView.setText(mDatas.get(holder.getAdapterPosition()));
    }

    @Override
    public DragViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_square_label, parent, false);
        return new DragViewHolder(itemView);
    }


    public void setmOnItemTouchListener(OnLabelItemTouchListener<String> mOnItemTouchListener) {
        this.mOnItemTouchListener = mOnItemTouchListener;
    }

    @Override
    public void onItemRemoved(int position) {
        mDatas.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        //never be used
        return false;
    }

    @Override
    public void onItemInsert(int position, String data) {
        mDatas.add(position, data);
        notifyItemInserted(position);
    }

    /**
     * Add item at the end
     *
     * @param data
     */
    public void addItem(String data) {
        onItemInsert(mDatas.size(), data);
    }

    /**
     * Add item with position
     *
     * @param insertPosition
     * @param data
     */
    public void addItem(int insertPosition, String data) {
        onItemInsert(insertPosition, data);
    }

}
