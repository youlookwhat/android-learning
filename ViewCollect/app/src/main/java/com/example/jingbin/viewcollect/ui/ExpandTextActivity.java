package com.example.jingbin.viewcollect.ui;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.jingbin.viewcollect.R;
import com.example.jingbin.viewcollect.view.MyExpandableTextView;

public class ExpandTextActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expand_text);

        MyExpandableTextView expandableTextView = (MyExpandableTextView) findViewById(R.id.expand_text_view);

        expandableTextView.setText("自昔财为伤命刃，从来智乃护身符。\n" +
                "贼髡毒手谋文士，淑女双眸识俊儒。\n" +
                "已幸余生逃密网，谁知好事在穷途？\n" +
                "一朝获把封章奏，雪怨酬恩显丈夫。\n" +
                "\n" +
                "笔落惊风雨，书成泣鬼神。\n" +
                "终非池沼物，堪作庙堂珍。\n" +
                "\n" +
                "轻眉俊眼，绣腿花拳，\n" +
                "风笠飘摇，雨衣鲜灿。\n" +
                "玉勒马一声嘶破柳堤烟，\n" +
                "碧帷车数武碾残松岭雪。\n" +
                "右悬雕矢，行色增雄；\n" +
                "左插鲛函，威风倍壮。\n" +
                "扬鞭喝跃，途人谁敢争先；\n" +
                "结队驱驰，村市尽皆惊盼。\n" +
                "正是:\n" +
                "处处绿杨堪系马，人人有路透长安。\n" +
                "\n");

    }

    public static void start(Context mContext) {
        Intent intent = new Intent(mContext, ExpandTextActivity.class);
        mContext.startActivity(intent);
    }
}
