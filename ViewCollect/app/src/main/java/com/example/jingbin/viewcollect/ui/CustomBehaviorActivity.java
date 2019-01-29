package com.example.jingbin.viewcollect.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.jingbin.viewcollect.R;
import com.example.jingbin.viewcollect.adapter.BehaviorAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义两种方法：
 * 1.定义的view监听 CoordinatorLayout 里的滑动状态
 * 2.定义的view监听另一个view的状态变化
 */
public class CustomBehaviorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_behavior);

//        ViewGroup.LayoutParams params = toolbar.getLayoutParams();
//        params.height = DensityUtil.getStatusHeight(activity) + getSystemActionBarSize(activity);
//        toolbar.setLayoutParams(params);
//        toolbar.setPadding(
//                toolbar.getLeft(),
//                toolbar.getTop() + DensityUtil.getStatusHeight(activity),
//                toolbar.getRight(),
//                toolbar.getBottom()
//        );
//        activity.setSupportActionBar(toolbar);
//        ActionBar actionBar = activity.getSupportActionBar();
//        actionBar.setDisplayHomeAsUpEnabled(true);
//        actionBar.setHomeButtonEnabled(true);


        setSupportActionBar((android.support.v7.widget.Toolbar) findViewById(R.id.toolbar_behavior));

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(null);
        BehaviorAdapter mAdapter = new BehaviorAdapter(R.layout.item_flipper, getData());
        recyclerView.setAdapter(mAdapter);
    }


    private List<String> getData() {
        ArrayList<String> list = new ArrayList<>();
        list.add("哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈");
        list.add("嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿");
        list.add("嘻嘻嘻嘻嘻嘻嘻嘻嘻嘻嘻嘻嘻嘻嘻");
        list.add("呵呵呵呵呵呵呵呵呵呵呵呵呵呵呵呵");
        list.add("呵呵呵呵呵呵呵呵呵呵呵呵呵呵呵呵");
        list.add("呵呵呵呵呵呵呵呵呵呵呵呵呵呵呵呵");
        list.add("呵呵呵呵呵呵呵呵呵呵呵呵呵呵呵呵");
        list.add("呵呵呵呵呵呵呵呵呵呵呵呵呵呵呵呵");
        list.add("呵呵呵呵呵呵呵呵呵呵呵呵呵呵呵呵");
        list.add("呵呵呵呵呵呵呵呵呵呵呵呵呵呵呵呵");
        list.add("呵呵呵呵呵呵呵呵呵呵呵呵呵呵呵呵");
        list.add("呵呵呵呵呵呵呵呵呵呵呵呵呵呵呵呵");
        list.add("呵呵呵呵呵呵呵呵呵呵呵呵呵呵呵呵");
        return list;
    }


    public static void start(Context mContext) {
        Intent intent = new Intent(mContext, CustomBehaviorActivity.class);
        mContext.startActivity(intent);
    }
}
