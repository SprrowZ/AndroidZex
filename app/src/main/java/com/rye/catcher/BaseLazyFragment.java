package com.rye.catcher;

import android.os.Bundle;
import android.util.Log;

/**
 * Created By user
 * at 2019/7/18
 * Fragment 懒加载
 */
public abstract class BaseLazyFragment extends BaseFragment {
    private static final String TAG = BaseLazyFragment.class.getSimpleName();
    private boolean isPrepared=false;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initPrepare();
    }


    /**
     * 第一次onResume中的调用onVisible避免操作与onFirstVisible操作重复
     */
    private boolean isFirstResume = true;

    @Override
    public void onResume() {
        super.onResume();
        if (isFirstResume) {
            isFirstResume = false;
            return;
        }
        if (getUserVisibleHint()) {
            onVisible();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (getUserVisibleHint()) {
            onInvisible();
        }
    }

    private boolean isFirstVisible = true;
    private boolean isFirstInvisible = true;

    /**
     * 当fragment结合viewpager使用的时候 这个方法会调用
     * @param isVisibleToUser
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            if (isFirstVisible) {
                isFirstVisible = false;
                initPrepare();
            } else {
                onVisible();
            }
        } else {
            if (isFirstInvisible) {
                isFirstInvisible = false;
                onFirstInvisible();
            } else {
                onInvisible();
            }
        }
    }

    /**
     * 进行初始化操作
     */
    public synchronized void initPrepare() {
        if (isPrepared) {
            onFirstVisible();
        } else {
            isPrepared = true;
        }
    }

    /**
     * 第一次fragment可见（进行初始化工作）
     */
    public void onFirstVisible() {
        Log.i(TAG,"onFirstVisible..");
    }

    /**
     * fragment可见（切换回来或者onResume）
     */
    public void onVisible() {
        Log.i(TAG,"onVisible..");
    }

    /**
     * 第一次fragment不可见（不建议在此处理事件）
     */
    public void onFirstInvisible() {
        Log.i(TAG,"onFirstInvisible..");
    }

    /**
     * fragment不可见（切换掉或者onPause）
     */
    public void onInvisible() {
        Log.i(TAG,"onInvisible..");
    }
}
