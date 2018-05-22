package com.example.jingbin.viewcollect.slideview;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;

/**
 * Created by jingbin on 2015/3/3.
 */
public class SlidingMenu extends ScrollView {
    private int mScreenHeight;


    private ScrollView wrapperMenu;
    private SlideWebView wrapperContent;
    private boolean isSetted = false;
    private boolean ispageOne = true;


    public SlidingMenu(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlidingMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public SlidingMenu(Context context) {
        this(context, null);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        mScreenHeight = MeasureSpec.getSize(heightMeasureSpec);
        if (!isSetted) {
            //得到里面的控件
            final LinearLayout wrapper = (LinearLayout) getChildAt(0);
            wrapperMenu = (ScrollView) wrapper.getChildAt(0);
            wrapperContent = (SlideWebView) wrapper.getChildAt(1);
            wrapperMenu.getLayoutParams().height = mScreenHeight;
            wrapperContent.getLayoutParams().height = mScreenHeight;
            isSetted = true;
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
    }

    protected boolean isReadyForPullEnd() {
        View scrollViewChild = wrapperMenu.getChildAt(0);
        if (null != scrollViewChild) {
            return wrapperMenu.getScrollY() >= (scrollViewChild.getHeight() - getHeight());
        }
        return false;
    }

    protected boolean isReadyForPullStart() {
        return wrapperContent.getScrollY() == 0;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        // boolean shouldIntercept = false;
        super.onInterceptTouchEvent(ev);
        int action = MotionEventCompat.getActionMasked(ev);
        switch (action) {
            case MotionEvent.ACTION_MOVE:
                checkCanDrag(ev);
                if (dragEdge == DragEdge.Bottom) {
                    if (ispageOne && isReadyForPullEnd())
                        return true;
                } else if (dragEdge == DragEdge.Top) {
                    if (!ispageOne && isReadyForPullStart())
                        return true;
                }
                break;
            case MotionEvent.ACTION_DOWN:
                sX = ev.getRawX();
                sY = ev.getRawY();
                break;
            default:
                break;

        }
        return false;
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_UP:
                //隐藏在左边的距离
                int scrollY = getScrollY();
                int creteria = mScreenHeight / 5;//滑动多少距离
                if (ispageOne) {
                    if (scrollY <= creteria) {
                        //显示菜单
                        this.smoothScrollTo(0, 0);
                        if (callBackToggleRefreshUi != null) {
                            callBackToggleRefreshUi.firstPager();
                        }
                    } else {
                        //隐藏菜单
                        this.smoothScrollTo(0, mScreenHeight);
                        ispageOne = false;
                        if (callBackToggleRefreshUi != null) {
                            callBackToggleRefreshUi.secondPager();
                        }
                    }
                } else {
                    int scrollpadding = mScreenHeight - scrollY;
                    if (scrollpadding >= creteria) {
                        this.smoothScrollTo(0, 0);
                        ispageOne = true;
                        if (callBackToggleRefreshUi != null) {
                            callBackToggleRefreshUi.firstPager();
                        }
                    } else {
                        this.smoothScrollTo(0, mScreenHeight);
                        if (callBackToggleRefreshUi != null) {
                            callBackToggleRefreshUi.secondPager();
                        }
                    }
                }
                // super.onTouchEvent(ev);
                return true;
        }
        return super.onTouchEvent(ev);
    }

    /**
     * 移动到顶端
     */
    public void moveToTop() {
        wrapperMenu.fullScroll(ScrollView.FOCUS_UP);
        wrapperContent.scrollTo(0, 0);
        fullScroll(ScrollView.FOCUS_UP);
        ispageOne = true;
    }


    public CallBack_toggleRefreshUi callBackToggleRefreshUi;

    public void setCallBack_addRefreshUi(CallBack_toggleRefreshUi callBackToggleRefreshUi) {
        this.callBackToggleRefreshUi = callBackToggleRefreshUi;
    }

    public interface CallBack_toggleRefreshUi {
        void secondPager();

        void firstPager();
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
