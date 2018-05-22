package com.example.jingbin.viewcollect.ui;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jingbin.viewcollect.R;
import com.example.jingbin.viewcollect.databinding.ActivityExpandableViewBinding;
import com.example.jingbin.viewcollect.view.ExpandableView;


public class ExpandableViewActivity extends AppCompatActivity implements ExpandableView.ExpandableViewListener {

    private ActivityExpandableViewBinding binding;
    private boolean isExpand;
    private TextView footerTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_expandable_view);
        setTitle("点击展开收起布局");
        setExpandable();
    }

    private void setExpandable() {
        footerTxt = (TextView) binding.expandableView.getFooterView().findViewById(R.id.tv_txt);
        if (isExpand) {
            footerTxt.setText("点击收起");
        } else {
            footerTxt.setText("显示全部");
        }
        binding.expandableView.setExpanded(isExpand, false);
        binding.expandableView.setExpandableViewListener(this);
    }


    @Override
    public boolean canExpand(ExpandableView expandableView) {
        return true;
    }

    @Override
    public boolean canCollapse(ExpandableView expandableView) {
        return true;
    }

    @Override
    public void willExpand(ExpandableView expandableView) {

    }

    @Override
    public void willCollapse(ExpandableView expandableView) {

    }

    @Override
    public void didExpand(ExpandableView expandableView) {
        isExpand = true;
        if (expandableView.getId() == R.id.expandable_view) {
            ImageView indicator = (ImageView) expandableView.findViewById(R.id.expandable_footer_arrow);
            indicator.setImageResource(R.drawable.ic_custom_fold);
            footerTxt.setText("点击收起");
        }
    }

    @Override
    public void didCollapse(ExpandableView expandableView) {
        isExpand = false;
        if (expandableView.getId() == R.id.expandable_view) {
            ImageView indicator = (ImageView) expandableView.findViewById(R.id.expandable_footer_arrow);
            indicator.setImageResource(R.drawable.ic_custom_unfold);
            footerTxt.setText("显示全部");
        }
    }

    @Override
    public void onHeightOffsetChanged(ExpandableView expandableView, float offset) {

    }


    public static void start(Context mContext) {
        Intent intent = new Intent(mContext, ExpandableViewActivity.class);
        mContext.startActivity(intent);
    }
}
