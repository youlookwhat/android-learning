package com.example.jingbin.viewcollect.ui;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.jingbin.viewcollect.R;
import com.example.jingbin.viewcollect.databinding.ActivityNumberAddViewBinding;
import com.example.jingbin.viewcollect.view.NumberAddView;

public class NumberAddViewActivity extends AppCompatActivity {

    private ActivityNumberAddViewBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_number_add_view);
        setTitle("加减商品数量");
        binding.naCartGoodsNum.setCount(1);
        binding.naCartGoodsNum.setNumberchangeListener(new NumberAddView.INumberchangeListener() {
            @Override
            public void onChange(int number) {
                // 做相关处理
            }

            @Override
            public void onAdd(int number) {
            }

            @Override
            public void onSub(int number) {
            }
        });
    }

    public static void start(Context mContext) {
        Intent intent = new Intent(mContext, NumberAddViewActivity.class);
        mContext.startActivity(intent);
    }
}
