package com.example.jingbin.viewcollect.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by CZH on 2015/9/7.
 * 算是重写了个吐司,优化误操作多的时候疯狂弹吐司的情况。
 */
public class ToastUtils {

    private static Toast mToast;
    private static Handler mHandler = new Handler();
    private static Runnable r = new Runnable() {
        public void run() {
            mToast.cancel();
            mToast = null;
        }
    };

    public static void showToast(Context mContext, String text, int duration, int type) {
        if (mContext == null) {
            return;
        }
        mHandler.removeCallbacks(r);
        if (mToast == null) {
            if (mContext != null) {
                mToast = Toast.makeText(mContext, text, Toast.LENGTH_SHORT);
            } else {
                /**若mToast为null并且mContext也为null,则返回*/
                return;
            }
        }
        ViewGroup toastView = (ViewGroup) mToast.getView();
        TextView msg = (TextView) toastView.getChildAt(0);
        msg.setGravity(Gravity.CENTER);
        msg.setCompoundDrawablePadding(10);
        Drawable drawable = null;
//        if (type == 1) {
//            drawable = mContext.getResources().getDrawable(R.drawable.ic_toast_success);
//        } else if (type == 2) {
//            drawable = mContext.getResources().getDrawable(R.drawable.ic_toast_false);
//        } else if (type == 3) {
//            drawable = mContext.getResources().getDrawable(R.drawable.ic_toast_tips);
//        }
        if (drawable != null) {
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
            msg.setCompoundDrawables(null, drawable, null, null);
        }
        msg.setText(text);
        mToast.setGravity(Gravity.CENTER, 0, 0);
        mHandler.postDelayed(r, duration);
        mToast.show();
    }

//    public static void showToast(Context mContext, int resId, int duration) {
//        showToast(mContext, Integer.parseInt(mContext.getResources().getString(resId)), duration);
//    }
}
