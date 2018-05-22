package com.example.jingbin.viewcollect.ui;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.jingbin.viewcollect.R;
import com.example.jingbin.viewcollect.databinding.ActivityWordWrapViewBinding;
import com.example.jingbin.viewcollect.utils.DensityUtil;
import com.example.jingbin.viewcollect.view.WordWrapView;

import java.util.ArrayList;
import java.util.List;

public class WordWrapViewActivity extends AppCompatActivity {

    private ActivityWordWrapViewBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_word_wrap_view);
        setTitle("自动换行标签");
        addPromotionTags(binding.wwViewTags, getList());
    }

    private ArrayList<String> getList() {
        ArrayList<String> objects = new ArrayList<>();
        objects.add("警世通言");
        objects.add("喻世明言");
        objects.add("醒世恒言");
        objects.add("未来简史");
        objects.add("人类简史");
        objects.add("空间简史");
        return objects;
    }

    /**
     * 添加标签
     */
    private void addPromotionTags(WordWrapView wwView_tags_left, List<String> promotion_tags) {
        //需先移除，不然显示重复
        wwView_tags_left.removeAllViews();
        for (int i = 0; i < promotion_tags.size(); i++) {
            final TextView disease = new TextView(this);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            int right = DensityUtil.dip2px(this, 5);//dp转px
            lp.setMargins(0, 0, right, 0);
            disease.setLayoutParams(lp);//设置margin
            disease.setText(promotion_tags.get(i));
            disease.setTextSize(13);
            disease.setTextColor(this.getResources().getColor(R.color.white));
            disease.setBackgroundResource(R.drawable.bg_tag);
            disease.setGravity(Gravity.CENTER);
            wwView_tags_left.addView(disease);
        }
    }


    public static void start(Context mContext) {
        Intent intent = new Intent(mContext, WordWrapViewActivity.class);
        mContext.startActivity(intent);
    }
}
