package com.rye.catcher;




import android.content.Intent;

import android.graphics.Typeface;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;

import android.view.View;

import android.view.ViewGroup;
import android.widget.TextView;


import com.rye.catcher.utils.PermissionsUtil;
import com.rye.base.utils.TypeFaceUtil;



import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by zzg
 */
public abstract class BaseOldFragment extends Fragment  {
   private  Unbinder unbinder;
   private View rootView;
   protected abstract  int getLayoutResId();
   protected abstract  void initData( );


    public  View getView(){//空指针？
       return rootView;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (getLayoutResId()!=0){
            rootView=inflater.inflate(getLayoutResId(),container,false);
            return rootView;
        }else {
            return super.onCreateView(inflater, container, savedInstanceState);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder= ButterKnife.bind(this,view);
        initData();
        initEvent();
    }

    protected  void initEvent(){

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        //LeakCanery检测内存泄露
//        RefWatcher refWatcher = LeakCanary.installedRefWatcher();
//        // We expect schrodingerCat to be gone soon (or not), let's watch it.
//        refWatcher.watch(this);
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



    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        getActivity().overridePendingTransition(R.anim.translate_to_left, R.anim.translate_to_left_hide);//activity间跳转动画
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //就多一个参数this
        PermissionsUtil.INSTANCE.onRequestPermissionsResult(getActivity(), requestCode, permissions, grantResults);
    }
}
