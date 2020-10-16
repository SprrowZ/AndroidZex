package com.rye.catcher.activity;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rye.base.BaseActivity;

import com.rye.catcher.R;
import com.rye.catcher.activity.fragment.AIDLFragment;
import com.rye.catcher.activity.fragment.PackageManagerFragment;

import butterknife.BindView;

import butterknife.OnClick;

public class AIDLActivity extends BaseActivity {
    private Fragment currentFragment;
    private int currentPos=-1;
    @BindView(R.id.pm)
    TextView pm;
    @BindView(R.id.aidl)
    TextView aidl;
    @BindView(R.id.base_container)
    LinearLayout container;

    @Override
    public int getLayoutId() {
        return R.layout.activity_aidl;
    }

    @Override
    public void initEvent() {

    }


    @OnClick({R.id.pm,R.id.aidl})//一定要public
    public void onViewClicked(View view){
        switch (view.getId()){
            case R.id.pm:
             changeFragment(0);
                break;
            case R.id.aidl:
             changeFragment(1);
                break;
        }
    }

    /**
     * 切换fragment
     * @param pos
     */
    private  void changeFragment(int pos){
        if (pos==currentPos) return;
        FragmentManager   manager=getSupportFragmentManager();
        FragmentTransaction   transaction=manager.beginTransaction();
        if (currentFragment!=null){
             transaction.hide(currentFragment);
        }
        currentPos=pos;
        Fragment fragment=manager.findFragmentByTag(getTag(pos));
        if (fragment!=null){
            currentFragment=fragment;
            transaction.show(fragment);
        }else{
            transaction.add(R.id.base_container,getFragment(pos),getTag(pos));
        }
        transaction.commitAllowingStateLoss();
    }
    private String getTag(int pos){
        return "Zzg"+pos;
    }

    /**
     * 根据pos获取对应的Fragment
     * @param pos
     * @return
     */
    private Fragment getFragment(int pos){
        switch (pos){
            case 1:
                currentFragment=new AIDLFragment();
                break;
            default:
                currentFragment=new PackageManagerFragment();
                break;
        }
        return currentFragment;
    }

}
