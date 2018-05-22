package com.example.jingbin.viewcollect.dragview.child;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;

import com.example.jingbin.viewcollect.R;
import com.example.jingbin.viewcollect.rx.RxBus;
import com.example.jingbin.viewcollect.rx.RxCodeConstants;
import com.example.jingbin.viewcollect.rx.VoidMessage;


/**
 * Created by xiaguangcheng on 16/10/25.
 */

public class LabelSelecterAdapter extends BaseLabelAdapter<String, DragViewHolder> {

    public static final int KEEP = Integer.MIN_VALUE;

    /**
     * Tag to judge current item mode
     */
    private boolean isLongPressMode = false;
    /**
     * Item Count that can not change
     */
    private int mKeepItemCount = 1;

    private DragItemStartListener mDragItemStartListener;
    /**
     * Listener for users to do with views outside RecyclerView when enter LongPress Mode
     */
    private OnLabelItemTouchListener<String> mOnLabelItemTouchListener;

    public LabelSelecterAdapter(DragItemStartListener listener) {
        mDragItemStartListener = listener;
    }

    @Override
    public int getItemViewType(int position) {
        if (position <= mKeepItemCount - 1) {
            return KEEP;
        }
        return super.getItemViewType(position);
    }
    private long time1;
    @Override
    public void onBindViewHolder(final DragViewHolder holder, int position) {

        holder.mTextView.setText(mDatas.get(holder.getAdapterPosition()));
        holder.mDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(System.currentTimeMillis()-time1>700){
                    time1=System.currentTimeMillis();
                    onItemRemoved(holder.getAdapterPosition());
                }

            }
        });
        mHolder = holder;

        if (holder.getItemViewType() != KEEP) {

            //change Background for different state
            if (isLongPressMode) {
                holder.onLongPressMode();
            } else {
                holder.onNormalMode();
            }
        } else {
            holder.mTextView.setBackgroundResource(R.drawable.shape_notclickbg);
        }

        holder.itemView.setOnTouchListener(new OnItemTouchListener(holder, mDragItemStartListener));
    }

    private DragViewHolder mHolder;

    @Override
    public DragViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_square_label, parent, false);
        return new DragViewHolder(itemView);
    }


    @Override
    public void onItemRemoved(int position) {
        if (position > -1 && position < mDatas.size()) {
            if (null != mOnLabelItemTouchListener) {
                mOnLabelItemTouchListener.onItemRemoved(position, mDatas.get(position));
            }
            mDatas.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, mDatas.size());
        }
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        moveDataInList(fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    private void moveDataInList(int fromPosition, int toPosition) {
        if (fromPosition == toPosition) {
            return;
        }
        String item = mDatas.get(fromPosition);
        mDatas.remove(item);
        mDatas.add(toPosition, item);
    }

    @Override
    public void onItemInsert(int position, String data) {
        //auto put the new item at the end
        mDatas.add(data);
        notifyItemInserted(mDatas.size() - 1);
        notifyItemRangeChanged(mDatas.size() - 1, mDatas.size());
    }

    public void setKeepItemCount(int keepItemCount) {
        mKeepItemCount = keepItemCount;
        notifyDataSetChanged();
    }


    public void setOnLongPressListener(OnLabelItemTouchListener onLabelItemTouchListener) {
        mOnLabelItemTouchListener = onLabelItemTouchListener;
    }


    /**
     * Change LongPressMode to Normal
     */
    public void quitLongPressMode() {
        if (isLongPressMode) {
            isLongPressMode = false;
            notifyDataSetChanged();
        }
    }

    /**
     * @return if current mode is LongPressMode or not
     */
    public boolean getLongPressMode() {
        return isLongPressMode;
    }

    public Runnable mLongPressRunnable = new Runnable() {
        @Override
        public void run() {
            isLongPressMode = true;
            RxBus.getDefault().post(RxCodeConstants.KAWS_LABEL, new VoidMessage());

            // 成功回调

            notifyDataSetChanged();
            mDragItemStartListener.onDragStart(mHolder);
            if (null != mOnLabelItemTouchListener) {
                mOnLabelItemTouchListener.onLongPress();
            }
        }
    };

    private class OnItemTouchListener implements View.OnTouchListener {

        /**
         * Distance to judge whether ACTION_MOVE act on or not
         */
        private float mDownY;
        private float mDownX;
        private boolean isMoved = false;
        private DragViewHolder mViewHolder;
        private DragItemStartListener mDragItemStartListener;


        public OnItemTouchListener(DragViewHolder holder, DragItemStartListener listener) {
            mViewHolder = holder;
            mDragItemStartListener = listener;
        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    if (isLongPressMode) {
                        //if current item state is longPressMode, start dragging immediately
                        if(System.currentTimeMillis()-time1>700){
                            mDragItemStartListener.onDragStart(mViewHolder);
                        }
                    } else {
                        mDownX = event.getX();
                        mDownY = event.getY();
                        isMoved = false;
                        v.postDelayed(mLongPressRunnable, ViewConfiguration.getLongPressTimeout());

                    }
                    break;
                case MotionEvent.ACTION_MOVE:
                    if (!isLongPressMode) {
                        if (isMoved) break;
                        if (Math.abs(event.getX() - mDownX) > 5 ||
                                Math.abs(event.getY() - mDownY) > 5) {
                            isMoved = true;
                            v.removeCallbacks(mLongPressRunnable);
                        }
                    }
                    break;
                case MotionEvent.ACTION_UP:

                    if (!isLongPressMode) {
                        v.removeCallbacks(mLongPressRunnable);
                        if (null != mOnLabelItemTouchListener) {
                            mOnLabelItemTouchListener.onItemClick(mViewHolder, mViewHolder.getAdapterPosition());
                        }
                    }
                    break;
            }
            return true;
        }
    }
}
