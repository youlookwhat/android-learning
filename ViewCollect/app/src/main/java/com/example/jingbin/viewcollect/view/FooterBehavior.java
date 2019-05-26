package com.example.jingbin.viewcollect.view;

import android.animation.Animator;
import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewPropertyAnimator;

/**
 * Created by jingbin on 2019/1/29.
 */

public class FooterBehavior extends CoordinatorLayout.Behavior<View> {

    // 滑动累加值
    private int directionChange;

    public FooterBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, View child, View directTargetChild, View target, int nestedScrollAxes) {
//        return super.onStartNestedScroll(coordinatorLayout, child, directTargetChild, target, nestedScrollAxes);
        return (nestedScrollAxes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
    }

    /**
     * @param child 我们定义的 LinearLayout
     * @param dy    水平滑动的距离，向上滑动为正，向下滑动为负
     */
    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, View child, View target, int dx, int dy, int[] consumed) {
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed);
        if (dy > 0 && directionChange < 0 || dy < 0 && directionChange > 0) {
            child.animate().cancel();
            directionChange = 0;
        }
        directionChange += dy;
        if (directionChange > child.getHeight() && child.getVisibility() == View.VISIBLE) {
            hide(child);
        } else if (directionChange < 0 && child.getVisibility() == View.GONE) {
            show(child);
        }
    }

    private void hide(final View view) {
        ViewPropertyAnimator animator = view.animate()
                .translationY(view.getHeight())
                .setInterpolator(new FastOutSlowInInterpolator())
                .setDuration(2000);
        animator.setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                view.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animator.start();
    }


    private void show(final View child) {
        ViewPropertyAnimator animator = child.animate()
                .translationY(0)
                .setInterpolator(new FastOutSlowInInterpolator())
                .setDuration(2000);
        animator.setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                child.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animator.start();
    }

}
