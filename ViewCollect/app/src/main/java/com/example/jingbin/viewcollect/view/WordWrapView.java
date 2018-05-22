package com.example.jingbin.viewcollect.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * 自动换行的View  癌种标签
 */
public class WordWrapView extends ViewGroup {
    private static final int SIDE_MARGIN = 10;// 左右间距
    private static final int TEXT_MARGIN = 10;//上下间距

    public WordWrapView(Context context) {
        super(context);
    }

    public WordWrapView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WordWrapView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = getChildCount();
        int autualWidth = r - l;
        int x = 0;// 横坐标开始
        int y = 0;// 纵坐标开始
        int rows = 1;
        for (int i = 0; i < childCount; i++) {
            View view = getChildAt(i);
            int width = view.getMeasuredWidth();
            int height = view.getMeasuredHeight();
            x += width + SIDE_MARGIN;
            if (x > autualWidth) {
                x = width + SIDE_MARGIN;
                rows++;
            }
            y = rows * height + TEXT_MARGIN * (rows - 1);
            view.layout(x - width - SIDE_MARGIN, y - height, x - SIDE_MARGIN, y);
        }

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int y = 0;// 纵坐标
        int rows = 1;// 总行数
        int actualWidth = MeasureSpec.getSize(widthMeasureSpec);
        int childCount = getChildCount();
        int childWidth = 0;
        int childLineIndex = 0;
        for (int index = 0; index < childCount; index++) {
            View child = getChildAt(index);
            child.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
            int width = child.getMeasuredWidth();
            int height = child.getMeasuredHeight();
            childWidth += width;
            int x = childWidth + childLineIndex * SIDE_MARGIN;
            childLineIndex++;
            if (x > actualWidth) {// 换行
                childWidth = 0;
                childLineIndex = 0;
                rows++;
            }
            y = rows * height + TEXT_MARGIN * (rows - 1);
        }
        setMeasuredDimension(actualWidth, y);
    }
}