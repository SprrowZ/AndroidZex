package com.dawn.zgstep.ui.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.TextView;

import com.dawn.zgstep.R;
import com.dawn.zgstep.ui.fragment.assist.CustomPageTransformer;
import com.dawn.zgstep.ui.fragment.assist.FixedSpeedScroller;
import com.dawn.zgstep.ui.fragment.assist.LoopViewPagerAdapter;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StackViewFragment#newInstance} factory method to
 * create an instance of this fragment.
 * 参考文章：
 * https://www.jianshu.com/p/4c474ca6c098
 * https://blog.51cto.com/u_14397532/5765178
 * https://juejin.cn/post/6955684084240613384
 *https://github.com/AlpsDog/Banner
 */
public class StackViewFragment extends Fragment {
    private TextView  mStartLoop;
    private ViewPager mViewPager;
    private View mRoot;
    private List<Integer> mData = new ArrayList<>();

    private final int LOOP_INTERVAL = 3000;
    private final int MSG_START = 11;
    private LoopViewPagerAdapter mViewPagerAdapter;
    private CustomPageTransformer mPageTransformer;


    public StackViewFragment() {
    }


    public static StackViewFragment newInstance() {
        StackViewFragment fragment = new StackViewFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_START:
                    if (mViewPager==null || mViewPagerAdapter==null) return;
                    int currentItemIndex = mViewPager.getCurrentItem();
                    int itemsCount = mViewPagerAdapter.getCount();
                    int currentIndex = (currentItemIndex+1);

                    Log.i("RRye","itemCount:"+itemsCount+",currentIndex:"+currentIndex);
                    if (currentIndex <itemsCount) {
                       mViewPager.setCurrentItem(currentIndex,true);
                    } else {
                        mViewPager.setCurrentItem(0,false);
                    }
                    break;
            }
        }
    };

    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            Message message = mHandler.obtainMessage();
            message.what = MSG_START;
            mHandler.sendMessage(message);
            mHandler.removeCallbacks(mRunnable);
            mHandler.postDelayed(this, LOOP_INTERVAL);
        }
    };


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mRoot = inflater.inflate(R.layout.fragment_stack_view, container, false);
        initViews();
        initEvents();
        return mRoot;
    }
    private void initViews() {
        mStartLoop = mRoot.findViewById(R.id.start_loop);
        mViewPager = mRoot.findViewById(R.id.loop_view);
        mViewPagerAdapter = new LoopViewPagerAdapter(getContext());
        mPageTransformer = new CustomPageTransformer();

    }
    private void initEvents() {
        mData = Arrays.asList(R.drawable.my_new1,R.drawable.my_new2,R.drawable.my_new3,R.drawable.my_new4);
        mViewPagerAdapter.setData(mData);

        mViewPager.setAdapter(mViewPagerAdapter);
        mViewPager.setCurrentItem(0);
        mViewPager.setOffscreenPageLimit(3);
        mViewPager.setPageTransformer(true,mPageTransformer);
        setViewPagerScrollSpeed();
        setAutoChange();
    }
    private void setAutoChange() {
        mHandler.postDelayed(mRunnable,LOOP_INTERVAL);
    }

    private void setViewPagerScrollSpeed( ){
        try {
            Field mScroller = null;
            mScroller = ViewPager.class.getDeclaredField("mScroller");
            mScroller.setAccessible(true);
            FixedSpeedScroller scroller = new FixedSpeedScroller(mViewPager.getContext(),new AccelerateDecelerateInterpolator());
            scroller.setDuration(800);
            mScroller.set(mViewPager, scroller);
        }catch(NoSuchFieldException | IllegalAccessException | IllegalArgumentException e){

        }
    }


    private void startAutoScroll() {
        cancelAutoScroll();
        mHandler.postDelayed(mRunnable,LOOP_INTERVAL);
    }
    private void cancelAutoScroll() {
        mHandler.removeCallbacksAndMessages(null);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mViewPager!=null) {
            mViewPager.removeAllViews();
            mViewPager = null;
            mHandler.removeCallbacksAndMessages(null);
            mHandler = null;
        }
    }
}