package com.example.jingbin.viewcollect.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.jingbin.viewcollect.R;
import com.example.jingbin.viewcollect.dragview.DragRecyclerView;
import com.example.jingbin.viewcollect.dragview.UnDragRecyclerView;
import com.example.jingbin.viewcollect.dragview.child.LabelSelecterAdapter;
import com.example.jingbin.viewcollect.dragview.child.OnLabelItemTouchListener;
import com.example.jingbin.viewcollect.rx.RxBus;
import com.example.jingbin.viewcollect.rx.RxCodeConstants;
import com.example.jingbin.viewcollect.rx.VoidMessage;

import java.util.ArrayList;
import java.util.List;

import rx.functions.Action1;

import static android.R.attr.type;


/**
 * Created by jingbin on 16/11/1.
 * 抗癌资讯首页 版块编辑
 * titlesForSend 上部分
 * tempListTag 可编辑部分
 * keepItemCount 固定的版块个数
 */
public class LabelEditActivity extends AppCompatActivity {

    // 是否在没编辑模式下点击了 添加更过版块的标签
    private boolean isClickAdd = false;
    private boolean isrRefresh = false;
    private final String ISCHILDADD = "isClickAdd";
    private int isClickPosition = 0;


    public DragRecyclerView dragRecyclerView;
    public UnDragRecyclerView unDragRecyclerView;
    public ArrayList<String> titlesForSend, tempListTag, fixedList;
    private ImageButton ibt_back_v3_title_bar;
    private Button btn_use_v3_title_bar;
    private TextView txt_title_v3_title_bar;
    public final String CLOSE = "close";
    public final String CHANGE = "change";
    public boolean isOK;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("可拖动的RecyclerView");
        setContentView(R.layout.layout_editlabel);
        titlesForSend = getIntent().getStringArrayListExtra("titlesForSend");
        tempListTag = getIntent().getStringArrayListExtra("tempListTag");
        fixedList = getIntent().getStringArrayListExtra("fixedList");


