package com.example.myappsecond.activity.fragment;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myappsecond.R;
import com.example.myappsecond.utils.TypeFaceUtil;

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

//    @Override
//    public final View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//      //  getActivity().setTheme(ThemeManager.instance().getTheme());
//
//        View view = onCreateView2(inflater, container, savedInstanceState);
//        //为了解决Fragment点击穿透的问题
//        if (view != null) {
//            view.setOnTouchListener(this);
//        }
//        return view;
//    }

    /**
     * 真正的onCreateView
     */
//    protected abstract View onCreateView2(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

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
    }

//    public final void runOnUiThread(Runnable action) {
//        Activity activity = getActivity();
//        if (activity != null) {
//            activity.runOnUiThread(action);
//        } else {
//            if (EChatApp.getInstance() != null) {
//                EChatApp.getInstance().getGlobalHandler().post(action);
//            }
//        }
//
//    }

    /**
     * 显示装载中对话框
     *
     * @param msg 提示消息内容
     */
//    public void showLoadingDlg(final String msg) {
//        this.runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                synchronized (lock) {
//                    if (_loadingDlg == null) {
//                        _loadingDlg = new ProgressDialog(getActivity());
//                        _loadingDlg.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//                        _loadingDlg.setIcon(R.drawable.ajax_loader);
//                        _loadingDlg.setCancelable(true);
//                    }
//
//                    if (StringUtils.isNotEmpty(msg)) {
//                        _loadingDlg.setMessage(msg);
//                    } else {
//                        _loadingDlg.setMessage(getResources().getString(R.string.please_wait));
//                    }
//
//                    if (!_loadingDlg.isShowing()) {
//                        _loadingDlg.show();
//                    }
//                }
//            }
//        });
//    }

    /**
     * 隐藏加载中对话框
     */
//    public void cancelLoadingDlg() {
//        if (this.getActivity() == null || !this.isAdded()){
//            return;
//        }
//        try {
//            this.runOnUiThread(new Runnable() {
//                public void run() {
//                    if (_loadingDlg != null) {
//                        _loadingDlg.cancel();
//                    }
//                }
//            });
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return true;
    }
}
