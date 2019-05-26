package com.example.jingbin.viewcollect.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.jingbin.viewcollect.R;

import java.util.List;

public class BehaviorAdapter extends BaseQuickAdapter<String, BehaviorAdapter.JViewHolder> {

    public BehaviorAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(JViewHolder helper, String item) {
        helper.txtFlipper.setText(item);
    }

    class JViewHolder extends BaseViewHolder {
        TextView txtFlipper;

        JViewHolder(View itemView) {
            super(itemView);
            txtFlipper = (TextView) itemView.findViewById(R.id.txt_flipper);
        }

    }
}
