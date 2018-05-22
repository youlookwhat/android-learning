package com.example.jingbin.viewcollect.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.jingbin.viewcollect.R;
import com.example.jingbin.viewcollect.utils.CheckNetwork;
import com.example.jingbin.viewcollect.utils.ToastUtils;

//<attr name="addOrSub_width" format="dimension"/>
//        <attr name="number_width" format="dimension"/>

/**
 * Created by yangcai on 16-4-21.
 */
public class NumberAddView extends LinearLayout {
    private int count = 0;
    private ImageView add;
    private ImageView sub;
    private EditText number;
    private int numberWidth;
    private int addOrSubWidth;
    private boolean editAble;
    private static final int DEFAULT_WIDTH = 30;

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }

    /**设置最大值*/
    private int maxValue=99;

    private INumberchangeListener numberchangeListener;

    public NumberAddView(Context context) {
        this(context, null);
    }

    public NumberAddView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NumberAddView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.NumberAddView);
        numberWidth = array.getDimensionPixelOffset(R.styleable.NumberAddView_number_width, DEFAULT_WIDTH);
        addOrSubWidth = array.getDimensionPixelOffset(R.styleable.NumberAddView_addOrSub_width, DEFAULT_WIDTH);
        editAble = array.getBoolean(R.styleable.NumberAddView_number_editable, true);
        array.recycle();
        init();
        bindLinstener();
    }

    private void init() {
        inflate(getContext(), R.layout.number_add_view, this);
        add = (ImageView) findViewById(R.id.tv_add);
        sub = (ImageView) findViewById(R.id.tv_sub);
        number = (EditText) findViewById(R.id.et_number);
        number.setInputType(EditorInfo.TYPE_CLASS_NUMBER);
        LayoutParams params = (LayoutParams) add.getLayoutParams();
        params.weight = addOrSubWidth;
        add.setLayoutParams(params);
        sub.setLayoutParams(params);

        params = (LayoutParams) number.getLayoutParams();
        params.width = numberWidth;
        if (!editAble) {
            number.setClickable(false);
            number.setEnabled(false);
        }
        number.setLayoutParams(params);
        number.setSelectAllOnFocus(true);
        number.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String tmp = editable.toString();
                if (!TextUtils.isEmpty(tmp)) {
                    count = Integer.parseInt(tmp);
                    if (count == 0) {
                        number.setText("1");
                        number.setSelection(number.length());
                    }
                    if (numberchangeListener != null) {
                        numberchangeListener.onChange(count);
                    }
                } else {
                    count = 1;
                }
            }
        });
    }

    private void bindLinstener() {
        add.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!CheckNetwork.isNetworkConnected(getContext())) {
                    ToastUtils.showToast(getContext(), "请检查网络链接", 2000, 0);
                    return;
                }
                if (count < maxValue) {
                    count++;
                    number.setText("" + count);
                    if (numberchangeListener != null) {
                        numberchangeListener.onChange(count);
                        numberchangeListener.onAdd(count);
                    }
                }
            }
        });

        sub.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!CheckNetwork.isNetworkConnected(getContext())) {
                    ToastUtils.showToast(getContext(), "请检查网络链接", 2000, 0);
                    return;
                }
                if (count > 1) {
                    count--;
                    number.setText("" + count);
                    if (numberchangeListener != null) {
                        numberchangeListener.onChange(count);
                        numberchangeListener.onSub(count);
                    }
                }
            }
        });
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
        number.setText("" + count);
    }

    public void setNumberchangeListener(INumberchangeListener numberchangeListener) {
        this.numberchangeListener = numberchangeListener;
    }

    public static interface INumberchangeListener {
        public void onChange(int number);

        public void onAdd(int number);

        public void onSub(int number);
    }

}
