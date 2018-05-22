package com.example.jingbin.viewcollect.ui;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.jingbin.viewcollect.R;
import com.example.jingbin.viewcollect.adapter.FlipperAdapter;
import com.example.jingbin.viewcollect.databinding.ActivityFlipperBinding;

import java.util.ArrayList;
import java.util.List;

public class FlipperActivity extends AppCompatActivity {

    private ActivityFlipperBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_flipper);
        setTitle("Flipper滚动条");

        FlipperAdapter flipperAdapter = new FlipperAdapter();
        flipperAdapter.clear();
        flipperAdapter.addAll(getData());
        binding.avfFlipper.setAdapter(flipperAdapter);
        binding.avfFlipper.setOutAnimation(this, R.animator.anim_line_out);
        binding.avfFlipper.setInAnimation(this, R.animator.anim_line_in);

    }

    private List<String> getData() {
        ArrayList<String> list = new ArrayList<>();
        list.add("哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈");
        list.add("嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿");
        list.add("嘻嘻嘻嘻嘻嘻嘻嘻嘻嘻嘻嘻嘻嘻嘻");
        list.add("呵呵呵呵呵呵呵呵呵呵呵呵呵呵呵呵");
        return list;
    }

    public static void start(Context mContext) {
        Intent intent = new Intent(mContext, FlipperActivity.class);
        mContext.startActivity(intent);
    }
}
