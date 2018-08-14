package com.example.myappsecond.project.ctmviews.dellist;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;
import android.widget.Scroller;
import android.widget.TextView;

/**
 * Created by ZZG on 2017/12/1.
 */

public class OtherLinear extends LinearLayout {
    Scroller mScroller;
    private TextView text;
    public OtherLinear(Context context) {
        super(context);
    }

    public OtherLinear(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mScroller=new Scroller(context);

    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }
}
