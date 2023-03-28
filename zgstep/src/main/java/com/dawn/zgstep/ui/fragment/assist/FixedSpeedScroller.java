package com.dawn.zgstep.ui.fragment.assist;

import android.content.Context;
import android.view.animation.Interpolator;
import android.widget.Scroller;

/**
 * Create by  [Rye]
 * <p>
 * at 2023/3/28 17:13
 */
public class FixedSpeedScroller extends Scroller {
    private int mDuration = 1000;
    public FixedSpeedScroller(Context context) {
        super(context);
    }

    public FixedSpeedScroller(Context context, Interpolator interpolator) {
        super(context, interpolator);
    }

    public FixedSpeedScroller(Context context, Interpolator interpolator, boolean flywheel) {
        super(context, interpolator, flywheel);
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy) {
        super.startScroll(startX, startY, dx, dy);
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy, int duration) {
        super.startScroll(startX, startY, dx, dy, mDuration);
    }

    public void setDuration(int time) {
        mDuration = time;
    }
}
