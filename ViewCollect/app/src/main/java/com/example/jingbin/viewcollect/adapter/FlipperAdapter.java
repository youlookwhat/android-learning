package com.example.jingbin.viewcollect.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.jingbin.viewcollect.R;
import com.example.jingbin.viewcollect.utils.ToastUtils;

public class FlipperAdapter extends BinBaseAdapter<String> {


    @Override
    protected BinBaseHolder<String> creatHolder(ViewGroup parent, int position) {
        View view = View.inflate(parent.getContext(), R.layout.item_flipper, null);
        return new ViewHolder(view);
    }


    private class ViewHolder extends BinBaseHolder<String> {

        private TextView txtFlipper;

        ViewHolder(View itemView) {
            super(itemView);

            txtFlipper = (TextView) itemView.findViewById(R.id.txt_flipper);
        }

        @Override
        public void onBindView(final String positionData, int position) {
            if (positionData != null) {
                txtFlipper.setText(positionData);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ToastUtils.showToast(view.getContext(), positionData, 2000, 0);
                    }
                });
            }
        }
    }
}