        ibt_back_v3_title_bar = (ImageButton) findViewById(R.id.ibt_back_v3_title_bar);
        btn_use_v3_title_bar = (Button) findViewById(R.id.btn_use_v3_title_bar);
        txt_title_v3_title_bar = (TextView) findViewById(R.id.txt_title_v3_title_bar);
        txt_title_v3_title_bar.setVisibility(View.VISIBLE);
        txt_title_v3_title_bar.setText("板块编辑");
        btn_use_v3_title_bar.setText("编辑");
        btn_use_v3_title_bar.setTextColor(getResources().getColor(R.color.theme));
        btn_use_v3_title_bar.setVisibility(View.VISIBLE);
        btn_use_v3_title_bar.setBackgroundResource(R.drawable.kuang_green);
        btn_use_v3_title_bar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ("完成".equals(btn_use_v3_title_bar.getText().toString())) {
                    dragRecyclerView.quitLongPressMode();
                    btn_use_v3_title_bar.setText("编辑");
                    putTags((List<String>) (dragRecyclerView.getDatas()), CHANGE);

                } else {
                    btn_use_v3_title_bar.setText("完成");
                    btn_use_v3_title_bar.post(((LabelSelecterAdapter) (dragRecyclerView.getAdapter())).mLongPressRunnable);

                }
            }
        });
        ibt_back_v3_title_bar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((LabelSelecterAdapter) (dragRecyclerView.getAdapter())).getLongPressMode()) {
                    btn_use_v3_title_bar.setText("编辑");
                    dragRecyclerView.quitLongPressMode();
                    putTags((List<String>) (dragRecyclerView.getDatas()), CHANGE);

                } else {
                    putTags((List<String>) (dragRecyclerView.getDatas()), CLOSE);

                }

            }
        });
        dragRecyclerView = (DragRecyclerView) findViewById(R.id.drag_view);
        unDragRecyclerView = (UnDragRecyclerView) findViewById(R.id.undrag_view);

        setData();
        RxBus.getDefault().toObservable(RxCodeConstants.KAWS_LABEL, VoidMessage.class).subscribe(new Action1<VoidMessage>() {
            @Override
            public void call(VoidMessage voidMessage) {
                btn_use_v3_title_bar.setText("完成");
            }
        });

    }


    public void setData() {
        dragRecyclerView.datas(titlesForSend).onItemTouch(new OnLabelItemTouchListener<String>() {
            @Override
            public void onItemRemoved(int position, String removedItem) {
                unDragRecyclerView.addItem(removedItem);
            }

            @Override
            public void onLongPress() {

            }

            @Override
            public void onItemClick(RecyclerView.ViewHolder holder, int position) {
                // 如果在未编辑的情况下"点击添加更多版块",先上传标签再跳到对应标签下
                if (isClickAdd) {
                    putTags((List<String>) (dragRecyclerView.getDatas()), ISCHILDADD);
                    isClickPosition = position;
                    // 如果没有编辑直接点击版块,则直接返回,并跳到对应标签下
                } else {
                    backInformation(isrRefresh, position);
                }
            }
        }).keepItemCount(2).build();// 固定2个
        unDragRecyclerView.datas(tempListTag).onItemTouch(new OnLabelItemTouchListener<String>() {
            @Override
            public void onItemRemoved(int position, String removedItem) {
                // 点击增加标签后,设置点击事件返回刷新和跳转
                dragRecyclerView.addItem(removedItem);
                isClickAdd = true;
                isrRefresh = true;
            }

            @Override
            public void onLongPress() {

            }

            @Override
            public void onItemClick(RecyclerView.ViewHolder holder, int position) {

            }
        }).build();
    }

    public void putTags(final List<String> titles, final String type) {
        boolean isPut = false;
        if (titles != null) {// 能拖动的部分的集合
            if (titles.size() != titlesForSend.size()) {
                isPut = true;// 集合大小不一致 更改过内容
            } else {
                for (int a = 0; a < titles.size(); a++) {
                    if (!titles.get(a).equals(titlesForSend.get(a))) {
                        isPut = true;// 集合大小一致 内容不一致 更改过内容
                        break;
                    }
                }
            }
        }
        if (isPut) {//更改过内容
//            ChannelIdsPostBean userTags = new ChannelIdsPostBean();
//            userTags.setUserkey(new SQuser(this).selectKey());
//            ArrayList<String> ids = new ArrayList<>();
//            for (String s : titles) {
//                if (!"推荐".equals(s)) {
//                    ids.add(KawsInformationActivity.tagMap.get(s));
//                }
//            }
//            userTags.setChannel_ids(ids);
//            Subscription subscription = HttpUtils.getInstance().getV4ApiServer().postData(HttpUtils.getInstance().getHeaderStr("POST"), userTags)
//                    .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<ArticleChannelsBean>() {
//                        @Override
//                        public void onCompleted() {
//
//                        }
//
//                        @Override
//                        public void onError(Throwable e) {
//                            stopProgressDialog();
//                            tipInfo("提交数据失败，请稍后重试", 3);
//                        }
//
//                        @Override
//                        public void onNext(ArticleChannelsBean articleChannelsBean) {
//                            isOK = true;
//                            /**清空用户选择的集合*/
//                            stopProgressDialog();

//                            titlesForSend.clear();
//                            titlesForSend.addAll(titles);
//
//                            isClickAdd = false;
//                            isrRefresh = true;
//                            if (CLOSE.equals(type)) {
//                                if (isOK) {
//                                    setResult(RESULT_OK);
//                                }
//                                finish();
//
//                                // 是否在未编辑的情况下"点击添加更多版块"
//                            } else if (ISCHILDADD.equals(type)) {
//                                backInformation(isrRefresh, isClickPosition);
//                            }
//                        }
//                    });
//            addSubscription(subscription);


            // 为了功能正常
            function(titles);



        } else if (CLOSE.equals(type)) {
            if (isOK) {
                setResult(RESULT_OK);
            }
            finish();
        }
    }

    private void function(List<String> titles) {
        isOK = true;
        titlesForSend.clear();
        titlesForSend.addAll(titles);

        isClickAdd = false;
        isrRefresh = true;
        if (CLOSE.equals(type)) {
            if (isOK) {
                setResult(RESULT_OK);
            }
            finish();

            // 是否在未编辑的情况下"点击添加更多版块"
        } else if (ISCHILDADD.equals(type)) {
            backInformation(isrRefresh, isClickPosition);
        }
    }

    /**
     * @param isRefresh 是否刷新页面
     * @param position  跳到哪个标签下
     */
    private void backInformation(boolean isRefresh, int position) {
        Intent intent = new Intent();
        intent.putExtra("isJump", true);
        intent.putExtra("isRefresh", isRefresh);
        intent.putExtra("position", position);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (((LabelSelecterAdapter) (dragRecyclerView.getAdapter())).getLongPressMode()) {
                btn_use_v3_title_bar.setText("编辑");
                dragRecyclerView.quitLongPressMode();
                putTags((List<String>) (dragRecyclerView.getDatas()), CHANGE);

            } else {
                putTags((List<String>) (dragRecyclerView.getDatas()), CLOSE);

            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public static void start(Context mContext) {
        Intent intent = new Intent(mContext, LabelEditActivity.class);
        ArrayList<String> titlesForSend = new ArrayList<>();
        titlesForSend.add("你好");
        titlesForSend.add("你是");
        titlesForSend.add("哈哈");
        titlesForSend.add("嘿嘿");
        titlesForSend.add("嘻嘻");
        ArrayList<String> tempListTag = new ArrayList<>();
        tempListTag.add("固定1");
        tempListTag.add("固定2");
        tempListTag.add("固定3");
        ArrayList<String> fixedList = new ArrayList<>();
        intent.putExtra("titlesForSend", titlesForSend);
        intent.putExtra("tempListTag", tempListTag);
        intent.putExtra("fixedList", fixedList);
        mContext.startActivity(intent);
    }


}
