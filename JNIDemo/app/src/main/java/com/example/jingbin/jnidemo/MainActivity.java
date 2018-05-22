package com.example.jingbin.jnidemo;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.jingbin.jnidemo.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        binding.tvHello.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HelloActivity.start(view.getContext());
            }
        });


    }
}
