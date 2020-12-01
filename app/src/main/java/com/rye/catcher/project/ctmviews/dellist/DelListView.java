package com.rye.catcher.project.ctmviews.dellist;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ListView;

/**
 * Created by ZZG on 2017/11/28.
 */
public class DelListView extends ListView {
    private ItemDelLinear mCurView;

    public DelListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                //我们想知道当前点击了哪一行
                int position = pointToPosition(x, y);
                if (position != INVALID_POSITION) {
                    DelAdapter.DataHolder data = (DelAdapter.DataHolder) getItemAtPosition(position);
                    mCurView = data.rootView;
                }
            }
            default:
                if (mCurView != null) {
                    mCurView.disPatchTouchEvent(event);
                }
                return super.onTouchEvent(event);
        }

    }

}