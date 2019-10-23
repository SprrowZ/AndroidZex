package com.rye.catcher.common.flowlayout;

import android.content.Context;
import android.content.res.TypedArray;
import androidx.core.content.ContextCompat;
import androidx.core.text.TextUtilsCompat;
import android.util.AttributeSet;
import android.util.LayoutDirection;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rye.catcher.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;


public class MyFlowLayout extends ViewGroup {
    private static final int LEFT = -1;
    private static final int CENTER = 0;
    private static final int RIGHT = 1;

    protected List<List<View>> mAllViews = new ArrayList<List<View>>();
    protected List<Integer> mLineHeight = new ArrayList<Integer>();
    protected List<Integer> mLineWidth = new ArrayList<Integer>();
    private int mGravity;
    private List<View> lineViews = new ArrayList<>();

    private OnItemClickListener onItemClickListener;

    public MyFlowLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.IDY_TagFlowLayout);
        mGravity = ta.getInt(R.styleable.IDY_TagFlowLayout_tag_gravity, LEFT);
        int layoutDirection = TextUtilsCompat.getLayoutDirectionFromLocale(Locale.getDefault());
        if (layoutDirection == LayoutDirection.RTL) {
            if (mGravity == LEFT) {
                mGravity = RIGHT;
            } else {
                mGravity = LEFT;
            }
        }
        ta.recycle();
    }

    public MyFlowLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyFlowLayout(Context context) {
        this(context, null);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // 获取父容器对布局的期望宽高 ，根据期望宽高 分别获取长宽的模式和值
        // widthMeasureSpec 其实是一个32位的数 其中前两位代表模式 ，后30位代表具体的值
        // 模式分为3种
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
        int modeHeight = MeasureSpec.getMode(heightMeasureSpec);

        // wrap_content
        int width = 0;
        int height = 0;

        int lineWidth = 0;
        int lineHeight = 0;

        int cCount = getChildCount();

        for (int i = 0; i < cCount; i++) {
            View child = getChildAt(i);
            if (child.getVisibility() == View.GONE) {
                if (i == cCount - 1) {
                    width = Math.max(lineWidth, width);
                    height += lineHeight;
                }
                continue;
            }
            // 测量子View
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
            MarginLayoutParams lp = (MarginLayoutParams) child
                    .getLayoutParams();

            int childWidth = child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
            int childHeight = child.getMeasuredHeight() + lp.topMargin + lp.bottomMargin;

            if (lineWidth + childWidth > sizeWidth - getPaddingLeft() - getPaddingRight()) {
                width = Math.max(width, lineWidth);
                lineWidth = childWidth;
                height += lineHeight;
                lineHeight = childHeight;
            } else {
                lineWidth += childWidth;
                lineHeight = Math.max(lineHeight, childHeight);
            }
            if (i == cCount - 1) {
                width = Math.max(lineWidth, width);
                height += lineHeight;
            }
        }

        // 保存FlowLaout的width和height ，当为match_parent 以及指定大小是 mode 都为EXACTLY
        setMeasuredDimension(
                //
                modeWidth == MeasureSpec.EXACTLY ? sizeWidth : width + getPaddingLeft() + getPaddingRight(),
                modeHeight == MeasureSpec.EXACTLY ? sizeHeight : height + getPaddingTop() + getPaddingBottom()//
        );
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        mAllViews.clear();
        mLineHeight.clear();
        mLineWidth.clear();
        lineViews.clear();

        int width = getWidth();

        int lineWidth = 0;
        int lineHeight = 0;

        int cCount = getChildCount();

        for (int i = 0; i < cCount; i++) {
            View child = getChildAt(i);
            if (child.getVisibility() == View.GONE) {
                continue;
            }
            MarginLayoutParams lp = (MarginLayoutParams) child
                    .getLayoutParams();

            int childWidth = child.getMeasuredWidth();
            int childHeight = child.getMeasuredHeight();

            if (childWidth + lineWidth + lp.leftMargin + lp.rightMargin > width - getPaddingLeft() - getPaddingRight()) {
                mLineHeight.add(lineHeight);
                mAllViews.add(lineViews);
                mLineWidth.add(lineWidth);

                lineWidth = 0;
                lineHeight = childHeight + lp.topMargin + lp.bottomMargin;
                lineViews = new ArrayList<View>();
            }
            lineWidth += childWidth + lp.leftMargin + lp.rightMargin;
            lineHeight = Math.max(lineHeight, childHeight + lp.topMargin
                    + lp.bottomMargin);
            lineViews.add(child);

        }
        mLineHeight.add(lineHeight);
        mLineWidth.add(lineWidth);
        mAllViews.add(lineViews);


        int left = getPaddingLeft();
        int top = getPaddingTop();

        int lineNum = mAllViews.size();

        for (int i = 0; i < lineNum; i++) {
            lineViews = mAllViews.get(i);
            lineHeight = mLineHeight.get(i);

            // set gravity
            int currentLineWidth = this.mLineWidth.get(i);
            switch (this.mGravity) {
                case LEFT:
                    left = getPaddingLeft();
                    break;
                case CENTER:
                    left = (width - currentLineWidth) / 2 + getPaddingLeft();
                    break;
                case RIGHT:
                    //  适配了rtl，需要补偿一个padding值
                    left = width - (currentLineWidth + getPaddingLeft()) - getPaddingRight();
                    //  适配了rtl，需要把lineViews里面的数组倒序排
                    Collections.reverse(lineViews);
                    break;
            }

            for (int j = 0; j < lineViews.size(); j++) {
                View child = lineViews.get(j);
                if (child.getVisibility() == View.GONE) {
                    continue;
                }

                MarginLayoutParams lp = (MarginLayoutParams) child
                        .getLayoutParams();

                int lc = left + lp.leftMargin;
                int tc = top + lp.topMargin;
                int rc = lc + child.getMeasuredWidth();
                int bc = tc + child.getMeasuredHeight();

                child.layout(lc, tc, rc, bc);

                left += child.getMeasuredWidth() + lp.leftMargin
                        + lp.rightMargin;
            }
            top += lineHeight;
        }

    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new MarginLayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
    }

    @Override
    protected LayoutParams generateLayoutParams(LayoutParams p) {
        return new MarginLayoutParams(p);
    }



    public void setItems(@NotNull ArrayList<HotKeyWordBean> hotwords) {
        removeAllViews();
        mAllViews.clear();
        for (int i = 0; i < hotwords.size(); i++) {
            TextView tv = new TextView(getContext());
            HotKeyWordBean word = hotwords.get(i);
            tv.setGravity(Gravity.CENTER);
            tv.setText(word.getKeyword());

            LinearLayout.LayoutParams params =
                    new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(10, 10, 10, 10);
            tv.setLayoutParams(params);

            if (word.is_highlight()) {
                tv.setTextColor(ContextCompat.getColor(this.getContext(), R.color.idy_search_tag_text_yellow));
                tv.setBackgroundResource(R.drawable.idy_myflow_layout_item_bg_yellow);
            } else {
                tv.setTextColor(ContextCompat.getColor(this.getContext(), R.color.black));
                tv.setBackgroundResource(R.drawable.idy_myflow_layout_item_bg_normal);
            }

            final int retint = i;
            final String retstring = word.getKeyword();
            tv.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(retint, retstring);
                }
            });
            addView(tv);
        }
    }

    public void setItems(List<String> hotwords) {
        removeAllViews();
        mAllViews.clear();
        for (int i = 0; i < hotwords.size(); i++) {
            TextView tv = new TextView(getContext());
            String word = hotwords.get(i);
            if (word.contains("_")) {
                word = word.substring(0, word.lastIndexOf("_"));
            }
            String[] strings = hotwords.get(i).split("_");
            String rank = strings[strings.length - 1];
            tv.setGravity(Gravity.CENTER);
            tv.setText(word);

            LinearLayout.LayoutParams params =
                    new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(10, 10, 10, 10);
            tv.setLayoutParams(params);

            if (rank.equals("-1")) {
                tv.setTextColor(ContextCompat.getColor(this.getContext(), R.color.idy_search_tag_text_yellow));
                tv.setBackgroundResource(R.drawable.idy_myflow_layout_item_bg_yellow);
            } else {
                tv.setTextColor(ContextCompat.getColor(this.getContext(), R.color.black));
                tv.setBackgroundResource(R.drawable.idy_myflow_layout_item_bg_normal);
            }

            final int retint = i;
            final String retstring = word;
            tv.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(retint, retstring);
                }
            });
            addView(tv);
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position, String txt);
    }
}
