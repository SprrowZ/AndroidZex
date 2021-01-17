package com.dawn.zgstep.ui.ctm.fragments;

import android.animation.ValueAnimator;
import android.util.Log;
import android.view.animation.LinearInterpolator;
import android.widget.TextView;

import com.dawn.zgstep.R;
import com.rye.base.BaseFragment;

/**
 * Create by rye
 * at 2020-09-08
 *
 * @description:
 */
public class CtmValueAnimatorFragment extends BaseFragment {
    private final String TAG = "CtmValueAnimator";
    ValueAnimator valueAnimator;
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_ctm_value_animator;
    }

    @Override
    public void initEvent() {
        super.initEvent();

        TextView tv = mRoot.findViewById(R.id.tv);

        valueAnimator = ValueAnimator.ofInt(0, 400);
        valueAnimator.setDuration(1000);
        valueAnimator.setRepeatMode(ValueAnimator.REVERSE);
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.addUpdateListener(animation -> {
            int result = (int) animation.getAnimatedValue();
            tv.setTranslationX(result);
        });

        valueAnimator.start();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (valueAnimator!=null){ //INFINITE的，结束要cancel，否则会导致内存泄露
            valueAnimator.cancel();
        }
    }
}
