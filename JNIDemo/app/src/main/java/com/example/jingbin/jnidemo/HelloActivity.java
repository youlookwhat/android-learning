package com.example.jingbin.jnidemo;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.jingbin.jnidemo.databinding.ActivityHelloBinding;

public class HelloActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ActivityHelloBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_hello);

        binding.priText.setText(helloFromC());

        binding.btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int result = add(Integer.parseInt(String.valueOf(binding.etOne.getText()))
                        , Integer.parseInt(String.valueOf(binding.etTwo.getText())));
                binding.etRes.setText(String.valueOf(result));
            }
        });

    }

    public static void start(Context mContext) {
        Intent intent = new Intent(mContext, HelloActivity.class);
        mContext.startActivity(intent);
    }

    static {
        // 导入静态库
        System.loadLibrary("hello");
    }

    /**
     * 声明一个native方法 让C语言来实现里面的逻辑
     */
    public static native String helloFromC();

    public static native int add(int x, int y);

}
