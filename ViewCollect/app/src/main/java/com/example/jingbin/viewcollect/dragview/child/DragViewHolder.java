package com.example.jingbin.viewcollect.dragview.child;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jingbin.viewcollect.R;


/**
 * Created by xiaguangcheng on 16/10/25.
 */

public class DragViewHolder extends  RecyclerView.ViewHolder {

    public TextView mTextView;
    public ImageView mDelete;

    public DragViewHolder(View itemView) {
        super(itemView);
        mTextView = (TextView) itemView.findViewById(R.id.txt_item_popUp_chooseCase);
        mDelete = (ImageView) itemView.findViewById(R.id.image_delete);
        mDelete.setVisibility(View.GONE);
    }

    public void onDrag() {
//        mTextView.setTextColor(Color.RED);
    }

    public void onDragFinished() {
//        mTextView.setTextColor(Color.BLACK);
    }

    public void onLongPressMode() {
        mDelete.setVisibility(View.VISIBLE);
//        mTextView.setBackgroundResource(R.drawable.border_longpress);
    }

    public void onNormalMode() {
        mDelete.setVisibility(View.GONE);
//        mTextView.setBackgroundResource(R.drawable.border_normal);
    }
}
