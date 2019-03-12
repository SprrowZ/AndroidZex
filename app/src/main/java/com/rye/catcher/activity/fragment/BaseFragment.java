package com.rye.catcher.activity.fragment;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.rye.catcher.R;
import com.rye.catcher.utils.DialogUtil;
import com.rye.catcher.utils.PermissionsUtil;
import com.rye.catcher.utils.TypeFaceUtil;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by jinyunyang on 15/4/13.
 */
public abstract class BaseFragment extends Fragment implements View.OnTouchListener {
    private ProgressDialog _loadingDlg = null;
    protected final Object lock = new Object();

    private Map<BroadcastReceiver, Integer> receiverMap = new ConcurrentHashMap<>();

    private int i=0;
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden){
            if (i==0){
                i++;
                //调用初始化方法
                initXmlView();
            }
        }
    }
    /**
     * 此方法在第一次fragment被显示时调用；避免在onactivitycreated时做耗时操作，导致页面黑屏
     * */
    public void initXmlView(){

    }





    /**
     * 设置标题栏标题
     */
    public void setBarTitle(String title) {
        if(null == getView()){
            return;
        }
        TextView view = (TextView) getView().findViewById(R.id.title);
       Typeface tf= TypeFaceUtil.getInstance().tf(getActivity());
        if (view != null) {
            view.setText(title);
            view.setTypeface(tf);
        }
    }

    public void setBarTitle(int resId) {
        if(null == getView()){
            return;
        }
        TextView view = (TextView) getView().findViewById(R.id.title);
        if (view != null) {
            view.setText(resId);
        }
    }

    /**
     * 设置标题栏右侧文本
     */
    public void setBarRightText(String rightText, View.OnClickListener listener) {
        if(null == getView()){
            return;
        }
        TextView view = (TextView) getView().findViewById(R.id.right_text);
        if (view != null) {
            view.setText(rightText);
            view.setVisibility(View.VISIBLE);

            if (listener != null) {
                view.setOnClickListener(listener);
            }
        }
    }

    public void setBarRightText(int resId, View.OnClickListener listener) {
        if(null == getView()){
            return;
        }
        TextView view = (TextView) getView().findViewById(R.id.right_text);
        if (view != null) {
            view.setText(resId);
            view.setVisibility(View.VISIBLE);

            if (listener != null) {
                view.setOnClickListener(listener);
            }
        }
    }

    public void hideBarBackBtn() {
        View back_parent = getView().findViewById(R.id.back_parent);
        if (back_parent != null) {
            back_parent.setVisibility(View.GONE);
        }
    }


    public void setBarleftText(String text, View.OnClickListener listener) {
        TextView left_text = (TextView) getView().findViewById(R.id.left_text);
        if (left_text != null) {
            left_text.setText(text);
            left_text.setVisibility(View.VISIBLE);
            if (listener != null) {
                left_text.setOnClickListener(listener);
            }
        }
    }

    /**
     * 隐藏标题栏左侧按钮
     */

    public void cancelLeftBar() {
        TextView left_text = (TextView) getView().findViewById(R.id.left_text);
        if (left_text != null) {
            left_text.setVisibility(View.GONE);
        }
    }

    //把左侧返回按钮替换为自定义的按钮
    public void setBarLeftDrawable(Drawable drawable, View.OnClickListener listener) {
        View back_parent = getView().findViewById(R.id.back_parent);
        if (back_parent == null) {
            return;
        }
        ImageView view = (ImageView) back_parent.findViewById(R.id.back);
        TextView tv = (TextView) back_parent.findViewById(R.id.tv_topbtntitle);
        if (view == null) {
            return;
        }
        view.setOnClickListener(null);
        tv.setOnClickListener(null);
        back_parent.setVisibility(View.VISIBLE);
        view.setVisibility(View.VISIBLE);
        view.setImageDrawable(drawable);
        if (listener != null) {
            back_parent.setOnClickListener(listener);
        }
    }

    //把左侧返回按钮替换为自定义的按钮
    public void setBarLeftDrawable(int resId, View.OnClickListener listener) {
        View back_parent = getView().findViewById(R.id.back_parent);
        if (back_parent == null) {
            return;
        }
        ImageView view = (ImageView) back_parent.findViewById(R.id.back);
        TextView tv = (TextView) back_parent.findViewById(R.id.tv_topbtntitle);
        if (view == null) {
            return;
        }
        view.setOnClickListener(null);
        tv.setOnClickListener(null);
        back_parent.setVisibility(View.VISIBLE);
        view.setVisibility(View.VISIBLE);
        view.setImageResource(resId);
        if (listener != null) {
            view.setOnClickListener(listener);
            back_parent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }

    /**
     * 设置标题栏右侧图片
     */
    public void setBarRightDrawable(Drawable drawable, View.OnClickListener listener) {
        ImageView view = (ImageView) getView().findViewById(R.id.right_image);
        if (view != null) {
            view.setImageDrawable(drawable);
            view.setVisibility(View.VISIBLE);

            if (listener != null) {
                view.setOnClickListener(listener);
            }
        }
    }

    public void setBarRightDrawable(int resId, View.OnClickListener listener) {
        //对“+”  图片设置点击事件
        ImageView view = (ImageView)getView().findViewById(R.id.right_image);
        if (view != null) {
            view.setImageResource(resId);
            view.setVisibility(View.VISIBLE);

            if (listener != null) {
                view.setOnClickListener(listener);
            }
        }
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);

        getActivity().overridePendingTransition(R.anim.translate_to_left, R.anim.translate_to_left_hide);//activity间跳转动画
    }

    protected void registerReceiver(String action, BroadcastReceiver receiver) {
        IntentFilter newFriendsFilter = new IntentFilter();
        newFriendsFilter.addAction(action);
        getActivity().registerReceiver(receiver, newFriendsFilter);
        receiverMap.put(receiver, 1);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // 取消注册的广播监听类
        for (BroadcastReceiver br : receiverMap.keySet()) {
            try {
                getActivity().unregisterReceiver(br);
            } catch (Exception ignored) {

            }
        }
        receiverMap.clear();

        //LeakCanery检测内存泄露
        RefWatcher refWatcher = LeakCanary.installedRefWatcher();

// We expect schrodingerCat to be gone soon (or not), let's watch it.
        refWatcher.watch(this);
    }



    /**
     * Loading Dialog
     * @param context
     */
    protected void showLoadingDlg(Context context){
        DialogUtil.createLoadingDialog(context);
    }

    protected  void cancelLoadingDlg(Context context){
        DialogUtil.closeLoadingDialog(context);
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //就多一个参数this
        PermissionsUtil.getInstance().onRequestPermissionsResult(getActivity(), requestCode, permissions, grantResults);
    }
}
