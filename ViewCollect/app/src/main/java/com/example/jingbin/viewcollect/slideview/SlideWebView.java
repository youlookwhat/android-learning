package com.example.jingbin.viewcollect.slideview;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.webkit.WebView;

/**
 * Created by jingbin on 2015/4/20.
 */
public class SlideWebView extends WebView {
    private int t;

    public SlideWebView(Context context) {
        super(context);
    }

    public SlideWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SlideWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        int action = MotionEventCompat.getActionMasked(ev);
        switch (action) {
            case MotionEvent.ACTION_MOVE:
                checkCanDrag(ev);
                //在滑动的时候获得当前值，并计算得到YS,用来判断是向上滑动还是向下滑动
                if (dragEdge == DragEdge.Top) {
                    if (t == 0) {
                        getParent().getParent().requestDisallowInterceptTouchEvent(false);
                    }
                } else {
                    getParent().getParent().requestDisallowInterceptTouchEvent(true);
                }
                break;
            case MotionEvent.ACTION_DOWN:
                //手指按下的时候，获得滑动事件，也就是让顶级scrollview失去滑动事件
                getParent().getParent().requestDisallowInterceptTouchEvent(true);
                //并且记录Y点值
                sX = ev.getRawX();
                sY = ev.getRawY();
                break;
            case MotionEvent.ACTION_UP:
                getParent().getParent().requestDisallowInterceptTouchEvent(false);
                break;
        }
        return super.onTouchEvent(ev);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        this.t = t;
        super.onScrollChanged(l, t, oldl, oldt);
    }

    private DragEdge dragEdge = DragEdge.None;
    private float sX = -1, sY = -1;

    private void checkCanDrag(MotionEvent ev) {
        float dx = ev.getRawX() - sX;
        float dy = ev.getRawY() - sY;
        float angle = Math.abs(dy / dx);
        angle = (float) Math.toDegrees(Math.atan(angle));
        if (Float.isNaN(angle)) {
            dragEdge = DragEdge.None;
        } else if (angle < 45) {
            if (dx > 0) {
                dragEdge = DragEdge.Left;
            } else if (dx < 0) {
                dragEdge = DragEdge.Right;
            }
        } else {
            if (dy > 0) {
                dragEdge = DragEdge.Top;
            } else if (dy < 0) {
                dragEdge = DragEdge.Bottom;
            }
        }
    }

    public enum DragEdge {
        None,
        Left,
        Top,
        Right,
        Bottom
    }


}
