package com.example.jingbin.viewcollect.ui;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.jingbin.viewcollect.R;
import com.example.jingbin.viewcollect.databinding.ActivityCountTimeViewBinding;
import com.example.jingbin.viewcollect.utils.ToastUtils;
import com.example.jingbin.viewcollect.view.TimeStopListener;

public class CountTimeViewActivity extends AppCompatActivity {

    private ActivityCountTimeViewBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_count_time_view);
        setTitle("倒计时控件");
//        binding.ctvCountDown.setText("还有" + 3 + "件可供抢购");

        // 倒计时：结束后自动请求刷新  参数一：倒计时秒数
        binding.ctvCountDown.calculateTime(9, false);

        // 时间结束时刷新数据
        binding.ctvCountDown.setTimeStopListener(new TimeStopListener() {
            @Override
            public void onTimeStopYouShouldDo() {
                ToastUtils.showToast(CountTimeViewActivity.this, "倒计时结束，刷新数据", 2000, 0);
            }
        });

    }

    public static void start(Context mContext) {
        Intent intent = new Intent(mContext, CountTimeViewActivity.class);
        mContext.startActivity(intent);
    }
}
