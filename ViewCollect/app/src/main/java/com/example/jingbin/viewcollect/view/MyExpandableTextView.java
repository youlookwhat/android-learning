package com.example.jingbin.viewcollect.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.jingbin.viewcollect.R;


/**
 * Created by jingbin on 2018/11/1.
 * 评论列表展开收起
 * 参考链接：https://github.com/Manabu-GT/ExpandableTextView
 */

public class MyExpandableTextView extends LinearLayout implements View.OnClickListener {

    /* The default number of lines */
    private static final int MAX_COLLAPSED_LINES = 5;

    protected TextView mTv;

    protected View mToggleView; // View to expand/collapse

    private boolean mRelayout;

    private boolean mCollapsed = true; // Show short version as default.

    private int mMaxCollapsedLines;

    private ExpandIndicatorController mExpandIndicatorController;
    /* Listener for callback */
    private OnExpandStateChangeListener mListener;
    @IdRes
    private int mExpandableTextId = R.id.expandable_text;

    @IdRes
    private int mExpandCollapseToggleId = R.id.expand_collapse;

    /* For saving collapsed status when used in ListView */
    private SparseBooleanArray mCollapsedStatus;
    private int mPosition;

    public MyExpandableTextView(Context context) {
        this(context, null);
    }

    public MyExpandableTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public MyExpandableTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs);
    }

    @Override
    public void setOrientation(int orientation) {
        if (LinearLayout.HORIZONTAL == orientation) {
            throw new IllegalArgumentException("ExpandableTextView only supports Vertical Orientation.");
        }
        super.setOrientation(orientation);
    }

    @Override
    public void onClick(View view) {
        if (mToggleView.getVisibility() != View.VISIBLE) {
            return;
        }

        mCollapsed = !mCollapsed;
        mExpandIndicatorController.changeState(mCollapsed);

        if (mCollapsedStatus != null) {
            mCollapsedStatus.put(mPosition, mCollapsed);
        }
        mTv.setMaxLines(mCollapsed ? mMaxCollapsedLines : Integer.MAX_VALUE);
        mTv.setEllipsize(TextUtils.TruncateAt.END);

        if (mListener != null) {
            mListener.onExpandStateChanged(mTv, mCollapsed);
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        findViews();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // If no change, measure and return
        if (!mRelayout || getVisibility() == View.GONE) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            return;
        }
        mRelayout = false;

        // Setup with optimistic case
        // i.e. Everything fits. No button needed
        mToggleView.setVisibility(View.GONE);
        mTv.setMaxLines(Integer.MAX_VALUE);

        // Measure
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        // If the text fits in collapsed mode, we are done.
        if (mTv.getLineCount() <= mMaxCollapsedLines) {
            return;
        }

        // Doesn't fit in collapsed mode. Collapse text view as needed. Show
        // button.
        if (mCollapsed) {
            mTv.setMaxLines(mMaxCollapsedLines);
            mTv.setEllipsize(TextUtils.TruncateAt.END);
        }
        mToggleView.setVisibility(View.VISIBLE);

        // Re-measure with new setup
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    public void setText(@Nullable CharSequence text) {
        mRelayout = true;
        mTv.setText(text);
        setVisibility(TextUtils.isEmpty(text) ? View.GONE : View.VISIBLE);
        getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
        requestLayout();
    }

    public void setText(@Nullable CharSequence text, @NonNull SparseBooleanArray collapsedStatus, int position) {
        mCollapsedStatus = collapsedStatus;
        mPosition = position;
        mCollapsed = collapsedStatus.get(position, true);
        mExpandIndicatorController.changeState(mCollapsed);
        setText(text);
    }

    @Nullable
    public CharSequence getText() {
        if (mTv == null) {
            return "";
        }
        return mTv.getText();
    }

    private void init(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.MyExpandableTextView);
        mMaxCollapsedLines = typedArray.getInt(R.styleable.MyExpandableTextView_maxCollapsedLines, MAX_COLLAPSED_LINES);
        mExpandableTextId = typedArray.getResourceId(R.styleable.MyExpandableTextView_expandableTextId, R.id.expandable_text);
        mExpandCollapseToggleId = typedArray.getResourceId(R.styleable.MyExpandableTextView_expandCollapseToggleId, R.id.expand_collapse);

        mExpandIndicatorController = setupExpandToggleController(getContext(), typedArray);

        typedArray.recycle();

        // enforces vertical orientation
        setOrientation(LinearLayout.VERTICAL);

        // default visibility is gone
        setVisibility(GONE);
    }

    private void findViews() {
        mTv = (TextView) findViewById(mExpandableTextId);
        mToggleView = findViewById(mExpandCollapseToggleId);
        mExpandIndicatorController.setView(mToggleView);
        mExpandIndicatorController.changeState(mCollapsed);
        mToggleView.setOnClickListener(this);
    }

    private static boolean isPostLolipop() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private static Drawable getDrawable(@NonNull Context context, @DrawableRes int resId) {
        Resources resources = context.getResources();
        if (isPostLolipop()) {
            return resources.getDrawable(resId, context.getTheme());
        } else {
            return resources.getDrawable(resId);
        }
    }

    private static ExpandIndicatorController setupExpandToggleController(@NonNull Context context, TypedArray typedArray) {

        final ExpandIndicatorController expandIndicatorController;

        Drawable expandDrawable = typedArray.getDrawable(R.styleable.MyExpandableTextView_expandIndicator);
        Drawable collapseDrawable = typedArray.getDrawable(R.styleable.MyExpandableTextView_collapseIndicator);

        if (expandDrawable == null) {
            expandDrawable = getDrawable(context, R.drawable.icon_comment_arrow_down);
        }
        if (collapseDrawable == null) {
            collapseDrawable = getDrawable(context, R.drawable.icon_comment_arrow_up);
        }
        expandIndicatorController = new RelativeLayoutExpandController(expandDrawable, collapseDrawable, "展开", "收起");

        return expandIndicatorController;
    }

    interface ExpandIndicatorController {
        void changeState(boolean collapsed);

        void setView(View toggleView);
    }

    static class RelativeLayoutExpandController implements ExpandIndicatorController {

        private final Drawable mExpandDrawable;
        private final Drawable mCollapseDrawable;
        private final String mExpandText;
        private final String mCollapseText;

        private RelativeLayout relativeLayout;

        RelativeLayoutExpandController(Drawable expandDrawable, Drawable collapseDrawable, String expandText, String collapseText) {
            mExpandDrawable = expandDrawable;
            mCollapseDrawable = collapseDrawable;
            mExpandText = expandText;
            mCollapseText = collapseText;
        }

        @Override
        public void changeState(boolean collapsed) {
            TextView textView = (TextView) relativeLayout.findViewById(R.id.tv_expand);
            ImageView imageView = (ImageView) relativeLayout.findViewById(R.id.iv_expand);

            imageView.setImageDrawable(collapsed ? mExpandDrawable : mCollapseDrawable);
            textView.setText(collapsed ? mExpandText : mCollapseText);
        }

        @Override
        public void setView(View toggleView) {
            relativeLayout = (RelativeLayout) toggleView;
        }
    }

    public void setOnExpandStateChangeListener(@Nullable OnExpandStateChangeListener listener) {
        mListener = listener;
    }

    public interface OnExpandStateChangeListener {
        /**
         * Called when the expand/collapse animation has been finished
         *
         * @param textView   - TextView being expanded/collapsed
         * @param isExpanded - true if the TextView has been expanded
         */
        void onExpandStateChanged(TextView textView, boolean isExpanded);
    }
}